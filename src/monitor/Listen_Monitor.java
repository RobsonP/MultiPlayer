/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

/**
 * Monitor to communicate with Listener
 * @author RobsonP
 */
public class Listen_Monitor {
    private boolean trylisten, exit, error;
    
    public Listen_Monitor() {
        exit = false;
        error = false;
    }

    /**
     * returns the trylisten-flag
     * @return boolean
     */
    
    public synchronized boolean isTrylisten() {
        return trylisten;
    }

    /**
     * sets the trylisten-flag
     * @param trylisten 
     */
    
    public synchronized void setTrylisten(boolean trylisten) {
        this.trylisten = trylisten;
    }

    /**
     * returns the exit-flag
     * @return boolean
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

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
    
    
}
