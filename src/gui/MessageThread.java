/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JOptionPane;

public class MessageThread extends Thread {
    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_WARNING = 1;
    public static final int TYPE_ERROR = 2;
    
    private String message = "";
    private int mtype = 0;
    
    public MessageThread(String message) {
        super();
        this.message = message;
    }
    
    public MessageThread(String message, int mtype) {
        super();
        this.message = message;
        this.mtype = mtype;
    }
    
    private void launchDialogBox() {

        switch (mtype) {
            case 0: JOptionPane.showMessageDialog(null, message);break;
            case 2: JOptionPane.showMessageDialog(null, message,"Inane warning", JOptionPane.ERROR_MESSAGE);break;
            default: JOptionPane.showMessageDialog(null, message);
        }
    }

    @Override
    public void run() {
        launchDialogBox();
    }

    public int getMtype() {
        return mtype;
    }

    public void setMtype(int mtype) {
        this.mtype = mtype;
    }

}

