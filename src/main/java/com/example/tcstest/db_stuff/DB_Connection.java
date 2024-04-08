package com.example.tcstest.db_stuff;

import java.sql.*;

public class DB_Connection {
    private static Connection connection;
    private static Statement stmt;

    public DB_Connection() throws SQLException {
        // JDBC URL for connecting to MySQL
        String dbName = "invoice_info";
        String db_path = "jdbc:mysql://localhost:3306/" + dbName;
        String username = "root";
        String pwd = "password";

        try {
            // Step 1 (OPTIONAL): Load the JDBC driver
            // Ensures compatibility with older versions of JDBC (prior to JDBC 4.0)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish a connection to the database
            connection = DriverManager.getConnection(db_path, username, pwd);

            // Step 3: Create a statement
            stmt = connection.createStatement();

            // Step 4: Check if the table exists
            DatabaseMetaData metaData = connection.getMetaData();
//            stmt.executeUpdate("DROP TABLE USERS");

            ResultSet tables = metaData.getTables(null, null, "USERS", null);
            if (!tables.next()) {
                // Step 5: Create the table
                String createTableSQL = "CREATE TABLE USERS (ID INT NOT NULL AUTO_INCREMENT, FName VARCHAR(50) NOT NULL, LName VARCHAR(50) NOT NULL, Email VARCHAR(50) NOT NULL, Password VARCHAR(50), PRIMARY KEY (ID))";
                stmt.executeUpdate(createTableSQL);
                System.out.println("TABLE 'USERS' CREATED!");
            }
            tables.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//        String query = "INSERT INTO users (FName, LName, Email, Password) " +
//                "VALUES (?, ?, ?, ?, ?)";
//
//        PreparedStatement pstmt = connection.prepareStatement(query);
//        pstmt.setString(1, "Hello");
//        pstmt.setString(2, "I");
//        pstmt.setString(3, "Email@gmail.com");
//        pstmt.setString(4, "22655342");
//        pstmt.setString(5, "password");
//
//        int rowsInserted = pstmt.executeUpdate();
//        System.out.println("rowsInserted: "+ rowsInserted);

    }

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {
        return stmt;
    }

    public static void close() {
        try {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
