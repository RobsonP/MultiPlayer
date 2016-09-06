/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author RobsonP
 */
public class MyWindowListener implements WindowListener{
    public boolean plcheck;
    
    public MyWindowListener() {
        plcheck = false;
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("OPENED");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("CLOSING");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("CLOSED");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {        
        System.out.println("ACTIVATED");   
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("DEACTIVATED");     
    }

    public boolean isPlcheck() {
        return plcheck;
    }

    public void setPlcheck(boolean plcheck) {
        this.plcheck = plcheck;
    }
    
}
