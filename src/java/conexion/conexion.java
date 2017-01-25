package conexion;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bottago SA
 */
public final class conexion {
    Connection con = null;
    String connectString = "jdbc:postgresql://localhost:5432/prueba";
    String user = "postgres";
    String password = "123456";
    
    
    public conexion(){
        try{
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection(connectString, user , password);
        //Statement stmt = con.createStatement();
        }catch(SQLException|ClassNotFoundException e){
                System.out.println(e);
                }
    }

    public static void main(String[] args) throws SQLException{
        conexion obj = new conexion();
        Connection con = obj.getConnection();
        con.close();
    }

    public Connection getConnection() {
        return con;
    }

    public void desconectar() throws SQLException {
    con.close();
    }
}