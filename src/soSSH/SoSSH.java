/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soSSH;

import instance.Instance_hold;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Locale;
import javax.swing.JComponent;

/**
 *
 * @author RobsonP
 */
public class SoSSH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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
    }
}