package com.crud.crud.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public void getDBConnect(){
        synchronized (""){
            try{
                if (this.getConnection() == null || this.getConnection().isClosed()){
                    try {
                        String url = "jdbc:mysql://localhost/student_list";
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        setConnection(DriverManager.getConnection(url, "root", ""));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void setConnection(Connection sample){
        connection = sample;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
