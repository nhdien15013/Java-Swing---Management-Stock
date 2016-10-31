/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

import java.sql.Connection;

/**
 *
 * @author Administrator
 */
public class TestConnect {
    public static void main(String[] args) {
        Connection conn = DataConnection.getConnection();
        if(conn!=null){
            System.out.println("connect successful!");
        }
    }
}
