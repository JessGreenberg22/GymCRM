package Controller;

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
import javafx.stage.Stage;
import model.Country;
import model.Customer;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateCustomers implements Initializable {
    public TextField customerIDFIeld;
    public TextField customerNameField;
    public TextField customerAddressField;
    public TextField postalCodeField;
    public TextField phoneNumberField;
    public ComboBox<String> stateComboBox;
    public ComboBox countryComboBox;
    public Button returnButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> countries = DBCountry.getAllCountries();
        countryComboBox.setItems(countries);
    }

/*set Customer information in textfields from custRecords Table*/
    public void setCustomer(Customer selectedCustomer) {
        customerIDFIeld.setText(String.valueOf(selectedCustomer.getId()));
        customerNameField.setText(selectedCustomer.getName());
        customerAddressField.setText(selectedCustomer.getAddress());
        countryComboBox.setValue(selectedCustomer.getCountry());
        stateComboBox.setValue(selectedCustomer.getDivision());
        postalCodeField.setText(selectedCustomer.getPostalCode());
        phoneNumberField.setText(selectedCustomer.getPhoneNumber());

    }
    public void onStateComboBox(ActionEvent actionEvent) {
    }

    public void onCountryComboBox(ActionEvent actionEvent) {
        Country selectedCountry = (Country) countryComboBox.getSelectionModel().getSelectedItem();
        if (selectedCountry != null)
        {
            ObservableList Division = DBCountry.getFirstLevelDivision(selectedCountry.getCountryID());
            stateComboBox.setItems(Division);
        }
    }
/*update Customer Information to Database*/
    public void updateCustomer(ActionEvent actionEvent) throws IOException
    {
        int customerID = Integer.parseInt(customerIDFIeld.getText());
        String name = customerNameField.getText();
        String address =customerAddressField.getText();
        String country = countryComboBox.getSelectionModel().getSelectedItem().toString();
        String division = stateComboBox.getSelectionModel().getSelectedItem().toString();
        int divisionID = DBCountry.getDivisionId(division);
        String postalCode = postalCodeField.getText();
        String phoneNumber = phoneNumberField.getText();

        if(!customerInputValidation(name) || !customerInputValidation(address) || !customerInputValidation(country) || !customerInputValidation(division)|| !customerInputValidation(postalCode)|| !customerInputValidation(phoneNumber)){
            JOptionPane.showMessageDialog(null, " Please ensure all fields contain an entry");
            return;
        }

        Customer updateCustomer = new Customer(customerID, name, address, divisionID, postalCode, phoneNumber);
        DBCustomer.modifyCustomer(updateCustomer);

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerRecords.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }

/*validate new data input*/
public static Boolean customerInputValidation(String string){
    if (string.isEmpty() || string.isBlank()) {
        return false;
    } else {
        return true;
    }
}

    public void Return(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Customer Update Cancelation");
        alert.setContentText("changes entered will not be saved, continue?");

        //lambda expression to handle alert message and navigation back to customer screen
        alert.showAndWait().ifPresent((response -> {
            if (response == ButtonType.OK) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/view/customerRecords.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 800, 600);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));
    }

    /*Navigation to Main Screen*/
    public void returnToMainScreenAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.setScene(new Scene(root,812,363));
    }
}

