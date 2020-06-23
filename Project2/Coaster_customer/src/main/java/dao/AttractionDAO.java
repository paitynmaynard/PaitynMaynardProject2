package dao;

import models.Attraction;
import utils.ConnectionUtils;
import utils.PostgresConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Project 2:<br>
 * <br>
 *  AttractionDAO
 *
 *  <br> <br>
 *  Created: <br>
 *     May 11, 2020 Paityn Maynard<br>
 *     With assistance from: Barthelemy Martinon<br>
 *  Modifications: <br>
 * <br>
 *     12 May 2020, Barthelemy Martinon,    Ported Coaster_manager's SQLDatabaseAttraction class to Coaster_customer
 *                                              as AttractionDAO. Modifications made according to new destination
 *                                              structure and DAO interface.
 * <br>
 *     19 May 2020, Barthelemy Martinon,    Re-ported code in accordance with changes made to equivalent class in
 *                                               Coaster_manager.
 * <br>
 *  @author Paityn Maynard With assistance from: Barthelemy Martinon
 *  @version 19 May 2020
 */
public class AttractionDAO implements DAO<Attraction,Integer> {//Start of AttractionDAO
    //Instance Variables
    private static ConnectionUtils connectionUtil;

    //Constructors
    public AttractionDAO(ConnectionUtils connectionUtil){
        this.connectionUtil = connectionUtil;
    }

    //Methods
    /**
     * Finds and returns all internal attractions in the database table attractions and pulling the status from
     * maintenance_tickets table
     *
     * @return results, which is an ArrayList<Attraction>, list of attractions
     */
    public ArrayList<Attraction> findAll() {//Start of findAll method
        ArrayList<Attraction> results = null;

        String sql = " select name,att.attractionid,imageurl,ratings, status " +
                "from project2.attractions as att " +
                "left outer join project2.maintenance_tickets as mt " +
                "on att.attractionid = mt.attractionid " +
                "where ((mt.isactive) or (mt.isactive is null)) or (mt.date_finished is not null)";

        try (Connection conn = connectionUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {//Start of try
            results = new ArrayList<>();

            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("attractionid");
                String status = rs.getString("status");
                if (status == null) {
                    status = "Operational";
                }
                String imageurl = rs.getString("imageurl");
                int rating = rs.getInt("ratings");

                results.add(new Attraction(name, status, imageurl, id, rating));
            }
        }//End of try
        catch (SQLException throwables) {//Start of catch
            throwables.printStackTrace();
        }//End of catch

        return results;

    }//End of findAll method


    /**
     *  Used to add an attraction to the internal attractions table (attractions)
     * @param attraction
     * @return addedRowCount if the database successfully updated, returns rows added as one
     *                       otherwise if the database was not able to update, returns rows added as zero
     *                       if no valid Attraction is given, return -1
     */
    public Integer save(Attraction attraction) {//Start of add method
        if (findById(attraction.getId()) == null) {//Start of if statement
            return -1;
        }//End of if statement
        int addedRowCount = 0;
        String sql = "INSERT INTO project2.attractions (attractionid, imageurl, name, ratings) values (?, ?, ?, ?)";

        try (Connection conn = connectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {//Start of try
            ps.setInt(1, attraction.getId());
            ps.setString(2, attraction.getImageurl());
            ps.setString(3, attraction.getName());
            ps.setInt(4, attraction.getRating());

            addedRowCount = ps.executeUpdate();
        } //End of try
        catch (SQLException throwables) {//Start of catch
            throwables.printStackTrace();
        }//End of catch

        return addedRowCount;
    }//End of save method


    /**
     * Used to find one specific in the internal attractions table (attractions) by its id
     * @param integer
     * @return result which is an Attraction object
     */
    public Attraction findById(Integer integer) {//Start of findByID method
        Attraction result = null;

        String sql = " select name,att.attractionid,imageurl,ratings, status " +
                "from project2.attractions as att " +
                "left outer join project2.maintenance_tickets as mt " +
                "on att.attractionid = mt.attractionid " +
                "where (((mt.isactive) or (mt.isactive is null) or (mt.date_finished is not null))  and (att.attractionid = ?))";


        try (Connection conn = connectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {//Start of first try
            ps.setInt(1, integer);

            try (ResultSet rs = ps.executeQuery()) {//Start of second try
                if (rs.next()) {//Start of first if
                    String name = rs.getString("name");
                    int id = rs.getInt("attractionid");
                    String status = rs.getString("status");
                    String imageurl = rs.getString("imageurl");
                    int rating = rs.getInt("ratings");
                    result = new Attraction(name,status,imageurl,id,rating);
                }//End of first if
            }//End of second try
        }//End of first try
        catch (SQLException throwables) {//Start of catch
            throwables.printStackTrace();
        }//End of catch

        if(result.getStatus()==null){
            result.setStatus("Operational");
        }

        try{//Start of third try
            result.getStatus();
        }//End of third try
        catch (Exception e){//Start of catch
            e.printStackTrace();
            return null;
        }//End of catch

        return result;

    }//End of findById Method


    /**
     * Used to remove an attraction from the internal attractions table (attractions) by the id of the attraction
     * @param obj the object to remove
     * @return true if the database returns rows added as one
     *         false if the datebase returns rows added as zero
     */
    public void delete(Attraction obj) {//Start of remove method
        int idNum = obj.getId();

        String sql = "DELETE FROM project2.attractions WHERE attractionid=" + idNum;

        try (Connection conn = connectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//End of delete method

    /**
     * UnImplemented
     * @param integer
     * @param newObj the new object that will replace the existing object in the database
     */
    public void update(Attraction newObj, Integer integer) {//Start of update method
        // Do nothing for now.
    }//End of update method

}//End of SQLDatabaseAttraction
