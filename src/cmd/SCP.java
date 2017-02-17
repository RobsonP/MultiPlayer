package cmd;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import control.Conversion;
import control.Main_controls;
import data.MyUserInfo;
import gui.MessageThread;
import instance.Instance_data;
import instance.Instance_hold;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SCP extends Thread{ 
    private File f;
    private SCP_OutProgressMonitor monitor;
    private String filename, flnm;
    private String[] args;
    private FileOutputStream fos;
    private int i, old_pl_length, anzgel;
    private boolean hasdownloaded = false, promptedyet = false;
    private ChannelSftp channel;
    private Session session;
    
    /**
     * starts the Buffer-Segment of the program, which is waiting
     * for added Items into playlist. It begins downloading after a double-click
     * on an playlist-item. After that it begins downloading data from
     * ssh-server in the moment, the user adds an Item to playlist.
     * If the user jumps to a file, which is available(completely downloaded
     * or still downloading), the Thread jumps to the File and starts buffering.
     * After it buffered enough data, it gives the Play-Segment the signal
     * to start playing media.
     * @return void
     */
    
    public SCP() {
        args = new String[2];
        old_pl_length = -1;
        anzgel = 0;
    }
    
    @Override
  public void run(){
    Instance_data.decrementExitedthreads();
    i = Instance_data.getDlindx();
    System.out.println("start");
    
    do {    
        try{  
            filename = Instance_hold.getPl().getEntries().get(i).split("/")[Instance_hold.getPl().getEntries().get(i).split("/").length-1];
            flnm = filename.replace("\\", "");
            f = new File(Instance_data.getTmpPath() + "\\" + flnm);

            hasdownloaded = false;

            if (f.exists()) {
                Instance_hold.getSCPFrom_Monitor().setRdytoplay(true);
                System.out.println("SCPFrom: I AM READY!!!");
            }
            if (!f.exists()) {
                hasdownloaded = true;
            try {
                f.createNewFile();
            }catch (IOException ex) {

            }
            
            System.out.println("PLENTRY: " + Instance_data.getHost() + ":" + Instance_hold.getPl().getEntries().get(i));
            args[0] = Instance_data.getHost() + ":" + Instance_hold.getPl().getEntries().get(i);
            args[1] = Instance_data.getTmpPath() + "\\" + flnm;
            Instance_data.setCpystr(args);
            
            System.out.println("ARGS: " + args[0]);
            System.out.println("ARGS: " + args[1]);

            if(Instance_data.getCpystr().length!=2){
                System.err.println("usage: java ScpFrom user@remotehost:file1 file2");
                throw new Exception();
            }

            Instance_hold.getSCPFrom_Monitor().setCurrentdl(f);

            fos=null;
            
            String user=Instance_data.getCpystr()[0].substring(0, Instance_data.getCpystr()[0].indexOf('@'));
            Instance_data.getCpystr()[0]=Instance_data.getCpystr()[0].substring(Instance_data.getCpystr()[0].indexOf('@')+1);
            String host=Instance_data.getCpystr()[0].substring(0, Instance_data.getCpystr()[0].indexOf(':'));
            String rfile=Instance_data.getCpystr()[0].substring(Instance_data.getCpystr()[0].indexOf(':')+1);
            String lfile=Instance_data.getCpystr()[1];

            JSch jsch=new JSch();
            java.util.Properties configuration = new java.util.Properties();
            configuration.put("kex", "diffie-hellman-group1-sha1,diffie-hellman-group14-sha1,diffie-hellman-group-exchange-sha1,diffie-hellman-group-exchange-sha256");
            configuration.put("StrictHostKeyChecking", "no");
            
            session=jsch.getSession(user, host, Instance_data.getPort());
            
            if (!Instance_data.getRsakeyPath().equals("")) {
                byte[] privatek = Conversion.getkeyfromFile(Instance_data.getRsakeyPath());
                byte[] publick = Conversion.getkeyfromFile(Instance_data.getRsakeyPath() + ".pub");
                
                try {
                    jsch.addIdentity(Instance_data.getUname(), privatek, publick , Instance_data.getRsakeypw().getBytes());
                }catch (Exception exc) {
                      
                }
            }

            UserInfo ui;
            ui = new MyUserInfo(Instance_data.getPw());

            session.setUserInfo(ui);
            session.setConfig(configuration);
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            Instance_data.setChannel(channel);

            channel.connect();            

            String[] splittedrfile = rfile.split("/");
            StringBuffer dir = new StringBuffer("");
              
            for (int j=0;j<splittedrfile.length-1;j++) {
                dir = dir.append(splittedrfile[j]).append("/");
            }

            try {
                channel.cd(dir.toString());
            }catch (Exception exc) {
                System.out.println("SCPFrom: Exception");
            }
  
            Vector<String> cmds = new Vector<String>();
            cmds.add("get");
            cmds.add(splittedrfile[splittedrfile.length-1]);
            cmds.add(lfile);

            String cmd=(String)cmds.elementAt(0);
            String p1=(String)cmds.elementAt(1);

            System.out.println("p1: " + p1);

            String p2=".";
            
            if(cmds.size()==3)p2=(String)cmds.elementAt(2);
            try{
                monitor = new SCP_OutProgressMonitor();

                if(cmd.startsWith("get")){
                    int mode=ChannelSftp.OVERWRITE;
                    switch (cmd) {
                        case "get-resume":
                            mode=ChannelSftp.RESUME;
                            break;
                        case "get-append":
                            mode=ChannelSftp.APPEND;
                            break;
                    }
                    
                    channel.get(p1, p2, monitor, mode);
                }
            }catch(Exception e) {
                System.out.println("SCPFrom: Exception");
                e.printStackTrace();
                
                Instance_hold.setMthread(new MessageThread("Network Error", MessageThread.TYPE_ERROR));
                Instance_hold.getMthread().start();
                
                DownThread dt = new DownThread();
                dt.start();
                
                break;
            }
            
            System.out.println("SCP after get");
            
            if (Instance_hold.getSCPFrom_Monitor().isInterrupted()) {
                System.out.println("SCP is interrupted");
                
                throw new Exception();
            }
            
            session.disconnect();
            
        }

            i++;
        }catch(Exception e) {            
            System.out.println("SCP: EXCEPTION");
            System.out.println(e.toString());
            if (!e.toString().equals("java.lang.Exception") && !e.getMessage().equals("verify: false")) {
                System.out.println("PULLING DOWN");

                try {
                    Instance_hold.setMthread(new MessageThread(e.toString().split(":")[2], MessageThread.TYPE_ERROR));
                    Instance_hold.getMthread().start();
                }catch (ArrayIndexOutOfBoundsException exc) {
                    Instance_hold.setMthread(new MessageThread("Error while downloading", MessageThread.TYPE_ERROR));
                    Instance_hold.getMthread().start();
                }
                
                try {
                    channel.disconnect();
                }catch(Exception exc) {
                    
                }
                try {
                    session.disconnect();
                }catch(Exception exc) {
                    
                }

                DownThread dt = new DownThread();
                dt.start();

                break;
            }

            try {
                if(fos!=null)fos.close();
            }catch(Exception ee){}
          
            Instance_hold.getSCPFrom_Monitor().setClosedlmonitor(true);
          
            if (Instance_hold.getSCPFrom_Monitor().isExit() || Instance_hold.getSCPFrom_Monitor().isNewsession() || Instance_hold.getSCPFrom_Monitor().isPlremove()) {
                Instance_hold.getVplay_mon().setExit(true);
                Instance_hold.getVplay_mon().setIrruptflag(1);
                /*
                try {
                    Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
                    Instance_hold.getFsf().getEmpc().getMediaPlayer().stop();
                    //Instance_hold.getPlayframe().getEmpc().getMediaPlayer().release();
                    //Instance_hold.getFsf().getEmpc().getMediaPlayer().release();
                }catch(NullPointerException exc) {
            
                }
                */
                do {
                    System.out.println("SCP: VPlay is waiting to exit");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main_controls.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }while (Instance_hold.getVplay().isAlive());
                Instance_hold.getVplay_mon().setExit(false);
                Instance_hold.getVplay_mon().setIrruptflag(0);

                    System.out.println("DELETING");
                    if (f.exists()) f.delete();

                    try {
                        if (channel.isConnected()) channel.disconnect();
                    }catch(NullPointerException exc) {

                    }
                    try {
                        if (session.isConnected()) session.disconnect();
                    }catch(NullPointerException exc) {

                    }
                    break;
                }
          
          
            if (hasdownloaded) {    
                File f_delete = new File(Instance_data.getTmpPath() + "\\" + flnm);

                while(!f_delete.delete())System.out.println("DELETING");

                System.out.println("FILE DELETED");
            }

            i = Instance_data.getDlindx();

            hasdownloaded = false;
        }

        System.out.println("DOWNLOAD FINISHED: " + Instance_hold.getSCPFrom_Monitor().isDlfinish());
        System.out.println("HASDOWNLOADED: " + hasdownloaded);
        System.out.println("LENGTH OF CREATED FILE: " +f.length());
        System.out.println("i: " + i + "  --   OLDLENGTH: " + old_pl_length);
        System.out.println("i: " + i + "  --   PLSIZE-1: " + (Instance_hold.getPl().getEntries().size()-1));
        if (hasdownloaded && f.length()>0) {
            anzgel++;
            hasdownloaded = false;
        }else if (hasdownloaded && !Instance_hold.getSCPFrom_Monitor().isDlfinish() && i != old_pl_length && i != Instance_hold.getPl().getEntries().size()-1){

            System.out.println("SCPFrom: DELETING FILE: " + f.delete());
            hasdownloaded = false;
        }else hasdownloaded = false;

        if (anzgel == Instance_hold.getPl().getEntries().size()) {
            old_pl_length  = Instance_hold.getPl().getEntries().size();
            Instance_hold.getSCPFrom_Monitor().setDlfinish(true);
        }
    }while(i<Instance_hold.getPl().getEntries().size());

    System.out.println("SCPFROM: I AM EXITED");  
  }

    public ChannelSftp getChannel() {
        return channel;
    }

    /**
     * checks, if data is available and readable
     * @param in
     * @return int
     * @throws IOException 
     */

    public boolean isPromptedyet() {
        return promptedyet;
    }

    public void setPromptedyet(boolean promptedyet) {
        this.promptedyet = promptedyet;
    }

    public File getF() {
        return f;
    }

    public void setF(File f) {
        this.f = f;
    }

    public SCP_OutProgressMonitor getMonitor() {
        return monitor;
    }


}