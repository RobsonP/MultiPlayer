/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package instance;

import cmd.ConnectThread;
import cmd.Shell_Out;
import cmd.SCP;
import cmd.Shell;
import monitor.Play_Monitor;
import monitor.Listen_Monitor;
import data.Playlist;
import monitor.SCPFrom_Monitor;
import monitor.Shell_Monitor;
import gui.*;
import multiPlayer.Play;

/**
 * contains all instances of all classes with non-static variables
 * and methods, which are used in the program. It contains getter- and setter-methods to access
 * the instances.
 * @author RobsonP
 */
public class Instance_hold {
    private static Shell_Out listen = new Shell_Out();
    private static Shell sh = new Shell();
    private static SCP scp = new SCP();
    private static Image_hold im_hold = new Image_hold();
    private static Play play = new Play();
    private static MainFrame mframe = new MainFrame();
    private static Playlist pl = new Playlist();
    private static Shell_Monitor sh_mon = new Shell_Monitor();
    private static Play_Monitor play_mon = new Play_Monitor();
    private static Listen_Monitor list_mon = new Listen_Monitor();
    private static SCPFrom_Monitor scp_mon = new SCPFrom_Monitor();
    private static PlayFrame playframe = new PlayFrame();
    private static FullScreenFrame fsf = new FullScreenFrame();
    private static FS_Navi_Tab fsnt = new FS_Navi_Tab();
    private static SettingsDiag setframe = new SettingsDiag();
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

    public static Play getPlay() {
        return play;
    }

    public static void setPlay(Play vplay) {
        Instance_hold.play = vplay;
    }

    public static Shell_Monitor getSh_mon() {
        return sh_mon;
    }

    public static void setSh_mon(Shell_Monitor sh_mon) {
        Instance_hold.sh_mon = sh_mon;
    }

    public static Play_Monitor getVplay_mon() {
        return play_mon;
    }

    public static void setVplay_mon(Play_Monitor vplay_mon) {
        Instance_hold.play_mon = vplay_mon;
    }


    public static SCPFrom_Monitor getSCPFrom_Monitor() {
        return scp_mon;
    }

    public static void setFrom_Monitor(SCPFrom_Monitor cnt_SCPFrom_Monitor) {
        Instance_hold.scp_mon = cnt_SCPFrom_Monitor;
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

    public static SettingsDiag getSetframe() {
        return setframe;
    }

    public static void setSetframe(SettingsDiag setframe) {
        Instance_hold.setframe = setframe;
    }

    public static MessageThread getMthread() {
        return mthread;
    }

    public static void setMthread(MessageThread mthread) {
        Instance_hold.mthread = mthread;
    }

    public static Listen_Monitor getLm() {
        return list_mon;
    }

    public static void setLm(Listen_Monitor lm) {
        Instance_hold.list_mon = lm;
    }
     
    public static void create_VPlay() {
        if (!Instance_hold.getPlay().isAlive()) Instance_hold.play = new Play();
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

    public static SCP getScpfrom() {
        return scp;
    }

    public static void setScpfrom(SCP scpfrom) {
        Instance_hold.scp = scpfrom;
    }

    public static Shell_Out getListen() {
        return listen;
    }

    public static void setListen(Shell_Out listen) {
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
/*
    public static Main_controls getMcontrols() {
        return mcontrols;
    }

    public static void setMcontrols(Main_controls mcontrols) {
        Instance_hold.mcontrols = mcontrols;
    }
*/
}