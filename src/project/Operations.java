package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Operations {

    public static void executeStatement(String SQLStatement) throws Exception {

        try {

            Connection connection = Operations.connectDB();
            System.out.println("Connection established");

            Statement statement = connection.createStatement();
            statement.executeUpdate(SQLStatement);

            System.out.println(SQLStatement);

            statement.close();
            connection.close();
            System.out.println("Connection closed");


        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("SQL statement is not executed!");

        }
    }

    public static Connection connectDB() throws Exception {

        DBConnection dbConnection = new DBConnection();

        return dbConnection.connectDB();
    }

    public static ResultSet executeQueryToGetData(String SQLStatement) throws Exception {

        Connection connection = Operations.connectDB();
        System.out.println("Connection established");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQLStatement);
        System.out.println("Connection closed");


        return resultSet;
    }

}
