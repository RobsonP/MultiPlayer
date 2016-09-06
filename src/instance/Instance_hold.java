/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package instance;

import cmd.ConnectThread;
import cmd.Listen;
import cmd.SCPFrom;
import cmd.Shell;
import monitor.VPlay_Monitor;
import monitor.Listen_Monitor;
import data.Playlist;
import monitor.SCPFrom_Monitor;
import monitor.Shell_Monitor;
import gui.*;
import soSSH.Video_Play;

/**
 * contains all instances of all classes with non-static variables
 * and methods, which are used in the program. It contains getter- and setter-methods to access
 * the instances.
 * @author RobsonP
 */
public class Instance_hold {
    private static Listen listen = new Listen();
    private static Shell sh = new Shell();
    private static SCPFrom scpfrom = new SCPFrom();
    private static Image_hold im_hold = new Image_hold();
    private static MainFrame mframe = new MainFrame();
    private static Playlist pl = new Playlist();
    private static Video_Play vplay = new Video_Play();
    private static Shell_Monitor sh_mon = new Shell_Monitor();
    private static VPlay_Monitor vplay_mon = new VPlay_Monitor();
    private static Listen_Monitor lm = new Listen_Monitor();
    private static SCPFrom_Monitor scpFrom_Monitor = new SCPFrom_Monitor();
    private static PlayFrame playframe = new PlayFrame();
    private static FullScreenFrame fsf = new FullScreenFrame();
    private static FS_Navi_Tab fsnt = new FS_Navi_Tab();
    private static SettingsDiag settdiag = new SettingsDiag();
    private static MessageThread mthread;    
    private static PasswordDiagRSA pframeRSA = new PasswordDiagRSA();
    private static PasswordDiag pframe = new PasswordDiag();
    private static ConnectThread cthread = new ConnectThread();
    
    
    public static MainFrame getMframe() {
        return mframe;
    }

    public static void setMframe(MainFrame mframe) {
        Instance_hold.mframe = mframe;
    }

    public static Playlist getPl() {
        return pl;
    }

    public static void setPl(Playlist pl) {
        Instance_hold.pl = pl;
    }

    public static Video_Play getVplay() {
        return vplay;
    }

    public static void setVplay(Video_Play vplay) {
        Instance_hold.vplay = vplay;
    }

    public static Shell_Monitor getSh_mon() {
        return sh_mon;
    }

    public static void setSh_mon(Shell_Monitor sh_mon) {
        Instance_hold.sh_mon = sh_mon;
    }

    public static VPlay_Monitor getVplay_mon() {
        return vplay_mon;
    }

    public static void setVplay_mon(VPlay_Monitor vplay_mon) {
        Instance_hold.vplay_mon = vplay_mon;
    }


    public static SCPFrom_Monitor getSCPFrom_Monitor() {
        return scpFrom_Monitor;
    }

    public static void setFrom_Monitor(SCPFrom_Monitor cnt_SCPFrom_Monitor) {
        Instance_hold.scpFrom_Monitor = cnt_SCPFrom_Monitor;
    }


    public static PlayFrame getPlayframe() {
        return playframe;
    }

    public static void setPlayframe(PlayFrame mainframe) {
        Instance_hold.playframe = mainframe;
    }

    public static FullScreenFrame getFsf() {
        return fsf;
    }

    public static void setFsf(FullScreenFrame fsf) {
        Instance_hold.fsf = fsf;
    }

    public static FS_Navi_Tab getFsnt() {
        return fsnt;
    }

    public static void setFsnt(FS_Navi_Tab fsnt) {
        Instance_hold.fsnt = fsnt;
    }

    public static SettingsDiag getSettDiag() {
        return settdiag;
    }

    public static void setSettDiag(SettingsDiag setframe) {
        Instance_hold.settdiag = setframe;
    }

    public static MessageThread getMthread() {
        return mthread;
    }

    public static void setMthread(MessageThread mthread) {
        Instance_hold.mthread = mthread;
    }

    public static Listen_Monitor getLm() {
        return lm;
    }

    public static void setLm(Listen_Monitor lm) {
        Instance_hold.lm = lm;
    }
     
    public static void create_VPlay() {
        if (!Instance_hold.getVplay().isAlive()) Instance_hold.vplay = new Video_Play();
    }

    public static PasswordDiagRSA getPframeRSA() {
        return pframeRSA;
    }

    public static void setPframeRSA(PasswordDiagRSA pframeRSA) {
        Instance_hold.pframeRSA = pframeRSA;
    }

    public static PasswordDiag getPframe() {
        return pframe;
    }

    public static void setPframe(PasswordDiag pframe) {
        Instance_hold.pframe = pframe;
    }

    public static Shell getSh() {
        return sh;
    }

    public static void setSh(Shell sh) {
        Instance_hold.sh = sh;
    }

    public static SCPFrom getScpfrom() {
        return scpfrom;
    }

    public static void setScpfrom(SCPFrom scpfrom) {
        Instance_hold.scpfrom = scpfrom;
    }

    public static Listen getListen() {
        return listen;
    }

    public static void setListen(Listen listen) {
        Instance_hold.listen = listen;
    }

    public static Image_hold getIm_hold() {
        return im_hold;
    }

    public static ConnectThread getCthread() {
        return cthread;
    }

    public static void setCthread(ConnectThread cthread) {
        Instance_hold.cthread = cthread;
    }

}
