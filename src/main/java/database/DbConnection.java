package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

   // private static final String URL = "jdbc:mysql://localhost:3306/bookstore_db"; //use your own db name
   // private static final String USER = "root";
  //  private static final String PASSWORD = "1234"; //use your own password

    private static Connection connection;

    public static Connection getConnection(String url, String username, String password)  {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("Fatal error when acquiring db connection!");
                System.exit(0);
            }
        }
        return connection;
    }
}
