/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import data.Node_entry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RobsonP
 */
public class Comparator {
    
    public static List<Node_entry> sortList(ArrayList<Node_entry> nodelist) {
        try {
            for (int i=0;i<nodelist.size();i++) {
                int j = i+1;
                //System.out.println("next");
                do {
                    if (compareTo(nodelist.get(i),nodelist.get(j)) == 1) {
                        Node_entry ne = nodelist.get(i);
                        nodelist.set(i, nodelist.get(j));
                        nodelist.set(j, ne);
                        
                        //System.out.println("CHANGED");
                        
                        j = i;
                    }
                    
                    j++;
                }while (j<nodelist.size());
            }
        }catch(Exception exc) {
            //exc.printStackTrace();
            //System.out.println("EXCEPTION SORT");
        }
        
        return nodelist;
    }
    
    private static int compareTo(Node_entry o1, Node_entry o2) {
        //System.out.println("Type o1: " + o1.getType());
        //System.out.println("Type o2: " + o2.getType());
        
        if (o1.getType() == 'd' && o2.getType() == 'f') {/*System.out.println("DIRVSFILE");*/return -1;}
        if (o1.getType() == 'f' && o2.getType() == 'd') {/*System.out.println("FILEVSDIR");*/return 1;}
        
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
