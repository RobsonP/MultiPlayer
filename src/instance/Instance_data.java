/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package instance;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import data.Node_entry;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * contains all data, which is necessery to run the program and needs to
 * change dynamically. It contains getter- and setter-methods to access
 * the data.
 * @author RobsonP
 */
public class Instance_data {
    private static String uname = "";
    private static String pw = "";
    private static String host;
    private static String hostnm;
    private static String rsakeyPath;
    private static String rsakeypw;
    private static String cmd;
    private static String tmpPath;
    private static String[] cpystr;
    private static int port;
    private static int lastout;
    private static long media_time;
    private static long media_length;
    private static StringBuffer dirbuf = new StringBuffer("");
    private static int dlindx;
    private static int currentplay = -1;
    private static boolean trackchange = false;
    private static String playpath;
    private static int exitedthreads = 3;
    private static ArrayList<Node_entry> allnodes = new ArrayList<>();
    private static ChannelSftp channel;
    private static ByteArrayOutputStream baos;
    private static Session session;
    private static JSch jsch;
    private static UserInfo ui;

    public synchronized static String getPw() {
        return pw;
    }

    public synchronized static void setPw(String pw) {
        Instance_data.pw = pw;
    }

    public synchronized static String getUname() {
        return uname;
    }

    public synchronized static void setUname(String uname) {
        Instance_data.uname = uname;
    }

    public synchronized static String getHost() {
        return host;
    }

    public synchronized static void setHost(String host) {
        Instance_data.host = host;
    }

    public synchronized static int getPort() {
        return port;
    }

    public synchronized static void setPort(int port) {
        Instance_data.port = port;
    }

    public synchronized static String getRsakeyPath() {
        return rsakeyPath;
    }

    public synchronized static void setRsakeyPath(String rsakeyPath) {
        Instance_data.rsakeyPath = rsakeyPath;
    }

    public synchronized static String getCmd() {
        return cmd;
    }

    public synchronized static void setCmd(String cmd) {
        Instance_data.cmd = cmd;
    }

    public synchronized static int getLastout() {
        return lastout;
    }

    public synchronized static void addtoLastout(int lastout) {
        Instance_data.lastout = Instance_data.lastout + lastout;
    }

    public synchronized static String[] getCpystr() {
        return cpystr;
    }

    public synchronized static void setCpystr(String[] cpystr) {
        Instance_data.cpystr = cpystr;
    }

    public synchronized static long getMedia_time() {
        return media_time;
    }

    public synchronized static void setMedia_time(long media_time) {
        Instance_data.media_time = media_time;
    }

    public synchronized static long getMedia_length() {
        return media_length;
    }

    public synchronized static void setMedia_length(long media_length) {
        Instance_data.media_length = media_length;
    }

    public synchronized static StringBuffer getDirbuf() {
        return dirbuf;
    }

    public synchronized static void setDirbuf(StringBuffer dirbuf) {
        Instance_data.dirbuf = dirbuf;
    }

    public synchronized static int getDlindx() {
        return dlindx;
    }

    public synchronized static void setDlindx(int dlindx) {
        Instance_data.dlindx = dlindx;
    }

    public synchronized static int getCurrentplay() {
        return currentplay;
    }

    public synchronized static void setCurrentplay(int currentplay) {
        Instance_data.currentplay = currentplay;
    }

    public synchronized static boolean isTrackchange() {
        return trackchange;
    }

    public synchronized static void setTrackchange(boolean trackchange) {
        Instance_data.trackchange = trackchange;
    }

    public synchronized static String getRsakeypw() {
        return rsakeypw;
    }

    public synchronized static void setRsakeypw(String rsakeypw) {
        Instance_data.rsakeypw = rsakeypw;
    }

    public synchronized static String getTmpPath() {
        return tmpPath;
    }

    public synchronized static void setTmpPath(String tmpPath) {
        Instance_data.tmpPath = tmpPath;
    }
    
    public synchronized static String getPlaypath() {
        return playpath;
    }

    public synchronized static void setPlaypath(String playpath) {
        Instance_data.playpath = playpath;
    }

    public synchronized static int getExitedthreads() {
        return exitedthreads;
    }

    public synchronized static void incrementExitedthreads() {
        Instance_data.exitedthreads++;
    }
    
    public synchronized static void decrementExitedthreads() {
        Instance_data.exitedthreads--;
    }
/*
    public synchronized static ArrayList<Node_entry> getAllnodes() {
        return allnodes;
    }

    public synchronized static void setAllnodes(ArrayList<Node_entry> allnodes) {
        Instance_data.allnodes = allnodes;
    }
*/
    public synchronized static ChannelSftp getChannel() {
        return channel;
    }

    public synchronized static void setChannel(ChannelSftp channel) {
        Instance_data.channel = channel;
    }

    public synchronized static ByteArrayOutputStream getBaos() {
        return baos;
    }

    public synchronized static void setBaos(ByteArrayOutputStream baos) {
        Instance_data.baos = baos;
    }
 /*   
    public synchronized static void create_new_Nodelist() {
        allnodes.clear();
        //allnodes = new ArrayList<>();
    }
*/
    public synchronized static String getHostnm() {
        return hostnm;
    }

    public synchronized static void setHostnm(String hostnm) {
        Instance_data.hostnm = hostnm;
    }

    public synchronized static Session getSession() {
        return session;
    }

    public synchronized static void setSession(Session session) {
        Instance_data.session = session;
    }

    public synchronized static JSch getJsch() {
        return jsch;
    }

    public synchronized static void setJsch(JSch jsch) {
        Instance_data.jsch = jsch;
    }

    public synchronized static UserInfo getUi() {
        return ui;
    }

    public synchronized static void setUi(UserInfo ui) {
        Instance_data.ui = ui;
    }

    
}
