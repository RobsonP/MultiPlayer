/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on 31.03.2012, 23:10:55
 */
package gui;


import control.MainControls;
import instance.Instance_data;
import instance.Instance_hold;
import data.Node_entry;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author RobsonP
 */
public class MainFrame extends javax.swing.JFrame {
    
    DefaultMutableTreeNode top;
    DefaultTableModel dtm;
    String filename;
    StringBuffer medpath = new StringBuffer("");
    private String flnm = "";
    private String filenminfobuf = "";
    private MyWindowListener wlistener = new MyWindowListener();
    private MyComponentListener clistener = new MyComponentListener();
    int count;
    public boolean interrupted = false, stop = false, statechange = false;
    private BasicSliderUI.TrackListener tl_main;

    public MainFrame() {
        initComponents();

        this.jSlider_Media.setBackground(new Color(240,240,240));
        this.jSlider_Media.setForeground(new Color(240,240,240));
        
        this.jPanel_nav.setVisible(false);
        
        Color c = new Color(240,240,240);
        Container con = this.getContentPane();
        con.setBackground(c);
        
        this.jPanel_loadfile.setBackground(c);
        this.jPanel_nav.setBackground(c);
        
        this.jTable_playlist.setDefaultRenderer(Object.class, new MyTableCellRenderer());
        TableColumn column = null;
        column = this.jTable_playlist.getColumnModel().getColumn(0);
        column.setPreferredWidth((int)(this.jTable_playlist.getSize().width*0.7));
        
        JRootPane rootPane = this.getRootPane();
        rootPane.setDefaultButton(this.jButton_connect);
        
        this.setIconImage(Instance_hold.getIm_hold().getLogo_big());
        this.jLabel_dir_info.setText("");
        this.jLabel_show_flnm.setText("");
        this.jLabel_show_status.setText("");
        this.jLabel_show_info.setText("");

        top = new DefaultMutableTreeNode("/");
        this.setTitle("MultiPlayer");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(wlistener);
        this.addComponentListener(clistener);
        
        this.addComponentListener(new ComponentAdapter() {  
        public void componentResized(ComponentEvent evt) {
            System.out.println("MFRAME RESIZED: " + Instance_hold.getMframe().getSize().getWidth() + " * " + Instance_hold.getMframe().getSize().getHeight());
            Instance_data.setShowflnm_size((int)((Instance_hold.getMframe().getSize().getWidth()/9)-92));
            Instance_hold.getPlay().show_flnm(Instance_data.getShowflnm_size());
        }
        
        });
        
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                statechange = true;
                
                TableColumn column = null;
                column = Instance_hold.getMframe().getjTable_playlist().getColumnModel().getColumn(0);

                if (e.getNewState() == JFrame.ICONIFIED && Instance_hold.getPlayframe().isIconfied()) Instance_hold.getPlayframe().setState(JFrame.ICONIFIED);
                if (e.getOldState() == JFrame.ICONIFIED) Instance_hold.getPlayframe().setState(JFrame.NORMAL);
                if (e.getNewState() == 7 && Instance_hold.getPlayframe().isIconfied()) Instance_hold.getPlayframe().setState(JFrame.ICONIFIED);
                if (e.getOldState() == 7) Instance_hold.getPlayframe().setState(JFrame.NORMAL);
                if (e.getNewState() == 6) {
                    System.out.println("WIDTH: " + java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.5);
                    column.setPreferredWidth((int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.5));
                    Instance_hold.getPlayframe().setLocation((int)Instance_hold.getMframe().getLocation().getX()+178, (int)Instance_hold.getMframe().getLocation().getY()+75);
                    Instance_hold.getMframe().repaint();
                }
                if (e.getNewState() == 0) {
                    System.out.println("MFRAME WIDTH: " + Instance_hold.getMframe().getSize().width);
                    column.setPreferredWidth((int)(Instance_hold.getMframe().getSize().width*0.3));
                    Instance_hold.getPlayframe().setLocation((int)Instance_hold.getMframe().getLocation().getX()+178, (int)Instance_hold.getMframe().getLocation().getY()+75);
                }
            }      
        });
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean crdir = false;

                System.out.println("WINDOW CLOSING ACTION");
                
                Instance_hold.getVplay_mon().setExit(true);
                Instance_hold.getVplay_mon().setIrruptflag(1);
                
                try {
                    Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
                    Instance_hold.getFsf().getEmpc().getMediaPlayer().stop();
                }catch(NullPointerException exc) {
            
                }
                
                System.out.println("MainFrame: Waiting for VPlay release...");
                do {
                    Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }while (Instance_hold.getPlay().isAlive());
                Instance_hold.getVplay_mon().setExit(false);
                Instance_hold.getVplay_mon().setIrruptflag(0);
                Instance_hold.getMframe().getjLabel_show_status().setText("");
                
                Instance_hold.getSCPFrom_Monitor().setExit(true);
                Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);
                if (Instance_hold.getScpfrom().isAlive()) {
                    Instance_hold.getSCPFrom_Monitor().setClosechannelflag(true);
                    
                    System.out.println("MainFrame: Waiting for SCP release...");
                    do {
                        Instance_hold.getMframe().getjLabel_show_status().setText("SFTP Release...");
                        Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }while(Instance_hold.getScpfrom().isAlive());
                    Instance_hold.getSCPFrom_Monitor().setClosechannelflag(false);
                    
                    Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
                    Instance_hold.getSCPFrom_Monitor().setExit(false);
                    Instance_hold.getMframe().getjLabel_show_status().setText("");
                }

                Instance_hold.getSh_mon().setExit(true);
                while (Instance_hold.getSh().isAlive()) {
                    System.out.println("MainFrame: Waiting for Shell release...");
                    Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Instance_hold.getSh_mon().setExit(true);

                try {
                    File file = new File(Instance_data.getTmpPath());
                    MainControls.del(file);

                    crdir = file.mkdirs();
                }catch (NullPointerException exc) {
                    System.out.println("Temp-Path doesn't exist!!!");
                }

                Instance_hold.getFsf().getWmh().release();
                Instance_hold.getMframe().dispose();
                System.exit(0);
            }
        });
        
        System.out.println("WIDTH: " + java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        System.out.println("HEIGHT: " + java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        this.setLocation((int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-this.getWidth()/2), (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-this.getHeight()/2));
        this.setMinimumSize(this.getSize());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_Main = new javax.swing.JLabel();
        jLabel_Server = new javax.swing.JLabel();
        jTextField_Server = new javax.swing.JTextField();
        jButton_connect = new javax.swing.JButton();
        jTextField_Port = new javax.swing.JTextField();
        jLabel_Port = new javax.swing.JLabel();
        jLabel_object = new javax.swing.JLabel();
        jButton_addToPlaylist = new javax.swing.JButton();
        jProgressBar_main = new javax.swing.JProgressBar();
        jLabel_dir_info = new javax.swing.JLabel();
        jButton_up = new javax.swing.JButton();
        jButton_down = new javax.swing.JButton();
        jButton_delete = new javax.swing.JButton();
        jPanel_loadfile = new javax.swing.JPanel();
        jProgressBar_SCP = new javax.swing.JProgressBar();
        jLabel_src = new javax.swing.JLabel();
        jLabel_speedval = new javax.swing.JLabel();
        jLabel_speed = new javax.swing.JLabel();
        jButton_reset = new javax.swing.JButton();
        jPanel_nav = new javax.swing.JPanel();
        jSlider_Media = new javax.swing.JSlider();
        jSlider_vol = new javax.swing.JSlider();
        jLabel_prev = new javax.swing.JLabel();
        jLabel_time = new javax.swing.JLabel();
        jLabel_stop = new javax.swing.JLabel();
        jLabel_play = new javax.swing.JLabel();
        jLabel_volume = new javax.swing.JLabel();
        jLabel_next = new javax.swing.JLabel();
        jToggleButton_lock = new javax.swing.JToggleButton();
        jSplitPane_main = new javax.swing.JSplitPane();
        jScrollPane_Playlist = new javax.swing.JScrollPane();
        jTable_playlist = new javax.swing.JTable();
        jSplitPane_nav_playl = new javax.swing.JSplitPane();
        jScrollPane_navman = new javax.swing.JScrollPane();
        jTextPane_info = new javax.swing.JTextPane();
        jScrollPane_nav = new javax.swing.JScrollPane();
        jTree_nav = new javax.swing.JTree();
        jButton_refresh = new javax.swing.JButton();
        jButton_pl_sav = new javax.swing.JButton();
        jButton_pl_load = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel_info = new javax.swing.JLabel();
        jLabel_show_info = new javax.swing.JLabel();
        jLabel_status = new javax.swing.JLabel();
        jLabel_show_status = new javax.swing.JLabel();
        jLabel_playnm = new javax.swing.JLabel();
        jProgressBar_play_visual = new javax.swing.JProgressBar();
        jLabel_show_flnm = new javax.swing.JLabel();
        jButton_closecon = new javax.swing.JButton();
        jMenuBar_main = new javax.swing.JMenuBar();
        jMenu_File = new javax.swing.JMenu();
        jMenuItem_new = new javax.swing.JMenuItem();
        jMenuItem_exit = new javax.swing.JMenuItem();
        jMenu_Edit = new javax.swing.JMenu();
        jMenuItem_settings = new javax.swing.JMenuItem();
        jMenu_Help = new javax.swing.JMenu();
        jMenuItem_about = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(240, 240, 240));

        jLabel_Main.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_Main.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/logo_big.jpg"))); // NOI18N
        jLabel_Main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_MainMouseClicked(evt);
            }
        });

        jLabel_Server.setText("Server");

        jTextField_Server.setDisabledTextColor(new Color(0,0,0));

        jButton_connect.setText("connect");
        jButton_connect.setEnabled(false);
        jButton_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_connectActionPerformed(evt);
            }
        });

        jTextField_Port.setDisabledTextColor(new Color(0,0,0));

        jLabel_Port.setText("Port");

        jLabel_object.setText("Objectinfo:");

        jButton_addToPlaylist.setBorderPainted(false);
        jButton_addToPlaylist.setContentAreaFilled(false);
        jButton_addToPlaylist.setFocusPainted(false);
        jButton_addToPlaylist.setOpaque(false);
        jButton_addToPlaylist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/plus.png"))); // NOI18N
        jButton_addToPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_addToPlaylistMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_addToPlaylistMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_addToPlaylistMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_addToPlaylistMouseReleased(evt);
            }
        });
        jButton_addToPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addToPlaylistActionPerformed(evt);
            }
        });

        jLabel_dir_info.setText("foo");

        jButton_up.setBorderPainted(false);
        jButton_up.setContentAreaFilled(false);
        jButton_up.setFocusPainted(false);
        jButton_up.setOpaque(false);
        jButton_up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/pfeil_kl.png"))); // NOI18N
        jButton_up.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_upMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_upMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_upMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_upMouseReleased(evt);
            }
        });
        jButton_up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_upActionPerformed(evt);
            }
        });

        jButton_down.setBorderPainted(false);
        jButton_down.setContentAreaFilled(false);
        jButton_down.setFocusPainted(false);
        jButton_down.setOpaque(false);
        jButton_down.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/pfeil_kl_unten.png"))); // NOI18N
        jButton_down.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_downMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_downMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_downMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_downMouseReleased(evt);
            }
        });
        jButton_down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_downActionPerformed(evt);
            }
        });

        jButton_delete.setBorderPainted(false);
        jButton_delete.setContentAreaFilled(false);
        jButton_delete.setFocusPainted(false);
        jButton_delete.setOpaque(false);
        jButton_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/delete.png"))); // NOI18N
        jButton_delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_deleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_deleteMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_deleteMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_deleteMouseReleased(evt);
            }
        });
        jButton_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteActionPerformed(evt);
            }
        });

        jLabel_speedval.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel_speed.setText("kB/s");

        jButton_reset.setVisible(false);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        jButton_reset.setBorder(emptyBorder);
        jButton_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/stop_dl.png"))); // NOI18N
        jButton_reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_resetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_resetMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_resetMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_resetMouseReleased(evt);
            }
        });
        jButton_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_loadfileLayout = new javax.swing.GroupLayout(jPanel_loadfile);
        jPanel_loadfile.setLayout(jPanel_loadfileLayout);
        jPanel_loadfileLayout.setHorizontalGroup(
            jPanel_loadfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar_SCP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_loadfileLayout.createSequentialGroup()
                .addComponent(jLabel_src)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_speedval, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_speed)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_loadfileLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton_reset))
        );
        jPanel_loadfileLayout.setVerticalGroup(
            jPanel_loadfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_loadfileLayout.createSequentialGroup()
                .addComponent(jButton_reset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel_loadfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_speed, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_speedval, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_src, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar_SCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSlider_Media.setMaximum(1000);
        MouseListener[] media_listeners = jSlider_Media.getMouseListeners();
        for (MouseListener l : media_listeners) jSlider_Media.removeMouseListener(l); // remove UI-installed TrackListener
        jSlider_Media.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSlider_Media.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jSlider_MediaMousePressed(evt);
            }
        });

        jSlider_vol.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jSlider_volMouseDragged(evt);
            }
        });
        jSlider_vol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jSlider_volMousePressed(evt);
            }
        });

        jLabel_prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/mcb_grey_prev_small.png"))); // NOI18N
        jLabel_prev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_prevMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_prevMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_prevMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_prevMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel_prevMouseReleased(evt);
            }
        });

        jLabel_time.setText("0:00");

        jLabel_stop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/mcb_grey_stop_small.png"))); // NOI18N
        jLabel_stop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_stopMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_stopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_stopMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_stopMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel_stopMouseReleased(evt);
            }
        });

        jLabel_play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/mcb_grey_pause_small.png"))); // NOI18N
        jLabel_play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_playMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_playMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_playMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_playMousePressed(evt);
            }
        });

        jLabel_volume.setText("Volume");

        jLabel_next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/mcb_grey_fwd_small.png"))); // NOI18N
        jLabel_next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_nextMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_nextMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_nextMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_nextMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel_nextMouseReleased(evt);
            }
        });

        jToggleButton_lock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/playframe_toggle.png"))); // NOI18N
        jToggleButton_lock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_lockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_navLayout = new javax.swing.GroupLayout(jPanel_nav);
        jPanel_nav.setLayout(jPanel_navLayout);
        jPanel_navLayout.setHorizontalGroup(
            jPanel_navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_navLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_play)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_stop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_time, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_prev)
                .addGap(18, 18, 18)
                .addComponent(jLabel_next)
                .addGap(39, 39, 39)
                .addComponent(jToggleButton_lock, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jLabel_volume)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider_vol, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addComponent(jSlider_Media, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_navLayout.setVerticalGroup(
            jPanel_navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_navLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSlider_Media, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSlider_vol, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_stop)
                        .addComponent(jLabel_play)
                        .addComponent(jLabel_prev, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_next, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton_lock, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_volume, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel_time, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(32, 32, 32))
        );

        jSplitPane_main.setDividerLocation(657);

        jTable_playlist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Length"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_playlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_playlistMouseClicked(evt);
            }
        });
        jTable_playlist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable_playlistKeyTyped(evt);
            }
        });
        jScrollPane_Playlist.setViewportView(jTable_playlist);

        jSplitPane_main.setRightComponent(jScrollPane_Playlist);

        //jSplitPane_nav_playl.setEnabled(false);
        jSplitPane_nav_playl.setDividerSize(0);
        jSplitPane_nav_playl.setDividerLocation(31);
        jSplitPane_nav_playl.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jTextPane_info.setDisabledTextColor(new Color(0,0,0));
        jTextPane_info.setMinimumSize(new java.awt.Dimension(6, 31));
        jScrollPane_navman.setViewportView(jTextPane_info);

        jSplitPane_nav_playl.setLeftComponent(jScrollPane_navman);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("/");
        jTree_nav.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane_nav.setViewportView(jTree_nav);

        jSplitPane_nav_playl.setBottomComponent(jScrollPane_nav);

        jSplitPane_main.setLeftComponent(jSplitPane_nav_playl);

        jButton_refresh.setBorderPainted(false);
        jButton_refresh.setContentAreaFilled(false);
        jButton_refresh.setFocusPainted(false);
        jButton_refresh.setOpaque(false);
        jButton_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/refresh.png"))); // NOI18N
        jButton_refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_refreshMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_refreshMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_refreshMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_refreshMouseReleased(evt);
            }
        });
        jButton_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_refreshActionPerformed(evt);
            }
        });

        jButton_pl_sav.setBorderPainted(false);
        jButton_pl_sav.setContentAreaFilled(false);
        jButton_pl_sav.setFocusPainted(false);
        jButton_pl_sav.setOpaque(false);
        jButton_pl_sav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/save_pl.png"))); // NOI18N
        jButton_pl_sav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_pl_savMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_pl_savMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_pl_savMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_pl_savMouseReleased(evt);
            }
        });
        jButton_pl_sav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_pl_savActionPerformed(evt);
            }
        });

        jButton_pl_load.setBorderPainted(false);
        jButton_pl_load.setContentAreaFilled(false);
        jButton_pl_load.setFocusPainted(false);
        jButton_pl_load.setOpaque(false);
        jButton_pl_load.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/load_pl.png"))); // NOI18N
        jButton_pl_load.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_pl_loadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_pl_loadMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_pl_loadMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_pl_loadMouseReleased(evt);
            }
        });
        jButton_pl_load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_pl_loadActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new Color(240,240,240));

        jLabel_info.setText("Info:");

        jLabel_show_info.setText("foo");

        jLabel_status.setText("Status:");

        jLabel_show_status.setText("foo");

        jLabel_playnm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_playnm.setText("Playing Now:");

        jLabel_show_flnm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_show_flnm.setText("foo");

        jButton_closecon.setBorderPainted(false);
        jButton_closecon.setContentAreaFilled(false);
        jButton_closecon.setFocusPainted(false);
        jButton_closecon.setOpaque(false);
        jButton_closecon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/wire.png"))); // NOI18N
        jButton_closecon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_closeconMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_closeconMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_closeconMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_closeconMouseReleased(evt);
            }
        });
        jButton_closecon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_closeconActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_info)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_show_info))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_show_status)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_playnm, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_show_flnm))
                    .addComponent(jProgressBar_play_visual, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_closecon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jButton_closecon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_playnm)
                            .addComponent(jLabel_show_flnm))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar_play_visual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_status)
                    .addComponent(jLabel_show_status))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_info)
                    .addComponent(jLabel_show_info))
                .addGap(31, 31, 31))
        );

        jMenu_File.setMnemonic('F');
        jMenu_File.setText("File");

        jMenuItem_new.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        jMenuItem_new.setText("New");
        jMenuItem_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_newActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_new);

        jMenuItem_exit.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        jMenuItem_exit.setText("Exit");
        jMenuItem_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_exitActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_exit);

        jMenuBar_main.add(jMenu_File);

        jMenu_Edit.setMnemonic('E');
        jMenu_Edit.setText("Edit");

        jMenuItem_settings.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        jMenuItem_settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/settings.png"))); // NOI18N
        jMenuItem_settings.setText("Settings");
        jMenuItem_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_settingsActionPerformed(evt);
            }
        });
        jMenu_Edit.add(jMenuItem_settings);

        jMenuBar_main.add(jMenu_Edit);

        jMenu_Help.setMnemonic('H');
        jMenu_Help.setText("Help");

        jMenuItem_about.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/info.png"))); // NOI18N
        jMenuItem_about.setText("about");
        jMenuItem_about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_aboutActionPerformed(evt);
            }
        });
        jMenu_Help.add(jMenuItem_about);

        jMenuBar_main.add(jMenu_Help);

        setJMenuBar(jMenuBar_main);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane_main)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel_Server)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_Server, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel_Port)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_Port, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel_object)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel_dir_info)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton_connect)
                                    .addComponent(jButton_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jProgressBar_main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_addToPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel_Main)
                                .addGap(30, 30, 30)
                                .addComponent(jPanel_nav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel_loadfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_pl_sav, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_pl_load, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_down, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_up, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel_Main, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jPanel_nav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Server, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Server)
                            .addComponent(jTextField_Port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Port)
                            .addComponent(jButton_connect)
                            .addComponent(jButton_addToPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar_main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel_object)
                                .addComponent(jLabel_dir_info))))
                    .addComponent(jButton_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_loadfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton_up, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_down, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(jButton_pl_load)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_pl_sav, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSplitPane_main, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_connectActionPerformed
        System.out.println("CONNECTION STARTED");
        Instance_hold.getSetframe().getjButton_import().setEnabled(false);
        Instance_hold.getSetframe().getjButton_ok().setEnabled(false);

        if (this.jTextPane_info.getText().equals("")) {
            Instance_data.setLs("/");
        }else Instance_data.setLs(this.jTextPane_info.getText());
        
        MainControls.connect();
    }//GEN-LAST:event_jButton_connectActionPerformed

    private void jButton_addToPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addToPlaylistActionPerformed
        MainControls.addToPlaylist();
    }//GEN-LAST:event_jButton_addToPlaylistActionPerformed

    private void jTable_playlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_playlistMouseClicked
        if (evt.getClickCount() >= 2) {
            this.jPanel_nav.setEnabled(false);
            Instance_hold.getPlayframe().getjPanel_nav().setEnabled(false);

            this.jProgressBar_main.setIndeterminate(true);
            this.disable_Objects();
            this.jButton_reset.setVisible(true);                
            Instance_hold.getPlayframe().disable_Objects();

            MainControls.playMedia();

            WaitThread wt = new WaitThread();
            wt.start();

            Instance_hold.getPlayframe().getjPanel_nav().setEnabled(true);
            this.jPanel_nav.setEnabled(true);

            interrupted = false;
            this.stop = false;
            Instance_hold.getPlayframe().setStop(false);
            Instance_hold.getFsnt().setStop(false);
        }
    }//GEN-LAST:event_jTable_playlistMouseClicked

    private void jMenuItem_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_newActionPerformed
        Instance_hold.getPlayframe().setAlwaysOnTop(false);
        
        Object[] options = {"Yes, please",
                    "No, thanks"};
        int n = JOptionPane.showOptionDialog(null,
                "Are you sure to create a new session? ",                
                "New Session",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (n == 0) {
            MainControls.newSession();
        }
        
        Instance_hold.getPlayframe().setAlwaysOnTop(true);
    }//GEN-LAST:event_jMenuItem_newActionPerformed

    private void jButton_upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_upActionPerformed
        MainControls.upInPlaylist();
    }//GEN-LAST:event_jButton_upActionPerformed

    private void jButton_downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_downActionPerformed
        MainControls.downInPlaylist();
    }//GEN-LAST:event_jButton_downActionPerformed

    private void jButton_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteActionPerformed
        MainControls.deleteFromPlaylist();       
    }//GEN-LAST:event_jButton_deleteActionPerformed

    private void jTable_playlistKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_playlistKeyTyped
        if (evt.getKeyChar() == '\n') {
            System.out.println("Selected Row. " + this.jTable_playlist.getSelectedRow());
            if (this.jTable_playlist.getSelectedRow() >= 0) {
                this.jProgressBar_main.setIndeterminate(true);
                //this.setEnabled(false);
                this.disable_Objects();
                this.jButton_reset.setVisible(true);
                //Instance_hold.getPlayframe().setEnabled(false);
                Instance_hold.getPlayframe().disable_Objects();
                this.jTable_playlist.getSelectionModel().setSelectionInterval(this.jTable_playlist.getSelectedRow()-1, this.jTable_playlist.getSelectedRow()-1);
                MainControls.playMedia();

                WaitThread wt = new WaitThread();
                wt.start();        

                interrupted = false;
            }
        }
        
        if ((int)evt.getKeyChar() == KeyEvent.VK_DELETE) {
            MainControls.deleteFromPlaylist();
        }
    }//GEN-LAST:event_jTable_playlistKeyTyped

    private void jLabel_MainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MainMouseClicked
        System.out.println("CLICKED");
    }//GEN-LAST:event_jLabel_MainMouseClicked

    private void jMenuItem_aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_aboutActionPerformed
        Instance_hold.getPlayframe().toBack();
        JOptionPane.showMessageDialog(this, "MultiPlayer\nVersion 2.6\nLicence: Freeware\n\nDesigned by Robert Mautz");
        Instance_hold.getPlayframe().toFront();
        Instance_hold.getPlayframe().setAlwaysOnTop(true);
    }//GEN-LAST:event_jMenuItem_aboutActionPerformed

    private void jMenuItem_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_exitActionPerformed
        boolean crdir = false;
        
        Object[] options = {"Yes, please",
                    "No, thanks"};
        int n = JOptionPane.showOptionDialog(null,
                "Are you sure to Exit? ",                
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (n == 0) {
            System.out.println("WINDOW CLOSING ACTION");

            Instance_hold.getVplay_mon().setExit(true);
            Instance_hold.getVplay_mon().setIrruptflag(1);
            
            try {
                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
                Instance_hold.getFsf().getEmpc().getMediaPlayer().stop();
                //Instance_hold.getPlayframe().getEmpc().getMediaPlayer().release();
                //Instance_hold.getFsf().getEmpc().getMediaPlayer().release();
            }catch(NullPointerException exc) {
            
            }
            do {
                System.out.println("MainFrame: Waiting for VPlay release...");
                Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while (Instance_hold.getPlay().isAlive());
            Instance_hold.getVplay_mon().setExit(false);
            Instance_hold.getVplay_mon().setIrruptflag(0);
            Instance_hold.getMframe().getjLabel_show_status().setText("");

            Instance_hold.getSCPFrom_Monitor().setExit(true);
            Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);
            if (Instance_hold.getScpfrom().isAlive()) {
                Instance_hold.getSCPFrom_Monitor().setClosechannelflag(true);
                
                System.out.println("MainFrame: Waiting for SCP release...");
                do {
                        Instance_hold.getMframe().getjLabel_show_status().setText("SFTP Release...");
                    Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }while(Instance_hold.getScpfrom().isAlive());
                Instance_hold.getSCPFrom_Monitor().setClosechannelflag(false);

                Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
                Instance_hold.getSCPFrom_Monitor().setExit(false);
                Instance_hold.getMframe().getjLabel_show_status().setText("SFTP Release...");
            }

            Instance_hold.getSh_mon().setExit(true);
            while (Instance_hold.getSh().isAlive()) {
                System.out.println("MainFrame: Waiting for Shell release...");
                Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Instance_hold.getSh_mon().setExit(true);

            try {
                File file = new File(Instance_data.getTmpPath());
                MainControls.del(file);

                crdir = file.mkdirs();
            }catch (NullPointerException exc) {
                System.out.println("Temp-Path doesn't exist!!!");
            }

            Instance_hold.getFsf().getWmh().release();
            Instance_hold.getMframe().dispose();
            System.exit(0);
        } 
    }//GEN-LAST:event_jMenuItem_exitActionPerformed

    private void jLabel_prevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_prevMouseClicked
        Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true); 
        this.disable_Objects();
        this.jButton_reset.setVisible(true);
        Instance_hold.getPlayframe().disable_Objects();
        MainControls.prevMedia();

        while(!Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying() && !Instance_hold.getFsf().getEmpc().getMediaPlayer().isPlaying()) {
            Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.enable_Objects();
        this.jButton_reset.setVisible(false);
        Instance_hold.getPlayframe().enable_Objects();
    }//GEN-LAST:event_jLabel_prevMouseClicked

    private void jLabel_prevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_prevMouseEntered
        this.jLabel_prev.setIcon(Instance_hold.getIm_hold().getMcb_blue_prev_small());
    }//GEN-LAST:event_jLabel_prevMouseEntered

    private void jLabel_prevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_prevMouseExited
        this.jLabel_prev.setIcon(Instance_hold.getIm_hold().getMcb_grey_prev_small());
    }//GEN-LAST:event_jLabel_prevMouseExited

    private void jLabel_prevMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_prevMousePressed
        this.jLabel_prev.setIcon(Instance_hold.getIm_hold().getMcb_green_prev_small());
    }//GEN-LAST:event_jLabel_prevMousePressed

    private void jLabel_prevMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_prevMouseReleased
        this.jLabel_prev.setIcon(Instance_hold.getIm_hold().getMcb_blue_prev_small());
    }//GEN-LAST:event_jLabel_prevMouseReleased

    private void jLabel_stopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_stopMouseClicked
        Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
        while(Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
            Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_grey_play_small());
        this.jSlider_Media.setValue(0);
        stop = true;
    }//GEN-LAST:event_jLabel_stopMouseClicked

    private void jLabel_stopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_stopMouseEntered
        this.jLabel_stop.setIcon(Instance_hold.getIm_hold().getMcb_blue_stop_small());
    }//GEN-LAST:event_jLabel_stopMouseEntered

    private void jLabel_stopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_stopMouseExited
        this.jLabel_stop.setIcon(Instance_hold.getIm_hold().getMcb_grey_stop_small());
    }//GEN-LAST:event_jLabel_stopMouseExited

    private void jLabel_stopMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_stopMousePressed
        this.jLabel_stop.setIcon(Instance_hold.getIm_hold().getMcb_green_stop_small());
    }//GEN-LAST:event_jLabel_stopMousePressed

    private void jLabel_stopMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_stopMouseReleased
        this.jLabel_stop.setIcon(Instance_hold.getIm_hold().getMcb_blue_stop_small());
    }//GEN-LAST:event_jLabel_stopMouseReleased

    private void jLabel_playMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_playMouseClicked
        int volume = 0;

        this.disable_Objects();
        this.jButton_reset.setVisible(true);
        Instance_hold.getPlayframe().disable_Objects();

        System.out.println("Media Player Sate: " + Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getMediaPlayerState());

        if (Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getMediaPlayerState().toString().equals("libvlc_Stopped") && !Instance_hold.getPlay().isAlive()) {
            Instance_hold.getVplay_mon().setInterrupted_play(true);
        }else if(Instance_hold.getPlay().isAlive() && Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getMediaPlayerState().toString().equals("libvlc_Ended")) {
            Instance_hold.getVplay_mon().setInterrupted_play(true);
        }else if (!Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying() && Instance_hold.getPlay().isAlive() && !Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getMediaPlayerState().equals("libvlc_Error") && !Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getMediaPlayerState().equals("false")) {
            this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_blue_pause_small());

            volume = Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume();
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume(0);

            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().start();
            
            for (int i=0;i<=volume;i++) {                    
                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume((Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume()+1));
                Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            stop = false;
        }
        else if (Instance_hold.getPlay().isAlive() && !Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getMediaPlayerState().equals("libvlc_Error") && !Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getMediaPlayerState().equals("false")) {
            this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_blue_play_small());

            volume = Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume();
            for (int i=0;i<200;i++) {                    
                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume((Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume()-1));
                Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().pause();
            while(Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            System.out.println("Volume: " + Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume());
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume(volume-1);
            System.out.println("Volume: " + Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume());

            stop = true;
        }else {
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().stop();
            while(Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_grey_play_small());
            this.jSlider_Media.setValue(0);
            stop = true;
        }
        
        this.enable_Objects();
        this.jButton_reset.setVisible(false);
        Instance_hold.getPlayframe().enable_Objects();
    }//GEN-LAST:event_jLabel_playMouseClicked

    private void jLabel_playMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_playMouseEntered
        if (stop) this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_blue_play_small());
        else this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_blue_pause_small());
    }//GEN-LAST:event_jLabel_playMouseEntered

    private void jLabel_playMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_playMouseExited
        if (stop) this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_grey_play_small());
        else this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_grey_pause_small());
    }//GEN-LAST:event_jLabel_playMouseExited

    private void jLabel_playMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_playMousePressed
        if (stop) {
            this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_green_play_small());
        }else {
            this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_green_pause_small());
        }
    }//GEN-LAST:event_jLabel_playMousePressed

    private void jLabel_nextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_nextMouseClicked
        this.disable_Objects();
        this.jButton_reset.setVisible(true);
        Instance_hold.getPlayframe().disable_Objects();

        Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true);

        MainControls.nextMedia();

        while(!Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying() && !Instance_hold.getFsf().getEmpc().getMediaPlayer().isPlaying()) {
            Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.enable_Objects();
        this.jButton_reset.setVisible(false);
        Instance_hold.getPlayframe().enable_Objects();
    }//GEN-LAST:event_jLabel_nextMouseClicked

    private void jLabel_nextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_nextMouseEntered
        this.jLabel_next.setIcon(Instance_hold.getIm_hold().getMcb_blue_fwd_small());
    }//GEN-LAST:event_jLabel_nextMouseEntered

    private void jLabel_nextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_nextMouseExited
        this.jLabel_next.setIcon(Instance_hold.getIm_hold().getMcb_grey_fwd_small());
    }//GEN-LAST:event_jLabel_nextMouseExited

    private void jLabel_nextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_nextMousePressed
        this.jLabel_next.setIcon(Instance_hold.getIm_hold().getMcb_green_fwd_small());
    }//GEN-LAST:event_jLabel_nextMousePressed

    private void jLabel_nextMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_nextMouseReleased
        this.jLabel_next.setIcon(Instance_hold.getIm_hold().getMcb_blue_fwd_small());
    }//GEN-LAST:event_jLabel_nextMouseReleased

    private void jToggleButton_lockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_lockActionPerformed
        this.jPanel_nav.setVisible(false);
        Instance_hold.getPlayframe().setVisible(true);
        Instance_hold.getPlayframe().getjLabel_play().setIcon(this.jLabel_play.getIcon());
        Instance_hold.getPlayframe().getjLabel_stop().setIcon(this.jLabel_stop.getIcon());
        Instance_hold.getPlayframe().getjLabel_prev().setIcon(this.jLabel_prev.getIcon());
        Instance_hold.getPlayframe().getjLabel_next().setIcon(this.jLabel_next.getIcon());
        Instance_hold.getPlayframe().setStop(this.stop);
        Instance_hold.getPlayframe().getjToggleButton_lock().setSelected(false);
    }//GEN-LAST:event_jToggleButton_lockActionPerformed

    private void jButton_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_refreshActionPerformed
        DefaultMutableTreeNode dmtn_test = null;
        System.out.println(top.getChildCount());
        for (int i=0;i<top.getChildCount();i++) {
            dmtn_test = (DefaultMutableTreeNode)top.getChildAt(i);
            dmtn_test.removeAllChildren();
            dmtn_test.add(new DefaultMutableTreeNode("content of " + dmtn_test.toString()));
        }
        
        ((DefaultTreeModel)Instance_hold.getMframe().getjTree_nav().getModel()).reload();
    }//GEN-LAST:event_jButton_refreshActionPerformed

    private void jButton_pl_savActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pl_savActionPerformed
        ArrayList<String> pl_entries = Instance_hold.getPl().getEntries();
        
        if (Instance_data.isPlLoadEnabled()) {
            JFileChooser fc = new JFileChooser();
            fc.setVisible(true);
            FC_PL_sav listener = new FC_PL_sav();
            listener.setJfc(fc);
            listener.setPlentries(pl_entries);
            fc.addActionListener(listener);
            fc.showSaveDialog(this);
        }
    }//GEN-LAST:event_jButton_pl_savActionPerformed

    private void jButton_pl_loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pl_loadActionPerformed
        if (Instance_data.isPlLoadEnabled()) {
            JFileChooser fc = new JFileChooser();
            fc.setVisible(true);
            FC_PL_load listener = new FC_PL_load();
            listener.setJfc(fc);
            listener.setMframe(this);
            fc.addActionListener(listener);
            fc.showOpenDialog(this);
        } 
    }//GEN-LAST:event_jButton_pl_loadActionPerformed

    private void jMenuItem_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_settingsActionPerformed
        Instance_hold.getSetframe().setVisible(true);
    }//GEN-LAST:event_jMenuItem_settingsActionPerformed

    private void jButton_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetActionPerformed
        if (Instance_hold.getScpfrom().getChannel() != null) {
            Instance_hold.getScpfrom().getChannel().disconnect();
            Instance_hold.getScpfrom().getChannel().exit();
        }
    }//GEN-LAST:event_jButton_resetActionPerformed

    private void jButton_resetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_resetMousePressed
        this.jButton_reset.setIcon(new ImageIcon(getClass().getResource("/gui/design/stop_dl_small.png")));
    }//GEN-LAST:event_jButton_resetMousePressed

    private void jButton_resetMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_resetMouseReleased
        this.jButton_reset.setIcon(new ImageIcon(getClass().getResource("/gui/design/stop_dl_big.png")));
    }//GEN-LAST:event_jButton_resetMouseReleased

    private void jButton_resetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_resetMouseEntered
        this.jButton_reset.setIcon(new ImageIcon(getClass().getResource("/gui/design/stop_dl_big.png")));
    }//GEN-LAST:event_jButton_resetMouseEntered

    private void jButton_resetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_resetMouseExited
        this.jButton_reset.setIcon(new ImageIcon(getClass().getResource("/gui/design/stop_dl.png")));
    }//GEN-LAST:event_jButton_resetMouseExited

    private void jSlider_volMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_volMouseDragged
        Instance_hold.getPlayframe().setVo_value(jSlider_vol.getValue());
        Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume(Instance_hold.getPlayframe().getVo_value());
        Instance_hold.getPlayframe().getjSlider_vol().setValue(Instance_hold.getPlayframe().getVo_value());
        Instance_hold.getFsnt().getjSlider_vol().setValue(Instance_hold.getPlayframe().getVo_value());
    }//GEN-LAST:event_jSlider_volMouseDragged

    private void jSlider_volMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_volMousePressed
        final BasicSliderUI ui = (BasicSliderUI) jSlider_vol.getUI();
        
        Point p = evt.getPoint();
        int value = ui.valueForXPosition(p.x);

        jSlider_vol.setValue(value);
        Instance_hold.getPlayframe().setVo_value(value);
        Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume(Instance_hold.getPlayframe().getVo_value());
        Instance_hold.getPlayframe().getjSlider_vol().setValue(Instance_hold.getPlayframe().getVo_value());
        Instance_hold.getFsnt().getjSlider_vol().setValue(Instance_hold.getPlayframe().getVo_value());
    }//GEN-LAST:event_jSlider_volMousePressed

    private void jSlider_MediaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_MediaMousePressed
        final BasicSliderUI media_ui = (BasicSliderUI) jSlider_Media.getUI();
        
        Point p = evt.getPoint();
        int value = media_ui.valueForXPosition(p.x);
        
        System.out.println("SLIDER: " + ((double)((double)value/(double)jSlider_Media.getMaximum())*100));
        System.out.println("PROGRESS BAR: " + Instance_hold.getMframe().getjProgressBar_SCP().getValue());
        
        if (!Instance_data.isSkip()) {
            if ((((double)((double)value/(double)jSlider_Media.getMaximum())*100)+5) < Instance_hold.getMframe().getjProgressBar_SCP().getValue()) {
                if (Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                    int volume = 0;

                    volume = Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume();
                    Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume(0);

                    Instance_hold.getPlayframe().getEmpc().getMediaPlayer().pause();
                    while(Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                        Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    System.out.println(value);
                    System.out.println(jSlider_Media.getMaximum());
                    System.out.println("Time Data: " + (double)((double)value/(double)jSlider_Media.getMaximum()));

                    Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setTime((int)(Instance_data.getMedia_length()*(double)((double)value/(double)jSlider_Media.getMaximum())));
                    Instance_hold.getPlayframe().getEmpc().getMediaPlayer().start();

                    Instance_hold.getMframe().getjLabel_show_status().setText("Waiting for Media Release");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    for (int i=0;i<volume;i++) {                    
                        Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume((Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume()+1));
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }else {
            if (Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                int volume = 0;

                volume = Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume();
                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume(0);

                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().pause();
                while(Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                System.out.println(value);
                System.out.println(jSlider_Media.getMaximum());
                System.out.println("Time Data: " + (double)((double)value/(double)jSlider_Media.getMaximum()));

                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setTime((int)(Instance_data.getMedia_length()*(double)((double)value/(double)jSlider_Media.getMaximum())));
                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().start();

                try {
                    Thread.sleep(40);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int i=0;i<volume;i++) {                    
                    Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume((Instance_hold.getPlayframe().getEmpc().getMediaPlayer().getVolume()+1));
                    try {
                        Thread.sleep(9);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_jSlider_MediaMousePressed

    private void jButton_addToPlaylistMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_addToPlaylistMouseEntered
        this.jLabel_show_info.setText("Add to Playlist");
        this.jButton_addToPlaylist.setIcon(new ImageIcon(getClass().getResource("/gui/design/plus_big.png")));
    }//GEN-LAST:event_jButton_addToPlaylistMouseEntered

    private void jButton_addToPlaylistMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_addToPlaylistMouseExited
        this.jLabel_show_info.setText("");
        this.jButton_addToPlaylist.setIcon(new ImageIcon(getClass().getResource("/gui/design/plus.png")));
    }//GEN-LAST:event_jButton_addToPlaylistMouseExited

    private void jButton_addToPlaylistMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_addToPlaylistMousePressed
        this.jButton_addToPlaylist.setIcon(new ImageIcon(getClass().getResource("/gui/design/plus_small.png")));
    }//GEN-LAST:event_jButton_addToPlaylistMousePressed

    private void jButton_addToPlaylistMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_addToPlaylistMouseReleased
        this.jButton_addToPlaylist.setIcon(new ImageIcon(getClass().getResource("/gui/design/plus_big.png")));
    }//GEN-LAST:event_jButton_addToPlaylistMouseReleased

    private void jButton_refreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_refreshMouseEntered
        this.jButton_refresh.setIcon(new ImageIcon(getClass().getResource("/gui/design/refresh_big.png")));
        this.jLabel_show_info.setText("Refresh Folders");
    }//GEN-LAST:event_jButton_refreshMouseEntered

    private void jButton_refreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_refreshMouseExited
        this.jButton_refresh.setIcon(new ImageIcon(getClass().getResource("/gui/design/refresh.png")));
        this.jLabel_show_info.setText("");
    }//GEN-LAST:event_jButton_refreshMouseExited

    private void jButton_refreshMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_refreshMousePressed
        this.jButton_refresh.setIcon(new ImageIcon(getClass().getResource("/gui/design/refresh_small.png")));
    }//GEN-LAST:event_jButton_refreshMousePressed

    private void jButton_refreshMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_refreshMouseReleased
        this.jButton_refresh.setIcon(new ImageIcon(getClass().getResource("/gui/design/refresh_big.png")));
    }//GEN-LAST:event_jButton_refreshMouseReleased

    private void jButton_deleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_deleteMouseEntered
        this.jButton_delete.setIcon(new ImageIcon(getClass().getResource("/gui/design/delete_big.png")));
        this.jLabel_show_info.setText("Delete from Playlist");
    }//GEN-LAST:event_jButton_deleteMouseEntered

    private void jButton_deleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_deleteMouseExited
        this.jButton_delete.setIcon(new ImageIcon(getClass().getResource("/gui/design/delete.png")));
        this.jLabel_show_info.setText("");
    }//GEN-LAST:event_jButton_deleteMouseExited

    private void jButton_deleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_deleteMousePressed
        this.jButton_delete.setIcon(new ImageIcon(getClass().getResource("/gui/design/delete_small.png")));
    }//GEN-LAST:event_jButton_deleteMousePressed

    private void jButton_deleteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_deleteMouseReleased
        this.jButton_delete.setIcon(new ImageIcon(getClass().getResource("/gui/design/delete_big.png")));
    }//GEN-LAST:event_jButton_deleteMouseReleased

    private void jButton_upMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_upMouseEntered
        this.jButton_up.setIcon(new ImageIcon(getClass().getResource("/gui/design/pfeil_kl_big.png")));
        this.jLabel_show_info.setText("Up in Playlist");
    }//GEN-LAST:event_jButton_upMouseEntered

    private void jButton_upMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_upMouseReleased
        this.jButton_up.setIcon(new ImageIcon(getClass().getResource("/gui/design/pfeil_kl_big.png")));
    }//GEN-LAST:event_jButton_upMouseReleased

    private void jButton_upMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_upMousePressed
        this.jButton_up.setIcon(new ImageIcon(getClass().getResource("/gui/design/pfeil_kl_small.png")));
    }//GEN-LAST:event_jButton_upMousePressed

    private void jButton_upMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_upMouseExited
        this.jButton_up.setIcon(new ImageIcon(getClass().getResource("/gui/design/pfeil_kl.png")));
        this.jLabel_show_info.setText("");
    }//GEN-LAST:event_jButton_upMouseExited

    private void jButton_downMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_downMouseEntered
        this.jButton_down.setIcon(new ImageIcon(getClass().getResource("/gui/design/pfeil_kl_unten_big.png")));
        this.jLabel_show_info.setText("Down in Playlist");
    }//GEN-LAST:event_jButton_downMouseEntered

    private void jButton_downMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_downMouseExited
        this.jButton_down.setIcon(new ImageIcon(getClass().getResource("/gui/design/pfeil_kl_unten.png")));
        this.jLabel_show_info.setText("");
    }//GEN-LAST:event_jButton_downMouseExited

    private void jButton_downMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_downMousePressed
        this.jButton_down.setIcon(new ImageIcon(getClass().getResource("/gui/design/pfeil_kl_unten_small.png")));
    }//GEN-LAST:event_jButton_downMousePressed

    private void jButton_downMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_downMouseReleased
        this.jButton_down.setIcon(new ImageIcon(getClass().getResource("/gui/design/pfeil_kl_unten_big.png")));
    }//GEN-LAST:event_jButton_downMouseReleased

    private void jButton_pl_savMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_pl_savMouseEntered
        this.jButton_pl_sav.setIcon(new ImageIcon(getClass().getResource("/gui/design/save_pl_big.png")));
        this.jLabel_show_info.setText("Save Playlist");
    }//GEN-LAST:event_jButton_pl_savMouseEntered

    private void jButton_pl_savMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_pl_savMouseExited
        this.jButton_pl_sav.setIcon(new ImageIcon(getClass().getResource("/gui/design/save_pl.png")));
        this.jLabel_show_info.setText("");
    }//GEN-LAST:event_jButton_pl_savMouseExited

    private void jButton_pl_savMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_pl_savMousePressed
        this.jButton_pl_sav.setIcon(new ImageIcon(getClass().getResource("/gui/design/save_pl_small.png")));
    }//GEN-LAST:event_jButton_pl_savMousePressed

    private void jButton_pl_savMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_pl_savMouseReleased
        this.jButton_pl_sav.setIcon(new ImageIcon(getClass().getResource("/gui/design/save_pl_big.png")));
    }//GEN-LAST:event_jButton_pl_savMouseReleased

    private void jButton_pl_loadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_pl_loadMouseEntered
        this.jButton_pl_load.setIcon(new ImageIcon(getClass().getResource("/gui/design/load_pl_big.png")));
        this.jLabel_show_info.setText("Load Playlist");
    }//GEN-LAST:event_jButton_pl_loadMouseEntered

    private void jButton_pl_loadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_pl_loadMouseExited
        this.jButton_pl_load.setIcon(new ImageIcon(getClass().getResource("/gui/design/load_pl.png")));
        this.jLabel_show_info.setText("");
    }//GEN-LAST:event_jButton_pl_loadMouseExited

    private void jButton_pl_loadMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_pl_loadMousePressed
        this.jButton_pl_load.setIcon(new ImageIcon(getClass().getResource("/gui/design/load_pl_small.png")));
    }//GEN-LAST:event_jButton_pl_loadMousePressed

    private void jButton_pl_loadMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_pl_loadMouseReleased
        this.jButton_pl_load.setIcon(new ImageIcon(getClass().getResource("/gui/design/load_pl_big.png")));
    }//GEN-LAST:event_jButton_pl_loadMouseReleased

    private void jButton_closeconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_closeconActionPerformed
        Instance_hold.getSCPFrom_Monitor().setExit(true);
        Instance_hold.getSCPFrom_Monitor().setIrruptflag(1);
        if (Instance_hold.getScpfrom().isAlive()) {
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(true);
            
            System.out.println("MainFrame: Waiting for SCP release...");
            do {
                Instance_hold.getMframe().getjLabel_show_status().setText("SFTP Release...");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(Instance_hold.getScpfrom().isAlive());
            Instance_hold.getSCPFrom_Monitor().setClosechannelflag(false);
            Instance_hold.getMframe().getjLabel_show_status().setText("");
        }   
        
        Instance_hold.getSCPFrom_Monitor().setClosedlmonitor(false);
        Instance_hold.getSCPFrom_Monitor().setExit(false);
        Instance_hold.getSCPFrom_Monitor().setDlfinish(false);
        Instance_hold.getSCPFrom_Monitor().setMonitorisclosed(false);
        Instance_hold.getSCPFrom_Monitor().setNewsession(false);
        Instance_hold.getSCPFrom_Monitor().setRdytoplay(false);
        Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
    }//GEN-LAST:event_jButton_closeconActionPerformed

    private void jButton_closeconMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_closeconMouseEntered
        this.jButton_closecon.setIcon(new ImageIcon(getClass().getResource("/gui/design/wire_big.png")));
        this.jLabel_show_info.setText("Close Download Connection");
    }//GEN-LAST:event_jButton_closeconMouseEntered

    private void jButton_closeconMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_closeconMouseExited
        this.jButton_closecon.setIcon(new ImageIcon(getClass().getResource("/gui/design/wire.png")));
        this.jLabel_show_info.setText("");
    }//GEN-LAST:event_jButton_closeconMouseExited

    private void jButton_closeconMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_closeconMousePressed
        this.jButton_closecon.setIcon(new ImageIcon(getClass().getResource("/gui/design/wire_small.png")));
    }//GEN-LAST:event_jButton_closeconMousePressed

    private void jButton_closeconMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_closeconMouseReleased
        this.jButton_closecon.setIcon(new ImageIcon(getClass().getResource("/gui/design/wire_big.png")));
    }//GEN-LAST:event_jButton_closeconMouseReleased

    public void createTopNodes(List<Node_entry> nodelist) {
        for (int i=0;i<nodelist.size();i++) {
            DefaultMutableTreeNode dmtn =  new DefaultMutableTreeNode(nodelist.get(i).getName());
            if (nodelist.get(i).getType() == 'd') dmtn.add(new DefaultMutableTreeNode("content of " + nodelist.get(i).getName()));
            
            top.add(dmtn);
        }         
    }

    public void addNodestoBranch(List<Node_entry> nodelist, TreePath path) {
        DefaultMutableTreeNode lastcomp = (DefaultMutableTreeNode) path.getLastPathComponent(); 

        if (nodelist.size() > 0) lastcomp.remove(0);
    
        for (int i=0;i<nodelist.size();i++) {
            DefaultMutableTreeNode dmtn =  new DefaultMutableTreeNode(nodelist.get(i).getName());
            if (nodelist.get(i).getType() == 'd') dmtn.add(new DefaultMutableTreeNode("content of " + nodelist.get(i).getName()));
            lastcomp.add(dmtn);
        }
    }

    public StringBuffer getPathfromTree(String str) {
        String selpath = str.toString();
        StringBuffer parts = new StringBuffer("");
        StringBuffer vidpath = new StringBuffer("");
        selpath = new StringBuffer(selpath).deleteCharAt(0).toString();
        selpath = new StringBuffer(selpath).deleteCharAt(selpath.length()-1).toString();
        String[] selpathparts = selpath.split(",");
        
        vidpath.append("/");
        
        for (int i=0;i<selpathparts.length-1;i++) {
            parts.append(selpathparts[i]);
            parts.deleteCharAt(0);

            selpathparts[i] = parts.toString();
            vidpath.append(selpathparts[i]);
            parts = new StringBuffer("");
            if (i<selpathparts.length-1 && i!=0) vidpath.append("/");
        }

        parts.append(selpathparts[selpathparts.length-1]);
        parts.deleteCharAt(0);

        selpathparts[selpathparts.length-1] = parts.toString();

        parts = new StringBuffer("");
        
        filename = selpathparts[selpathparts.length-1];
        vidpath = vidpath.append(filename);
        
        vidpath = new StringBuffer(vidpath.toString().replace("=", ","));
        return vidpath;
    }

    public void enable_Objects() {
        this.jTree_nav.setEnabled(true);
        this.jButton_refresh.setEnabled(true);
        this.jButton_addToPlaylist.setEnabled(true);
        this.jLabel_play.setEnabled(true);
        this.jLabel_stop.setEnabled(true);
        this.jLabel_prev.setEnabled(true);
        this.jLabel_next.setEnabled(true);
        this.jToggleButton_lock.setEnabled(true);
        this.jSlider_vol.setEnabled(true);
        this.jSlider_Media.setEnabled(true);
        this.jTable_playlist.setEnabled(true);
        this.jButton_delete.setEnabled(true);
        this.jButton_up.setEnabled(true);
        this.jButton_down.setEnabled(true);
        this.jButton_pl_sav.setEnabled(true);
        this.jButton_pl_load.setEnabled(true);
    }
    
    public void disable_Objects() {
        this.jTree_nav.setEnabled(false);
        this.jButton_refresh.setEnabled(false);
        this.jButton_addToPlaylist.setEnabled(false);
        this.jLabel_play.setEnabled(false);
        this.jLabel_stop.setEnabled(false);
        this.jLabel_prev.setEnabled(false);
        this.jLabel_next.setEnabled(false);
        this.jToggleButton_lock.setEnabled(false);
        this.jSlider_vol.setEnabled(false);
        this.jSlider_Media.setEnabled(false);
        this.jTable_playlist.setEnabled(false);
        this.jButton_delete.setEnabled(false);
        this.jButton_up.setEnabled(false);
        this.jButton_down.setEnabled(false);
        this.jButton_pl_sav.setEnabled(false);
        this.jButton_pl_load.setEnabled(false);
    }
    
    public JTextPane getjTextPane_info() {
        return jTextPane_info;
    }

    public void setjTextPane_info(JTextPane jTextPane_info) {
        this.jTextPane_info = jTextPane_info;
    }

    public JLabel getjLabel_dir_info() {
        return jLabel_dir_info;
    }

    public void setjLabel_dir_info(JLabel jLabel_dir_info) {
        this.jLabel_dir_info = jLabel_dir_info;
    }

    public String getFilenminfobuf() {
        return filenminfobuf;
    }

    public void setFilenminfobuf(String filenminfobuf) {
        this.filenminfobuf = filenminfobuf;
    }

    public JTextField getjTextField_Server() {
        return jTextField_Server;
    }

    public void setjTextField_Server(JTextField jTextField_Server) {
        this.jTextField_Server = jTextField_Server;
    }

    public JTextField getjTextField_Port() {
        return jTextField_Port;
    }

    public void setjTextField_Port(JTextField jTextField_Port) {
        this.jTextField_Port = jTextField_Port;
    }

    public DefaultMutableTreeNode getTop() {
        return top;
    }

    public void setTop(DefaultMutableTreeNode top) {
        this.top = top;
    }
    
    public JScrollPane getjScrollPane_nav() {
        return jScrollPane_nav;
    }

    public void setjScrollPane_nav(JScrollPane jScrollPane_nav) {
        this.jScrollPane_nav = jScrollPane_nav;
    }

    public JButton getjButton_connect() {
        return jButton_connect;
    }

    public void setjButton_connect(JButton jButton_connect) {
        this.jButton_connect = jButton_connect;
    }

    public StringBuffer getMedpath() {
        return medpath;
    }

    public void setMedpath(StringBuffer medpath) {
        this.medpath = medpath;
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }

    public void setDtm(DefaultTableModel dtm) {
        this.dtm = dtm;
    }

    public String getFlnm() {
        return flnm;
    }

    public void setFlnm(String flnm) {
        this.flnm = flnm;
    }

    public JProgressBar getjProgressBar_main() {
        return jProgressBar_main;
    }

    public void setjProgressBar_main(JProgressBar jProgressBar_main) {
        this.jProgressBar_main = jProgressBar_main;
    }

    public JScrollPane getjScrollPane_navman() {
        return jScrollPane_navman;
    }

    public void setjScrollPane_navman(JScrollPane jScrollPane_navman) {
        this.jScrollPane_navman = jScrollPane_navman;
    }

    public JScrollPane getjScrollPane_Playlist() {
        return jScrollPane_Playlist;
    }

    public void setjScrollPane_Playlist(JScrollPane jScrollPane_Playlist) {
        this.jScrollPane_Playlist = jScrollPane_Playlist;
    }

    public JProgressBar getjProgressBar_SCP() {
        return jProgressBar_SCP;
    }

    public void setjProgressBar_SCP(JProgressBar jProgressBar_SCP) {
        this.jProgressBar_SCP = jProgressBar_SCP;
    }

    public JLabel getjLabel_src() {
        return jLabel_src;
    }

    public void setjLabel_src(JLabel jLabel_src) {
        this.jLabel_src = jLabel_src;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public MyComponentListener getClistener() {
        return clistener;
    }

    public MyWindowListener getWlistener() {
        return wlistener;
    }

    public boolean isStatechange() {
        return statechange;
    }

    public void setStatechange(boolean statechange) {
        this.statechange = statechange;
    }

    public JTable getjTable_playlist() {
        return jTable_playlist;
    }

    public void setjTable_playlist(JTable jTable_playlist) {
        this.jTable_playlist = jTable_playlist;
    }

    public JTree getjTree_nav() {
        return jTree_nav;
    }

    public void setjTree_nav(JTree jTree_nav) {
        this.jTree_nav = jTree_nav;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public JPanel getjPanel_nav() {
        return jPanel_nav;
    }

    public void setjPanel_nav(JPanel jPanel_nav) {
        this.jPanel_nav = jPanel_nav;
    }

    public JLabel getjLabel_time() {
        return jLabel_time;
    }

    public void setjLabel_time(JLabel jLabel_time) {
        this.jLabel_time = jLabel_time;
    }

    public JLabel getjLabel_stop() {
        return jLabel_stop;
    }

    public void setjLabel_stop(JLabel jLabel_stop) {
        this.jLabel_stop = jLabel_stop;
    }

    public JSlider getjSlider_Media() {
        return jSlider_Media;
    }

    public void setjSlider_Media(JSlider jSlider_Media) {
        this.jSlider_Media = jSlider_Media;
    }

    public JSlider getjSlider_vol() {
        return jSlider_vol;
    }

    public void setjSlider_vol(JSlider jSlider_vol) {
        this.jSlider_vol = jSlider_vol;
    }

    public JLabel getjLabel_play() {
        return jLabel_play;
    }

    public JLabel getjLabel_prev() {
        return jLabel_prev;
    }

    public JLabel getjLabel_next() {
        return jLabel_next;
    }

    public void setjLabel_next(JLabel jLabel_next) {
        this.jLabel_next = jLabel_next;
    }

    public void setjLabel_prev(JLabel jLabel_prev) {
        this.jLabel_prev = jLabel_prev;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public JLabel getjLabel_speedval() {
        return jLabel_speedval;
    }
    
    public JToggleButton getjToggleButton_lock() {
        return jToggleButton_lock;
    }

    public JLabel getjLabel_show_flnm() {
        return jLabel_show_flnm;
    }
    
    public void setjLabel_show_flnm(JLabel jLabel_show_flnm) {
        this.jLabel_show_flnm = jLabel_show_flnm;
    }

    public JLabel getjLabel_show_status() {
        return jLabel_show_status;
    }

    public void setjLabel_show_status(JLabel jLabel_show_status) {
        this.jLabel_show_status = jLabel_show_status;
    }

    public JProgressBar getjProgressBar_play_visual() {
        return jProgressBar_play_visual;
    }

    public void setjProgressBar_play_visual(JProgressBar jProgressBar_play_visual) {
        this.jProgressBar_play_visual = jProgressBar_play_visual;
    }

    public JButton getjButton_reset() {
        return jButton_reset;
    }

    public void setjButton_reset(JButton jButton_reset) {
        this.jButton_reset = jButton_reset;
    }
    
    public BasicSliderUI.TrackListener getTl_main() {
        return tl_main;
    }

    public void setTl_main(BasicSliderUI.TrackListener tl_main) {
        this.tl_main = tl_main;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_addToPlaylist;
    private javax.swing.JButton jButton_closecon;
    private javax.swing.JButton jButton_connect;
    private javax.swing.JButton jButton_delete;
    private javax.swing.JButton jButton_down;
    private javax.swing.JButton jButton_pl_load;
    private javax.swing.JButton jButton_pl_sav;
    private javax.swing.JButton jButton_refresh;
    private javax.swing.JButton jButton_reset;
    private javax.swing.JButton jButton_up;
    private javax.swing.JLabel jLabel_Main;
    private javax.swing.JLabel jLabel_Port;
    private javax.swing.JLabel jLabel_Server;
    private javax.swing.JLabel jLabel_dir_info;
    private javax.swing.JLabel jLabel_info;
    private javax.swing.JLabel jLabel_next;
    private javax.swing.JLabel jLabel_object;
    private javax.swing.JLabel jLabel_play;
    private javax.swing.JLabel jLabel_playnm;
    private javax.swing.JLabel jLabel_prev;
    private javax.swing.JLabel jLabel_show_flnm;
    private javax.swing.JLabel jLabel_show_info;
    private javax.swing.JLabel jLabel_show_status;
    private javax.swing.JLabel jLabel_speed;
    private javax.swing.JLabel jLabel_speedval;
    private javax.swing.JLabel jLabel_src;
    private javax.swing.JLabel jLabel_status;
    private javax.swing.JLabel jLabel_stop;
    private javax.swing.JLabel jLabel_time;
    private javax.swing.JLabel jLabel_volume;
    private javax.swing.JMenuBar jMenuBar_main;
    private javax.swing.JMenuItem jMenuItem_about;
    private javax.swing.JMenuItem jMenuItem_exit;
    private javax.swing.JMenuItem jMenuItem_new;
    private javax.swing.JMenuItem jMenuItem_settings;
    private javax.swing.JMenu jMenu_Edit;
    private javax.swing.JMenu jMenu_File;
    private javax.swing.JMenu jMenu_Help;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_loadfile;
    private javax.swing.JPanel jPanel_nav;
    private javax.swing.JProgressBar jProgressBar_SCP;
    private javax.swing.JProgressBar jProgressBar_main;
    private javax.swing.JProgressBar jProgressBar_play_visual;
    private javax.swing.JScrollPane jScrollPane_Playlist;
    private javax.swing.JScrollPane jScrollPane_nav;
    private javax.swing.JScrollPane jScrollPane_navman;
    private javax.swing.JSlider jSlider_Media;
    private javax.swing.JSlider jSlider_vol;
    private javax.swing.JSplitPane jSplitPane_main;
    private javax.swing.JSplitPane jSplitPane_nav_playl;
    private javax.swing.JTable jTable_playlist;
    private javax.swing.JTextField jTextField_Port;
    private javax.swing.JTextField jTextField_Server;
    private javax.swing.JTextPane jTextPane_info;
    private javax.swing.JToggleButton jToggleButton_lock;
    private javax.swing.JTree jTree_nav;
    // End of variables declaration//GEN-END:variables
}