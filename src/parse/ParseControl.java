/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parse;

import data.Node_entry;
import instance.Instance_hold;
import java.util.ArrayList;

/**
 *
 * @author RobsonP
 */
public class ParseControl {
    
    public static String parsePath(String evtPath) {
        StringBuffer strpathbuf = new StringBuffer("");
        String strpath;
        
        strpathbuf.insert(0, evtPath.replace("=", ","));
        strpathbuf.deleteCharAt(0);
        strpathbuf.deleteCharAt(strpathbuf.length()-1);
        strpath = strpathbuf.toString().replaceAll(", ", "/");
        strpathbuf = new StringBuffer(strpath);
        strpathbuf.deleteCharAt(1);
        
        strpath = strpathbuf.toString() + "/";
        return strpath;
    }
    
    public static ArrayList<Node_entry> parseInput(String[] strlist) {
        String dirinfo;
        boolean last_garbage = false;
        boolean garbage_found = false;
        boolean wait_for_bs = false;
        StringBuffer filenmbuf = new StringBuffer("");
        String filenm;
        ArrayList<Node_entry> nodelist = new ArrayList<>();
        
        for (int i=0;i<strlist.length;i++) {
            String[] hashbuflist = strlist[i].split(" ");

            if ((strlist[i].startsWith("d") || strlist[i].startsWith("-") || strlist[i].startsWith("l")) 
                    && hashbuflist[hashbuflist.length-1].hashCode() != -1174522598 && hashbuflist[hashbuflist.length-1].hashCode() != 1251707213) {
                    dirinfo = new StringBuffer(strlist[i]).toString();

                    Node_entry ne = new Node_entry();

                    String[] strpartlist = strlist[i].split(" ");

                    for (int k=0;k<strpartlist.length;k++) {
                        boolean except = false;
                        boolean noyear = false;
                        
                        try {
                            for (int h=0;h<strpartlist[k].length();h++) {
                                if (strpartlist[k].charAt(h)<48 || strpartlist[k].charAt(h)>57) noyear = true;
                            }
                            try {
                                if (strpartlist[k].charAt(2) == ':' || (strpartlist[k].length() == 4 && !noyear && (strpartlist[k-1].length() == 3 || strpartlist[k-2].length() == 3 || strpartlist[k-3].length() == 3 || strpartlist[k-4].length() == 3))) garbage_found = true;
                            }catch(Exception exc) {

                            }
                        }catch (StringIndexOutOfBoundsException exc) {

                        }                                 
                        try {
                            int exccheck = (int)strpartlist[k].charAt(0);
                            except = false;
                        }catch(Exception exc) {
                            if (!wait_for_bs) except = true;
                        }

                        if (last_garbage == true && !except) {
                           if (wait_for_bs == true) {
                               if (!strpartlist[k].equals("/")) {
                                   filenmbuf.append("\\ ");
                               }
                           }
                           filenmbuf.append(strpartlist[k]);
                           wait_for_bs = true;
                        }
                        if (garbage_found == true) {
                           last_garbage = true;
                        }
                    }
                    last_garbage = false;
                    garbage_found = false;
                    wait_for_bs = false;

                    filenm = filenmbuf.toString();
                    Instance_hold.getMframe().setFilenminfobuf(filenm);
                    filenmbuf = new StringBuffer("");

                    filenm = filenm.replace("[30;42m", "");
                    filenm = filenm.replace("[34;42m", "");
                    filenm = filenm.replace("[0;30m", "");
                    filenm = filenm.replace("[01;30m", "");
                    filenm = filenm.replace("[0;31m", "");
                    filenm = filenm.replace("[01;31m", "");
                    filenm = filenm.replace("[0;32m", "");
                    filenm = filenm.replace("[01;32m", "");
                    filenm = filenm.replace("[0;33m", "");
                    filenm = filenm.replace("[01;33m", "");
                    filenm = filenm.replace("[0;34m", "");
                    filenm = filenm.replace("[01;34m", "");
                    filenm = filenm.replace("[0;35m", "");
                    filenm = filenm.replace("[01;35m", "");
                    filenm = filenm.replace("[0;36m", "");
                    filenm = filenm.replace("[01;36m", "");
                    filenm = filenm.replace("[0;37m", "");
                    filenm = filenm.replace("[01;37m", "");
                    filenm = filenm.replace("[0m", "");
                    filenm = filenm.replace("[36m", "");

                    try {
                    int g = 0;
                    while(g<filenm.length()) {
                        if (filenm.charAt(g) == 27){
                            filenm  = new StringBuffer(filenm).deleteCharAt(g).toString();
                            g--;
                        }
                        g++;
                    }

                    if (filenm.endsWith(" [")) filenm = filenm.substring(0, filenm.length()-3);

                    filenm = filenm.replace("'", "\\'");
                    filenm = filenm.replace("(", "\\(");
                    filenm = filenm.replace(")", "\\)");
                    filenm = filenm.replace("&\\","\\&\\");
                    filenm = filenm.replace("[","\\[");
                    filenm = filenm.replace("]", "\\]");
                    filenm = filenm.replace("[K*", "");
                    filenm = filenm.replace("[K/", "");
                    filenm = filenm.replace("!", "\\!");
                    filenm = filenm.replace("$", "\\$");

                    char ch = 96;
                    filenm = filenm.replace("" + ch, "\\"+ch);

                    ne.setName(filenm);

                    if (strlist[i].charAt(strlist[i].length()-2) == '/' || strlist[i].startsWith("d") || strlist[i].startsWith("l")) {    
                        ne.setType('d');   
                    } else {

                      if (ne.getName().contains("*")) ne.setName(ne.getName().substring(0,ne.getName().length()-2));
                      while(true) {
                          if (ne.getName().charAt(0) != ' ')break;
                          ne.setName(ne.getName().substring(1, ne.getName().length()-1));
                      }
                      ne.setType('f');
                    }

                    ne.setName(ne.getName().replace(",", "="));

                    if (!ne.getName().equals(".") && !ne.getName().equals(".."))
                    nodelist.add(ne);

                    }catch (StringIndexOutOfBoundsException exc) {
                        //exc.printStackTrace();
                    }
                    Instance_hold.getMframe().setFilenminfobuf(Instance_hold.getMframe().getFilenminfobuf().replace("\\", ""));

                    dirinfo = dirinfo.replace(Instance_hold.getMframe().getFilenminfobuf(), "");
                    dirinfo = new StringBuffer(dirinfo).reverse().toString();
                    String[] dirinfosplit = dirinfo.split(" ");
                    
                    ArrayList<String> dirinfosplist = new ArrayList<>();
                    
                    for (int k=0;k<dirinfosplit.length;k++) {         
                        if (!dirinfosplit[k].equals(" ")) dirinfosplist.add(dirinfosplit[k]);
                    }
                    
                    dirinfo = dirinfosplist.get(1) + " " + dirinfosplist.get(2) + " " + dirinfosplist.get(3) + " " + dirinfosplist.get(4) + "  " + dirinfosplist.get(5);                            
                    dirinfo = new StringBuffer(dirinfo).reverse().toString();
                    
                    if (dirinfo.charAt(0) == ' ') dirinfo = dirinfo.substring(2, dirinfo.length());

                    if (!ne.getName().equals(".") && !ne.getName().equals(".."))
                    nodelist.get(nodelist.size()-1).setInfo(dirinfo);
            }
        }
        return nodelist;
    }
    
    public static String formatSpeed(double speed) {
        String str = "" + speed;
        String[] strlist = str.split("\\.");
        
        StringBuffer strbuf = new StringBuffer("");
        
        strbuf.append(strlist[0]);
        strbuf.append(".");
        strbuf.append(strlist[1].charAt(0));
        
        return strbuf.toString();
    }    
}