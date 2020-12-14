/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author rndis
 */
public class Library extends Application {

    ChoiceBox names;
    ComboBox bookCB;
    ComboBox IssuedIDtf;
    ComboBox tFMember;
    public final Alert successalert = new Alert(Alert.AlertType.INFORMATION);
    public final Alert failurealert = new Alert(Alert.AlertType.INFORMATION);

    private ObservableList<Person> observableNames;
    private Person connect = new Person();

    public Library() throws ClassNotFoundException, SQLException {
        try {
            observableNames = FXCollections.observableArrayList(connect.viewIssued());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Person p = new Person();

//     ========================LOGIN INTERFACE==============================================================
        Text userText = new Text("Username");
        TextField userTF = new TextField();

        Text passText = new Text("Password");
        final PasswordField pb = new PasswordField();

        Button loginBtn = new Button("LOGIN");

        GridPane loginGP = new GridPane();
        loginGP.setMinSize(600, 400);
        loginGP.setPadding(new Insets(10, 10, 10, 10));
        loginGP.setVgap(10);
        loginGP.setHgap(10);
        loginGP.setAlignment(Pos.CENTER);

        loginGP.add(userText, 0, 0);
        loginGP.add(userTF, 1, 0);
        loginGP.add(passText, 0, 1);
        loginGP.add(pb, 1, 1);
        loginGP.add(loginBtn, 1, 2);

        Scene loginScene = new Scene(loginGP);
        primaryStage.setTitle("LIBRARY MANAGEMENT SYSTEM");

//     ========================MENU INTERFACE=====================================================================================
        Button addMember = new Button("Add Member");
        Button viewMember = new Button("View Issued");
        Button addBook = new Button("Add Book");
        Button issueBook = new Button("Issue Book");
        Button returnBook = new Button("Return Book");
        Button genReport = new Button("Reports");
        Button refresh = new Button("Refresh");
        Button createUsers = new Button("Add Users");
        Button exitApp = new Button("Exit");
        exitApp.setStyle("-fx-background-color: #F08080;-fx-text-fill: #0000ff;-fx-font-size: 1.5em;");
        exitApp.setOnAction((e) -> {
            primaryStage.close();
        });

        GridPane dashboardGP = new GridPane();
        dashboardGP.setMinSize(600, 400);
        dashboardGP.setPadding(new Insets(10, 10, 10, 10));
        dashboardGP.setVgap(10);
        dashboardGP.setHgap(10);
        dashboardGP.setAlignment(Pos.CENTER);

        dashboardGP.add(addMember, 0, 0);
        dashboardGP.add(addBook, 1, 0);
        dashboardGP.add(issueBook, 2, 0);
        dashboardGP.add(returnBook, 0, 1);
        dashboardGP.add(genReport, 1, 1);
        dashboardGP.add(createUsers, 2, 1);
        dashboardGP.add(exitApp, 2, 3);
        dashboardGP.add(viewMember, 2, 2);
        dashboardGP.add(refresh, 1, 2);

        Scene dashboardscene = new Scene(dashboardGP);

        loginBtn.setOnAction((e) -> {

            try {
                final String A_Username = userTF.getText();
                final String A_Password = pb.getText();

                Constants DBconnection = new Constants();
                Class.forName(DBconnection.getDriver());
                Connection con = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
                Statement stmt = (Statement) con.createStatement();
                String statement = "SELECT * FROM users ";
                ResultSet rs = stmt.executeQuery(statement);

                while (rs.next()) {
                    String name = rs.getString("Username");
                    String password = rs.getString("Password");

                    if (A_Username.equals(name) && A_Password.equals(password)) {
                        successalert.setTitle("ADD STATUS");
                        successalert.setHeaderText(null);
                        successalert.setContentText("Welcome " + name);
                        successalert.showAndWait();
                        primaryStage.setScene(dashboardscene);

                    } else {

                    }
                }

                if (A_Username.isEmpty()) {
                    failurealert.setTitle("FAILED");
                    failurealert.setHeaderText(null);
                    failurealert.setContentText("Please enter your username");
                    failurealert.show();

                }
                if (A_Password.isEmpty()) {
                    failurealert.setTitle("FAILED");
                    failurealert.setHeaderText(null);
                    failurealert.setContentText("Please enter your password");
                    failurealert.show();

                }
            } catch (Exception ef) {
                System.out.print(ef);

            }
        });

//     ========================END OF MENU INTERFACE=====================================================================================
//      ========================ADD MEMBER INTERFACE=====================================================================================
        Text tName = new Text("Name");
        TextField tfMember = new TextField();

        Text tEmail = new Text("Email");
        TextField tfEmail = new TextField();

        Text tel_no = new Text("Phone No.");
        TextField tfNo = new TextField();

        Text addressT = new Text("Address");
        TextField addressTF = new TextField();

        Button addBtn = new Button("Add Member");
        Button mainMenu = new Button("Main Menu");

        GridPane memberPane = new GridPane();
        memberPane.setMinSize(600, 400);
        memberPane.setPadding(new Insets(10, 10, 10, 10));
        memberPane.setVgap(10);
        memberPane.setHgap(10);
        memberPane.setAlignment(Pos.CENTER);

        memberPane.add(tName, 0, 0);
        memberPane.add(tfMember, 1, 0);
        memberPane.add(tEmail, 0, 1);
        memberPane.add(tfEmail, 1, 1);
        memberPane.add(tel_no, 0, 2);
        memberPane.add(tfNo, 1, 2);
        memberPane.add(addressT, 0, 3);
        memberPane.add(addressTF, 1, 3);
        memberPane.add(addBtn, 1, 4);
        memberPane.add(mainMenu, 0, 4);

        addBtn.setOnAction((e) -> {
            try {
                p.addMember(tfMember.getText(), tfEmail.getText(), tfNo.getText(), addressTF.getText());
                tfMember.clear();
                tfEmail.clear();
                tfNo.clear();
                addressTF.clear();
            } catch (Exception ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        mainMenu.setOnAction((e) -> {
            primaryStage.setScene(dashboardscene);
        });

        Scene A_memberScene = new Scene(memberPane);
//      ========================END OF ADD MEMBER INTERFACE=====================================================================================

//      ======================== VIEW ISSUED BOOKS INTERFACE=====================================================================================
        TableView viewMembertableView = new TableView();
        Constants DBconnection = new Constants();
        Class.forName(DBconnection.getDriver());
        Connection con = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
        Statement stmt = (Statement) con.createStatement();
        ResultSet rs = (ResultSet) stmt.executeQuery("SELECT * FROM `members`");

        TableColumn<IssuedDisplay, String> column1 = new TableColumn<>("ISSUED ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("issuedID"));
        TableColumn<IssuedDisplay, String> column2 = new TableColumn<>("MEMBER NAME");
        column2.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        TableColumn<IssuedDisplay, String> column3 = new TableColumn<>("BOOK NAME");
        column3.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        TableColumn<IssuedDisplay, String> column4 = new TableColumn<>("PERIOD");
        column4.setCellValueFactory(new PropertyValueFactory<>("period"));
        TableColumn<IssuedDisplay, String> column5 = new TableColumn<>("ISSUED DATE");
        column5.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        TableColumn<IssuedDisplay, String> column6 = new TableColumn<>("RETURN DATE");
        column6.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        viewMembertableView.getColumns().addAll(column1, column2, column3, column4, column5, column6);
        viewMembertableView.setItems(p.viewIssued());
        VBox usersvbox = new VBox();
        usersvbox.setSpacing(5);
        usersvbox.setMinSize(1000, 1000);
        usersvbox.setPadding(new Insets(10, 0, 0, 10));

        Button reLoadTable = new Button("REFRESH TABLE");
        Button returnBackToMenuButton = new Button("Main Menu");

        reLoadTable.setOnAction((e) -> {
            try {
//                System.out.println("Restarting the program");
                primaryStage.close();
                Platform.runLater(() -> {
                    try {
                        new Library().start(new Stage());
                    } catch (Exception ex) {
                        Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } catch (Exception ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        returnBackToMenuButton.setOnAction((e) -> {
            primaryStage.setScene(dashboardscene);
        });

        HBox topPane = new HBox(returnBackToMenuButton, reLoadTable);
        topPane.setSpacing(30);
        usersvbox.getChildren().addAll(topPane, viewMembertableView);
        Scene viewmembersScene = new Scene(new Group());
        ((Group) viewmembersScene.getRoot()).getChildren().addAll(usersvbox);

//      ========================END OF VIEW MEMBER INTERFACE=====================================================================================
//      ========================ADD BOOK INTERFACE=====================================================================================
        Text bookName = new Text("Book Name");
        TextField tfBKN = new TextField();

        Text tGenre = new Text("Genre");
        TextField tfGenre = new TextField();

        Text tPrice = new Text("Price");
        TextField tfPrice = new TextField();

        Button addB = new Button("Add");
        Button back = new Button("Main Menu");

        GridPane bookAddPane = new GridPane();
        bookAddPane.setMinSize(600, 400);
        bookAddPane.setPadding(new Insets(10, 10, 10, 10));
        bookAddPane.setVgap(10);
        bookAddPane.setHgap(10);
        bookAddPane.setAlignment(Pos.CENTER);

        bookAddPane.add(bookName, 0, 0);
        bookAddPane.add(tfBKN, 1, 0);
        bookAddPane.add(tGenre, 0, 1);
        bookAddPane.add(tfGenre, 1, 1);
        bookAddPane.add(tPrice, 0, 2);
        bookAddPane.add(tfPrice, 1, 2);
        bookAddPane.add(addB, 1, 3);
        bookAddPane.add(back, 0, 3);

        addB.setOnAction((e) -> {
            try {
                p.addBook(tfBKN.getText(), tfGenre.getText(), tfPrice.getText());
                tfBKN.clear();
                tfGenre.clear();
                tfPrice.clear();
            } catch (Exception ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        back.setOnAction((e) -> {
            primaryStage.setScene(dashboardscene);
        });

        Scene A_BookScene = new Scene(bookAddPane);
//      ========================END OF ADD BOOK INTERFACE=====================================================================================

//      ========================ADD USER INTERFACE=====================================================================================
        Text tUsername = new Text("Name");
        TextField tfUser = new TextField();

        Text tPassword = new Text("Password");
        final PasswordField userPB = new PasswordField();

        Button addUSER = new Button("Add");
        Button backUSER = new Button("Main Menu");

        GridPane addUserP = new GridPane();
        addUserP.setMinSize(600, 400);
        addUserP.setPadding(new Insets(10, 10, 10, 10));
        addUserP.setVgap(10);
        addUserP.setHgap(10);
        addUserP.setAlignment(Pos.CENTER);

        addUserP.add(tUsername, 0, 0);
        addUserP.add(tfUser, 1, 0);
        addUserP.add(tPassword, 0, 1);
        addUserP.add(userPB, 1, 1);
        addUserP.add(addUSER, 1, 2);
        addUserP.add(backUSER, 0, 2);

        addUSER.setOnAction((e) -> {
            try {
                p.createUsers(tfUser.getText(), userPB.getText());
                tfUser.clear();
                userPB.clear();
            } catch (Exception ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        backUSER.setOnAction((e) -> {
            primaryStage.setScene(dashboardscene);
        });

        Scene A_UserScene = new Scene(addUserP);

//      ========================END OF ADD USER INTERFACE=====================================================================================
//      ========================ISSUE INTERFACE==========================================================================
        Text userT = new Text("Member");
        names = new ChoiceBox();

        try {
            //    Constants DBconnection2 = new Constants();
            Class.forName(DBconnection.getDriver());
            Connection con2 = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
            Statement st = con2.createStatement();
            String statement = "SELECT * FROM members";
            ResultSet resultSet = st.executeQuery(statement);

            while (resultSet.next()) {  // loop
                String data = resultSet.getString("name");
                names.getItems().addAll(data);
            }
            con.close();
        } catch (Exception ee) {
            System.out.println(ee);
        }

        Text BIDT = new Text("Book Name");
        bookCB = new ComboBox();

        try {
            //    Constants DBconnection = new Constants();
            Class.forName(DBconnection.getDriver());
            Connection con3 = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
            Statement st = con3.createStatement();
            String statement = "SELECT * FROM books";
            ResultSet resultSet = st.executeQuery(statement);

            while (resultSet.next()) {  // loop
                String data = resultSet.getString("BNAME");
                bookCB.getItems().addAll(data);
            }
            con.close();
        } catch (Exception ee) {
            System.out.println(ee);
        }
        Text pText = new Text("Period");
        TextField tfP = new TextField();

        Text issuedT = new Text("Issued Date(yyyy-MM-dd)");
        TextField issuEDP = new TextField();

        Button issueBTN = new Button("Issue");
        Button backBTN = new Button("Main Menu");

        GridPane issueGP = new GridPane();
        issueGP.setMinSize(600, 400);
        issueGP.setPadding(new Insets(10, 10, 10, 10));
        issueGP.setVgap(10);
        issueGP.setHgap(10);
        issueGP.setAlignment(Pos.CENTER);

        issueGP.add(userT, 0, 0);
        issueGP.add(names, 1, 0);
        issueGP.add(BIDT, 0, 1);
        issueGP.add(bookCB, 1, 1);
        issueGP.add(pText, 0, 2);
        issueGP.add(tfP, 1, 2);
        issueGP.add(issuedT, 0, 3);
        issueGP.add(issuEDP, 1, 3);
        issueGP.add(issueBTN, 1, 4);
        issueGP.add(backBTN, 0, 4);

        issueBTN.setOnAction((e) -> {
            try {
                final String memberName = (String) names.getValue();
                final String bookNAME = (String) bookCB.getValue();
                final String perioDTT = tfP.getText();
                final String DATE = issuEDP.getText();

                //        Constants DBconnection = new Constants();
                Class.forName(DBconnection.getDriver());
                Connection con4 = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
                Statement stmt8 = (Statement) con4.createStatement();
                stmt8.executeUpdate("INSERT INTO issued(Member,Book_Name,PERIOD,ISSUED_DATE)VALUES('" + memberName + "','" + bookNAME + "','" + perioDTT + "','" + DATE + "')");
                Statement st3 = (Statement) con4.createStatement();
                String query = "SELECT IID FROM issued WHERE Member = '"+memberName+"' AND ReturnDate IS NULL ";
                ResultSet rs7 = st3.executeQuery(query);

                if (rs7.next()) {
                    String total3 = rs7.getString("IID");
                    successalert.setTitle("ADD STATUS");
                    successalert.setHeaderText(null);
                    successalert.setContentText("YOUR ISSUE ID IS " + total3);
                    successalert.showAndWait();
                }

                con4.close();

            } catch (Exception ex) {
                System.out.print(ex);
            }
        });

        backBTN.setOnAction((e) -> {
            primaryStage.setScene(dashboardscene);
        });

        Scene issueScene = new Scene(issueGP);

//      ========================END OF ISSUE INTERFACE==========================================================================
//      ========================RETURN BOOK INTERFACE==========================================================================
        Text IssuedID = new Text("Issued ID");
        IssuedIDtf = new ComboBox();

        try {
            //    Constants DBconnection = new Constants();
            Class.forName(DBconnection.getDriver());
            Connection con5 = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
            Statement st = con5.createStatement();
            String statement = "SELECT IID FROM issued WHERE ReturnDate IS NULL";
            ResultSet resultSet = st.executeQuery(statement);

            while (resultSet.next()) {  // loop
                String data = resultSet.getString("IID");
                IssuedIDtf.getItems().addAll(data);
            }
            con.close();
        } catch (Exception ee) {
            System.out.println(ee);
        }

        Text Return = new Text("Return Date(yyyy-MM-dd)");
        TextField returnTF = new TextField();

        Button returnBTN = new Button("Return");
        Button menuBTN = new Button("Main Menu");

        GridPane returnPane = new GridPane();
        returnPane.setMinSize(600, 400);
        returnPane.setPadding(new Insets(10, 10, 10, 10));
        returnPane.setVgap(10);
        returnPane.setHgap(10);
        returnPane.setAlignment(Pos.CENTER);

        returnPane.add(IssuedID, 0, 0);
        returnPane.add(IssuedIDtf, 1, 0);
        returnPane.add(Return, 0, 1);
        returnPane.add(returnTF, 1, 1);
        returnPane.add(menuBTN, 0, 2);
        returnPane.add(returnBTN, 1, 2);

        menuBTN.setOnAction((e) -> {
            primaryStage.setScene(dashboardscene);
        });

        returnBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    final String returnDate = returnTF.getText();
                    final String issueID = (String) IssuedIDtf.getValue();
                    Constants DBconnection = new Constants();
                    Class.forName(DBconnection.getDriver());
                    Connection con = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
                    Statement stmt = (Statement) con.createStatement();
                    stmt.executeUpdate("UPDATE issued SET ReturnDate='" + returnDate + "' WHERE IID='" + issueID + "'");
                    con.close();
                    successalert.setTitle("ADD STATUS");
                    successalert.setHeaderText(null);
                    successalert.setContentText("Book was successfully returned");
                    successalert.showAndWait();

                } catch (Exception ee) {
                    failurealert.setTitle("FAILED");
                    failurealert.setHeaderText(null);
                    failurealert.setContentText(ee.toString());
                    failurealert.showAndWait();
                    System.out.println(ee);
                }
            }
        });

        Scene returnScene = new Scene(returnPane);

//      ========================END OF RETURN BOOK INTERFACE==========================================================================
//      ======================== REPORT INTERFACE==========================================================================
        GridPane gridpane = new GridPane();
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        gridpane.setAlignment(Pos.TOP_CENTER);
        gridpane.setPadding(new Insets(10, 10, 10, 10));
        gridpane.setMinSize(700, 700);
        TextArea area = new TextArea();
        area.setMinSize(600, 600);
        Button report = new Button("Quick Report");
        Button print = new Button("Save Report");
        Button UsersT = new Button("Users");
        Button membersT = new Button("Members");
        Button issuedBookt = new Button("Issued Books");
        Button returnBookT = new Button("Return Books");
        Button backMenu = new Button("Main Menu");
        area.setEditable(false);

        gridpane.add(area, 0, 0);
        gridpane.add(report, 1, 1);
        gridpane.add(print, 1, 3);
        gridpane.add(backMenu, 1, 2);

        backMenu.setOnAction((e) -> {
            primaryStage.setScene(dashboardscene);
        });

        report.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Constants DBconnection = new Constants();
                    Class.forName(DBconnection.getDriver());
                    Connection con = (Connection) DriverManager.getConnection(DBconnection.getDatabaseUrl(), DBconnection.getUser(), DBconnection.getPassword());
                    Statement stmt = (Statement) con.createStatement();
                    Statement stmt2 = (Statement) con.createStatement();
                    Statement stmt3 = (Statement) con.createStatement();
                    Statement stmt4 = (Statement) con.createStatement();
                    Statement stmt5 = (Statement) con.createStatement();

                    // NUMBER OF USERS
                    String query = "SELECT UID, COUNT(UID) FROM users ";
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        String total = rs.getString("COUNT(UID)");
                        // int count = 0;
                        //int count = rs.getInt("UID");
                        area.setText("*********************************************************************************************\n");
                        area.setText(area.getText() + "\t\t\t     * QUICK REPORT *\n");
                        area.setText(area.getText() + "Total number of Users: " + total + "\n");

                    }

                    // NUMBER OF MEMBERS
                    String query2 = "SELECT MemberID, COUNT(MemberID) FROM members ";
                    ResultSet rs2 = stmt2.executeQuery(query2);

                    if (rs2.next()) {
                        String total2 = rs2.getString("COUNT(MemberID)");
                        area.setText(area.getText() + "Total number of Members: " + total2 + "\n");

                    }

                    //NUMBER OF BOOKS
                    String query3 = "SELECT BID, COUNT(BID) FROM books ";
                    ResultSet rs3 = stmt3.executeQuery(query3);

                    if (rs3.next()) {
                        String total3 = rs3.getString("COUNT(BID)");
                        area.setText(area.getText() + "Total number of Books: " + total3 + "\n");
                    }

                    // NUMBER OF BOOKS ISSUED
                    String query4 = "SELECT IID, COUNT(IID) FROM issued WHERE ReturnDate IS NULL";
                    ResultSet rs4 = stmt4.executeQuery(query4);

                    if (rs4.next()) {
                        String total4 = rs4.getString("COUNT(IID)");
                        area.setText(area.getText() + "Total number of Books Issued: " + total4 + "\n");
                    }

                    // NUMBER OF BOOKS RETURNED
                    String query5 = "SELECT IID, COUNT(IID) FROM issued WHERE ReturnDate IS NOT NULL";
                    ResultSet rs5 = stmt5.executeQuery(query5);

                    if (rs5.next()) {
                        String total5 = rs5.getString("COUNT(IID)");
                        area.setText(area.getText() + "Total number of Books Returned: " + total5 + "\n");

                    }

                } catch (Exception ee) {
                    System.out.println(ee);
                }

            }
        });

        Scene reportScene = new Scene(gridpane);

//      ========================END OF REPORT INTERFACE==========================================================================
        // UNIVERSAL MENU BUTTONS
        addMember.setOnAction((e) -> {
            primaryStage.setScene(A_memberScene);
        });

        addBook.setOnAction((e) -> {
            primaryStage.setScene(A_BookScene);
        });

        createUsers.setOnAction((e) -> {
            primaryStage.setScene(A_UserScene);
        });

        issueBook.setOnAction((e) -> {
            primaryStage.setScene(issueScene);
        });
        returnBook.setOnAction((e) -> {
            primaryStage.setScene(returnScene);
        });
        genReport.setOnAction((e) -> {
            primaryStage.setScene(reportScene);
        });

        viewMember.setOnAction((e) -> {
            primaryStage.setScene(viewmembersScene);
        });

        refresh.setOnAction((e) -> {
            try {
//                System.out.println("Restarting the program");
                primaryStage.close();
                Platform.runLater(() -> {
                    try {
                        new Library().start(new Stage());
                    } catch (Exception ex) {
                        Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } catch (Exception ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
