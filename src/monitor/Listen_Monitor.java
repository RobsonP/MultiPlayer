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

    public synchronized boolean isTrylisten() {
        return trylisten;
    }

    public synchronized void setTrylisten(boolean trylisten) {
        this.trylisten = trylisten;
    }

    public synchronized boolean isExit() {
        return exit;
    }

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