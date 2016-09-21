/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cmd;

import control.Main_controls;
import data.Playlist;
import instance.Instance_data;
import instance.Instance_hold;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author RobsonP
 */
public class DownThread extends Thread {
    
    public DownThread() {
        super();
    }
    
    @Override
    public void run() {
        boolean crdir = false;
        System.out.println("DOWN THREAD ACTION");
        
        Instance_hold.getVplay_mon().setExit(true);
        Instance_hold.getVplay_mon().setIrruptflag(1);
        do {
            System.out.println("VPlay is waiting to exit");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main_controls.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while (Instance_hold.getVplay().isAlive());
                
        Instance_hold.getSCPFrom_Monitor().setExit(true);
        Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);

        if (Instance_hold.getScpfrom().isAlive()) {
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(true);
            do{
                    System.out.println("waiting for SCP release");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main_controls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(Instance_hold.getScpfrom().isAlive());
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(false);
        }
        
        Instance_hold.getLm().setExit(true);
        while(Instance_hold.getListen().isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(DownThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("LISTEN EXITED? " + !Instance_hold.getListen().isAlive());
            System.out.println("WAITING FOR LISTENEXIT");
        }    
            
        Instance_hold.getSh_mon().setExit(true);
        while (Instance_hold.getSh().isAlive()) {
            System.out.println("WAITING FOR SHELL EXIT");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
        }

        Instance_hold.getVplay_mon().setExit(false);
        Instance_hold.getVplay_mon().setIrruptflag(0);
        
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
        //Instance_hold.getSCPFrom_Monitor().setMedreleased(false);
        Instance_hold.getSCPFrom_Monitor().setMonitorisclosed(false);
        Instance_hold.getSCPFrom_Monitor().setNewsession(false);
        Instance_hold.getSCPFrom_Monitor().setRdytoplay(false);
        Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
        
        
        if (Instance_data.getTmpPath() != null) {
            File file = new File(Instance_data.getTmpPath());
            Main_controls.del(file);

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
        
        do {
            System.out.println("WAITING FOR CThread EXIT");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(DownThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(Instance_hold.getCthread().isAlive());
        
        System.out.println("NEW WINDOW");
        Instance_hold.getMframe().getjScrollPane_Playlist().setViewportView(Instance_hold.getMframe().getjTable_playlist());   
        Instance_hold.getMframe().setjTree_nav(new JTree(Instance_hold.getMframe().getTop()));
        Instance_hold.getMframe().getjPanel_nav().setVisible(false);
        Instance_hold.getMframe().getjScrollPane_nav().setViewportView(Instance_hold.getMframe().getjTree_nav());
        Instance_hold.getMframe().getjProgressBar_SCP().setValue(0);
        Instance_hold.getMframe().getjLabel_src().setText("");
        Instance_hold.getMframe().getjLabel_speedval().setText("");
        //Instance_hold.getMframe().getContentPane().repaint();
        Instance_hold.getMframe().getjButton_connect().setEnabled(false);
        Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(false);
        
        if (!Instance_hold.getSh_mon().isCancel()) {
            System.out.println("NEW WINDOW with Server");
            Instance_hold.getMframe().getjTextField_Server().setText("");
            Instance_hold.getMframe().getjTextField_Port().setText("");
            Instance_hold.getSetframe().getjButton_import().setEnabled(true);
        }else {
            Instance_hold.getMframe().getjButton_connect().setEnabled(true);
        }
        
        Instance_hold.getSh_mon().setCancel(false);
    }
}