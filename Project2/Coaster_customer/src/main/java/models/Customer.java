package models;

/**
 *  Project 2:<br>
 * <br>
 *  The Customer class serves as a representation of a real-world customer interacting with the system.
 *  	Customer instances hold information of its real-world counterpart as variables.
 *
 *  <br> <br>
 *  Created: <br>
 *     11 May 2020, Barthelemy Martinon<br>
 *     With assistance from: <br>
 *  Modifications: <br>
 *     11 May 2020, Barthelemy Martinon,    Created class.
 * <br>
 *  @author Barthelemy Martinon   With assistance from:
 *  @version 11 May 2020
 */
public class Customer {//Start of Customer Class
// Instance Variables
    private int customerID;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

// Constructor
    public Customer(int customerID, String firstname, String lastname, String email, String password) {
        this.customerID = customerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

// Getters

    public int getCustomerID() {
        return customerID;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    // Setter Methods
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//Methods
}//End of Customer Class
