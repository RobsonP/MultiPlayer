/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import instance.Instance_hold;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author RobsonP
 */
public class FC_tmp_Listener implements ActionListener{
    private File file;
    private JFileChooser jfc;
    
    @Override
    public void actionPerformed(ActionEvent e) {  
        if (e.getActionCommand().equals("ApproveSelection")) {
            file = jfc.getSelectedFile();
            Instance_hold.getSettDiag().getjTextField_tmp().setText(file.getPath());
        }
    }

    public JFileChooser getJfc() {
        return jfc;
    }

    public void setJfc(JFileChooser jfc) {
        this.jfc = jfc;
    }
}
