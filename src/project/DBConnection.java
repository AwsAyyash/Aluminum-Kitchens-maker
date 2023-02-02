package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private Connection con;
    private String dbUsername = "root";
    private String dbPassword = "123456789";
    private String URL = "127.0.0.1";
    private String port = "3306";
    private String dbName = "AluminumCompany";
    private String dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";


    public DBConnection() {


    }

    public Connection connectDB() throws ClassNotFoundException, SQLException {

        dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
        System.out.println(dbURL);

        Properties p = new Properties();
        p.setProperty("user", dbUsername);
        p.setProperty("password", dbPassword);
        p.setProperty("useSSL", "false");
        p.setProperty("autoReconnect", "true");

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dbURL, p);


        return con;
    }

}
