/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cmd;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import instance.Instance_data;
import instance.Instance_hold;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RobsonP
 */
public class Shell_Out extends Thread{
    private Vector v;
    /**
     * starts the Listener which is waiting for
     * a command as a Thread. It also connects the channel
     * if an an established session exists.
     * @return void
     */
    
    @Override
    public void run() {
        while (true) {
            if (Instance_hold.getLm().isTrylisten()) {    
                try {
                    Instance_data.getChannel().connect(10000);
                } catch (JSchException ex) {
                    try {
                        Instance_data.setSession(Instance_data.getJsch().getSession(Instance_data.getUname(), Instance_data.getHostnm(), Instance_data.getPort()));
                        Instance_data.getSession().setUserInfo(Instance_data.getUi());
                        Instance_data.getSession().connect(1000);
                
                
                        Instance_data.setChannel((ChannelSftp)Instance_data.getSession().openChannel("sftp"));
                        Instance_data.getChannel().connect(10000);
                    }catch(JSchException exc) {
                        exc.printStackTrace();
                        
                        //System.out.println("JSCHException");
                        
                        DownThread dt = new DownThread();
                        dt.start();
                        break;
                    }
                }
                while (!Instance_data.getChannel().isConnected()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Shell_Out.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                try {
                    v = Instance_data.getChannel().ls(Instance_data.getCmd());
                } catch (SftpException ex) {
                    Instance_hold.getLm().setTrylisten(false);
                    Instance_hold.getLm().setError(true);
                    break;
                }

                for (int i=0;i<v.size();i++) {
                    Instance_data.getDirbuf().append(v.get(i) + "\n");
                }

                Instance_hold.getLm().setTrylisten(false);
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Shell_Out.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (Instance_hold.getLm().isExit()) break;
        }        
    }
    /**
     * Access to Output of executed command
     * @return String
     */
}