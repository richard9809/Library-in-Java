/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import javafx.scene.control.Alert;

/**
 *
 * @author rndis
 */
public class Constants {
    //    Alert Boxes
    public final Alert successalert = new Alert(Alert.AlertType.INFORMATION);
    public final Alert failurealert = new Alert(Alert.AlertType.INFORMATION);
//  =============================================Database Constants===========================================================================================================
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/library?useTimezone=true&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD ="";
    public  String getDriver(){
        return DRIVER;
    }
    public  String getDatabaseUrl(){
        return DATABASE_URL;
    }
    public  String getUser(){
        return USER;
    }
    public  String getPassword(){
        return PASSWORD;
    }
//  ========================================End of Database Constants===========================================================================================================
}
