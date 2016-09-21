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
public class My_PL_ComponentListener implements ComponentListener{
    
    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("RESIZED");
        try {
            Instance_hold.getPlayframe().getjPanel_View().getComponent(0).setSize(Instance_hold.getPlayframe().getjPanel_View().getSize());
            Instance_hold.getPlayframe().getjPanel_View().repaint();
        }catch (ArrayIndexOutOfBoundsException exc) {
            
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
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
}