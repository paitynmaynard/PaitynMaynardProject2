package dto;

import models.Maintenance_Ticket;

import java.util.ArrayList;
/**
 *  Project 2:<br>
 * <br>
 *
 *  <br> <br>
 *  Created: <br>
 *     <br>
 *     With assistance from: <br>
 *  Modifications:
 *      Paityn Maynard added comment block and comment lines<br>
 *      Paityn Maynard also reorganize getters and setter<br>
 * <br>
 *  @author
 *  @version
 */
public class MaintenanceTicketWrapper {//Start of MaintenanceTicketWrapper
//Instance Variables
    private ArrayList<Maintenance_Ticket> tickets;

//Constructor
    public MaintenanceTicketWrapper() {
        tickets = new ArrayList<>();
    }


    public void addTicket(Maintenance_Ticket maintenance_ticket) {
        tickets.add(maintenance_ticket);
    }

    public void setTickets(ArrayList<Maintenance_Ticket> tickets) {
        this.tickets = tickets;
    }

    public ArrayList<Maintenance_Ticket> getTickets(){
        return tickets;
    }

}//End of MaintenanceTicketWrapper
