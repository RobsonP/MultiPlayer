/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Timer;

/**
 *
 * @author RobsonP
 */
public class FS_MouseListener implements MouseMotionListener{
    private Timer timer;
    private Ausblender ausblender;

    public FS_MouseListener() {
        this.ausblender = new Ausblender();
        this.timer = new Timer();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        FS_Navi_Tab_Scroll_Up fsntsu = new FS_Navi_Tab_Scroll_Up();
        fsntsu.start();
        ausblender.cancel();
        timer.cancel();
        timer = new Timer();
        ausblender = new Ausblender();
        timer.schedule(ausblender,2000);           
    }

    public Timer getTimer() {
        return timer;
    }
    
    public void setTimer(Timer timer) {
        this.timer = timer;
    }    
}
