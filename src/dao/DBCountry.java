package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountry {
    /*
   method to get a list of all countries
    */
    public static ObservableList<Country> getAllCountries(){

        ObservableList<Country> clist = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * from countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");


                Country c = new Country(countryID, countryName);
                clist.add(c);
            }
        } catch (SQLException t) {
            t.printStackTrace();
        }

        return clist;

    }

    /** get divisions using countryID */
    public static ObservableList<String> getFirstLevelDivision(int countryID){

        ObservableList<String> clist = FXCollections.observableArrayList();

        try{

            String sql = "SELECT Division from first_level_divisions WHERE Country_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String divisionName = rs.getString("Division");
                clist.add(divisionName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;

    }

    /*method to get divisionID*/
    public static int getDivisionId(String division){

        int divisionId = -1;

        try{

            String sql = "SELECT Division_ID from first_level_divisions WHERE Division = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, division);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                divisionId = rs.getInt("Division_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionId;

    }

    /*** method to get country by passing division ID*/
    public static Country getCountryByDivisionID(int divisionID) {
        Country country = null;
        try {
            String sql = "SELECT c.country_ID, c.country FROM countries AS c INNER JOIN first_level_divisions AS d ON d.country_ID = c.country_ID AND d.division_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1,divisionID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("country_ID");
                String countryName = rs.getString("country");
                country = new Country(countryID, countryName);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return country;
    }

}