package org.example;

import java.sql.*;

public class ReadAccessDataBaseData {

    static Connection conn = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    static PreparedStatement ps = null;
    static String dbURL = "jdbc:ucanaccess://D:/Work/NGDatabase.accdb";

    public static void main(String[] args) {
        String selectQuery = "SELECT TOP 1 AutoID FROM tblTestData ORDER BY ID DESC";
        String autoId = executeSelectQuery(selectQuery);

        String updatedAutoId = incrementAutoId(autoId);

        String updateQuery = "INSERT INTO tblTestData(AutoID) VALUES (?)";
        executeUpdateQuery(updateQuery,updatedAutoId);
    }

    private static String executeSelectQuery(String sqlQuery) {
        String autoId = null;
        try {
            // Register the JDBC driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conn = DriverManager.getConnection(dbURL);
            statement = conn.createStatement();
            //String query = "SELECT TOP 1 AutoID FROM tblTestData ORDER BY ID DESC";
            resultSet = statement.executeQuery(sqlQuery);

            // Check if there is a result
            if (resultSet.next()) {
                // Retrieve the value of the last inserted record
                autoId = resultSet.getString("AutoID");
                System.out.println("AutoId: "+autoId);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return autoId;
    }

    private static void executeUpdateQuery(String sqlQuery, String updatedAutoId) {
        try {
            // Register the JDBC driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conn = DriverManager.getConnection(dbURL);
            ps = conn.prepareStatement(sqlQuery);
            ps.setString(1, updatedAutoId);
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String incrementAutoId(String autoID) {
        String numberString = autoID.replaceAll("\\D+", "");
        int number = Integer.parseInt(numberString);
        // Increment the number
        number++;
        // Generate the new string with the incremented number
        String updatedAutoId = autoID.replaceAll(numberString, String.valueOf(number));
        System.out.println("Updated AutoID:"+updatedAutoId);
        return updatedAutoId;
    }
}
/*
        <dependency>
            <groupId>net.sf.ucanaccess</groupId>
            <artifactId>ucanaccess</artifactId>
            <version>5.0.1</version>
        </dependency>
 */