package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import javax.faces.context.FacesContext;

/**
 *
 * @author Bottago SA
 */
public final class conexion {
//
//    Connection con = null;
//    String connectString = "jdbc:postgresql://localhost:5432/prueba";
//    String user = "postgres";
//    String password = "123456";

    Connection con = null;
    String host;
    String databaseName;
    String databaseUser;
    String databasePassword;

    public conexion() {
//        try {
//            Class.forName("org.postgresql.Driver");
//            con = DriverManager.getConnection(connectString, user, password);
//            //Statement stmt = con.createStatement();
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println(e);
//        }
        try {
            Class.forName("org.postgresql.Driver");
            loadDatabaseConfig(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("environment"));

            con = DriverManager.getConnection("jdbc:postgresql://" + host + ":5432/" + databaseName, databaseUser, databasePassword);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void loadDatabaseConfig(String environment) {
        Properties p = new Properties();
        try {
            InputStream input = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/" + environment + ".properties");
            p.load(input);
            host = p.getProperty("host");
            databaseName = p.getProperty("databaseName");
            databaseUser = p.getProperty("databaseUser");
            databasePassword = p.getProperty("databasePassword");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
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
