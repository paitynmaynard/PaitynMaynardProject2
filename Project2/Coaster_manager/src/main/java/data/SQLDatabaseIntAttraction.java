package data;

import models.Attraction;
import utils.ConnectionUtil;
import utils.PostgresConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Project 2:<br>
 * <br>
 *   SQLDatabaseIntAttraction is used to send commands to the PostgresQL Database table, attractions<br/>
 *  Implements findAll, add, findById, and remove methods from GenericDAO
 *
 *  <br> <br>
 *  Created: <br>
 *     May 11, 2020 Paityn Maynard<br>
 *     With assistance from: <br>
 *  Modifications: <br>
 *
 * <br>
 *  @author
 *  @version 11 May 2020
 */
public class SQLDatabaseIntAttraction implements GenericDAO<Attraction,Integer> {//Start of SQLDatabaseAttraction
//Instance Variables
    private static ConnectionUtil connectionUtil;


//Constructors
    public SQLDatabaseIntAttraction(ConnectionUtil connectionUtil){
        this.connectionUtil=connectionUtil;
    }

//Methods

    /**
     * Finds and returns all internal attractions in the database table attractions and pulling the status from
     * maintenance_tickets table
     *
     * @return results, which is a List<Attraction>, list of attractions
     */
    public List<Attraction> findAll() {//Start of findAll method
        List<Attraction> results = null;
        String schema = connectionUtil.getDefaultSchema();

        String sql = " select name,att.attractionid,imageurl,ratings, status " +
                "from project2.attractions as att " +
                "left outer join project2.maintenance_tickets as mt " +
                "on att.attractionid = mt.attractionid " +
                "where (((mt.isactive) or (mt.isactive is null)) or (mt.date_finished is null))" +
                "order by attractionid";

        try (Connection conn = connectionUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {//Start of try
            results = new ArrayList<>();

            while (rs.next()) {//Start of while loop
                String name = rs.getString("name");
                int id = rs.getInt("attractionid");
                String status = rs.getString("status");
                    if (status == null) {//Start of if statement
                        status = "Operational";
                    }//End of if statement
                String imageurl = rs.getString("imageurl");
                int rating = rs.getInt("ratings");

                results.add(new Attraction(name, status, imageurl, id, rating));
            }//End of while loop
        }//End of try
        catch (SQLException throwables) {//Start of catch
            throwables.printStackTrace();
        }//End of catch

        return results;

    }//End of findAll method

    /**
     *  Used to add an attraction to the internal attractions table (attractions)
     * @param attraction
     * @return true if the database returns rows added as one
     *         false if the datebase returns rows added as zero
     */
    public boolean add(Attraction attraction) {//Start of add method
        String schema = connectionUtil.getDefaultSchema();
        if (findByID(attraction.getId()) != null) {//Start of if statement
            return false;
        }//End of if statement
        int addedRowCount = 0;
        String sql = "INSERT INTO " + schema +
                ".attractions (attractionid, imageurl, name, ratings) values (?, ?, ?, ?)";

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

        return addedRowCount == 1;
}//End of add method

    /**
     * Used to find one specific in the internal attractions table (attractions) by its id
     * @param integer
     * @return result which is an Attraction object
     */
    public Attraction findByID(Integer integer) {//Start of findByID method
        String schema = connectionUtil.getDefaultSchema();
        Attraction result = new Attraction();

        String sql = " select name,att.attractionid,imageurl,ratings, status " +
                "from "+schema+".attractions as att " +
                "left outer join "+schema+".maintenance_tickets as mt " +
                "on att.attractionid = mt.attractionid " +
                "where (((mt.isactive) or (mt.isactive is null) or (mt.date_finished is null))  and (att.attractionid = ?))" +
                "order by attractionid";

        try (Connection conn = connectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {//Start of first try
            ps.setInt(1, integer);

            try (ResultSet rs = ps.executeQuery()) {//Start of second try
                if (rs.next()) {//Start of first if
                        result = new Attraction();
                        result.setName(rs.getString("name"));
                        result.setId(rs.getInt("attractionid"));
                        result.setImageurl(rs.getString("imageurl"));
                        result.setRating(rs.getInt("ratings"));
                        result.setStatus(rs.getString("status"));
                }//End of first if
                else
                {
                    return null;
                }
            }//End of second try
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }//End of first try
        catch (SQLException throwables) {//Start of catch
            throwables.printStackTrace();
        }//End of catch

        if(result.getStatus() == (null)){//Start of second if statement
            result.setStatus("Operational");
        }//End of second if statement

           try{//Start of third try
               result.getStatus();
           }//End of third try
           catch (Exception e){//Start of catch
               result.setStatus("Operational");
               e.printStackTrace();
               return null;
           }//End of catch

        System.out.println(result);
        return result;

    }//End of findByIDMethod

    /**
     * Used to remove an attraction from the internal attractions table (attractions) by the id of the attraction
     * @param id the ID of the object to remove
     * @return true if the database returns rows added as one
     *         false if the datebase returns rows added as zero
     */
    public boolean remove(Integer id) {//Start of remove method
        String schema = connectionUtil.getDefaultSchema();
        int deletedRowCount = -1;

        String sql = "DELETE FROM " + schema + ".attractions WHERE attractionid = ?";

        try (Connection conn = connectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {//Start of try
            ps.setInt(1, id);

            deletedRowCount = ps.executeUpdate();
        }//End of try
        catch (SQLException e) {//Start of catch
            e.printStackTrace();
        }//End of catch

        return deletedRowCount != -1;

}//End of remove method

    /**
     * UnImplemented
     * @param integer
     * @param newObj the new object that will replace the existing object in the database
     * @return false
     */
    public boolean update(Integer integer, Attraction newObj) {//Start of update method
        return false;
    }//End of update method

}//End of SQLDatabaseAttraction
