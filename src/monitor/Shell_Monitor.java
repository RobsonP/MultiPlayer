package monitor;
/**
 * Monitor to communicate with Shell-Segment of the program
 * @author RobsonP
 */
public class Shell_Monitor {
    private boolean loading, exit, connecterror, startshell, rdytolisten, write_output, cancel;
    
    public Shell_Monitor() {
        loading = false;
        exit = false;
        connecterror = false;
        startshell = false;
        rdytolisten = false;
        write_output = false;
        cancel = false;
    }

    public synchronized boolean isExit() {
        return exit;
    }

    public synchronized void setExit(boolean exit) {
        this.exit = exit;
    }
 
    public synchronized boolean isLoading() {
        return loading;
    }
   
    public synchronized void setLoading(boolean loading) {
        this.loading = loading;
    }
   
    public synchronized boolean isConnectError() {
        return connecterror;
    }
   
    public synchronized void setConnectError(boolean connecterror) {
        this.connecterror = connecterror;
    }
   
    public synchronized boolean isStartshell() {
        return startshell;
    }
   
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

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }   
}