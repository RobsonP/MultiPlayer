/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import cmd.SCP;
import instance.Instance_data;
import instance.Instance_hold;
import data.Node_entry;
import data.Playlist;
import cmd.ConnectThread;
import gui.MessageThread;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import parse.ParseControl;

/**
 *
 * @author RobsonP
 */
public class MainControls {    
    
    public static void expand_NavTree(javax.swing.event.TreeExpansionEvent evt) {                   
        Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true);
        Instance_hold.getSh_mon().setLoading(true);
        String strpath;
        ArrayList<Node_entry> nodelist;
        strpath = ParseControl.parsePath(evt.getPath().toString());
        Instance_data.setCmd(strpath);
        Instance_hold.getSh_mon().setWrite_output(true);
        while(Instance_hold.getSh_mon().isWrite_output()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String[] strlist = Instance_data.getDirbuf().toString().split("\n");
        nodelist = ParseControl.parseInput(strlist);
        Instance_hold.getMframe().getjTextPane_info().setText(Instance_hold.getMframe().getPathfromTree(evt.getPath().toString()).toString());
        List<Node_entry> list = Comparator.sortList(nodelist);
        
        
        for (int i=0;i<list.size();i++) {
            System.out.println("Folder Entry: " + list.get(i).getName());
        }
        
        Instance_hold.getMframe().addNodestoBranch(list, evt.getPath());
        DefaultMutableTreeNode dtm_test = (DefaultMutableTreeNode)evt.getPath().getLastPathComponent();
        System.out.println("Element Path: " + dtm_test.toString());
        System.out.println("Number of Elements " + dtm_test.getChildCount());
    }
    
    public static void connect() {  
        if (!Instance_hold.getCthread().isAlive()) {
            System.out.println("CONNECT THREAD STARTED");
            Instance_hold.setCthread(new ConnectThread());
            Instance_hold.getCthread().start();
        }    
    }
    
    public static void addToPlaylist() {
        Instance_hold.getSCPFrom_Monitor().setDlfinish(false);
        for (int j=0;j<Instance_hold.getMframe().getjTree_nav().getSelectionCount();j++) {
            Instance_hold.getMframe().setMedpath(Instance_hold.getMframe().getPathfromTree(Instance_hold.getMframe().getjTree_nav().getSelectionPaths()[j].toString()));

            TreeNode tn = (TreeNode)Instance_hold.getMframe().getjTree_nav().getSelectionPaths()[j].getLastPathComponent();

            if (tn.getChildCount() == 0) {
                Instance_hold.getPl().addEntry(Instance_hold.getMframe().getMedpath());
                Instance_hold.getMframe().setDtm((DefaultTableModel)Instance_hold.getMframe().getjTable_playlist().getModel());
                Vector<String> v = new Vector<String>();
                v.addElement(Instance_hold.getMframe().getMedpath().toString().split("/")[Instance_hold.getMframe().getMedpath().toString().split("/").length-1].replace("\\", ""));
                Instance_hold.getMframe().getDtm().addRow(v);
            }
            else {        
                Instance_hold.setMthread(new MessageThread("No Directories Allowed"));
                Instance_hold.getMthread().start();
            }        
        }
    }
    
    public static void prevMedia() {
        Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true);
                        
        System.out.println("PREV MEDIA ACTION");
        
        Instance_hold.getVplay_mon().setExit(true);
        Instance_hold.getVplay_mon().setIrruptflag(1);

        System.out.println("Main Controls: Waiting for VPlay release...");
        do { 
            Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while (Instance_hold.getPlay().isAlive());
        Instance_hold.getVplay_mon().setExit(false);
        Instance_hold.getVplay_mon().setIrruptflag(0);
        Instance_hold.getMframe().getjLabel_show_status().setText("");
                
        Instance_hold.getSCPFrom_Monitor().setExit(true);
        Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);

