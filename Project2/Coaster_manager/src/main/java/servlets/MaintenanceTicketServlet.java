package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.SQLDatabaseMaintenance_Ticket;
import dto.MaintenanceTicketTransfer;
import dto.MaintenanceTicketWrapper;
import models.Maintenance_Ticket;
import utils.PostgresConnectionUtil;
/**
 * Project 2
 *  MaintenanceTicketServlet
 *  Created:
 *     May 13, 2020 Rayan Vakil
 *
 * Modifications:
 *	Jean Aldoph: Changed SQLDatabaseMaintenance_ticket ticket = sqlDatabaseMaintenance_ticket.findByID(Integer);
 * 	            to Maintenance_Ticket ticket = sqlDatabaseMaintenance_ticket.findByID(new Integer(indexHeaderValue));
 *
 *  Paityn Maynard: Added find active to doGet -May 20
 *                  Added comment blocks and comment lines -May 20
 * @author
 * @version 05/20/2020
 */

public class MaintenanceTicketServlet extends HttpServlet {//Start of MaintenanceTicketServlet
//Instance Variables
	Maintenance_Ticket maintenance_ticket = new Maintenance_Ticket();
	SQLDatabaseMaintenance_Ticket sqlDatabaseMaintenance_ticket = new SQLDatabaseMaintenance_Ticket(new PostgresConnectionUtil());
	private static Logger LOG = Logger.getLogger(MaintenanceTicketServlet.class);

//Methods

    /**
     *
      * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//Start of doGet method
		String getType = req.getHeader("find");

		if (getType == null) {//Start of first if statement
			resp.setStatus(400);
		}//End of first if statement

		else if (getType.trim().equalsIgnoreCase("all")) {//Start of first else if statement
			ObjectMapper om = new ObjectMapper();
			ArrayList<Maintenance_Ticket> tickets = sqlDatabaseMaintenance_ticket.findAll();
			if (tickets != null) {//Start of second if statement
				MaintenanceTicketWrapper ticketList = new MaintenanceTicketWrapper();
				ticketList.setTickets(tickets);
				String ticketsResponse = om.writeValueAsString(tickets);
				resp.getWriter().write(ticketsResponse);
				resp.setStatus(200);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
			}//End of second if statement
			else {//Start of second else statement
				resp.setStatus(400);
			}//End of second else statement
		}//End of first else if statement

		else if (getType.trim().equalsIgnoreCase("id")) {//Start of second else if statement
            System.out.println("findByID");
            System.out.println(req.getHeader("id"));
			ObjectMapper om = new ObjectMapper();
			Integer id = Integer.parseInt(req.getHeader("id"));
			maintenance_ticket = sqlDatabaseMaintenance_ticket.findByID(id);
			if (maintenance_ticket != null) {//Start of third if statement
				String ticketsResponse = om.writeValueAsString(maintenance_ticket); 
				resp.getWriter().write(ticketsResponse);
				resp.setStatus(200);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
			}//End of third if statement
			else {//Start of third else statement
			    resp.getWriter().write("No Ticket found with that ID");
				resp.setStatus(400);
			}//End of third else statement
		}//End of second else if statement

		else if (getType.trim().equalsIgnoreCase("attraction")) {//Start of third else if statement
			ObjectMapper om = new ObjectMapper();
			Integer id = Integer.parseInt(req.getHeader("id"));
            ArrayList<Maintenance_Ticket> tickets = sqlDatabaseMaintenance_ticket.findByAttraction(id);
			if (maintenance_ticket != null) {//Start of fourth if statement
				String ticketsResponse = om.writeValueAsString(tickets);
				resp.getWriter().write(ticketsResponse);
				resp.setStatus(200);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
			}//End of fourth if statement
			else {//Start of fourth else statement
				resp.setStatus(400);
			}//End of fourth else statement
		} //End of third else if statement

		else if(getType.trim().equalsIgnoreCase("active")) {//Start of fourth else if statement
            ObjectMapper om = new ObjectMapper();
            ArrayList<Maintenance_Ticket> tickets = sqlDatabaseMaintenance_ticket.findActive();
            if (tickets != null) {//Start of fifth if statement
                MaintenanceTicketWrapper ticketList = new MaintenanceTicketWrapper();
                ticketList.setTickets(tickets);
                String ticketsResponse = om.writeValueAsString(tickets);
                resp.getWriter().write(ticketsResponse);
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
            }//End of fifth if statement
            else {//Start of fifth else statement
                resp.setStatus(400);
            }//End of fifth else statement
        }//End of fourth else if statement

		else {//Start of first else statement
			resp.setStatus(400);
		}//End of first else statement
	}//End doGet method

    /**
     * Not implemented
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
      @Override
     protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doHead(req, resp);
     }

        /*
         * Requried JSON for addition
		{
 			"attractionId":[Integer value],
  			"employeeId": [Integer value],
			"status":[String value],
			"description":[String value]
			"isActive":[boolean value]
			"
		}
         */

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
     @Override
     protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//Start of doPost method

