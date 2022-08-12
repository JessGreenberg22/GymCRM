package model;

import dao.DBContact;

import java.sql.Timestamp;
import java.time.LocalDateTime;
/** Appointments Model
 * @Author Jessica Greenberg Student ID 001462404
 */

/**Appointment Object Class*/
public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    public Appointment() {
    }


    public Appointment(int appointmentID, String appointmentTitle, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {

        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;

    }

    /** getters*/

    public int getAppointmentID(){
        return appointmentID;
    }

    public String getAppointmentTitle(){
        return appointmentTitle;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation(){
        return location;
    }

    public String getType() {return type;}

    public String getContactName(){
        String contactName ="";
        Contact contact = DBContact.getContact(contactID);
        if(contact != null){
            contactName = contact.getContactName();
        }
        return contactName;
    }

    public LocalDateTime getStart(){
        return start;
    }

    public LocalDateTime getEnd(){
        return end;
    }

    public LocalDateTime getCreateDate(){
        return createDate;
    }

    public String getCreatedBy(){
        return createdBy;
    }

    public LocalDateTime getLastUpdate(){
        return lastUpdate;
    }

    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    public int getCustomerID(){
        return customerID;
    }

    public int getUserID(){
        return userID;
    }

    public int getContactID() { return contactID;}

    /**setters*/

    public void setAppointmentID(int appointmentID){
        this.appointmentID = appointmentID;
    }

    public void setAppointmentTitle(String appointmentTitle){
        this.appointmentTitle = appointmentTitle;
    }

    public void setDescription (String description){
        this.description = description;
    }

    public void setLocation (String location){
        this.location = location;
    }

    public void setType (String type) {this.type = type;}

    public void setStart (LocalDateTime start){
        this.start = start;
    }

    public void setEnd (LocalDateTime end){
        this.end = end;
    }

    public void setCreateDate(LocalDateTime createDate){
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    public void setLastUpdate(LocalDateTime lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setCustomerID (int customerID){
        this.customerID = customerID;
    }

    public void setUserID (int userID){
        this.userID = userID;
    }

    public void setContactID(int contactID) { this.contactID = contactID;}

}