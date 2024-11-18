package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lombok.SneakyThrows;

public class DBConnection {
    public static DBConnection dbConnection;
    public Connection connection;
    private String dbPath;
    @SneakyThrows
    private DBConnection(String dbPath) {
        this.dbPath = dbPath;
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    @SneakyThrows
    public String getDocument(String gcsPath) {
        PreparedStatement statement = 
                connection.prepareStatement("SELECT * FROM document WHERE path=?");
        statement.setString(1, gcsPath);
        ResultSet resultSet = statement.executeQuery();
        // return resultSet.getString("parsed");
        return resultSet.next() ? resultSet.getString("parsed") : null;
    }
    
    public static DBConnection getInstance() {
        return getInstance("cache.db");
        // if (dbConnection == null) {
        //     dbConnection = new DBConnection();
        // }
        // return dbConnection;
    }

    @SneakyThrows
    public void createDocument(String gcsPath, String parse) {
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO document (path, parsed) VALUES (?, ?)");
        preparedStatement.setString(1, gcsPath);
        preparedStatement.setString(2, parse);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static DBConnection getInstance(String dbPath) {
        if (dbConnection == null || !dbConnection.dbPath.equals(dbPath)) {
            dbConnection = new DBConnection(dbPath);
        }
        return dbConnection;
    }
}
