package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;

public class DBAppointments {

        /**method to add appointment data to tableview on AppointmentScreen*/
        public static ObservableList<Appointment> getAllAppointments()  {

            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

            try {

                String sql = "SELECT * from appointments";

                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int appointmentID = rs.getInt("Appointment_ID");
                    String appointmentTitle = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("type");
                    Timestamp appointmentStart = rs.getTimestamp("start");
                    LocalDateTime start = appointmentStart.toLocalDateTime();
                    Timestamp appointmentEnd = rs.getTimestamp("end");
                    LocalDateTime end = appointmentEnd.toLocalDateTime();
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("contact_ID");
                    Appointment a = new Appointment(appointmentID,appointmentTitle,description,location,type,start,end,customerID,userID,contactID);
                    appointmentList.add(a);

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return appointmentList;
        }

        /**
         * method to check for overlapping appointments
         * @param apptStart
         * @param apptEnd
         * @param customerId
         * @param appointmentId
         * @return
         */
        public static boolean checkAppointmentConflict(Timestamp apptStart, Timestamp apptEnd, int customerId, int appointmentId)  {

            boolean conflictExists = false;

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
                    conflictExists = true;
                }
                else {
                    conflictExists = false;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return conflictExists;
        }

        /**
         * method to insert new appointments into database
         * @param appointment
         * @throws SQLException
         */
        public static void addAppointment (Appointment appointment) throws SQLException {

            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID, create_date, created_by,last_update, last_updated_by) VALUES (?,?,?,?,?,?,?,?,?,?,now(),?,now())";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            try{

                ps.setString(1,appointment.getAppointmentTitle());
                ps.setString(2, appointment.getDescription());
                ps.setString(3, appointment.getLocation());
                ps.setString(4, appointment.getType());
                ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
                ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
                ps.setInt(7, appointment.getCustomerID());
                ps.setInt(8, appointment.getUserID());
                ps.setInt(9, appointment.getContactID());
                ps.setString(10, appointment.getCreatedBy());
                ps.setString(11, appointment.getLastUpdatedBy());

                int status = ps.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        /**
         * method to modify appointment and update database
         * @param appointment
         */
        public static void modifyAppointment(Appointment appointment) {

            try {

                String sql = "Update appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, create_date = now(), created_by = ?, last_update = now(), last_updated_by = ? Where Appointment_ID = ? ";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setString(1,appointment.getAppointmentTitle());
                ps.setString(2, appointment.getDescription());
                ps.setString(3, appointment.getLocation());
                ps.setString(4, appointment.getType());
                ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
                ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
                ps.setInt(7, appointment.getCustomerID());
                ps.setInt(8, appointment.getUserID());
                ps.setInt(9, appointment.getContactID());
                ps.setString(10, appointment.getCreatedBy());
                ps.setString(11, appointment.getLastUpdatedBy());
                ps.setInt(12, appointment.getAppointmentID());

                int status = ps.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        /** check for appointment within 15min of login*/

        public static Appointment get15minuteAppointment(String userName) {
            Appointment appointment = null;
            Date today = new Date();
            Timestamp now = new Timestamp(today.getTime());
            long plus15 = now.getTime() + (15 * (60 * 1000));
            Timestamp nowPlus15 = new Timestamp(plus15);
            User currentUser = DBUser.getUserId(userName);

            try {

                String sql = "SELECT * FROM appointments WHERE start BETWEEN ? AND ? AND user_ID = ?";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setTimestamp(1, now);
                ps.setTimestamp(2, nowPlus15);
                ps.setInt(3, currentUser.getUserID());

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int appointmentID = rs.getInt("Appointment_ID");
                    String appointmentTitle = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("type");
                    Timestamp appointmentStart = rs.getTimestamp("start");
                    LocalDateTime start = appointmentStart.toLocalDateTime();
                    Timestamp appointmentEnd = rs.getTimestamp("end");
                    LocalDateTime end = appointmentEnd.toLocalDateTime();
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("contact_ID");
                    appointment = new Appointment(appointmentID, appointmentTitle, description, location, type, start, end, customerID, userID, contactID);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return appointment;
        }

        /**method to delete selected appointment*/
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