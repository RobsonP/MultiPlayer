/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import instance.Instance_hold;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RobsonP
 */
public class WaitThread extends Thread {
    
    public WaitThread() {
        super();
    }
    
    @Override
    public void run() {
        while(!Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying() && !Instance_hold.getFsf().getEmpc().getMediaPlayer().isPlaying() && !Instance_hold.getMframe().interrupted && Instance_hold.getPlay().isAlive()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(WaitThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Instance_hold.getMframe().enable_Objects();
        Instance_hold.getMframe().getjButton_reset().setVisible(false);
    }
}