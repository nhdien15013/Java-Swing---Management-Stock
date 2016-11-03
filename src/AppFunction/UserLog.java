/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppFunction;

/**
 *
 * @author Administrator
 */
public class UserLog {
    private int userid;
    private String username;
    public UserLog(int userid, String username) {
        this.userid = userid;
        this.username = username; 
    }
    
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
