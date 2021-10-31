package com.example.noteapp;

import java.sql.*;
import javax.sql.*;

public class DBUtil {
    private static Connection conn;

    public DBUtil() throws SQLException {
        String databaseURL = "./database";
        conn = DriverManager.getConnection("jdbc:h2:./data/database", "sa", "");
    }

    public static void createTable(String name) throws SQLException {
        Statement statement = conn.createStatement();
        String command = "CREATE TABLE " + name + ";";
        statement.execute(command);
        System.out.println("OK");
    }

    public static void showTables() throws SQLException {
        Statement statement = conn.createStatement();
        String command = "SHOW TABLES;";
        ResultSet set = statement.executeQuery(command);
        while(set.next()) {
            String name = set.getString();

        }
    }
}
