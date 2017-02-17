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


import control.Main_controls;
import instance.Instance_data;
import instance.Instance_hold;
import data.Node_entry;
import java.awt.Color;
import java.awt.Container;
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
import javax.swing.UIManager.*;



/**
 *
 * @author RobsonP
 */
public class MainFrame extends javax.swing.JFrame {
    
    DefaultMutableTreeNode top;
    DefaultTableModel dtm;
    String filename;
    StringBuffer medpath = new StringBuffer("");
    private String flnm;
    private String filenminfobuf = "";
    private MyWindowListener wlistener = new MyWindowListener();
    private MyComponentListener clistener = new MyComponentListener();
    int count;
    public boolean playcontrol = true, interrupted = false, stop = false, statechange = false, dragged = false;

    /** Creates new form MainFrame */
    public MainFrame() {
        try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
        } catch (Exception e) {
    // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        /*       
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        initComponents();
        /*
        try {
            //this.jSlider_Media.setUI(new MySliderUI(this.jSlider_Media));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
/*     
        UIDefaults sliderDefaults = new UIDefaults();
        sliderDefaults.put("Slider.background", Color.BLUE);
        sliderDefaults.put("Slider.foreground", Color.BLUE);
        sliderDefaults.put("Slider:SliderThumb.backgroundPainter", new Painter<JComponent>() {
                @Override
                public void paint(Graphics2D g, JComponent c, int w, int h) {
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g.setStroke(new BasicStroke(2f));
                        g.setColor(Color.RED);
                        g.fillOval(1, 1, w-3, h-3);
                        g.setColor(Color.WHITE);
                        g.drawOval(1, 1, w-3, h-3);
                    }

                });
        sliderDefaults.put("Slider:SliderTrack.backgroundPainter", new Painter<JComponent>() {
                @Override
                public void paint(Graphics2D g, JComponent c, int w, int h) {
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g.setStroke(new BasicStroke(2f));
                        g.setColor(Color.GRAY);
                        g.fillRoundRect(0, 6, w-1, 8, 8, 8);
                        g.setColor(Color.WHITE);
                        g.drawRoundRect(0, 6, w-1, 8, 8, 8);
                    }
                });
        this.jSlider_Media.putClientProperty("Nimbus.Overrides",sliderDefaults);
        this.jSlider_Media.putClientProperty("Nimbus.Overrides.InheritDefaults",false);
*/
        this.jSlider_Media.setBackground(new Color(240,240,240));
        this.jSlider_Media.setForeground(new Color(240,240,240));
        
        this.jPanel_nav.setVisible(false);
        
        Color c = new Color(240,240,240);
        Container con = this.getContentPane();
        con.setBackground(c);
        
        this.jPanel_loadfile.setBackground(c);
        this.jPanel_nav.setBackground(c);
        
        this.jTable_playlist.setDefaultRenderer(Object.class, new MyTableCellRenderer());;
        TableColumn column = null;
        column = this.jTable_playlist.getColumnModel().getColumn(0);
        column.setPreferredWidth((int)(this.jTable_playlist.getSize().width*0.7));
        
        JRootPane rootPane = this.getRootPane();
        rootPane.setDefaultButton(this.jButton_connect);
        
        this.setIconImage(Instance_hold.getIm_hold().getNeu12());
        this.jLabel_dir_info.setText("");

        top = new DefaultMutableTreeNode("/");
        this.setTitle("SoSSH");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(wlistener);
        this.addComponentListener(clistener);
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                statechange = true;
                System.out.println("New Window State: " + e.getNewState());
                System.out.println("Old State: " + e.getOldState());
                
                TableColumn column = null;
                column = Instance_hold.getMframe().getjTable_playlist().getColumnModel().getColumn(0);

                if (e.getNewState() == JFrame.ICONIFIED && Instance_hold.getPlayframe().isIconfied()) Instance_hold.getPlayframe().setState(JFrame.ICONIFIED);
                if (e.getOldState() == JFrame.ICONIFIED) Instance_hold.getPlayframe().setState(JFrame.NORMAL);
                if (e.getNewState() == 7 && Instance_hold.getPlayframe().isIconfied()) Instance_hold.getPlayframe().setState(JFrame.ICONIFIED);
                if (e.getOldState() == 7) Instance_hold.getPlayframe().setState(JFrame.NORMAL);
                if (e.getNewState() == 6) {
                    System.out.println("MAXIMIZED_BOTH");
                    System.out.println("BREITE: " + java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.5);
                    column.setPreferredWidth((int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.5));
                    Instance_hold.getPlayframe().setLocation((int)Instance_hold.getMframe().getLocation().getX()+200, (int)Instance_hold.getMframe().getLocation().getY()+80);
                    Instance_hold.getMframe().repaint();
                }
                if (e.getNewState() == 0) {
                    System.out.println("MFRAME BREITE: " + Instance_hold.getMframe().getSize().width);
                    column.setPreferredWidth((int)(Instance_hold.getMframe().getSize().width*0.3));
                    Instance_hold.getPlayframe().setLocation((int)Instance_hold.getMframe().getLocation().getX()+200, (int)Instance_hold.getMframe().getLocation().getY()+80);
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
                do {
                    System.out.println("VPlay is waiting to exit");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main_controls.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }while (Instance_hold.getVplay().isAlive());
                Instance_hold.getVplay_mon().setExit(false);
                Instance_hold.getVplay_mon().setIrruptflag(0);
                
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
                    
                    Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
                    Instance_hold.getSCPFrom_Monitor().setExit(false);
                }

                Instance_hold.getSh_mon().setExit(true);
                while (Instance_hold.getSh().isAlive()) {
                    System.out.println("WAITING FOR SHELL EXIT");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        //ex.printStackTrace();
                    }
                }
                Instance_hold.getSh_mon().setExit(true);

