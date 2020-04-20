/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import instance.Instance_hold;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Melonman
 */
public class Show_State extends Thread {
    private boolean end;
    
    public Show_State() {
        end = false;
    }
    
    @Override
    public void run() {        
        while (true) {
            Instance_hold.getMframe().getjLabel_show_status().setText("Buffering.");
            for (int i=0;i<40;i++) {
                if (end) {
                    
                    break;
                }
            
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Show_State.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
            Instance_hold.getMframe().getjLabel_show_status().setText("Buffering..");
            for (int i=0;i<40;i++) {
                if (end) {
                    
                    break;
                }
            
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Show_State.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            Instance_hold.getMframe().getjLabel_show_status().setText("Buffering...");
            for (int i=0;i<40;i++) {
                if (end) {
                    
                    break;
                }
            
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Show_State.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (end) {
                Instance_hold.getMframe().getjLabel_show_status().setText("");
                break;
            }
        }     
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
