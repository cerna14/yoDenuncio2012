package cl.cxc.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConexion {
    public static Connection obtieneConexion(){
        
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "yodenuncio";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "undertaker";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("Connected to the database");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void cerrarConexion(Connection con){
        try {
            con.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        System.out.println("Disconnected from database");
    }

}