        if (Instance_hold.getScpfrom().isAlive()) {
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(true);
            
            System.out.println("Main Controls: Waiting for SCP release...");
            do {
                Instance_hold.getMframe().getjLabel_show_status().setText("SFTP Release...");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(Instance_hold.getScpfrom().isAlive());
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(false);
            
            Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
            Instance_hold.getSCPFrom_Monitor().setExit(false);
            Instance_hold.getMframe().getjLabel_show_status().setText("");
        }

        Instance_hold.getSCPFrom_Monitor().setRdytoplay(false);
        if (Instance_data.getCurrentplay() != 0) Instance_data.setDlindx(Instance_data.getCurrentplay()-1);

        if (!Instance_hold.getScpfrom().isAlive()) {
            Instance_hold.setScpfrom(new SCP());
            Instance_hold.getScpfrom().start();
        }

        try {
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer();
        }catch (NullPointerException exc) {
            System.out.println("INIT");
            Instance_hold.getPlayframe().init();       
        }

        if (!Instance_hold.getPlay().isAlive()) {
            System.out.println("VPLAY STARTED");
            Instance_hold.create_VPlay();
            Instance_hold.getPlay().start();
        }
        else {
            System.out.println("Media Player must be started otherwise!!!");
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
            Instance_hold.getVplay_mon().setIrruptflag(1);
        }
    }
    
    public static void nextMedia() {
        Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true);

        System.out.println("NEXT MEDIA ACTION");
        
        Instance_hold.getVplay_mon().setExit(true);
        Instance_hold.getVplay_mon().setIrruptflag(1);

        System.out.println("Main Controls: Waiting for VPlay release...");
        do {
            Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while (Instance_hold.getPlay().isAlive());
        Instance_hold.getVplay_mon().setExit(false);
        Instance_hold.getVplay_mon().setIrruptflag(0);
        Instance_hold.getMframe().getjLabel_show_status().setText("");
        
        Instance_hold.getSCPFrom_Monitor().setExit(true);
        Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);

        if (Instance_hold.getScpfrom().isAlive()) {
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(true);
            
            System.out.println("Main Controls: Waiting for SCP release...");
            do {
                Instance_hold.getMframe().getjLabel_show_status().setText("SFTP Release...");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(Instance_hold.getScpfrom().isAlive());
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(false);
            
            Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
            Instance_hold.getSCPFrom_Monitor().setExit(false);
            Instance_hold.getMframe().getjLabel_show_status().setText("");
        }

        Instance_hold.getSCPFrom_Monitor().setRdytoplay(false);
        if (Instance_data.getCurrentplay()<(Instance_hold.getPl().getEntries().size()-1)) Instance_data.setDlindx(Instance_data.getCurrentplay()+1);

        if (!Instance_hold.getScpfrom().isAlive()) {
            Instance_hold.setScpfrom(new SCP());
            Instance_hold.getScpfrom().start();
        }

        try {
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer();
        }catch (NullPointerException exc) {
            System.out.println("INIT");
            Instance_hold.getPlayframe().init();       
        }

        if (!Instance_hold.getPlay().isAlive()) {
            System.out.println("VPLAY STARTED");
            Instance_hold.create_VPlay();
            Instance_hold.getPlay().start();
        }
        else {
            System.out.println("Media Player must be started otherwise!!!");
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
            Instance_hold.getVplay_mon().setIrruptflag(1);
        }
    }
    
