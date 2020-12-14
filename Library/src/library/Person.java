/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author rndis
 */
public class Person extends Constants {

    // LOGIN USER METHOD
    

    // ADD MEMBER
    public void addMember(String A_name, String A_email, String A_phone, String A_address) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(getDriver());
            Connection con = (Connection) DriverManager.getConnection(getDatabaseUrl(), getUser(), getPassword());
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("INSERT INTO members(name,email,phone,address) VALUES('" + A_name + "', '" + A_email + "', '" + A_phone + "' ,'" + A_address + "') ");
            successalert.setTitle("ADD STATUS");
            successalert.setHeaderText(null);
            successalert.setContentText(A_name + " was added successfully");
            successalert.showAndWait();
        } catch (ClassNotFoundException | SQLException e) {
            failurealert.setTitle("FAILED");
            failurealert.setHeaderText(null);
            failurealert.setContentText(e.toString());
            failurealert.showAndWait();
            System.out.println(e);
        }
    }

    public void addBook(String A_bname, String A_bgenre, String A_price) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(getDriver());
            Connection con = (Connection) DriverManager.getConnection(getDatabaseUrl(), getUser(), getPassword());
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("INSERT INTO books(BNAME,GENRE,PRICE) VALUES('" + A_bname + "', '" + A_bgenre + "', '" + A_price + "') ");
            successalert.setTitle("ADD STATUS");
            successalert.setHeaderText(null);
            successalert.setContentText(A_bname + " was added successfully");
            successalert.showAndWait();
        } catch (ClassNotFoundException | SQLException e) {
            failurealert.setTitle("FAILED");
            failurealert.setHeaderText(null);
            failurealert.setContentText(e.toString());
            failurealert.showAndWait();
            System.out.println(e);
        }
    }

    public void createUsers(String A_uname, String A_upass) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(getDriver());
            Connection con = (Connection) DriverManager.getConnection(getDatabaseUrl(), getUser(), getPassword());
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("INSERT INTO users(Username,Password) VALUES('" + A_uname + "', '" + A_upass + "') ");
            successalert.setTitle("ADD STATUS");
            successalert.setHeaderText(null);
            successalert.setContentText(A_uname + " was added successfully");
            successalert.showAndWait();
        } catch (ClassNotFoundException | SQLException e) {
            failurealert.setTitle("FAILED");
            failurealert.setHeaderText(null);
            failurealert.setContentText(e.toString());
            failurealert.showAndWait();
            System.out.println(e);
        }

    }
    
    public void ReturnBook(float returnDate, float issueDate, float Period) {
        float borrowed = 0;
        borrowed = returnDate - issueDate;
        float overTime = 0;
        overTime = Period - borrowed;
        float fine = 0;

        if (overTime < 0) {
            fine = 0 * overTime;
            try {
                Constants DBconnection = new Constants();
                Class.forName(DBconnection.getDriver());
                Connection con = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
                Statement st = con.createStatement();
                String statement = "INSERT INTO issued(`FINE`)VALUES('" + fine + "')";
                st.executeUpdate(statement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            fine = 20 * overTime;
            try {
                Constants DBconnection = new Constants();
                Class.forName(DBconnection.getDriver());
                Connection con = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
                Statement st = con.createStatement();
                String statement = "INSERT INTO issued(`FINE`)VALUES('" + fine + "')";
                st.executeUpdate(statement);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    public ObservableList viewIssued() throws ClassNotFoundException, SQLException{
        final ObservableList<IssuedDisplay> issueDetails = FXCollections.observableArrayList();
        Class.forName(getDriver());
        Connection con = (Connection) DriverManager.getConnection(getDatabaseUrl(), getUser(), getPassword());
        Statement stmt = (Statement) con.createStatement();
        ResultSet rs = (ResultSet) stmt.executeQuery("SELECT * FROM `issued`");
        
        while(rs.next()){
            issueDetails.add(new IssuedDisplay(rs.getString("IID"),rs.getString("Member"),rs.getString("Book_Name"),rs.getString("PERIOD"),rs.getString("ISSUED_DATE"),rs.getString("ReturnDate")));
        }
        return issueDetails;
        
    }
    

}
