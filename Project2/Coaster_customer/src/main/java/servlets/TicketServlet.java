package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.GenerationMessages;
import dao.TicketDAO;
import dto.TicketTransfer;
import dto.TicketWrapper;
import models.Ticket;
import utils.PostgresConnectionUtil;

/**
 * Servlet implementation class TicketServlet
 * 
 * @author Joshua Brewer
 */
public class TicketServlet extends HttpServlet {
	private PostgresConnectionUtil connection = new PostgresConnectionUtil();
	private TicketDAO ticketDao = new TicketDAO(connection);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String indexHeaderValue = req.getHeader("searchIndex");
		if(indexHeaderValue == null) {
			resp.setStatus(400);
		} else {
			ObjectMapper om = new ObjectMapper();
			int searchIndex = Integer.parseInt(indexHeaderValue);
			if(searchIndex <= 0) {
				ArrayList<Ticket> tickets = ticketDao.findAll();
				if(tickets != null) {
					TicketWrapper ticketList = new TicketWrapper();
					ticketList.setTickets(tickets);
					String ticketsResponse = om.writeValueAsString(ticketList);
					resp.getWriter().write(ticketsResponse);
					resp.setStatus(200);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
				} else {
					resp.setStatus(400);
				}
			} else {
				Ticket ticket = ticketDao.findById(searchIndex);
				if(ticket != null) {
					String ticketResponse = om.writeValueAsString(ticket);
					resp.getWriter().write(ticketResponse);
					resp.setStatus(200);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
				} else {
					resp.setStatus(400);
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getHeader("special")!=null)
		{
			JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
			String json = null;
			GenerationMessages.events(data.get("customerID").getAsString(),
					data.get("number").getAsInt());
			resp.setStatus(200);
		}
		else if(req.getContentType().equals("application/json")) {
			ObjectMapper om = new ObjectMapper();
			TicketTransfer ticketData = om.readValue(req.getReader(), TicketTransfer.class);
			LocalDateTime startDate = LocalDateTime.parse(ticketData.getStartDate(), DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
			LocalDateTime endDate = LocalDateTime.parse(ticketData.getEndDate(), DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
			Ticket newTicket = new Ticket(ticketData.getCustomerID(), ticketData.getAccessLevel(), startDate, endDate);
			if(ticketDao.save(newTicket) <= 0) {
				resp.setStatus(400);
			} else {
				resp.setStatus(201);
			}
		} else {
			resp.setStatus(400);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getContentType().equals("application/json")) {
			ObjectMapper om = new ObjectMapper();
			TicketTransfer ticketData = om.readValue(req.getReader(), TicketTransfer.class);
			LocalDateTime startDate = LocalDateTime.parse(ticketData.getStartDate(), DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
			LocalDateTime endDate = LocalDateTime.parse(ticketData.getEndDate(), DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
			Ticket updateTicket = new Ticket(ticketData.getTicketID(), ticketData.getCustomerID(), ticketData.getAccessLevel(), startDate, endDate);
			if(ticketDao.findById(updateTicket.getTicketID()) != null) {
				ticketDao.update(updateTicket, updateTicket.getTicketID());
				resp.setStatus(204);
			} else {
				resp.setStatus(400);
			}
		} else {
			resp.setStatus(400);
		}
	}
}
