/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.TimerTask;

/**
 *
 * @author RobsonP
 */
class Ausblender extends TimerTask {
    int ypos;

    @Override
    public void run() {
        FS_Navi_Tab_Scroll_Down fsntsd = new FS_Navi_Tab_Scroll_Down();
        fsntsd.start();
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }      
}