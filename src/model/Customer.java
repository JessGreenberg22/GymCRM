package model;
/** customer Model
 * @Author Jessica Greenberg Student ID 001462404
 */

import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**customer Object class*/
public class Customer{
    private int customerID;
    private String name;
    private String address;
    private int divisionID;
    private String country;
    private String division;
    private String postalCode;
    private String phoneNumber;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**constructor for a new Customer*/

    public Customer (int customerID, String name, String address, int divisionID, String postalCode, String phoneNumber) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.divisionID = divisionID;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }
   /* public Customer (int customerID, String name, String address,String country, int divisionID, String postalCode, String phoneNumber) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.divisionID = divisionID;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }*/

    /**Getters*/

    public int getId(){
        return customerID;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getCountry(){ return country; }

    public int getDivisionID(){
        return divisionID;
    }

    public String getDivision(){   return division; }

    public String getPostalCode(){
        return postalCode;
    }

    public String getPhoneNumber(){
        return phoneNumber;
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



    /**setters*/
    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setDivisionID(int divisionID){
        this.divisionID = divisionID;
    }

    public void setPostalCode (String postalCode){
        this.postalCode = postalCode;
    }

    public void setPhoneNumber ( String phoneNumber){
        this.phoneNumber = phoneNumber;
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

    public void setCountry ( String country){
        this.country = country;
    }

    public void setDivision ( String division){
        this.division = division;
    }

    @Override
    public String toString() {

        return (Integer.toString(customerID));
    }
}