    public static void newSession() {        
        boolean crdir = false;
        System.out.println("NEW SESSION ACTION");
        
        Instance_hold.getVplay_mon().setExit(true);
        Instance_hold.getVplay_mon().setIrruptflag(1);
        
        try {
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
            Instance_hold.getFsf().getEmpc().getMediaPlayer().stop();
        }catch(NullPointerException exc) {
            
        }
        
        System.out.println("Main Controls: Waiting for VPlay release...");
        do {
            Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while (Instance_hold.getPlay().isAlive());
        Instance_hold.getMframe().getjLabel_show_status().setText("");
        
        Instance_hold.getSCPFrom_Monitor().setExit(true);
        Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);
        if (Instance_hold.getScpfrom().isAlive()) {
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(true);
            
            System.out.println("Main Controls: Waiting for SCP release...");
            do {
                Instance_hold.getMframe().getjLabel_show_status().setText("SFTP Release...");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(Instance_hold.getScpfrom().isAlive());
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(false);
            Instance_hold.getMframe().getjLabel_show_status().setText("");
        }   

        Instance_hold.getSh_mon().setExit(true);            
        while (Instance_hold.getSh().isAlive()) {
            System.out.println("Main Controls: Waiting for Shell release...");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Instance_hold.getLm().setExit(true);
        while(Instance_hold.getListen().isAlive()) {
            System.out.println("LISTEN EXITED? " + !Instance_hold.getListen().isAlive());
            System.out.println("WAITING FOR LISTENEXIT");
        }
        
        
        Instance_hold.getVplay_mon().setIrruptflag(0);
        Instance_hold.getVplay_mon().setExit(false);
        
        Instance_hold.getLm().setTrylisten(false);
        Instance_hold.getLm().setExit(false);

        Instance_hold.getSh_mon().setRdytolisten(false);
        Instance_hold.getSh_mon().setExit(false);
        Instance_hold.getSh_mon().setLoading(false);
        Instance_hold.getSh_mon().setStartshell(false);
        Instance_hold.getSh_mon().setConnectError(false);
        Instance_hold.getSh_mon().setWrite_output(false);

        Instance_hold.getSCPFrom_Monitor().setClosedlmonitor(false);
        Instance_hold.getSCPFrom_Monitor().setExit(false);
        Instance_hold.getSCPFrom_Monitor().setDlfinish(false);
        Instance_hold.getSCPFrom_Monitor().setMonitorisclosed(false);
        Instance_hold.getSCPFrom_Monitor().setNewsession(false);
        Instance_hold.getSCPFrom_Monitor().setRdytoplay(false);
        Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
 
        if (Instance_data.getTmpPath() != null) {
            File file = new File(Instance_data.getTmpPath());
            del(file);

            do {
                crdir = file.mkdirs();
            }while(!crdir);
        }
        System.out.println("EXITED THREADS: " + Instance_data.getExitedthreads());

        Instance_hold.getPlayframe().setVisible(false);
        Instance_hold.getFsf().setVisible(false);

        Instance_hold.getMframe().getTop().removeAllChildren();
        Instance_hold.getMframe().getTop().removeFromParent();
        Instance_hold.getMframe().setTop(new DefaultMutableTreeNode("/"));
        Instance_hold.getMframe().getjTextPane_info().setText("/");
        Instance_hold.getMframe().getjTextField_Server().setEnabled(true);
        Instance_hold.getMframe().getjTextField_Port().setEnabled(true);
        Instance_hold.getMframe().getjTextPane_info().setEditable(true);

        Instance_hold.setPl(new Playlist());
        try {
            Instance_hold.getMframe().setCount(Instance_hold.getMframe().getDtm().getRowCount());
        }catch (NullPointerException exc) {
            Instance_hold.getMframe().setCount(0);
        }
        
        int h=0;
        while (h<Instance_hold.getMframe().getCount()) {
            Instance_hold.getMframe().getDtm().removeRow(0);
            h++;
        }
        
        Instance_hold.getMframe().getjTextField_Server().setText("");
        Instance_hold.getMframe().getjTextField_Port().setText("");
        Instance_hold.getMframe().getjScrollPane_Playlist().setViewportView(Instance_hold.getMframe().getjTable_playlist());   
        Instance_hold.getMframe().setjTree_nav(new JTree(Instance_hold.getMframe().getTop()));
        Instance_hold.getMframe().getjScrollPane_nav().setViewportView(Instance_hold.getMframe().getjTree_nav());
        Instance_hold.getMframe().getjProgressBar_SCP().setValue(0);
        Instance_hold.getMframe().getjLabel_src().setText("");
        Instance_hold.getMframe().getjLabel_speedval().setText("");
        Instance_hold.getMframe().getjLabel_show_flnm().setText("");
        Instance_hold.getMframe().getjButton_connect().setEnabled(false);
        Instance_data.setPlLoadEnabled(false);
        Instance_hold.getSetframe().getjButton_import().setEnabled(true);
        Instance_hold.getSetframe().getjButton_ok().setEnabled(true);
        Instance_hold.getSetframe().getRootPane().setDefaultButton(Instance_hold.getSetframe().getjButton_ok());
    }
    
    public static void playMedia() {
        Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true);

        System.out.println("PLAY MEDIA ACTION");
        
        System.out.println("Main Controls: Waiting for VPlay release...");
        
        Instance_hold.getVplay_mon().setExit(true);
        Instance_hold.getVplay_mon().setIrruptflag(1);
        do {
            Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while (Instance_hold.getPlay().isAlive());
        Instance_hold.getVplay_mon().setExit(false);
        Instance_hold.getVplay_mon().setIrruptflag(0);
        Instance_hold.getMframe().getjLabel_show_status().setText("");

        Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);
        Instance_hold.getSCPFrom_Monitor().setExit(true);

        Instance_hold.getSCPFrom_Monitor().setClosechannelflag(true);
        
        System.out.println("Main Controls: Waiting for SCP release...");
        do {
            Instance_hold.getMframe().getjLabel_show_status().setText("SFTP Release...");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainControls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(Instance_hold.getScpfrom().isAlive());
        Instance_hold.getSCPFrom_Monitor().setClosechannelflag(false);

        Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
        Instance_hold.getSCPFrom_Monitor().setExit(false);
        Instance_hold.getMframe().getjLabel_show_status().setText("");
       
        Instance_data.setDlindx(Instance_hold.getMframe().getjTable_playlist().getSelectedRow());
        Instance_hold.getSCPFrom_Monitor().setRdytoplay(false);
        Instance_hold.getMframe().setFlnm(Instance_hold.getPl().getEntries().get(Instance_hold.getMframe().getjTable_playlist().getSelectedRow()).toString().split("/")[Instance_hold.getPl().getEntries().get(Instance_hold.getMframe().getjTable_playlist().getSelectedRow()).toString().split("/").length-1].replace("\\", ""));
        
        if (Instance_hold.getMframe().getFlnm().length() <= 70) {
            Instance_hold.getPlayframe().getjLabel_show_flnm().setText(Instance_hold.getMframe().getFlnm());
        }else {
            Instance_hold.getPlayframe().getjLabel_show_flnm().setText(Instance_hold.getMframe().getFlnm().substring(0, 70) + "...");
        }

        if (!Instance_hold.getScpfrom().isAlive()) {
            Instance_hold.setScpfrom(new SCP());
            Instance_hold.getScpfrom().start();
        }

        try {
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer();
        }catch (NullPointerException exc) {
            System.out.println("INIT");
            Instance_hold.getPlayframe().init();       
        }

        if (!Instance_hold.getPlay().isAlive()) {
            System.out.println("VPLAY STARTED");
            Instance_hold.create_VPlay();
            Instance_hold.getPlay().start();
        }        
    }
    
    public static void upInPlaylist() {
        int rowcount = Instance_hold.getMframe().getjTable_playlist().getRowCount();
        int[] i = Instance_hold.getMframe().getjTable_playlist().getSelectedRows();
        String plentry;
        
        try {
            if (i[0]-1 == Instance_data.getCurrentplay()) {
                Instance_data.setCurrentplay(i[i.length-1]);
            }
            else if (Instance_data.getCurrentplay()>=i[0] && Instance_data.getCurrentplay()<=i[i.length-1] && Instance_data.getCurrentplay() != 0) {
                Instance_data.setCurrentplay(Instance_data.getCurrentplay()-1);
            }

            Instance_hold.getPlay().setI(Instance_data.getCurrentplay());

            if (i[0]>0) {
                for(int j=0;j<i.length;j++) {
                    plentry = Instance_hold.getPl().getEntries().get(i[j]);
                    Instance_hold.getPl().getEntries().remove(i[j]);
                    Instance_hold.getPl().getEntries().add(i[j]-1, plentry);
                }

                Instance_hold.getMframe().setDtm((DefaultTableModel)Instance_hold.getMframe().getjTable_playlist().getModel());

                for (int j=0;j<rowcount;j++) Instance_hold.getMframe().getDtm().removeRow(0);

                for (int j=0;j<Instance_hold.getPl().getEntries().size();j++) {
                    String medpath = Instance_hold.getPl().getEntries().get(j);
                    Vector<String> v = new Vector<String>();
                    v.addElement(medpath.toString().split("/")[medpath.toString().split("/").length-1].replace("\\", ""));
                    Instance_hold.getMframe().getDtm().addRow(v);
                }

                Instance_hold.getMframe().getjTable_playlist().getSelectionModel().setSelectionInterval(i[0]-1, i[i.length-1]-1);
            }
        }catch(ArrayIndexOutOfBoundsException ex) {
            
        }
    }
    
    public static void downInPlaylist() {
        int rowcount = Instance_hold.getMframe().getjTable_playlist().getRowCount();
        int[] i = Instance_hold.getMframe().getjTable_playlist().getSelectedRows();
        String plentry;
        
        try {
            if (i[i.length-1]+1 == Instance_data.getCurrentplay()) {
                Instance_data.setCurrentplay(i[0]);
            }
            else if (Instance_data.getCurrentplay()>=i[0] && Instance_data.getCurrentplay()<=i[i.length-1] && Instance_data.getCurrentplay() != Instance_hold.getPl().getEntries().size()-1) {
                Instance_data.setCurrentplay(Instance_data.getCurrentplay()+1);
            }
        
        
            Instance_hold.getPlay().setI(Instance_data.getCurrentplay());

            if (i[i.length-1]<Instance_hold.getPl().getEntries().size()-1) {
                for(int j=i.length-1;j>=0;j--) {
                    plentry = Instance_hold.getPl().getEntries().get(i[j]);
                    Instance_hold.getPl().getEntries().remove(i[j]);
                    Instance_hold.getPl().getEntries().add(i[j]+1, plentry);
                }

                Instance_hold.getMframe().setDtm((DefaultTableModel)Instance_hold.getMframe().getjTable_playlist().getModel());

                for (int j=0;j<rowcount;j++) Instance_hold.getMframe().getDtm().removeRow(0);

                for (int j=0;j<Instance_hold.getPl().getEntries().size();j++) {
                    String medpath = Instance_hold.getPl().getEntries().get(j);
                    Vector<String> v = new Vector<String>();
                    v.addElement(medpath.toString().split("/")[medpath.toString().split("/").length-1].replace("\\", ""));
                    Instance_hold.getMframe().getDtm().addRow(v);
                }

                Instance_hold.getMframe().getjTable_playlist().getSelectionModel().setSelectionInterval(i[0]+1, i[i.length-1]+1);   
            }
        }catch(ArrayIndexOutOfBoundsException ex) {
            
        }
    }
    
    public static void deleteFromPlaylist() {
        DefaultTableModel dtm = (DefaultTableModel)Instance_hold.getMframe().getjTable_playlist().getModel();
        
        int[] rows = Instance_hold.getMframe().getjTable_playlist().getSelectedRows();
        
        try {
            String playfl = Instance_data.getTmpPath() + "\\" + Instance_hold.getPlay().getFlnm();
            String loadfl = Instance_hold.getScpfrom().getMonitor().getDest();
            System.out.println(playfl);
            System.out.println(loadfl);
        }catch(NullPointerException exc) {
            
        }
        
        for (int i=0;i<rows.length;i++) {           
            String filename_delete = Instance_hold.getPl().getEntries().get(rows[i]-i).split("/")[Instance_hold.getPl().getEntries().get(rows[i]-i).split("/").length-1];
            filename_delete = filename_delete.replace("\\", "");
            
            File f_delete = new File(Instance_data.getTmpPath() + "\\" + filename_delete);
            if (f_delete.exists()) f_delete.delete();
            
            Instance_hold.getPl().getEntries().remove(rows[i]-i);
                        
            dtm.removeRow(rows[i]-i);
            
            System.out.println(Instance_data.getTmpPath() + "\\" + filename_delete + "    deleted");
            System.out.println(rows[i] + "-te line deleted");
        
            if (rows[i]-i<Instance_data.getCurrentplay()) Instance_data.setCurrentplay(Instance_data.getCurrentplay()-1);
            else if (rows[i]-i==Instance_data.getCurrentplay()) Instance_data.setCurrentplay(-1);
        }

        try {
            if (rows[rows.length-1]<Instance_hold.getPlay().getI()) Instance_hold.getPlay().setI(Instance_hold.getPlay().getI()-rows.length);
            else if (rows[0]<=Instance_hold.getPlay().getI() && rows[rows.length-1]>=Instance_hold.getPlay().getI()) Instance_hold.getPlay().setI(0);
        }catch(ArrayIndexOutOfBoundsException ex) {
            
        }
    }
    
    public static boolean del(File dir){
        if (dir.isDirectory()){
                String[] entries = dir.list();
                for (int x=0;x<entries.length;x++){
                    File aktFile = new File(dir.getPath(),entries[x]);
                    del(aktFile);
                }
                if (dir.delete())
                    return true;
                else
                    return false;
            }
            else{
                if (dir.delete())
                    return true;
                else
                    return false;
            }
    } 
        
    public boolean deltreefiles(File dir) {
        String[] entries = dir.list();

        for (int x=0;x<entries.length;x++){
            File aktFile = new File(dir.getPath(),entries[x]);
            if (!del(aktFile)) return false;
        }

        return true;
    }
}