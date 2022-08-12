package main;


import dao.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/** Main Class Application
 * @Author Jessica Greenberg Student ID 001462404
 */

public class Main extends Application {
/*
Opens and closes connections to Database
 */

    /*
    Loads Stage as Primary/Parent Scene
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load((getClass().getResource("/view/loginScreen.fxml")));
        primaryStage.setTitle("Customer Scheduling System/CRM");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }
    public static void main(String[] args){
            JDBC.openConnection();
    {launch(args); }

        Locale france = new Locale("fr","FR");

        try{
            ResourceBundle rb = ResourceBundle.getBundle("login_fr",Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("fr"))
                System.out.println(rb.getString("Login Good"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JDBC.closeConnection();
    }
}
