package monitor;
/**
 * Monitor to communicate with Shell-Segment of the program
 * @author RobsonP
 */
public class Shell_Monitor {
    private boolean loading, exit, connecterror, startshell, rdytolisten, write_output;
    
    public Shell_Monitor() {
        loading = false;
        exit = false;
        connecterror = false;
        startshell = false;
        rdytolisten = false;
        write_output = false;
    }

    /**
     * returns the exit-bit
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

    /**
     * returns the loading-flag
     * @return boolean
     */
    
    public synchronized boolean isLoading() {
        return loading;
    }

    /**
     * sets the loading-flag
     * @param loading 
     */
    
    public synchronized void setLoading(boolean loading) {
        this.loading = loading;
    }
        
    /**
     * returns the connect-flag
     * @return 
     */
    
    public synchronized boolean isConnectError() {
        return connecterror;
    }

    /**
     * sets the connect-flag
     * @param connecterror 
     */
    
    public synchronized void setConnectError(boolean connecterror) {
        this.connecterror = connecterror;
    }
    
    
    /**
     * returns the startshell-flag
     * @return boolean
     */
    
    public synchronized boolean isStartshell() {
        return startshell;
    }

    /**
     * sets the startshell-flag
     * @param startshell 
     */
    
    public synchronized void setStartshell(boolean startshell) {
        this.startshell = startshell;
    }

    public boolean isRdytolisten() {
        return rdytolisten;
    }

    public void setRdytolisten(boolean rdytolisten) {
        this.rdytolisten = rdytolisten;
    }

    public boolean isWrite_output() {
        return write_output;
    }

    public void setWrite_output(boolean write_output) {
        this.write_output = write_output;
    }
  
    
}