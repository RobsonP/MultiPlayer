/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import instance.Instance_data;
import instance.Instance_hold;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RobsonP
 */
public class FS_KeyListener implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("KEYCHAR: " + (int)e.getKeyChar());
        System.out.println("KeyChar: " + e.getKeyChar());
        System.out.println("KeyCode: " + e.getKeyCode());
        
        
        if ((int)e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            Instance_hold.getFsnt().setVisible(false);
            Instance_hold.getFsf().setVisible(false);
            Instance_data.setMedia_time(Instance_hold.getFsf().getEmpc().getMediaPlayer().getTime());
            Instance_hold.getFsf().getEmpc().getMediaPlayer().stop();
            Instance_hold.getPlayframe().setVisible(true);
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().playMedia(Instance_data.getPlaypath());
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setTime(Instance_data.getMedia_time());
            while(!Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                System.out.println("WAITING TO START PLAYING:::::");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FS_KeyListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            Instance_hold.getPlayframe().getjLabel_play().setIcon(Instance_hold.getIm_hold().getMcb_grey_pause_small());
            Instance_hold.getMframe().getClistener().setFullscreen(false);
        }
        
        if ((int)e.getKeyChar() == 'n') {
            Instance_hold.getFsnt().setVisible(true);
            Instance_hold.getFsnt().toFront();
            Instance_hold.getFsnt().repaint();            
        };
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}