/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import java.awt.Dimension;

/**
 * Monitor to communicate with SCP-Segment
 * @author RobsonP
 */
public class VPlay_Monitor {
    private int irruptflag;
    private boolean /*stopped,*/ exit, finished;
            
    public VPlay_Monitor() {
        exit = false;
        irruptflag = 0;
    }
    
    /**
     * returns the interrupt-flag as boolean
     * @return boolean
     */
    
    public synchronized boolean isInterrupted() {
        switch (irruptflag) {
            case 1: return true;
            case 0: return false;
            default: return false;
        }
    }

    /**
     * returns the interrupt-flag as int
     * @return int
     */
    
    public synchronized int getIrruptflag() {
        return irruptflag;
    }

    /**
     * sets the interrupt-flag
     * @param irruptflag 
     */
    
    public synchronized void setIrruptflag(int irruptflag) {
        this.irruptflag = irruptflag;
    }

    /**
     * returns the exit-flag
     * @return 
     */
    
    public synchronized boolean isExit() {
        return exit;
    }

    /**
     * sets the exit-flag
     * @param exit 
     */
    
    public synchronized void setExit(boolean exit) {
        this.exit = exit;
    }
    
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    
}
