package backend.database;

import java.sql.*;

public class DatabaseConnection {

    static String url = "jdbc:mysql://localhost:3306/unimart";
    static String username = "root";
    static String password = "root123!";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, username, password);
    }
}