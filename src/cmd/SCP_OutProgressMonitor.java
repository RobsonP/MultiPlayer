/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cmd;

import com.jcraft.jsch.SftpProgressMonitor;
import control.Main_controls;
import instance.Instance_hold;
import java.util.logging.Level;
import java.util.logging.Logger;
import parse.ParseControl;

/**
 *
 * @author RobsonP
 */
public class SCP_OutProgressMonitor implements SftpProgressMonitor
{
    private String dest;
    private int anz16384bytes;
    private long loaded_bytes;
    private long speed_bytes;
    private long speed_stamp;
    private long max;
    private long starttime = 0;
    private long endtime = 0;
       
    public SCP_OutProgressMonitor() {
        anz16384bytes = 0;
        loaded_bytes = 0;
        speed_bytes = 0;
        speed_stamp = 0;
    }

    @Override
    public void init(int op, java.lang.String src, java.lang.String dest, long max) 
    {
        System.out.println("STARTING: "+op+" "+src+" -> "+dest+" total: "+max);
        this.dest = dest;
        this.max = max;
        String source = src.split("/")[src.split("/").length-1];
        
        if (source.length() > 50) Instance_hold.getMframe().getjLabel_src().setText(source.substring(0, 50) + "...");
        else Instance_hold.getMframe().getjLabel_src().setText(source);
        
        Instance_hold.getSCPFrom_Monitor().setInit(true);
    }

        
    @Override
    public boolean count(long bytes)
    {        
        int value = 0;
        anz16384bytes++;
        loaded_bytes = loaded_bytes + bytes;
        
        endtime = System.currentTimeMillis();        
        double abstime = (endtime-starttime)/1000.0;        
        if (abstime>=0.5) {
            speed_bytes = loaded_bytes-speed_stamp;
            speed_stamp = loaded_bytes;         
            double kbs = (speed_bytes/1024)/abstime;

            Instance_hold.getMframe().getjLabel_speedval().setText(""+ParseControl.formatSpeed(kbs));
            starttime = endtime;
        }
        
        if (new String(dest).endsWith("mp3") || new String(dest).endsWith("MP3") || new String(dest).endsWith("wav") || new String(dest).endsWith("WAV")) {
            if (anz16384bytes == 16) Instance_hold.getSCPFrom_Monitor().setRdytoplay(true);
        }else if (!new String(dest).endsWith("jpg") && !new String(dest).endsWith("JPG")){
            if (anz16384bytes == 512) Instance_hold.getSCPFrom_Monitor().setRdytoplay(true);
        }
        
        if (Instance_hold.getSCPFrom_Monitor().isClosechannelflag()) {
            Instance_hold.getVplay_mon().setExit(true);
            Instance_hold.getVplay_mon().setIrruptflag(1);
            /*
            try {
                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
                Instance_hold.getFsf().getEmpc().getMediaPlayer().stop();
            }catch(NullPointerException exc) {
            
                }
            */
            do {
                System.out.println("VPlay is waiting to exit");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main_controls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while (Instance_hold.getVplay().isAlive());
            Instance_hold.getVplay_mon().setExit(false);
            Instance_hold.getVplay_mon().setIrruptflag(0);
            
            
            System.out.println("SCPOUTPROGRESSMONITOR is interrupted!!!");
            if (Instance_hold.getVplay().isAlive()) {
                try {
                    if (Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                        Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
                        Instance_hold.getPlayframe().getEmpc().getMediaPlayer().release();
                    }
                    if (Instance_hold.getFsf().getEmpc().getMediaPlayer().isPlaying()) {
                        Instance_hold.getFsf().getEmpc().getMediaPlayer().stop();
                        Instance_hold.getFsf().getEmpc().getMediaPlayer().release();
                    }
                }catch(NullPointerException exc) {
                    exc.printStackTrace();
                }
            }

            Instance_hold.getSCPFrom_Monitor().setExit(true);
            Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);
            
            return false;
        }
  
        value = (int)(((double)(loaded_bytes)/(double)max)*100.0);
        Instance_hold.getMframe().getjProgressBar_SCP().setValue(value);
        return(true);
    }

    @Override
    public void end()
    {
        System.out.println("\nFINISHED!");
        Instance_hold.getSCPFrom_Monitor().setRdytoplay(true);
    }

    public String getDest() {
        return dest;
    } 
}