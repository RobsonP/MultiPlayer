/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.jcraft.jsch.UserInfo;

/**
 *
 * @author RobsonP
 */
    public class MyUserInfo implements UserInfo {
        @Override
        public String getPassword(){ return passwd; }

        public MyUserInfo(String password) {
            this.passwd = password;
        }

        @Override
        public boolean promptYesNo(String str) {
            System.out.println(str+"promptYesNo");
            return true;
        }

        String passwd;

        @Override
        public String getPassphrase() {
            return null;
        }
        
        @Override
        public boolean promptPassphrase(String message) {
            return true;
        }
        
        @Override
        public boolean promptPassword(String message) {
            return true;
        }
        
        @Override
        public void showMessage(String message) {
            System.out.println(message);
        }

        public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt, boolean[] echo) {
            return new String[3];
        }
    }
