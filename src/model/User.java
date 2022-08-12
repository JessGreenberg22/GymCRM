package model;

import java.time.LocalDateTime;

/** user Model
 * @Author Jessica Greenberg Student ID 001462404
 */
public class User {

    private int userID;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**constructor*/
    public User(int userID, String userName,String password){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    public User(int userID) {
    }

    /**Getters*/
    public int getUserID(){
        return userID;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
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

    /** Setters*/
    public void setUserID (String userID){
        this.userName = userID;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword (String password){
        this.password = password;
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
    public String toString(){

        return(Integer.toString(userID));
    }
}