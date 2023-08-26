package com.clevertecbank.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnector {
    private final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    private final String URL = "jdbc:postgresql://localhost:5432/bankapp";
    private final String USER = "postgres";
    private final String PASSWORD = "rootroot";
    private static DBconnector instance = null;


    private DBconnector(){
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }

    public static DBconnector getInstance() {
        DBconnector localInstance = instance;
        if (localInstance == null) {
            synchronized (DBconnector.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DBconnector();
                }
            }
        }
        return localInstance;
    }
}

