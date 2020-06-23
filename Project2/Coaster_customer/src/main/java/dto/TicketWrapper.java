package dto;

import java.util.ArrayList;

import models.Ticket;

public class TicketWrapper {
	private ArrayList<Ticket> tickets;
	
	public TicketWrapper() {
		tickets = new ArrayList<>();
	}
	
	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
	}
	
	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public ArrayList<Ticket> getTickets(){
		return tickets;
	}
}
