package Controller;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import utility.localTime;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;
/** Schedule Appointment Controller
 * @Author Jessica Greenberg Student ID 001462404
 */
public class ScheduleAppointment implements Initializable {

   /*Button Id's*/
    public Button ReturnToAppointmentScreenId;
    public Button returnToMainScreenId;
    /*Ids for combo box and textfields*/
    public ComboBox <Contact> contactComboBox;
    public ComboBox locationComboBox;
    public ComboBox startComboBox;
    public ComboBox endComboBox;
    public ComboBox typeComboBox;
    public ComboBox <User> userComboBox;
    public ComboBox <Customer> customerComboBox;

    public TextField titleField;
    public TextField descriptionField;
    public DatePicker datePicker;
    private ObservableList<String> types = FXCollections.observableArrayList("Planning Session", "De-Briefing", "Follow-Up", "Termination");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contact> contacts = DBContact.getAllContacts();
        contactComboBox.setItems(contacts);

        ObservableList<User> userList = DBUser.getAllUsers();
        userComboBox.setItems(userList);

        ObservableList<Country> countries = DBCountry.getAllCountries();
        locationComboBox.setItems(countries);

        ObservableList<Customer> customers = DBCustomer.getAllCustomers();
        customerComboBox.setItems(customers);

        startComboBox.setItems(localTime.getStartTimeList());

        endComboBox.setItems(localTime.getEndTimeList());

        typeComboBox.setItems(types);


    }

    public void scheduleAppt(ActionEvent actionEvent) throws IOException, SQLException {

        String appointmentTitle = titleField.getText();
        String description = descriptionField.getText();
        String location = locationComboBox.getSelectionModel().getSelectedItem().toString();
        String type = typeComboBox.getSelectionModel().getSelectedItem().toString();
        LocalDate date = datePicker.getValue();
        LocalDateTime start = LocalDateTime.of(date, (LocalTime) startComboBox.getValue());
        LocalDateTime end = LocalDateTime.of(date, (LocalTime) endComboBox.getValue());
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getId();
        int userID = userComboBox.getSelectionModel().getSelectedItem().getUserID();
        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();

        Appointment newAppointment = new Appointment(-1,appointmentTitle,description,location,type,start,end,customerID,userID, contactID);
        Timestamp startTimestamp = Timestamp.valueOf(start);
        Timestamp endTimestamp = Timestamp.valueOf(end);
        boolean conflictExists = DBAppointments.checkAppointmentConflict(startTimestamp, endTimestamp, customerID, -1);

        if(!appointmentInputValidation(appointmentTitle) || !appointmentInputValidation(description)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid input for each field before saving. No fields can be left blank");
            alert.showAndWait();
            return;
        }
        if(end.isBefore(start)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("End time cannot be before start time. Please choose a new end time that is after the start time.");
            alert.showAndWait();
            return;
        }
        if(start.isEqual(end)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Start time cannot be equal to end time. Please choose a new end time that is after the start time.");
            alert.showAndWait();
            return;
        }

        if(conflictExists == true){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Conflict exists");
            alert.setContentText("These appointments overlap. Please choose a new time.");

            alert.showAndWait();
            return;
        }
        else{
            DBAppointments.addAppointment(newAppointment);
        }


        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000,600);
        stage.setScene(scene);
        stage.show();
    }
    /*validation of appointment input*/
    public static Boolean appointmentInputValidation(String string){
        if (string.isEmpty() || string.isBlank()) {
            return false;
        } else {
            return true;
        }
    }


/**Navigation to return to Appointment Screen*/
    public void ReturnToAppointmentScreenBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointmentScreen.fxml")));
        Stage window = (Stage) ReturnToAppointmentScreenId.getScene().getWindow();
        window.setScene(new Scene(root, 800,500));
    }
/**Navigation to return to Main Menu*/
    public void returnToMainScreenBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage window = (Stage) returnToMainScreenId.getScene().getWindow();
        window.setScene(new Scene(root, 812,363));
    }
}
