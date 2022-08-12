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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;
/** Update Appointment Controller
 * @Author Jessica Greenberg Student ID 001462404
 */
public class UpdateAppointment implements Initializable {
    /**Button IDs*/
    public Button ReturnToAppointmentScreenId;
    public Button returnToMainScreenId;
    public Button updateAptId;

    /**Form Fields and combobox Id's*/
    public TextField apptId;
    public TextField title;
    public TextField desc;
    public TextField type;
    public ComboBox startComboBox;
    public ComboBox endComboBox;
    public ComboBox custIdBox;
    public ComboBox userIdBox;
    public ComboBox contactComboBox;
    public ComboBox locationComboBox;
    public ComboBox typeComboBox;
    public DatePicker startDate;
    public DatePicker endDate;
    /**method to pull appointment info from appt page' table*/
    private Appointment selectedAppointment;
    /**set types variable*/
    private ObservableList<String> types = FXCollections.observableArrayList("Planning Session", "De-Briefing", "Follow-Up", "Termination");

    /**Navigation to Appointment Screen*/
    public void ReturnToAppointmentScreenBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointmentScreen.fxml")));
        Stage window = (Stage) ReturnToAppointmentScreenId.getScene().getWindow();
        window.setScene(new Scene(root, 800, 500));
    }

    /*Navigation to MainScreen*/
    public void returnToMainScreenBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage window = (Stage) returnToMainScreenId.getScene().getWindow();
        window.setScene(new Scene(root, 812,363));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**Sets comboBoxs to carried Values*/
        ObservableList<Contact> contacts = DBContact.getAllContacts();
        contactComboBox.setItems(contacts);

        javafx.collections.ObservableList<User> userList = DBUser.getAllUsers();
        userIdBox.setItems(userList);

        ObservableList<Country> countries = DBCountry.getAllCountries();
        locationComboBox.setItems(countries);

        ObservableList<Customer> customers = DBCustomer.getAllCustomers();
        custIdBox.setItems(customers);

        startComboBox.setItems(localTime.getStartTimeList());

        endComboBox.setItems(localTime.getEndTimeList());

        typeComboBox.setItems(types);
    }

    /**sets the selected appointment into text fields from AppointmentScreen*/
    public void setAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
        apptId.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        title.setText(selectedAppointment.getAppointmentTitle());
        desc.setText(selectedAppointment.getDescription());
        locationComboBox.setValue(selectedAppointment.getLocation());
        typeComboBox.setValue(selectedAppointment.getType());
        startDate.setValue(selectedAppointment.getStart().toLocalDate());
        endDate.setValue(selectedAppointment.getEnd().toLocalDate());
        startComboBox.setValue(LocalTime.from(selectedAppointment.getStart()));
        endComboBox.setValue(LocalTime.from(selectedAppointment.getEnd()));
        custIdBox.setValue(selectedAppointment.getCustomerID());
        userIdBox.setValue(selectedAppointment.getUserID());
        contactComboBox.setValue(selectedAppointment.getContactID());

    }

    /**Save Updates to Appointments*/
    public void updateAptBtn(ActionEvent actionEvent) throws IOException {
        int appointmentID = Integer.parseInt(apptId.getText());
        String appointmentTitle = title.getText();
        String description = desc.getText();
        String location = locationComboBox.getSelectionModel().getSelectedItem().toString();
        String type = typeComboBox.getSelectionModel().getSelectedItem().toString();
        LocalDate date = startDate.getValue();
        LocalDateTime start = LocalDateTime.of(date, (LocalTime) startComboBox.getValue());
        date = endDate.getValue();
        LocalDateTime end = LocalDateTime.of(date, (LocalTime) endComboBox.getValue());
        int customerID = (int) custIdBox.getSelectionModel().getSelectedItem();
        int userID = (int) userIdBox.getSelectionModel().getSelectedItem();
        int contactID = (int) contactComboBox.getSelectionModel().getSelectedItem();

        Appointment newAppointment = new Appointment(appointmentID, appointmentTitle, description, location, type, start, end, customerID, userID, contactID);
        Timestamp startTimestamp = Timestamp.valueOf(start);
        Timestamp endTimestamp = Timestamp.valueOf(end);
        boolean conflictExists = DBAppointments.checkAppointmentConflict(startTimestamp, endTimestamp, customerID, appointmentID);
        if (!appointmentInputValidation(appointmentTitle) || !appointmentInputValidation(description)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Enter Valid Values");
            alert.setContentText("Please enter a valid input for each field before saving. Textfields cannot be blank");
            alert.showAndWait();
            return;
        }
        if (end.isBefore(start)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Scheduling time discrepancy");
            alert.setContentText("select a different end time for this appointment.");
            alert.showAndWait();
            return;
        }
        if (start.isEqual(end)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Scheduling time discrepancy");
            alert.setContentText("Select a different end time for this appointment.");
            alert.showAndWait();
            return;
        }
        if (conflictExists == true) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Conflict");
            alert.setHeaderText("Overlapping appointments");
            alert.setContentText("Select a different time.");

            alert.showAndWait();
            return;
        }
        DBAppointments.modifyAppointment(newAppointment);

        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**Validate update to Appointments*/
    public static Boolean appointmentInputValidation(String string) {
        if (string.isEmpty() || string.isBlank()) {
            return false;
        } else {
            return true;
        }
    }
}

