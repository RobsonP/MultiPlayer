/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import data.Node_entry;
import java.util.Comparator;

/**
 *
 * @author RobsonP
 */
public class NodeComparator implements Comparator<Node_entry> {

    @Override
    public int compare(Node_entry o1, Node_entry o2) {
        if (o1.getType() == 'd' && o2.getType() == 'n') return 1;
        if (o1.getType() == 'n' && o2.getType() == 'd') return -1;
        
        for (int i=0;i<o1.getName().length();i++) {
            try {
                if ((int)o1.getName().charAt(i)<(int)o2.getName().charAt(i)) return -1;
                if ((int)o1.getName().charAt(i)>(int)o2.getName().charAt(i)) return 1;
            }catch(Exception exc) {
                break;
            }
        }
      
        return 0;
    }
    
}
