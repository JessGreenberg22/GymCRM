package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.User;

public class ListManager {

    /*List of all Appointments*/
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /*List of all Customers*/
    ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /*list of all Users*/
    public static ObservableList<User> allUsers = FXCollections.observableArrayList();
}
