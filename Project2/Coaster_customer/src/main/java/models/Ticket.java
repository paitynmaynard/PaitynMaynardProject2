package models;

import java.time.LocalDateTime;

/**
 *  Project 2:<br>
 * <br>
 *  The Ticket class serves as a representation of a real-world ticket used for interacting with the system.
 *  	Ticket instances hold information of its real-world counterpart as variables.
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
public class Ticket {//Start of Ticket Class
// Instance Variables
    private int ticketID;
    private int customerID;
    private int accessLevel;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

// Constructors
    public Ticket(int ticketID, int customerID, int accessLevel, LocalDateTime startDate, LocalDateTime endDate) {
        this.ticketID = ticketID;
        this.customerID = customerID;
        this.accessLevel = accessLevel;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public Ticket(int customerID, int accessLevel, LocalDateTime startDate, LocalDateTime endDate) {
    	this.customerID = customerID;
    	this.accessLevel = accessLevel;
    	this.startDate = startDate;
    	this.endDate = endDate;
    }

// Getters
    public int getTicketID() {
        return ticketID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

// Setters
    public void setTicketID(int ticketID)
    {
        this.ticketID = ticketID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel; }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

// Methods
}//End of Ticket Class