                try {
                    File file = new File(Instance_data.getTmpPath());
                    Main_controls.del(file);

                    crdir = file.mkdirs();
                }catch (NullPointerException exc) {
                    System.out.println("Temp-Path doesn't exist!!!");
                }

                Instance_hold.getFsf().getWmh().release();
                Instance_hold.getMframe().dispose();
                System.exit(0);
            }
        });
        
        System.out.println("BREITE: " + java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        System.out.println("HOEHE: " + java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight());
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
        jSplitPane_main = new javax.swing.JSplitPane();
        jScrollPane_navman = new javax.swing.JScrollPane();
        jTextPane_info = new javax.swing.JTextPane();
        jScrollPane_nav = new javax.swing.JScrollPane();
        jTree_nav = new javax.swing.JTree();
        jButton_connect = new javax.swing.JButton();
        jTextField_Port = new javax.swing.JTextField();
        jLabel_Port = new javax.swing.JLabel();
        jLabel_object = new javax.swing.JLabel();
        jButton_addToPlaylist = new javax.swing.JButton();
        jScrollPane_Playlist = new javax.swing.JScrollPane();
        jTable_playlist = new javax.swing.JTable();
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
        jPanel_nav = new javax.swing.JPanel();
        jSlider_Media = new javax.swing.JSlider();
        jSlider_vol = new javax.swing.JSlider();
        jLabel_prev = new javax.swing.JLabel();
        jLabel_time = new javax.swing.JLabel();
        jLabel_stop = new javax.swing.JLabel();
        jLabel_play = new javax.swing.JLabel();
        jLabel_volume = new javax.swing.JLabel();
        jLabel_next = new javax.swing.JLabel();
        jLabel_medianame = new javax.swing.JLabel();
        jToggleButton_lock = new javax.swing.JToggleButton();
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

        jSplitPane_main.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jScrollPane_navman.setViewportView(jTextPane_info);

        jSplitPane_main.setLeftComponent(jScrollPane_navman);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("/");
        jTree_nav.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree_navMouseClicked(evt);
            }
        });
        jScrollPane_nav.setViewportView(jTree_nav);

        jSplitPane_main.setBottomComponent(jScrollPane_nav);

        jButton_connect.setText("connect");
        jButton_connect.setEnabled(false);
        jButton_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_connectActionPerformed(evt);
            }
        });

        jLabel_Port.setText("Port");

        jLabel_object.setText("Objectinfo:");

        jButton_addToPlaylist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/plus.png"))); // NOI18N
        jButton_addToPlaylist.setText("add to Playlist");
        jButton_addToPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addToPlaylistActionPerformed(evt);
            }
        });

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

        jLabel_dir_info.setText("foo");

        jButton_up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/pfeil_kl.png"))); // NOI18N
        jButton_up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_upActionPerformed(evt);
            }
        });

        jButton_down.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/pfeil_kl_unten.png"))); // NOI18N
        jButton_down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_downActionPerformed(evt);
            }
        });

        jButton_delete.setText("jButton3");
        jButton_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteActionPerformed(evt);
            }
        });

        jLabel_speedval.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel_speed.setText("kB/s");

        javax.swing.GroupLayout jPanel_loadfileLayout = new javax.swing.GroupLayout(jPanel_loadfile);
        jPanel_loadfile.setLayout(jPanel_loadfileLayout);
        jPanel_loadfileLayout.setHorizontalGroup(
            jPanel_loadfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar_SCP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel_src, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_loadfileLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel_speedval, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_speed))
        );
        jPanel_loadfileLayout.setVerticalGroup(
            jPanel_loadfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_loadfileLayout.createSequentialGroup()
                .addGroup(jPanel_loadfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_speed)
                    .addComponent(jLabel_speedval, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_src, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar_SCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSlider_Media.setMaximum(1000);
        jSlider_Media.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSlider_Media.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jSlider_MediaMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSlider_MediaMouseClicked(evt);
            }
        });
        jSlider_Media.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jSlider_MediaMouseDragged(evt);
            }
        });

        jSlider_vol.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jSlider_volMouseDragged(evt);
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

        jLabel_medianame.setText(" ");

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
            .addComponent(jSlider_Media, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_navLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel_medianame)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel_navLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_play)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_stop)
                .addGap(18, 18, 18)
                .addComponent(jLabel_time)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jLabel_prev)
                .addGap(18, 18, 18)
                .addComponent(jLabel_next)
                .addGap(39, 39, 39)
                .addComponent(jToggleButton_lock, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel_volume)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider_vol, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        jPanel_navLayout.setVerticalGroup(
            jPanel_navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_navLayout.createSequentialGroup()
                .addComponent(jSlider_Media, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel_navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider_vol, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_navLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel_time))
                    .addComponent(jLabel_stop)
                    .addComponent(jLabel_play)
                    .addComponent(jLabel_prev, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_next, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel_volume)
                        .addComponent(jToggleButton_lock, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel_medianame))
        );

        jMenu_File.setText("File");

        jMenuItem_new.setText("New");
        jMenuItem_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_newActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_new);

        jMenuItem_exit.setText("Exit");
        jMenuItem_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_exitActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_exit);

        jMenuBar_main.add(jMenu_File);

        jMenu_Edit.setText("Edit");

        jMenuItem_settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/design/settings.png"))); // NOI18N
        jMenuItem_settings.setText("Settings");
        jMenuItem_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_settingsActionPerformed(evt);
            }
        });
        jMenu_Edit.add(jMenuItem_settings);

        jMenuBar_main.add(jMenu_Edit);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel_object)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel_dir_info))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel_Server)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField_Server, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel_Port)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField_Port, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton_connect)
                                    .addComponent(jProgressBar_main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_addToPlaylist))
                            .addComponent(jSplitPane_main, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel_Main)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel_nav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane_Playlist, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                    .addComponent(jPanel_loadfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jButton_up, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton_down, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel_loadfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Main, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel_nav, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane_Playlist, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_delete)
                                .addGap(173, 173, 173)
                                .addComponent(jButton_up)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_down))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField_Server, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_Server)
                                    .addComponent(jTextField_Port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_Port)
                                    .addComponent(jButton_connect))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jProgressBar_main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel_object)
                                        .addComponent(jLabel_dir_info))))
                            .addComponent(jButton_addToPlaylist))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jSplitPane_main, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_connectActionPerformed
        System.out.println("CONNECTION STARTED");
        Instance_hold.getSetframe().getjButton_import().setEnabled(false);

        if (this.jTextPane_info.getText().equals("")) {
            Instance_data.setLs("/");
        }else Instance_data.setLs(this.jTextPane_info.getText());
        
        Main_controls.connect();
        
    }//GEN-LAST:event_jButton_connectActionPerformed

    private void jButton_addToPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addToPlaylistActionPerformed
        Main_controls.addToPlaylist();
    }//GEN-LAST:event_jButton_addToPlaylistActionPerformed

    private void jTable_playlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_playlistMouseClicked
        System.out.println("PLAYCONTROL: " + this.playcontrol);
        if (this.playcontrol) {
            this.playcontrol = false;
            System.out.println("CLICK COUNT: " + evt.getClickCount());
            if (evt.getClickCount() >= 2) {
                System.out.println("DOUBLE CLICK");
                this.jProgressBar_main.setIndeterminate(true);
                this.setEnabled(false);
                
                Main_controls.playMedia();
                
                WaitThread wt = new WaitThread();
                wt.start();
                
                interrupted = false;
                this.stop = false;
                Instance_hold.getPlayframe().setStop(false);
                Instance_hold.getFsnt().setStop(false);
            }
            this.playcontrol = true;
        }
    }//GEN-LAST:event_jTable_playlistMouseClicked

    private void jMenuItem_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_settingsActionPerformed
        Instance_hold.getSetframe().setVisible(true);
    }//GEN-LAST:event_jMenuItem_settingsActionPerformed

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
            Main_controls.newSession();
        }
        
        Instance_hold.getPlayframe().setAlwaysOnTop(true);
    }//GEN-LAST:event_jMenuItem_newActionPerformed

    private void jButton_upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_upActionPerformed
        Main_controls.upInPlaylist();
    }//GEN-LAST:event_jButton_upActionPerformed

    private void jButton_downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_downActionPerformed
        Main_controls.downInPlaylist();
    }//GEN-LAST:event_jButton_downActionPerformed

    private void jButton_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteActionPerformed
        Main_controls.deleteFromPlaylist();       
    }//GEN-LAST:event_jButton_deleteActionPerformed

    private void jTable_playlistKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_playlistKeyTyped
        if (evt.getKeyChar() == '\n' && this.playcontrol) {
            if (this.playcontrol) {
                this.playcontrol = false;
                this.jProgressBar_main.setIndeterminate(true);
                this.setEnabled(false);
                this.jTable_playlist.getSelectionModel().setSelectionInterval(this.jTable_playlist.getSelectedRow()-1, this.jTable_playlist.getSelectedRow()-1);
                Main_controls.playMedia();

                WaitThread wt = new WaitThread();
                wt.start();        
                        
                interrupted = false;
                this.playcontrol = true;
            }
        }
        if ((int)evt.getKeyChar() == KeyEvent.VK_DELETE) {
            Main_controls.deleteFromPlaylist();
        }
    }//GEN-LAST:event_jTable_playlistKeyTyped

    private void jTree_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree_navMouseClicked
        System.out.println("CLICKED");
    }//GEN-LAST:event_jTree_navMouseClicked

    private void jLabel_MainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MainMouseClicked
        System.out.println("CLICKED");
    }//GEN-LAST:event_jLabel_MainMouseClicked

    private void jMenuItem_aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_aboutActionPerformed
        JOptionPane.showMessageDialog(this, "SoSSH\nVersion 1.10\n\nDesigned by Robert Mautz");
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
                System.out.println("VPlay is waiting to exit");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main_controls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while (Instance_hold.getVplay().isAlive());
            Instance_hold.getVplay_mon().setExit(false);
            Instance_hold.getVplay_mon().setIrruptflag(0);

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

                Instance_hold.getSCPFrom_Monitor().setIrruptflag(0);
                Instance_hold.getSCPFrom_Monitor().setExit(false);
            }

            Instance_hold.getSh_mon().setExit(true);
            while (Instance_hold.getSh().isAlive()) {
                System.out.println("WAITING FOR SHELL EXIT");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    //ex.printStackTrace();
                }
            }
            Instance_hold.getSh_mon().setExit(true);

            try {
                File file = new File(Instance_data.getTmpPath());
                Main_controls.del(file);

                crdir = file.mkdirs();
            }catch (NullPointerException exc) {
                System.out.println("Temp-Path doesn't exist!!!");
            }

            Instance_hold.getFsf().getWmh().release();
            Instance_hold.getMframe().dispose();
            System.exit(0);
        } 
    }//GEN-LAST:event_jMenuItem_exitActionPerformed

    private void jSlider_MediaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_MediaMouseReleased
        if (dragged) {
            Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setTime((int)(Instance_data.getMedia_length()*(Instance_hold.getPlayframe().getSl_value()/1000.0)));
            dragged = false;
        }
    }//GEN-LAST:event_jSlider_MediaMouseReleased

    private void jSlider_MediaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_MediaMouseClicked
        if (!dragged) Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setTime((int)(Instance_data.getMedia_length()*(this.jSlider_Media.getMousePosition().getX()/this.jSlider_Media.getSize().getWidth())));
    }//GEN-LAST:event_jSlider_MediaMouseClicked

    private void jSlider_MediaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_MediaMouseDragged
        dragged = true;
        Instance_hold.getPlayframe().setSl_value(this.jSlider_Media.getValue());
    }//GEN-LAST:event_jSlider_MediaMouseDragged

    private void jSlider_volMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_volMouseDragged
        Instance_hold.getPlayframe().setVo_value(this.jSlider_vol.getValue());
        Instance_hold.getPlayframe().getEmpc().getMediaPlayer().setVolume(Instance_hold.getPlayframe().getVo_value());
        Instance_hold.getPlayframe().getjSlider_vol().setValue(Instance_hold.getPlayframe().getVo_value());
        Instance_hold.getFsnt().getjSlider_vol().setValue(Instance_hold.getPlayframe().getVo_value());
    }//GEN-LAST:event_jSlider_volMouseDragged

    private void jLabel_prevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_prevMouseClicked
        System.out.println("PLAYCONTROL: " + this.playcontrol);
        if (this.playcontrol) {
            this.playcontrol = false;

            Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true);
            this.setEnabled(false);
            Main_controls.prevMedia();

            while(!Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying() && !Instance_hold.getFsf().getEmpc().getMediaPlayer().isPlaying()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            this.setEnabled(true);
            this.playcontrol = true;
        }
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
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlayFrame.class.getName()).log(Level.SEVERE, null, ex);
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
        if (this.playcontrol) {
            this.setEnabled(false);
            if (!Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().start();
                while(!Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                    //System.out.println("WAITING TO START PLAYING:::::");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {

                    }
                }

                this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_blue_pause_small());
                stop = false;
            }
            else {
                Instance_hold.getPlayframe().getEmpc().getMediaPlayer().pause();
                while(Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PlayFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.jLabel_play.setIcon(Instance_hold.getIm_hold().getMcb_blue_play_small());
                stop = true;
            }
            this.setEnabled(true);
        }
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
        System.out.println("PLAYCONTROL: " + this.playcontrol);
        if (this.playcontrol) {
            this.playcontrol = false;
            //System.out.println("PLAYCONTROL: " + this.playcontrol);

            Instance_hold.getMframe().getjProgressBar_main().setIndeterminate(true);
            this.setEnabled(false);
            Main_controls.nextMedia();

            while(!Instance_hold.getPlayframe().getEmpc().getMediaPlayer().isPlaying() && !Instance_hold.getFsf().getEmpc().getMediaPlayer().isPlaying()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            this.setEnabled(true);
            this.playcontrol = true;
        }
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
            System.out.println("Node: " + nodelist.get(i).getName());
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

    public boolean isPlaycontrol() {
        return playcontrol;
    }

    public void setPlaycontrol(boolean playcontrol) {
        this.playcontrol = playcontrol;
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

    public JLabel getjLabel_medianame() {
        return jLabel_medianame;
    }

    public void setjLabel_medianame(JLabel jLabel_medianame) {
        this.jLabel_medianame = jLabel_medianame;
    }
    
    public JToggleButton getjToggleButton_lock() {
        return jToggleButton_lock;
    }
    
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_addToPlaylist;
    private javax.swing.JButton jButton_connect;
    private javax.swing.JButton jButton_delete;
    private javax.swing.JButton jButton_down;
    private javax.swing.JButton jButton_up;
    private javax.swing.JLabel jLabel_Main;
    private javax.swing.JLabel jLabel_Port;
    private javax.swing.JLabel jLabel_Server;
    private javax.swing.JLabel jLabel_dir_info;
    private javax.swing.JLabel jLabel_medianame;
    private javax.swing.JLabel jLabel_next;
    private javax.swing.JLabel jLabel_object;
    private javax.swing.JLabel jLabel_play;
    private javax.swing.JLabel jLabel_prev;
    private javax.swing.JLabel jLabel_speed;
    private javax.swing.JLabel jLabel_speedval;
    private javax.swing.JLabel jLabel_src;
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
    private javax.swing.JPanel jPanel_loadfile;
    private javax.swing.JPanel jPanel_nav;
    private javax.swing.JProgressBar jProgressBar_SCP;
    private javax.swing.JProgressBar jProgressBar_main;
    private javax.swing.JScrollPane jScrollPane_Playlist;
    private javax.swing.JScrollPane jScrollPane_nav;
    private javax.swing.JScrollPane jScrollPane_navman;
    private javax.swing.JSlider jSlider_Media;
    private javax.swing.JSlider jSlider_vol;
    private javax.swing.JSplitPane jSplitPane_main;
    private javax.swing.JTable jTable_playlist;
    private javax.swing.JTextField jTextField_Port;
    private javax.swing.JTextField jTextField_Server;
    private javax.swing.JTextPane jTextPane_info;
    private javax.swing.JToggleButton jToggleButton_lock;
    private javax.swing.JTree jTree_nav;
    // End of variables declaration//GEN-END:variables
}