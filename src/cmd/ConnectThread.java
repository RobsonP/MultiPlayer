/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cmd;

import cmd.Shell;
import control.Comparator;
import control.Main_controls;
import data.Node_entry;
import gui.MessageThread;
import instance.Instance_data;
import instance.Instance_hold;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTree;
import parse.ParseControl;

/**
 *
 * @author RobsonP
 */
public class ConnectThread extends Thread{
                            
            @Override
            public void run() {
                       try {
            //System.out.println("CNT_VPLAY: " + Instance_hold.getCnt_vplay().isStarted());
            if (!Instance_hold.getSh().isAlive()) {
                Instance_hold.setSh(new Shell());
            }
            
            Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true);
            Instance_hold.getSh_mon().setLoading(true);

            ArrayList<Node_entry> nodelist;

            
            Instance_data.setHost(Instance_data.getUname() + "@" + Instance_hold.getMframe().getjTextField_Server().getText());
            Instance_data.setPort(Integer.parseInt(Instance_hold.getMframe().getjTextField_Port().getText()));
                
            if (!Instance_hold.getSh().isAlive()) Instance_hold.getSh().start();
            Instance_hold.getSh_mon().setStartshell(true);
                     
            while(!Instance_hold.getSh_mon().isRdytolisten()) {
                if (Instance_hold.getSh_mon().isConnectError()) throw new Exception();
                System.out.println("WAITING FOR RDYTOLISTEN");
                Thread.sleep(100);
            }
  
            Instance_data.setCmd("/");
            Instance_hold.getSh_mon().setWrite_output(true);
            System.out.println("DECREMENTED");

            while(Instance_hold.getSh_mon().isWrite_output()) {
                if (Instance_hold.getSh_mon().isConnectError()) throw new Exception();
                Thread.sleep(100);
            }

            String[] strlist = Instance_data.getDirbuf().toString().split("\n");

            nodelist = ParseControl.parseInput(strlist);

            List<Node_entry> list = Comparator.sortList(nodelist);;
            //Collections.sort(list, new NodeComparator());
            
            Instance_hold.getMframe().createTopNodes(list);
            
            Instance_hold.getMframe().setjTree_nav(new JTree(Instance_hold.getMframe().getTop()));
            
            Instance_hold.getMframe().getjTree_nav().addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
                        @Override
                        public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                        }
                        @Override
                        public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                            Main_controls.jTreeWillExpand(evt);
                        }
            });
            
            Instance_hold.getMframe().getjTree_nav().addMouseListener(new java.awt.event.MouseListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            /*
                            Thread thread = new Thread(){
                                @Override
                              public void run() {
                                  for (int i=0;i<Instance_data.getAllnodes().size();i++) {
                                      try {
                                        if (Instance_data.getAllnodes().get(i).getPath().equals(Instance_hold.getMframe().getjTree_nav().getSelectionPath().toString())) {
                                            Instance_hold.getMframe().getjLabel_dir_info().setText(Instance_data.getAllnodes().get(i).getInfo());
                                        }
                                      }catch(NullPointerException ex) {
                                          
                                      }
                                  }
                              }  
                            };
                            if (!thread.isAlive()) thread.start();  
                            */
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet.");
                        }
                
            });
            Instance_hold.getMframe().getjScrollPane_nav().setViewportView(Instance_hold.getMframe().getjTree_nav());
            Instance_hold.getMframe().getjButton_connect().setEnabled(false);
            
        } catch (Exception ex) {
            System.out.println("CTHREAD EXCEPTION");
            Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(false);          
            if (!Instance_hold.getSh_mon().isConnectError()) {
                Instance_hold.setMthread(new MessageThread("Error while connecting",2));
                Instance_hold.getMthread().start();
            }
/*
            Instance_hold.getVplay_mon().setIrruptflag(0);
            //Instance_hold.getVplay_mon().setEnde(false);
            Instance_hold.getVplay_mon().setExit(false);
            
            Instance_hold.getLm().setTrylisten(false);
            
            Instance_hold.getSh_mon().setRdytolisten(false);
            //Instance_hold.getSh_mon().setExit(false);
            Instance_hold.getSh_mon().setLoading(false);
            Instance_hold.getSh_mon().setStartshell(false);
            Instance_hold.getSh_mon().setConnectError(false);
            Instance_hold.getSh_mon().setWrite_output(false);
*/
            Instance_hold.getMframe().getjButton_connect().setEnabled(true);
            //ex.printStackTrace();
        }
                       
            Instance_hold.getMframe().getjTextPane_info().setText("/");           
            
            System.out.println("CONNECT: I AM EXITED");
    }
}
