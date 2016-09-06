/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import java.io.File;

/**
 *
 * @author RobsonP
 */
public class SCPFrom_Monitor {
    private int c, irruptflag;
    private File currentdl;
    private boolean dlfinish, exit, rdytoplay, medreleased, newsession, closedlmonitor, monitorisclosed, plremove, init;
    
    public SCPFrom_Monitor() {
        dlfinish = false;
        exit = false;
        rdytoplay = false;
        medreleased = false;
        newsession = false;
        closedlmonitor = false;
        monitorisclosed = false;
        plremove = false;
        init = false;
        irruptflag = 0;
        c = 1;
    }

    public synchronized File getCurrentdl() {
        return currentdl;
    }

    public synchronized void setCurrentdl(File currentdl) {
        this.currentdl = currentdl;
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

    public synchronized boolean isDlfinish() {
        return dlfinish;
    }

    public synchronized void setDlfinish(boolean dlfinish) {
        this.dlfinish = dlfinish;
    }

    public synchronized boolean isExit() {
        return exit;
    }

    public synchronized void setExit(boolean exit) {
        this.exit = exit;
    }
    
    public synchronized boolean isRdytoplay() {
        return rdytoplay;
    }

    public synchronized void setRdytoplay(boolean rdytoplay) {
        this.rdytoplay = rdytoplay;
    }

    public synchronized boolean isMedreleased() {
        return medreleased;
    }

    public synchronized void setMedreleased(boolean medreleased) {
        this.medreleased = medreleased;
    }

    public synchronized boolean isNewsession() {
        return newsession;
    }

    public synchronized void setNewsession(boolean newsession) {
        this.newsession = newsession;
    }

    public synchronized boolean isClosedlmonitor() {
        return closedlmonitor;
    }

    public synchronized void setClosedlmonitor(boolean closedlmonitor) {
        this.closedlmonitor = closedlmonitor;
    }

    public synchronized boolean isMonitorisclosed() {
        return monitorisclosed;
    }

    public synchronized void setMonitorisclosed(boolean monitorisclosed) {
        this.monitorisclosed = monitorisclosed;
    }

    public synchronized boolean isPlremove() {
        return plremove;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
    
}
