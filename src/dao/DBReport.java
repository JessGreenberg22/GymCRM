package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBReport {
    /*method to query total number of appointments by type and month*/
public static ObservableList getTotalNumberOfAppointments(){

    ObservableList<Report> reports = FXCollections.observableArrayList();

    try {

        String sql = "Select monthname(start) as month, type, count(*) as TotalNumberOfAppointments from appointments GROUP BY month, type ORDER BY month asc";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String month = rs.getString("month");
            String type = rs.getString("type");
            String totalNumberOfAppointments = rs.getString("TotalNumberOfAppointments");
            Report report = new Report(month, type, totalNumberOfAppointments);
            reports.add(report);
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }

    return reports;
}

    /**method to generate contact schedule report from database*/
    public static ObservableList<Report> getAllAppointmentsByConsultant(int contactId)  {

        ObservableList<Report> consultantSchedule = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID, Contact_ID FROM appointments WHERE Contact_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String appointmentType = rs.getString("type");
                String description = rs.getString("Description");
                Timestamp appointmentStart = rs.getTimestamp("start");
                LocalDateTime start = appointmentStart.toLocalDateTime();
                Timestamp appointmentEnd = rs.getTimestamp("end");
                LocalDateTime end = appointmentEnd.toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int contactID = rs.getInt("Contact_ID");

                Report report = new Report(appointmentID,title,appointmentType,description,start,end,customerID,contactID);
                consultantSchedule.add(report);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return consultantSchedule;
    }

    /**method to query customer ID and type */
    public static ObservableList getIDandType(){
        ObservableList<Report> IDandType = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Customer_ID,Type FROM appointments ORDER BY Customer_ID asc";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int customerID  = rs.getInt("Customer_ID");
                String appType = rs.getString("Type");

                Report m = new Report(customerID,appType);
                IDandType.add(m);
            }
        } catch (SQLException throwables)
        { throwables.printStackTrace();}


        return IDandType;
    }

}

