package database;

import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum DbConnection {

    INSTANCE;

    @Getter
    private Connection connection;


    DbConnection() {

        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/db.properties"));

            String url = properties.getProperty("url");
            String password = properties.getProperty("password");
            String username = properties.getProperty("username");

            this.connection = DriverManager.getConnection(url, username, password);

        } catch (IOException | SQLException e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }


}
