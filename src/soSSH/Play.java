
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soSSH;

import cmd.SCP;
import instance.Instance_data;
import instance.Instance_hold;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RobsonP
 */
public class Play extends Thread {
    String path;
    int i=0;
    String flnm;
    boolean interrupted;
    File f;
    
    public Play() {
        interrupted = false;
    }
    
    @Override
    public void run() {
        Instance_data.decrementExitedthreads();
        Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume(Instance_hold.getPlayframe().getjSlider_vol().getValue());
        
        System.out.println("VPlay: I AM ALIVE");
    
        i = Instance_data.getDlindx();
        System.out.println("DLIndex --> " + i);
        flnm = Instance_hold.getPl().getEntries().get(i).split("/")[Instance_hold.getPl().getEntries().get(i).split("/").length-1];
        f = new File(Instance_data.getTmpPath() + "\\" + flnm);
        
        while (!Instance_hold.getSCPFrom_Monitor().isRdytoplay() && !Instance_hold.getVplay_mon().isInterrupted()) {
            System.out.println("SCPFROM ready? " + Instance_hold.getSCPFrom_Monitor().isRdytoplay());
            System.out.println("warten auf dlbeginn_2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Instance_hold.getSCPFrom_Monitor().setRdytoplay(false);
        
        do {
            Instance_data.setTrackchange(true);
            Instance_data.setCurrentplay(i);

            flnm = Instance_hold.getPl().getEntries().get(i).split("/")[Instance_hold.getPl().getEntries().get(i).split("/").length-1];
            System.out.println("VPLAY FILENAME(" + i + "): " + flnm);

            flnm = flnm.replace("\\", "");
            
            if ((flnm.endsWith(".mp3") || flnm.endsWith(".wav") || flnm.endsWith(".MP3") || flnm.endsWith(".WAV"))) {
                Instance_hold.getPlayframe().setResizable(false);
                if (!Instance_hold.getPlayframe().isVisible() && !Instance_hold.getMframe().getjPanel_nav().isVisible()) {
                    Instance_hold.getPlayframe().setVisible(true);
                    Instance_hold.getPlayframe().setLocation((int)Instance_hold.getMframe().getLocation().getX()+200, (int)Instance_hold.getMframe().getLocation().getY()+80);
                }
                Instance_hold.getPlayframe().getjPanel_View().setVisible(false);
                Instance_hold.getPlayframe().setSize(500, 110);
                System.out.println("SIZE CHANGED FROM PLAYFRAME");
                //Instance_hold.getPlayframe().pack();
                Instance_hold.getPlayframe().getjButton_fullscreen().setEnabled(false);
                Instance_hold.getMframe().getClistener().setVplay(false);
            }else if (!Instance_hold.getPlayframe().isVisible() && !Instance_hold.getMframe().getjPanel_nav().isVisible()) {
                Instance_hold.getPlayframe().setResizable(true);
                Instance_hold.getPlayframe().setVisible(true);
                Instance_hold.getPlayframe().setLocation((int)Instance_hold.getMframe().getLocation().getX()+200, (int)Instance_hold.getMframe().getLocation().getY()+80);
                Instance_hold.getPlayframe().getjPanel_View().setVisible(true);
                Instance_hold.getPlayframe().setSize(800, 600);
                Instance_hold.getPlayframe().getjButton_fullscreen().setEnabled(true);
                Instance_hold.getMframe().getClistener().setVplay(true);
            }else if (!flnm.endsWith(".mp3") && !flnm.endsWith(".wav")  && !flnm.endsWith(".MP3") && !flnm.endsWith(".WAV")) {
                Instance_hold.getPlayframe().setResizable(true);
                if (!Instance_hold.getPlayframe().isIconfied()) {
                    if (!Instance_hold.getPlayframe().isVisible() && !Instance_hold.getMframe().getjPanel_nav().isVisible()) {
                        Instance_hold.getPlayframe().setVisible(true);
                        Instance_hold.getPlayframe().setLocation((int)Instance_hold.getMframe().getLocation().getX()+200, (int)Instance_hold.getMframe().getLocation().getY()+80);
                    }
                    Instance_hold.getPlayframe().getjPanel_View().setVisible(true);
                    Instance_hold.getPlayframe().setSize(800, 600);
                }
                Instance_hold.getPlayframe().getjButton_fullscreen().setEnabled(true);
                Instance_hold.getMframe().getClistener().setVplay(true);
            }
            Instance_data.setPlaypath(Instance_data.getTmpPath() + "\\" + flnm);
            
            File file = new File(Instance_data.getPlaypath());
            System.out.println(Instance_data.getPlaypath());
            System.out.println("FILE EXIST? " + file.exists());
            if (!file.exists()) {
                System.out.println("VPLAY FILE EXIST: " + file.exists());
                Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true);
                
                Instance_data.setDlindx(i);                
                
                if (!Instance_hold.getSCPFrom_Monitor().isDlfinish()) {
                    Instance_hold.getSCPFrom_Monitor().setExit(true);
                    Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);
                    //Instance_hold.getSCPFrom_Monitor().setMedreleased(true);
                    while (Instance_hold.getSCPFrom_Monitor().getIrruptflag() == 1 && !Instance_hold.getVplay_mon().isExit()) {
                        System.out.println("V PLay");
                        if (!Instance_hold.getScpfrom().isAlive()) {
                            Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            //ex.printStackTrace();
                        }
                    }
                    //Instance_hold.getSCPFrom_Monitor().setMedreleased(false);

                    System.out.println("SCPFROM: " + Instance_hold.getScpfrom().isAlive());            
                }
                try {
                    Instance_hold.getMframe().setFlnm(Instance_hold.getPl().getEntries().get(Instance_hold.getMframe().getjTable_playlist().getSelectedRow()).toString().split("/")[Instance_hold.getPl().getEntries().get(Instance_hold.getMframe().getjTable_playlist().getSelectedRow()).toString().split("/").length-1].replace("\\", ""));
                }catch (ArrayIndexOutOfBoundsException exc) {
                    
                }
                Instance_hold.getSCPFrom_Monitor().setRdytoplay(false);
                if (!Instance_hold.getScpfrom().isAlive()) {
                    Instance_hold.setScpfrom(new SCP());
                    Instance_hold.getScpfrom().start();
                }
               

                System.out.println("RDYTOPLAY? " + Instance_hold.getSCPFrom_Monitor().isRdytoplay());
                
                while (!Instance_hold.getSCPFrom_Monitor().isRdytoplay() && !Instance_hold.getVplay_mon().isInterrupted()) {
                    System.out.println("SCPFROM ready? " + Instance_hold.getSCPFrom_Monitor().isRdytoplay());
                    System.out.println("warten auf dlbeginn_1");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                Instance_hold.getSCPFrom_Monitor().setRdytoplay(false);
            }
            
            try {
                Instance_hold.getVplay_mon().setFinished(false);
                System.out.println("PLAYING NOW -- " + Instance_data.getTmpPath() + "\\" + flnm);
                Instance_hold.getPlayframe().getjLabel_medianame().setText(flnm);
                Instance_hold.getMframe().getjLabel_medianame().setText(flnm);
                if (!Instance_hold.getFsf().isVisible() && !Instance_hold.getVplay_mon().isExit()) {
                    Instance_hold.getPlayframe().getEmpc().getMediaPlayer().playMedia(Instance_data.getTmpPath() + "\\" + flnm);
                    Instance_hold.getPlayframe().getjLabel_play().setIcon(Instance_hold.getIm_hold().getMcb_grey_pause_small());
                    Instance_hold.getMframe().getjLabel_play().setIcon(Instance_hold.getIm_hold().getMcb_grey_pause_small());
                }
                else if (!Instance_hold.getVplay_mon().isExit()){
                    Instance_hold.getPlayframe().setVisible(false);
                    Instance_hold.getFsf().getEmpc().getMediaPlayer().playMedia(Instance_data.getTmpPath() + "\\" + flnm);
                    Instance_hold.getFsnt().getjLabel_play().setIcon(Instance_hold.getIm_hold().getMcb_grey_pause_small());
                }
            }catch (Exception exc) {
                System.out.println("Error while start Playing");
            }

            Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(false);
            do {
                if (Instance_hold.getVplay_mon().isInterrupted()) {
                    System.out.println("Interrupted!!!");

                    if (Instance_hold.getVplay_mon().isExit()) {
                        Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
                        Instance_hold.getFsf().getEmpc().getMediaPlayer().stop();
                        //Instance_hold.getVplay_mon().setStopped(true);
                        //Instance_hold.getSCPFrom_Monitor().setMedreleased(true);
                        System.out.println("BREAKED");
                        break;
                    }
                    i = Instance_data.getDlindx()-1;
                    interrupted = true; 
                }
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("PLAYING");
                System.out.println("Interrupted: " + interrupted);
                System.out.println("Is Finished: " + Instance_hold.getVplay_mon().isFinished());
            }while(!Instance_hold.getVplay_mon().isFinished() && !interrupted/* && !Instance_hold.getVplay_mon().isStopped()*/);
            
            if (Instance_hold.getVplay_mon().isExit()) {
                i = Instance_hold.getPl().getEntries().size()+1;
                Instance_hold.getVplay_mon().setExit(false);
                break;
            }
            
            System.out.println("VPLAY PLAYINDEX: " + i);
            i++;
            interrupted = false;            
        }while(i<Instance_hold.getPl().getEntries().size());
        System.out.println("VPLAY: I AM EXITED");
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
  
    public String getFlnm() {
        return flnm;
    }
}