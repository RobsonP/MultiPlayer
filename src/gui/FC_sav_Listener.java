/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import instance.Instance_hold;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author RobsonP
 */
public class FC_sav_Listener implements ActionListener{
    private JFileChooser jfc;
    private File file;
    private FileWriter fw;
    private BufferedWriter bufwr;
    
    @Override
    public void actionPerformed(ActionEvent e)  {
        if (e.getActionCommand().equals("ApproveSelection")) {
            try {
                file = jfc.getSelectedFile();
                
                fw = new FileWriter(file);
                bufwr = new BufferedWriter(fw);
                
                bufwr.write(Instance_hold.getSettDiag().getjTextField_uname().getText() + "\n");
                if (Instance_hold.getSettDiag().getjTextField_RSA().getText().equals("")) bufwr.write("EMPTY" + "\n");
                else bufwr.write(Instance_hold.getSettDiag().getjTextField_RSA().getText() + "\n");
                bufwr.write(Instance_hold.getSettDiag().getjTextField_tmp().getText() + "\n");
                bufwr.write(Instance_hold.getSettDiag().getjTextField_server().getText() + "\n");
                if (Instance_hold.getSettDiag().getjTextField_port().getText().equals("")) {
                    bufwr.write("22");
                }else {
                    bufwr.write(Instance_hold.getSettDiag().getjTextField_port().getText());
                }
                
                bufwr.flush();
                
                bufwr.close();
                fw.close();
                
                JOptionPane.showMessageDialog(null, "File saved", null, JOptionPane.INFORMATION_MESSAGE);
                Instance_hold.getSettDiag().dispose();
            } catch (IOException ex) {
                Logger.getLogger(FC_sav_Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      
    }

    public JFileChooser getJfc() {
        return jfc;
    }

    public void setJfc(JFileChooser jfc) {
        this.jfc = jfc;
    }
    
}
