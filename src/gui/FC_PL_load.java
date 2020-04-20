/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import instance.Instance_hold;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Melonman
 */
public class FC_PL_load implements ActionListener {
    private JFileChooser jfc;
    private JFrame mframe;
    private File file;
    private FileReader fr;
    private BufferedReader bufread;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (jfc.getSelectedFile() != null) {
                file = jfc.getSelectedFile();
                fr = new FileReader(file);
                bufread = new BufferedReader(fr);

                ArrayList<String> pl_entries = new ArrayList<String>();

                boolean eof = false;
                while(!eof) {
                    String str = bufread.readLine();
                    if (str == null) eof = true;
                    else {
                        pl_entries.add(str);
                    }
                }

                if (Instance_hold.getMframe().getDtm() != null) {
                    int rowcount = Instance_hold.getMframe().getDtm().getRowCount();

                    for (int i=0;i<rowcount;i++) {
                        Instance_hold.getPl().removeEntry(0);
                        Instance_hold.getMframe().getDtm().removeRow(0);
                    }
                }
                for (int i=0;i<pl_entries.size();i++) {
                    System.out.println("New Entries: " + pl_entries.get(i));
                    StringBuffer strbuf = new StringBuffer(pl_entries.get(i));
                    Instance_hold.getPl().addEntry(strbuf);
                    Instance_hold.getMframe().setDtm((DefaultTableModel)Instance_hold.getMframe().getjTable_playlist().getModel());
                    Vector<String> v = new Vector<String>();
                    v.addElement(strbuf.toString().split("/")[strbuf.toString().split("/").length-1].replace("\\", ""));
                    Instance_hold.getMframe().getDtm().addRow(v);
                }

                JOptionPane.showMessageDialog(Instance_hold.getMframe() , "Playlist loaded", null, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FC_PL_load.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FC_PL_load.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JFileChooser getJfc() {
        return jfc;
    }

    public void setJfc(JFileChooser jfc) {
        this.jfc = jfc;
    }

    public JFrame getMframe() {
        return mframe;
    }

    public void setMframe(JFrame mframe) {
        this.mframe = mframe;
    }
}
