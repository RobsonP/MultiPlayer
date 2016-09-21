/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soSSH;

import instance.Instance_hold;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author RobsonP
 */
public class SoSSH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        JFrame startupFrame = showStartupWindow();
        
        File pfad = new File("");
        System.out.println(pfad.getAbsolutePath());
        System.setProperty("jna.library.path", pfad.getAbsolutePath()+"/VLC");
        System.setProperty("file.encoding","UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null,null);
        
        Locale.setDefault(Locale.US);
        JComponent.setDefaultLocale(Locale.US);

        Instance_hold.getMframe().setVisible(true);
        startupFrame.setVisible(false);
    }
    
    public static JFrame showStartupWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        JFrame frame = new JFrame("Display Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        
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
 
        frame.setLocation((int)((screenSize.getWidth()/2.0)-(imico.getIconWidth()/2.0)), (int)((screenSize.getHeight()/2.0)-(imico.getIconHeight()/2.0))/*(int)((screenSize.getHeight()/2)-(frame.getHeight()/2))*/);
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
        
        return frame;
    }
}