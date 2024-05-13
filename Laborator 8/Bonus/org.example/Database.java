package org.example;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;


public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";
    private static Connection connection = null;
    private static final DataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        dataSource = new HikariDataSource(config);
    }

    public Database() {
    }

    public static Connection getConnection() {
        if(connection == null) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection() {
        try {
            //connection = DriverManager.getConnection(URL, USER, PASSWORD);
            //pool connection
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("1" + e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("2" + e);
            }
        }
    }

    public static void rollback() {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("3" + e);
            }
        }
    }
}
