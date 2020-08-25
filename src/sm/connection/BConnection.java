/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ngoc Tuan
 */
public class BConnection {

    public static final String HOST = "jdbc:sqlserver://localhost:1433;databaseName=StudentManager";
    public static final String USERNAME = "sa";
    public static final String PASS = "123456";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(HOST, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(BConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
