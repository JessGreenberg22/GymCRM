package Controller;

import dao.DBAppointments;
import dao.DBCountry;
import dao.DBCustomer;
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
import model.Country;
import model.Customer;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Customer Records Controller
 * @Author Jessica Greenberg Student ID 001462404
 */
public class CustomerRecords implements Initializable {
   /*TextField and ComboBox Ids*/
   public ComboBox countryComboBox;
    public ComboBox stateComboBox;
    public TextField customerIDFIeld;
    public TextField customerNameField;
    public TextField customerAddressField;
    public TextField postalCodeField;
    public TextField phoneNumberField;

    /**Customer Table*/
    public TableView customerTable;
    public TableColumn customerIdCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerCountryCol;
    public TableColumn customerStateCol;
    public TableColumn customerPostalCol;
    public TableColumn customerPhoneCol;

    /*Ids for buttons*/
    public Button returnButton;
    public Button deleteButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ObservableList<Country> countries = DBCountry.getAllCountries();
        countryComboBox.setItems(countries);

        /*set Values in the customer Table*/
        customerTable.setItems(DBCustomer.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerStateCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    /**handler for the state combo box*/
    public void onCountryComboBox() {
        Country selectedCountry = (Country) countryComboBox.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            ObservableList Division = DBCountry.getFirstLevelDivision(selectedCountry.getCountryID());
            stateComboBox.setItems(Division);
        }
    }

    public void onStateComboBox(ActionEvent actionEvent) {

    }

    /**Add a customer to the database*/
    public void addCustomer(ActionEvent actionEvent) throws IOException {String name = customerNameField.getText();
        String address = customerAddressField.getText();
        String division = stateComboBox.getSelectionModel().getSelectedItem().toString();
        int divisionID = DBCountry.getDivisionId(division);
        String postalCode = postalCodeField.getText();
        String phoneNumber = phoneNumberField.getText();

        if(!verifyInput(name) || !verifyInput(address) || !verifyInput(postalCode)|| !verifyInput(phoneNumber)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Enter Values for Each Field");
            alert.setContentText("Please enter values for each field");
            alert.showAndWait();
            return; }


        Customer newCustomer = new Customer(-1, name, address, divisionID, postalCode, phoneNumber);
        DBCustomer.addCustomer(newCustomer);

        JOptionPane.showMessageDialog(null, "You have Successfully added a Customer");
        customerTable.setItems(DBCustomer.getAllCustomers());
    }
    /*Validate input data*/
    public static Boolean verifyInput(String string) {
        return !string.isEmpty() && !string.isBlank();

    }
    public void updateCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/updateCustomers.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene (root, 800,600);
        UpdateCustomers updateCustomers = loader.getController();
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.WARNING.WARNING);
            alert.setTitle("Select a Customer");
            alert.setHeaderText(null);
            alert.setContentText("Select customer from the table to modify");
            alert.showAndWait();

            return;
        }
        else {
            updateCustomers.setCustomer((Customer) customerTable.getSelectionModel().getSelectedItem());
        }
        stage.setScene(scene);
        stage.show();
    }

    /**Delete a Customer from the Database*/
    public void deleteCustomer(ActionEvent actionEvent) {
        Customer selection = (Customer) customerTable.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setContentText("Select a Customer to Delete");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are You Sure?");
            alert.setContentText("Delete Customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                //* delete all customer appointments*/
                DBCustomer.deleteCustomer(selection.getId());
                DBAppointments.deleteAppointment(selection.getId());

                customerTable.setItems(DBCustomer.getAllCustomers());

            }
        }
    }

    /** Navigate to the MainScreen */
    public void returnToMainScreenAction() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.setScene(new Scene(root,812,363));
    }
}