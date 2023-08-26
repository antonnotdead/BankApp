package com.clevertecbank.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnector {
    private final String url = "jdbc:postgresql://localhost:5432/bankapp";
    private final String user = "postgres";
    private final String password = "rootroot";
}
