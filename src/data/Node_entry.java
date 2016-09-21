/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 * contains all data of a file or a folder. It also contains getter- and setter-methods
 * to access the data.
 * @author RobsonP
 */
public class Node_entry implements Comparable<Node_entry>{
    private String name;
    private char type;
    private String info;
    private String path;
    
    public Node_entry() {
        name = "";
        type = ' ';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String dirinfo) {
        this.info = dirinfo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int compareTo(Node_entry ne) {
        return this.name.compareTo(ne.getName());
    }   
}
