package com.suzen.data.utilities;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {


    private static final Logger logs = Logger.getLogger(DataConnection.class);
    private static final String driver="com.mysql.cj.jdbc.Driver";
    private static final String Url="jdbc:mysql://localhost:3306/studentdb";
    private static final String username="root";
    private static final String password="Password@123";
    private static final String exceptionFormat="exception in %s,message: %s,code: %s";
    private static  Connection connect;



    public static Connection getConnect() {

        if (connect == null) {
            synchronized (DataConnection.class) {  //prevents multiple threads from creating multiple connections at the same time.
                if (connect == null) {
                    try {
                        Class.forName(driver).newInstance(); //registers the driver
                        connect = DriverManager.getConnection(Url, username, password);
                        logs.info("------CONNECTED------");
                    } catch (SQLException e) {
                        handleSQLException("DataConnection.getConnection",e,logs);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Failed class not found", e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    }catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to instantiate JDBC driver", e);
                    }
                }
            }


        }
        return connect;
    }


    public static void handleSQLException(String method,SQLException e,Logger log){
        log.warn(String.format(exceptionFormat,method,e.getMessage(),e.getErrorCode()));
        throw new RuntimeException(e);
    }

    public static void handleClassException(String method,ClassNotFoundException e,Logger log){
        log.warn(String.format(exceptionFormat,method,e.getMessage(),e.getCause()));
        throw new RuntimeException(e);
    }


//        try{
//            String driver = "com.mysql.cj.jdbc.Driver";
//            String url = "";
//            String username = "";
//            String password = "";
//            Class.forName( driver).newInstance();
//            Connection connect=DriverManager.getConnection(url,username,password);
//            logs.info("------CONNECTED------");
//            return connect;
//        }catch (ClassNotFoundException e){
//            logs.error(" ERROR: Class not found " + e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }

}
