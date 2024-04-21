package lot.lotapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection databaseLink;

    public static Connection getConnect(String url, String user, String passwd) {
        try {
            databaseLink = DriverManager.getConnection(url, user, passwd);
            System.out.println(("Connected to database"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }
}
