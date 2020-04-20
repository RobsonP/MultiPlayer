/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;

/**
 * contains the media-files which are added by user. It contains getter- and setter-methods
 * to access the data.
 * @author RobsonP
 */
public class Playlist {
    private ArrayList<String> entries;
    
    public Playlist() {
        entries = new ArrayList<>();
    }
    
    public void addEntry(StringBuffer entry) {
        entries.add(entry.toString());
    }

    public void removeEntry(int i) {
        entries.remove(i);
    }
    
    public ArrayList<String> getEntries() {
        return entries;
    }   
}
