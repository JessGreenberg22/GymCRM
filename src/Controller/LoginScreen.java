package Controller;

import dao.DBAppointments;
import dao.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
/** Login Screen Controller
 * @Author Jessica Greenberg Student ID 001462404
 */
public class LoginScreen implements Initializable {


    public Label welcomeLabel;
    public Label enterUserIdLabel;
    public Label userLocationLabel;
    public TextField userIdLabel;
    public TextField passwordLabel;
    public Button LogInButtonId;

    /**retrieves users zoneID and sets User location label on Login Screen
 * Based on users Zone Id the language will be set to users locale (either French of English)**/
public void setLocation(){
    ZoneId currentLocation = ZoneId.systemDefault();
    userLocationLabel.setText("User Location: " + currentLocation);
    Locale French = new Locale("fr","FR");
    ResourceBundle lf = ResourceBundle.getBundle("login_fr",Locale.getDefault());

    if(Locale.getDefault().getLanguage().equals("fr"))
    {
        Locale.setDefault(French);
        welcomeLabel.setText((lf.getString("Welcome!")).replaceAll("",""));
        enterUserIdLabel.setText((lf.getString("enter")).replaceAll("",""));
        userIdLabel.setPromptText(((lf.getString("UserId")).replaceAll("","")));
        passwordLabel.setPromptText((lf.getString("Password")).replaceAll("",""));
        LogInButtonId.setText(lf.getString("Login"));

        int indexOfSeperation = (currentLocation.toString()).indexOf("/");
        String countryToPrint = (currentLocation.toString()).substring(0,indexOfSeperation);
        String countryToPrintFR;

        if (countryToPrint.equals("Pacific")||countryToPrint.equals("United States of America") || countryToPrint.equals("Europe"))
        {
            countryToPrintFR = (lf.getString(countryToPrint));
            int indexOfEnd =(currentLocation.toString().length());
            String locationToPrint = (currentLocation.toString().substring(indexOfSeperation,indexOfEnd));
            userLocationLabel.setText((lf.getString("Users location")).replaceAll(",","") + ": " + countryToPrintFR + locationToPrint);
        }
        else
        {
            userLocationLabel.setText(lf.getString("location").replaceAll("","") + ": " + currentLocation);
    }
        logInErrorMessage = lf.getString("Incorrect user Id or Password").replaceAll("","");
        logInErrorTitle = lf.getString("FailedLoginAttempt").replaceAll("","");

    }
}

String logInErrorMessage ="Incorrect user Id or Password";
String logInErrorTitle = "Log-In Failed";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLocation();
    }


/** Login With credentials and Navigate to the MainScreen */
//credentials for UserName and Password test,test or admin,admin
        public void onLogInBtn(ActionEvent actionEvent) throws IOException, SQLException {
        String username = userIdLabel.getText();
        String password =  passwordLabel.getText();
        String dateTime = ZonedDateTime.now(ZoneId.of("UTC")).toString();

        boolean loginValidation = DBUser.validate(username,password);

        if(!loginValidation){
            String loggerAttempt = dateTime + " ," + username + "failed to log in";
            utility.Logger.createLogger(loggerAttempt);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            String alertTitle = null;
            alert.setTitle(alertTitle);
            String alertContent = null;
            alert.setContentText(alertContent);
            alert.showAndWait();
        }
        else{
            String loggerAttempt = dateTime + " user: '" + username + "'logged in";
            utility.Logger.createLogger(loggerAttempt);
            Appointment appointment = DBAppointments.get15minuteAppointment(username);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            if(appointment != null) {
                alert.setContentText("Appointment is scheduled within 15 minutes" + "\n"
                        + "Appointment ID: " + appointment.getAppointmentID() + "\n" + "Date & Time: " + appointment.getStart());
            }
            else {
                alert.setContentText("There are no upcoming appointments");

            }
            Optional<ButtonType> result = alert.showAndWait();
            Locale english = new Locale("en","EN");
            Locale.setDefault(english);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
            Stage window = (Stage) LogInButtonId.getScene().getWindow();
            window.setScene(new Scene(root, 812,363));
        }
    }
}

