package com.example.repo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {
    protected static String url = "jdbc:postgresql://localhost:5432/project0";
    protected static String username = "yoram";
    protected static String password = "Tennisball101";

    private static final Logger logger =
        LoggerFactory.getLogger(DatabaseConnection.class);

        public static Connection getConnection() throws SQLException
        {
        try {
            Connection connection = DriverManager.getConnection(url,username,password);

            if (connection != null && connection.isValid(2))
            {
                return connection;
            }
            throw new SQLException("Database connectmon is invalid or timed out.");
        } catch (SQLException e) {
            logger.error( "Database could not connect");
            throw e;
        }
    }
 }

