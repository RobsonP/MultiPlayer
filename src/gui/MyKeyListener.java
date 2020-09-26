/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import instance.Instance_hold;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author mautz
 */
public class MyKeyListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println("Typed");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("MainFrame -> JTree - Pressed - " + e.getKeyChar());
        
        if (Instance_hold.getMframe().getjTree_nav().getSelectionRows().length > 0 && e.getKeyCode() == 10) {
            Instance_hold.getMframe().getjTree_nav().expandRow(Instance_hold.getMframe().getjTree_nav().getSelectionRows()[0]);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("Released");
    }
    
}
