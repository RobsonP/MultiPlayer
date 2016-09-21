/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author RobsonP
 */
public class Conversion {
    
    /**
     * returns a key as byte-Array from a given filepath as String
     * @param filepath
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static synchronized byte[] getkeyfromFile(String filepath) throws FileNotFoundException, IOException {
        try {
            FileReader f = new FileReader(filepath);

            BufferedReader bufread = new BufferedReader(f);
            StringBuffer strbuf = new StringBuffer("");
            String str;

            try {
                do {
                    str = bufread.readLine();
                    strbuf = strbuf.append(str).append("\n");
                }while (str != null);
            }catch (NullPointerException exc) {

            }

            return strbuf.toString().getBytes();
        }catch (FileNotFoundException exc) { 
          return null;
        } 
    }
}