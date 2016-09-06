/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cmd;

import control.Conversion;
import com.jcraft.jsch.*;
import data.MyUserInfo;
import instance.Instance_data;
import instance.Instance_hold;
import gui.MessageThread;
import gui.PasswordDiag;
import gui.PasswordDiagRSA;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.*;

public class Shell extends Thread{
    private ChannelSftp channel;
    private boolean passwordcheck;
    private ByteArrayOutputStream out;
    
    
    public Shell() {
        super();
    }
    
    /**
     * starts the Shell-Segment of the program, which is waiting for a signal to
     * start the Listener for Reading data(in this case file- and folderstructures).
     */
    @Override
    public void run() {
        passwordcheck = true;
        out = new ByteArrayOutputStream();
        Instance_data.decrementExitedthreads();
        
        while (!Instance_hold.getSh_mon().isExit() && passwordcheck) {
            if (Instance_hold.getSh_mon().isStartshell() && !Instance_hold.getSh_mon().isConnectError()) {
                if (!Instance_data.getHost().startsWith("@"))
                    connect(Instance_data.getHost(), Instance_data.getPort(), Instance_data.getRsakeyPath());
                else {
                    Instance_hold.setMthread(new MessageThread("No User Data!!!",MessageThread.TYPE_ERROR));
                    Instance_hold.getMthread().start();
                    Instance_hold.getSh_mon().setConnectError(true);
                }
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Instance_hold.getSh_mon().setExit(false);
        Instance_hold.getSh_mon().setStartshell(false);
        Instance_hold.getSh_mon().setWrite_output(false);
        
        if (!passwordcheck) Instance_hold.getSh_mon().setConnectError(true);
        
    }
    
    
    @SuppressWarnings("empty-statement")
    
  public void connect(String host, int port, String rsakeyPath){
      
      System.out.println("RSAKEYP: " + rsakeyPath);
        try {
            File rsakey = new File(rsakeyPath);
        }catch (NullPointerException exc) {
            return;
        }
    try{
        JSch jsch=new JSch();
        Instance_data.setJsch(jsch);

        String hostnm=null;
        if(!host.equals("")){
            hostnm=host;
        }else {
            hostnm=JOptionPane.showInputDialog("Enter username@hostname",
            System.getProperty("user.name") + "@localhost");
        }
        
        String usernm=hostnm.substring(0, hostnm.indexOf('@'));
        hostnm=hostnm.substring(hostnm.indexOf('@')+1);

        System.out.println("username: " + usernm);
        System.out.println("hostnm: " + hostnm);

        Instance_data.setUname(usernm);
        Instance_data.setHostnm(hostnm);
        Instance_data.setPort(port);
        Session session=jsch.getSession(usernm, hostnm, port);
        Instance_data.setSession(session);

        if (!rsakeyPath.equals("EMPTY")) {
            byte[] privatek = Conversion.getkeyfromFile(Instance_data.getRsakeyPath());
            byte[] publick = Conversion.getkeyfromFile(Instance_data.getRsakeyPath() + ".pub");

            Instance_hold.setPframeRSA(new PasswordDiagRSA());
            Instance_hold.getPframeRSA().setUname(usernm);
            Instance_hold.getPframeRSA().setJsch(jsch);
            Instance_hold.getPframeRSA().setPublick(publick);
            Instance_hold.getPframeRSA().setPrivatek(privatek);

            Instance_hold.getPframeRSA().setVisible(true);

            while(Instance_hold.getPframeRSA().isVisible()) Thread.sleep(100);

        }else {
            Instance_hold.setPframe(new PasswordDiag());
            Instance_hold.getPframe().setVisible(true);
            while(Instance_hold.getPframe().isVisible()) Thread.sleep(100);          
        }

        if (passwordcheck) {
            System.out.println("Passwordcheck: " + passwordcheck);
            UserInfo ui = new MyUserInfo(Instance_data.getPw()) {
            
            @Override
            public void showMessage(String message) {
                JOptionPane.showMessageDialog(null, message);
            }
            
            @Override
            public boolean promptYesNo(String message) {
                Object[] options={ "yes", "no" };
                int foo=JOptionPane.showOptionDialog(null,
                                                     message,
                                                     "Warning",
                                                     JOptionPane.DEFAULT_OPTION,
                                                     JOptionPane.WARNING_MESSAGE,
                                                     null, options, options[0]);
                return foo==0;
            }


            // If password is not given before the invocation of Session#connect(),
            // implement also following methods,
            // * UserInfo#getPassword(),
            // * UserInfo#promptPassword(String message) and
            // * UIKeyboardInteractive#promptKeyboardInteractive()

            };
            
            Instance_data.setUi(ui);
            Instance_hold.getSh_mon().setRdytolisten(true);
            Instance_hold.getLm().setError(false);
            
            if (!Instance_hold.getListen().isAlive()){
                Instance_hold.setListen(new Listen());
                Instance_hold.getListen().start();
            }
        
            do {
                if (Instance_hold.getSh_mon().isWrite_output()) {
                    if (!session.isConnected()) {
                        session = jsch.getSession(usernm, hostnm, port);
                        session.setUserInfo(ui);
                        session.connect(1000);
                    }

                    channel = (ChannelSftp)session.openChannel("sftp");


                    while (!session.isConnected()) {
                        Thread.sleep(100);
                    }

                    pushcommand();
                    Instance_hold.getSh_mon().setWrite_output(false);
                    channel.disconnect();
                }

                if (Instance_hold.getSh_mon().isLoading()) {
                    Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(false);
                    Instance_hold.getSh_mon().setLoading(false);
                }
                
                if (Instance_hold.getSh_mon().isExit()) {
                    break;
                }

                Thread.sleep(100);
            }while(true);
            
            Instance_data.incrementExitedthreads();
        } 
    }
    catch(JSchException | IOException | InterruptedException e){
        System.out.println("Shell Exception");
        System.out.println(e.toString());
        try {
            Instance_hold.setMthread(new MessageThread(e.toString().split(":")[2], MessageThread.TYPE_ERROR));
            Instance_hold.getMthread().start();
        }catch (ArrayIndexOutOfBoundsException exc) {
            try {
                Instance_hold.setMthread(new MessageThread(e.toString().split(":")[1], MessageThread.TYPE_ERROR));
                Instance_hold.getMthread().start();
            }catch (ArrayIndexOutOfBoundsException ex) {
                Instance_hold.setMthread(new MessageThread("Error while connecting", MessageThread.TYPE_ERROR));
                Instance_hold.getMthread().start();
            }
        }
        
        Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(false);

        System.out.println("DOWN THREAD STARTED");
        if (Instance_hold.getCthread().isAlive()) {
            Instance_hold.getSh_mon().setConnectError(true);

            while (Instance_hold.getCthread().isAlive()) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        DownThread dt = new DownThread();
        dt.start();
        
    }
    
    System.out.println(this);
  }
  
  /**
   * pushes a command to Listener
   * @param cmd
   * @throws IOException
   * @throws JSchException 
   */
   private void pushcommand() throws IOException, JSchException {
        Instance_data.setDirbuf(new StringBuffer(""));
            
        Instance_data.setBaos(out);
        Instance_data.setChannel(channel);
        Instance_hold.getLm().setError(false);
        Instance_hold.getLm().setTrylisten(true);

        while (Instance_hold.getLm().isTrylisten() && !Instance_hold.getLm().isExit()) {
            System.out.println("LISTENING...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (Instance_hold.getLm().isError()) {
            System.out.println("LISTEN ERROR");
            throw new JSchException();
        }
    }   

    public void throwExc() throws JSchException {
        throw new JSchException();
    }
  
    public boolean isPasswordcheck() {
        return passwordcheck;
    }

    public void setPasswordcheck(boolean passwordcheck) {
        this.passwordcheck = passwordcheck;
    }

    public ChannelSftp getChannel() {
        return channel;
    }

    public void setChannel(ChannelSftp channel) {
        this.channel = channel;
    }
  
  
  
}
