/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import control.Main_controls;
import instance.Instance_data;
import instance.Instance_hold;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author RobsonP
 */
public class FC_load_Listener implements ActionListener{
    private JFileChooser jfc;
    private File file;
    private FileReader fr;
    private BufferedReader bufread;
    private String str;
    
    @Override
    public void actionPerformed(ActionEvent e)  {
        boolean crdir = false;
        
        if (e.getActionCommand().equals("ApproveSelection")) {
        ArrayList<String> strlist = new ArrayList<>();
            try {
                file = jfc.getSelectedFile();
                
                fr = new FileReader(file);
                bufread = new BufferedReader(fr);
                                
                try {
                    do {
                        str = bufread.readLine();
                        strlist.add(str);
                    }while (!str.equals(null));
                }catch (NullPointerException exc) {
                    strlist.remove(strlist.size()-1);
                }
                
                for (int i=0;i<strlist.size();i++) {
                    System.out.println(strlist.get(i));
                    switch (i) {
                        case 0: Instance_data.setUname(strlist.get(i));break;
                        case 1: Instance_data.setRsakeyPath(strlist.get(i));break;
                        case 2: Instance_data.setTmpPath(strlist.get(i));break;
                        case 3: Instance_hold.getMframe().getjTextField_Server().setText(strlist.get(i));break;
                        case 4: Instance_hold.getMframe().getjTextField_Port().setText(strlist.get(i));break;
                        default:;
                    }
                }
                
                File file = new File(Instance_data.getTmpPath());
        
                System.out.println(file.getAbsolutePath());
                
                Object[] options = {"Yes, please", "No, thanks"};
                int n = JOptionPane.showOptionDialog(null,"Would you like to add Temp-Settings? All Files will be deleted in Temp-folder!!!", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                System.out.println(n);

                switch (n) {
                    case 0: Main_controls.del(file);
                        
                    crdir = file.mkdirs();
                    if (!crdir) {
                        JOptionPane.showMessageDialog(null, "Directory is busy", null, JOptionPane.ERROR_MESSAGE);
                        throw new IOException();
                    }
                    
                    Instance_hold.getMframe().getjButton_connect().setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Settings loaded");break;
                    case 1: break;
                    default: ;
                }    
                             
                System.out.println("PATH: " + Instance_data.getRsakeyPath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error while loading settings", "Inane error", JOptionPane.ERROR_MESSAGE);
                //Logger.getLogger(FC_sav_Listener.class.getName()).log(Level.SEVERE, null, ex);
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
