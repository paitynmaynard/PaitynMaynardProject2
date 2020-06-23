package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import models.Ticket;
import utils.ConnectionUtils;
import utils.PostgresConnectionUtil;

/**
 *  Project 2:<br>
 * <br>
 *  The TicketDAO class serves to collect, manipulate and persist data brought in from a AWS RDB hosted
 *    Postgresql database used as the main method of data storage for Project 2's coaster system.
 *  This will only work with Tickets and integers as a primary key.
 *
 *  <br> <br>
 *  Created: <br>
 *     11 May 2020, Barthelemy Martinon<br>
 *     With assistance from:<br>
 *  Modifications: <br>
 *     11 May 2020, Barthelemy Martinon,    Created class.
 * <br>
 *     12 May 2020, Barthelemy Martinon,    Commented out delete.
 * <br>
 *  @author Barthelemy Martinon   With assistance from:
 *  @version 12 May 2020
 */

public class TicketDAO implements DAO<Ticket, Integer> {
    // Instance Variables
    private ConnectionUtils connectionUtil;

    // Constructor
    public TicketDAO(PostgresConnectionUtil connectionUtil) {
        if(connectionUtil != null) {
            this.connectionUtil = connectionUtil;
        }
    }

    // Getter Methods

    public ConnectionUtils getConnectionUtil() { return connectionUtil; }

    // Setter Methods

    public void setConnectionUtil(ConnectionUtils connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    // Methods

    /**
     * Takes the database content, runs a hard-coded SELECT SQL query with WHERE clause to search for an entry with the
     *   specified integer for idnum. Returns null is nothing is found.
     *
     * 	@return t Ticket with target idnum (or null)
     */
    public Ticket findById(Integer integer) {
        Connection connection = null;
        Ticket t = null;
        Integer targetIdNum = integer;

        try {
            connection = connectionUtil.getConnection();
            String sql = "Select * from project2.tickets where ticketid=?";
            PreparedStatement findByIDStatement = connection.prepareStatement(sql);
            findByIDStatement.setInt(1,integer);
            ResultSet rs = findByIDStatement.executeQuery();

            while(rs.next()) {
                int ticketid = rs.getInt("ticketid");
                int customerid = rs.getInt("customerid");
                int accsslvl = rs.getInt("accsslevel");
                LocalDateTime startdate =  LocalDateTime.parse(rs.getString("startdate"), DateTimeFormatter.ISO_DATE_TIME);
                LocalDateTime enddate =  LocalDateTime.parse(rs.getString("enddate"), DateTimeFormatter.ISO_DATE_TIME);

                t = new Ticket(ticketid,customerid,accsslvl,startdate,enddate);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return t;
    }

    /**
     * Takes the database content, runs a hard-coded SELECT SQL query to obtain all entries.
     * All "translated" entries are put into an ArrayList of Tickets, which is returned.
     *
     * 	@return ticketList Ticket ArrayList of all database rows.
     */
    public ArrayList<Ticket> findAll() {
        Connection connection = null;
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

        try {
            connection = connectionUtil.getConnection();
            String sql = "Select * from project2.tickets order by ticketid";
            PreparedStatement findAllStatement = connection.prepareStatement(sql);
            ResultSet rs = findAllStatement.executeQuery();

            while(rs.next()) {
                Ticket temp = null;

                int ticketid = rs.getInt("ticketid");
                int customerid = rs.getInt("customerid");
                int accsslvl = rs.getInt("accsslevel");
                LocalDateTime startdate =  LocalDateTime.parse(rs.getString("startdate"), DateTimeFormatter.ISO_DATE_TIME);
                LocalDateTime enddate =  LocalDateTime.parse(rs.getString("enddate"), DateTimeFormatter.ISO_DATE_TIME);

                temp = new Ticket(ticketid,customerid,accsslvl,startdate,enddate);
                ticketList.add(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return ticketList;
    }

    /**
     * Takes a newly created Ticket and runs a hard-coded INSERT SQL statement to add the Ticket into the database
     *   as a table entry.
     * Information found within the Ticket given as input is collected and converted into the necessary values needed
     *   to complete the INSERT statement.
     *
     *  @param obj New Ticket created to be added
     *
     * 	@return statement.executeUpdate(sql) Integer value representing the amount of rows affected by the statement.
     */
    public Integer save(Ticket obj) {
        Connection connection = null;
        int success = -1;

        // Extract all information from Ticket instance to be stored as values for the new table entry
        //String accsslvl = "'" + obj.getAccessLevel() + "'";
        /*String startdate = "'" + obj.getStartDate() + "'";
        String enddate = "'" + obj.getEndDate() + "'";*/
        String startdate = obj.getStartDate().format(DateTimeFormatter.ISO_DATE_TIME);
        String enddate = obj.getEndDate().format(DateTimeFormatter.ISO_DATE_TIME);

        try {
            connection = connectionUtil.getConnection();
            String sql = "Insert into project2.tickets (customerid, accsslevel, startdate, enddate) values (?,?,?,?)";
            PreparedStatement saveStatement = connection.prepareStatement(sql);
            saveStatement.setInt(1, obj.getCustomerID());
            saveStatement.setInt(2, obj.getAccessLevel());
            saveStatement.setString(3, startdate);
            saveStatement.setString(4, enddate);
            success = saveStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return success;
    }

    /**
     * Takes a Ticket with updated information and an Integer that represents a target Ticket's ID number and search
     *    through the tickets table for a match. If one is found, the new Ticket information overrides the target's
     *   information. (Mainly ticket information) If none are found, nothing happens.
     *
     *  @param newObj Ticket to be updated
     *  @param integer Integer value representing target Ticket ID
     */
    public void update(Ticket newObj, Integer integer) {
        Connection connection = null;

        int targetTicketID = integer;

        /*String customerid = "'" + newObj.getCustomerID() + "'";
        String accsslvl = "'" + newObj.getAccessLevel() + "'";
        String startdate = "'" + newObj.getStartDate() + "'";
        String enddate = "'" + newObj.getEndDate() + "'";*/
        String startdate = newObj.getStartDate().format(DateTimeFormatter.ISO_DATE_TIME);
        String enddate = newObj.getEndDate().format(DateTimeFormatter.ISO_DATE_TIME);

        try {
            connection = connectionUtil.getConnection();
            String sql = "Update project2.tickets set " +
                    "customerid=?, accsslevel=?, startdate=?, enddate=? where ticketid=?";
            PreparedStatement updateStatement = connection.prepareStatement(sql);
            updateStatement.setInt(1, newObj.getCustomerID());
            updateStatement.setInt(2, newObj.getAccessLevel());
            updateStatement.setString(3, startdate);
            updateStatement.setString(4, enddate);
            updateStatement.setInt(5, integer);
            updateStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * Takes a Ticket and runs a hard-coded DELETE SQL statement to remove the entry from the database.
     *
     *  @param obj Ticket to be removed
     */
    public void delete(Ticket obj) {
        // Do nothing for now.
    }
}
