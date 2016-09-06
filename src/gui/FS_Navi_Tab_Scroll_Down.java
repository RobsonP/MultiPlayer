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
public class FS_Navi_Tab_Scroll_Down extends Thread {
    
    @Override
    public void run() {                  
        for (int i=70;i>=0;i--) {
            Instance_hold.getFsnt().setOpacity((float)(i/100.0));
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(FS_Navi_Tab_Scroll_Up.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Instance_hold.getFsf().toFront();
    }
}
