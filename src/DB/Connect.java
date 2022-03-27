package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static String URL = "jdbc:postgresql://127.0.0.1:5432/dentistry";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "54321";
    Connection connection;
    public Connect() throws SQLException {
        try {
            Class<?> dbDriver = Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
