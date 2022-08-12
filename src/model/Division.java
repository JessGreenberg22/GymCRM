package model;

import java.time.LocalDateTime;
/** Division Model
 * @Author Jessica Greenberg Student ID 001462404
 */
public class Division {

    private int divisionID;
    private String divisionName;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryID;



    public Division (int divisionID, String divisionName){
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    /**Getters*/

    public int getDivisionID(){
        return divisionID;
    }

    public String getDivisionName(){
        return divisionName;
    }

    public   LocalDateTime getCreateDate(){
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


    public int getCountryID(){
        return countryID;
    }

    /**setters*/
    public void setDivisionID(int divisionID){
        this.divisionID = divisionID;
    }

    public void setDivisionName(String divisionName){
        this.divisionName = divisionName;
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

    public void setCountryID(int countryID){
        this.countryID = countryID;
    }

    @Override
    public String toString() {
        return divisionName;
    }

}


