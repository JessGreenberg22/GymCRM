package model;

import java.time.LocalDateTime;
/** Report Model
 * @Author Jessica Greenberg Student ID 001462404
 */
public class Report {

    private String month;
    private String totalNumberOfAppointments;
    private String type;
    private String appType;

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    private String title;
    private String appointmentType;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int contactID;
    private int appointmentID;

    /*constructor for total number of appointment types per month*/
    public Report(String month, String type, String totalNumberOfAppointments) {
        this.month = month;
        this.type = type;
        this.totalNumberOfAppointments = totalNumberOfAppointments;
    }

    /* constructor for contact schedule*/
    public Report(int appointmentID, String title, String appointmentType, String description, LocalDateTime start, LocalDateTime end, int customerID, int contactID) {

        this.appointmentID = appointmentID;
        this.title = title;
        this.appointmentType = appointmentType;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.contactID = contactID;
    }

    /*constructor for IDandType*/
    public Report( int customerID, String appType) {
        this.customerID = customerID;
        this.appType = appType;

    }
    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    private String monthName;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalNumberOfAppointments() {
        return totalNumberOfAppointments;
    }

    public void setTotalNumberOfAppointments(String totalNumberOfAppointments) {
        this.totalNumberOfAppointments = totalNumberOfAppointments;
    }

    /**Getters & Setters*/
    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String type) {
        this.appointmentType = type;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Report{" +
                "month='" + month + '\'' +
                ", totalNumberOfAppointments='" + totalNumberOfAppointments + '\'' +
                ", type='" + type + '\'' +
                ", appointmentID=" + appointmentID +
                ", title='" + title + '\'' +
                ", appointmentType='" + appointmentType + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", customerID=" + customerID +
                ", contactID=" + contactID +
                ", appType='" + appType + '\'' +
                ", monthName='" + monthName + '\'' +
                '}';
    }
}


