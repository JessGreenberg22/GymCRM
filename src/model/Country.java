package model;

import java.time.LocalDateTime;

/** Country Model
 * @Author Jessica Greenberg Student ID 001462404
 */


public class Country {
    private int countryID;
    private String countryName;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public Country(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**Getters*/
    public int getCountryID(){
        return countryID;
    }

    public String getCountryName(){
        return countryName;
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

    /**Setters*/
    public void setCountryID(int countryID){
        this.countryID = countryID;
    }

    public void setCountryName(String countryName){
        this.countryName = countryName;
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

    @Override
    public String toString() {
        return countryName;
    }

}
