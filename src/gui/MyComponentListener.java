/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import instance.Instance_hold;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 *
 * @author RobsonP
 */
public class MyComponentListener implements ComponentListener{

    private boolean vplay, fullscreen;
    
    public MyComponentListener() {
        vplay = false;
        fullscreen = false;
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        System.out.println("MOVED");
        System.out.println("fullscreen? " + fullscreen);
        if (!fullscreen && !vplay) {
            System.out.println("LOCATED");
            Instance_hold.getPlayframe().setLocation((int)Instance_hold.getMframe().getLocation().getX()+200, (int)Instance_hold.getMframe().getLocation().getY()+80);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isVplay() {
        return vplay;
    }

    public void setVplay(boolean vplay) {
        this.vplay = vplay;
    }
}