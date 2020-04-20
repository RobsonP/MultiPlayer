/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Melonman
 */
public class FC_PL_sav implements ActionListener {
    private JFileChooser jfc;
    private File file;
    private FileWriter fwr;
    private BufferedWriter bufwr;
    private ArrayList<String> pl_entries;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (jfc.getSelectedFile() != null) {
                file = jfc.getSelectedFile();       
                fwr = new FileWriter(file);
                bufwr = new BufferedWriter(fwr);

                for (int i=0;i<pl_entries.size();i++) {
                    if (i==pl_entries.size()-1) bufwr.write(pl_entries.get(i));
                    else bufwr.write(pl_entries.get(i) + "\n"); 
                }         
                bufwr.flush();

                bufwr.close();
                fwr.close();

                JOptionPane.showMessageDialog(Instance_hold.getMframe() , "Playlist saved", null, JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(IOException exc) {
            
        }
    }

    public JFileChooser getJfc() {
        return jfc;
    }

    public void setJfc(JFileChooser jfc) {
        this.jfc = jfc;
    }

    public ArrayList<String> getPlentries() {
        return pl_entries;
    }

    public void setPlentries(ArrayList<String> plentries) {
        this.pl_entries = plentries;
    }
}
