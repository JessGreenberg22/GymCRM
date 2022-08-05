package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;

public class DBAppointments {
    public static ObservableList<Appointment> getAllAppointments()  {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {

            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("type");
                Timestamp appointmentStart = rs.getTimestamp("start");
                LocalDateTime start = appointmentStart.toLocalDateTime();
                Timestamp appointmentEnd = rs.getTimestamp("end");
                LocalDateTime end = appointmentEnd.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("contact_ID");
                Appointment a = new Appointment(appointmentId,title,description,location,type,start,end,customerId,userId,contactId);
                allAppointments.add(a);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allAppointments;
    }

    /**method to check for overlapping appointments*/
    public static boolean checkAppointmentOverlap(Timestamp apptStart, Timestamp apptEnd, int customerId, int appointmentId)  {

        boolean overlapExists = false;

        try {

            String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND Appointment_ID <> ? AND (? = start OR ? = end) or ( ? < start and ? > end) or (? > start AND ? < end) or (? > start AND ? < end)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, appointmentId);
            ps.setTimestamp(3, apptStart);
            ps.setTimestamp(4, apptEnd);
            ps.setTimestamp(5, apptStart);
            ps.setTimestamp(6, apptEnd);
            ps.setTimestamp(7, apptStart);
            ps.setTimestamp(8, apptStart);
            ps.setTimestamp(9, apptEnd);
            ps.setTimestamp(10, apptEnd);
            //System.out.println("overlap sql =" + ps.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                overlapExists = true;
            }
            else {
                overlapExists = false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return overlapExists;
    }

    /***method to insert new appointments into database*/
    public static void addAppointment (Appointment appointment) throws SQLException {

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID, create_date, created_by,last_update, last_updated_by) VALUES (?,?,?,?,?,?,?,?,?,?,now(),?,now())";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        try{

            ps.setString(1,appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(String.valueOf(appointment.getStart())));
            ps.setTimestamp(6, Timestamp.valueOf(String.valueOf(appointment.getEnd())));
            ps.setInt(7, appointment.getCustomerId());
            ps.setInt(8, appointment.getUserId());
            ps.setInt(9, appointment.getContactId());


            int status = ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /***method to modify appointment and update database*/
    public static void modifyAppointment(Appointment appointment) {

        try {

            String sql = "Update appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, create_date = now(), created_by = ?, last_update = now(), last_updated_by = ? Where Appointment_ID = ? ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1,appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(String.valueOf(appointment.getStart())));
            ps.setTimestamp(6, Timestamp.valueOf(String.valueOf(appointment.getEnd())));
            ps.setInt(7, appointment.getCustomerId());
            ps.setInt(8, appointment.getUserId());
            ps.setInt(9, appointment.getContactId());
            ps.setInt(12, appointment.getAppointmentId());

            int status = ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteAppointment(int appointmentId) {

        try {

            String sql = "DELETE FROM client_schedule.appointments where Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);

            int status = ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

