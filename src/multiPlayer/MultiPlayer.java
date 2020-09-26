/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiPlayer;

import com.jtattoo.plaf.mint.MintLookAndFeel;
import instance.Instance_hold;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author RobsonP
 */
public class MultiPlayer {

    /**
     * @param args the command line arguments
     * @throws java.lang.NoSuchFieldException
     * @throws java.lang.IllegalAccessException
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        JFrame startupFrame = showStartupWindow();
        
        try {    
            Properties p = new Properties();
            p.put("logoString", "MP");
            MintLookAndFeel.setCurrentTheme(p);
            
            MintLookAndFeel mlaf = new MintLookAndFeel();
            
            UIManager.setLookAndFeel(mlaf);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MultiPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        File pfad = new File("");
        System.out.println(pfad.getAbsolutePath());
        System.setProperty("jna.library.path", pfad.getAbsolutePath()+"/VLC");
        System.setProperty("file.encoding","UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null,null);
        
        Locale.setDefault(Locale.US);
        JComponent.setDefaultLocale(Locale.US);

        Instance_hold.getMframe().setSize(800, 650);
        Instance_hold.getMframe().setVisible(true);
        startupFrame.setVisible(false);
    }
    
    public static JFrame showStartupWindow() {
        JFrame frame = new JFrame("Display Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.toFront();
        
        File pfad = new File("");
        System.out.println(pfad.getAbsolutePath() + "/gui/design/logo_small.jpg");
        System.out.println(pfad.getAbsolutePath() + "/gui/design/logo_startup.jpg");
        ImageIcon imtask = new ImageIcon(pfad.getAbsolutePath() + "/gui/design/logo_small.jpg");
        frame.setIconImage(imtask.getImage());
 
        JPanel panel = (JPanel)frame.getContentPane();
 
        JLabel label = new JLabel();
        ImageIcon imico = new ImageIcon(pfad.getAbsolutePath() + "/gui/design/logo_startup.jpg");
        label.setIcon(imico);
        
        
        panel.add(label);
 
        //frame.setLocation((int)((screenSize.getWidth()/2.0)-(imico.getIconWidth()/2.0)), (int)((screenSize.getHeight()/2.0)-(imico.getIconHeight()/2.0))/*(int)((screenSize.getHeight()/2)-(frame.getHeight()/2))*/);
        frame.setLocationRelativeTo(null);
        frame.setLocation((int)frame.getLocation().getX()-(int)(imico.getIconWidth()/2.0), (int)frame.getLocation().getY()-(int)(imico.getIconHeight()/2.0));
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
        
        return frame;
    }
}

