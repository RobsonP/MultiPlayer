/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

/**
 * Monitor to communicate with SCP-Segment
 * @author RobsonP
 */
public class Play_Monitor {
    private int irruptflag;
    private boolean /*stopped,*/ exit, finished;
            
    public Play_Monitor() {
        exit = false;
        irruptflag = 0;
    }
    
    public synchronized boolean isInterrupted() {
        switch (irruptflag) {
            case 1: return true;
            case 0: return false;
            default: return false;
        }
    }
  
    public synchronized int getIrruptflag() {
        return irruptflag;
    }
  
    public synchronized void setIrruptflag(int irruptflag) {
        this.irruptflag = irruptflag;
    }
 
    public synchronized boolean isExit() {
        return exit;
    }
  
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