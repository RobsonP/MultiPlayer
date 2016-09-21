/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
/*
import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;
import java.awt.Graphics2D;
import java.awt.Stroke;
*/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author RobsonP
 */
public class MySliderUI extends BasicSliderUI{
    private ImageIcon image = new ImageIcon("C:\\Users\\RobsonP\\Documents\\NetBeansProjects\\GitHub\\SOSSH\\images\\point2.png");
    
    public MySliderUI(JSlider slider) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        super(slider);
        
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }

    @Override
    protected TrackListener createTrackListener(JSlider slider) {
        return new TrackListener() {
            @Override public void mousePressed(MouseEvent e) {
                JSlider slider = (JSlider)e.getSource();
                switch (slider.getOrientation()) {
                  case JSlider.VERTICAL:
                    slider.setValue(valueForYPosition(e.getY()));
                    break;
                  case JSlider.HORIZONTAL:
                    slider.setValue(valueForXPosition(e.getX()));
                    break;
                }
                super.mousePressed(e); //isDragging = true;
                super.mouseDragged(e);
            }

            @Override public boolean shouldScroll(int direction) {
              return false;
            }
        };
    }
    
    @Override
    public void paintThumb (Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(thumbRect.x, thumbRect.y+5, 10, 10);
        g.setColor(Color.WHITE);
        g.drawOval(thumbRect.x, thumbRect.y+5, 10, 10);
        
        //g.drawImage(image.getImage(),thumbRect.x,thumbRect.y, null);
        /*
        Graphics2D g2d = (Graphics2D) g;
        int x1 = thumbRect.x + 2;
        int x2 = thumbRect.x + thumbRect.width - 2;
        int width = thumbRect.width - 4;
        int topY = thumbRect.y + thumbRect.height / 2 - thumbRect.width / 3;
        GeneralPath shape = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        shape.moveTo(x1, topY);
        shape.lineTo(x2, topY);
        shape.lineTo((x1 + x2) / 2, topY + width);
        shape.closePath();
        g2d.setPaint(new Color(81, 83, 186));
        g2d.fill(shape);
        Stroke old = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2f));
        g2d.setPaint(new Color(131, 127, 211));
        g2d.draw(shape);
        g2d.setStroke(old);
        */
    }
}