        if(req.getContentType().equals("application/json")) {//Start of first if statement
            SQLDatabaseMaintenance_Ticket sqlDatabaseMaintenance_ticket = new SQLDatabaseMaintenance_Ticket(new PostgresConnectionUtil());
            ObjectMapper om = new ObjectMapper();
            MaintenanceTicketTransfer ticketData = om.readValue(req.getReader(), MaintenanceTicketTransfer.class);
            int attractionId = ticketData.getAttractionId();
            String status = ticketData.getStatus();
            String description = ticketData.getDescription();
            int employeeId = ticketData.getEmployeeId();
            boolean isActive = ticketData.getIsActive();
            Maintenance_Ticket newTicket = new Maintenance_Ticket(attractionId, employeeId, status, description, isActive);
                if(!sqlDatabaseMaintenance_ticket.add(newTicket)) {//Start of second if statement
                    resp.setStatus(400);
                }//End of second if statement
                else {//Start of second else statement
                    resp.setStatus(201);
                }//End of second else statement
        }//End of first if statement
        else {//Start of first else statement
            resp.setStatus(400);
        }//End of first else statement
     }//End of doPost method

       /*
        *  Required JSON for update
        * {
        *	 "mainId":[Integer value],
        *	 "status":[String value],
   	 	*	 "endDate":[String value, format MM-dd-yyyy HH:mm]
   	 	*    "isActive:[boolean value]
        * }
        */

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
     @Override
     protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{//Start of doPut method
        if(req.getContentType().equals("application/json")) {//Start of first if statement
            ObjectMapper om = new ObjectMapper();
            MaintenanceTicketTransfer ticketData = om.readValue(req.getReader(), MaintenanceTicketTransfer.class);
            LocalDateTime endDate = LocalDateTime.parse(ticketData.getEndDate(), DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
            Maintenance_Ticket updateTicket = sqlDatabaseMaintenance_ticket.findByID(ticketData.getMainId());
                if(updateTicket != null) {//Start of second if statement
                    updateTicket.setEndDate(endDate);
                    updateTicket.setStatus(ticketData.getStatus());
                    sqlDatabaseMaintenance_ticket.update(updateTicket.getMainId(), updateTicket);
                    resp.setStatus(204);
                }//End of second if statement
                else {//Start of second else statement
                    resp.setStatus(400);
                }//End of second else statement
        }//End of first if statement
        else {//Start of first else statement
            resp.setStatus(400);
        }//End of else statement
     }//End of doPut method

    /**
     * Unimplemented doDelete
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
     @Override
     protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
     }

    /**
     * Unimplemented destroy
     */
    @Override
     public void destroy() {
        super.destroy();
     }

    /**
     * Unimplemented init
     * @throws ServletException
     */
    @Override
     public void init() throws ServletException {
        super.init();
     }
}//End of MaintenanceTicketServlet
