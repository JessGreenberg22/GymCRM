package Controller;

import dao.DBContact;
import dao.DBReport;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsScreen implements Initializable {
    public ComboBox<Contact> contactBox;

    /*1st Report*/
    public TableView<model.Report> contactScheduleTable;
    public TableColumn appointmentIdCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descriptionCol;
    public TableColumn startCol;
    public TableColumn endCol;

   /**2nd Report*/
    public TableColumn customerIdCol;
    public TableView totalTable;
    public TableColumn monthCol;
    public TableColumn totalTypeCol;
    public TableColumn totalCol;


    /*3rd report*/
    public TableView customersTypeTable;
    public TableColumn customerIdCol2;
    public TableColumn customerATypeCol;

    public Button ReturnId;



    @Override
    /** displays database information to tables for reports*/

    public void initialize(URL url, ResourceBundle resourceBundle) {

        totalTable.setItems(DBReport.getTotalNumberOfAppointments());
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        totalTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalNumberOfAppointments"));

        customersTypeTable.setItems(DBReport.getIDandType());
        customerIdCol2.setCellValueFactory(new PropertyValueFactory<>("customerID"));
       customerATypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));


        ObservableList<Contact> contacts = DBContact.getAllContacts();
        contactBox.setItems(contacts);
    }

    /** report of upcoming appointments for contacts*/
    public void onContactComboBox(javafx.event.Event event){
        Contact selectedContact = contactBox.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            contactScheduleTable.setItems(DBReport.getAllAppointmentsByConsultant(selectedContact.getContactID()));
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }

    }
    /**Navigate to the MainScreen*/
    public void onReturn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 812, 363);
        stage.setScene(scene);
        stage.show();
    }
}