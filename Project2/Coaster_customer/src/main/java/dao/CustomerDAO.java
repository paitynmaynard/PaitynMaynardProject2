package dao;

import models.Customer;
import utils.ConnectionUtils;
import utils.PostgresConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  Project 2:<br>
 * <br>
 *  The CustomerDAO class serves to collect, manipulate and persist data brought in from a AWS RDB hosted
 *    Postgresql database used as the main method of data storage for Project 2's coaster system.
 *  This will only work with Customers and strings as a query key.
 *
 *  <br> <br>
 *  Created: <br>
 *     11 May 2020, Barthelemy Martinon<br>
 *     With assistance from:<br>
 *  Modifications: <br>
 *     11 May 2020, Barthelemy Martinon,    Created class.
 * <br>
 *     12 May 2020, Barthelemy Martinon,    Modified code to work around Strings for email instead of Integers for
 *                                              customerID.
 *                                          Commented out delete.
 * <br>
 *  @author Barthelemy Martinon   With assistance from:
 *  @version 12 May 2020
 */

public class CustomerDAO implements DAO<Customer, String> {
    // Instance Variables
    private ConnectionUtils connectionUtil;

    // Constructor
    public CustomerDAO(PostgresConnectionUtil connectionUtil) {
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
     * 	@return c Customer with target idnum (or null)
     */
    public Customer findById(String inputEmail) {
        Connection connection = null;
        Customer c = null;
        String targetEmail = inputEmail;

        try {
            connection = connectionUtil.getConnection();
            String sql = "Select * from project2.customers where email=?";
            PreparedStatement findByEmailStatement = connection.prepareStatement(sql);
            findByEmailStatement.setString(1, inputEmail);
            ResultSet rs = findByEmailStatement.executeQuery();

            while(rs.next()) {
                int customerid = rs.getInt("customerid");
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String password = rs.getString("pword");

                c = new Customer(customerid,firstname,lastname,email,password);
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
        return c;
    }

    /**
     * Takes the database content, runs a hard-coded SELECT SQL query to obtain all entries.
     * All "translated" entries are put into an ArrayList of Customers, which is returned.
     *
     * 	@return customerList Customer ArrayList of all database rows.
     */
    public ArrayList<Customer> findAll() {
        Connection connection = null;
        ArrayList<Customer> customerList = new ArrayList<Customer>();

        try {
            connection = connectionUtil.getConnection();
            String sql = "Select * from project2.customers order by customerid";
            PreparedStatement findAllStatement = connection.prepareStatement(sql);
            ResultSet rs = findAllStatement.executeQuery();

            while(rs.next()) {
                Customer temp = null;

                int customerid = rs.getInt("customerid");
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String password = rs.getString("pword");

                temp = new Customer(customerid,firstname,lastname,email,password);
                customerList.add(temp);
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
        return customerList;
    }

    /**
     * Takes a newly created Customer and runs a hard-coded INSERT SQL statement to add the Customer into the database
     *   as a table entry.
     * Information found within the Customer given as input is collected and converted into the necessary values needed
     *   to complete the INSERT statement.
     *
     *  @param obj New Customer created to be added
     *
     * 	@return outputstatus String value acting as a means to determine if the new Customer was successfully added
     */
    public String save(Customer obj) {
        Connection connection = null;
        String outputstatus = "Failed";

        // Extract all information from Customer instance to be stored as values for the new table entry
        String email = "'" + obj.getEmail() + "'";
        String lastname = "'" + obj.getLastname() + "'";
        String firstname = "'" + obj.getFirstname() + "'";
        String password = "'" + obj.getPassword() + "'";

        try {
            connection = connectionUtil.getConnection();
            String sql = "Insert into project2.customers (email, lastname, firstname, pword) values " +
                    "(" + email + ", " + lastname + ", " + firstname + ", " + password + ")";
            PreparedStatement saveStatement = connection.prepareStatement(sql);
            saveStatement.executeUpdate();
            outputstatus = "Success";
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
        return outputstatus;
    }

    /**
     * Takes a Customer with updated information and an Integer that represents a target Customer's ID number and search
     *    through the customers table for a match. If one is found, the new Customer information overrides the target's
     *   information. (Mainly ticket information) If none are found, nothing happens.
     *
     *  @param newObj Customer to be updated
     *  @param inputEmail String value representing target Customer email
     */
    public void update(Customer newObj, String inputEmail) {
        Connection connection = null;

        String targetEmail = "'" + inputEmail + "'";

        String lastname = "'" + newObj.getLastname() + "'";
        String firstname = "'" + newObj.getFirstname() + "'";
        String password = "'" + newObj.getPassword() + "'";

        try {
            connection = connectionUtil.getConnection();
            String sql = "Update project2.customers set " +
                    "lastname=" + lastname + "," +
                    "firstname=" + firstname + "," +
                    "pword=" + password + " where email=" + targetEmail;
            PreparedStatement updateStatement = connection.prepareStatement(sql);
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
     * Takes a Customer and runs a hard-coded DELETE SQL statement to remove the entry from the database.
     *
     *  @param obj Customer to be removed
     */
    public void delete(Customer obj) {
        // Do nothing for now.
    }
}
