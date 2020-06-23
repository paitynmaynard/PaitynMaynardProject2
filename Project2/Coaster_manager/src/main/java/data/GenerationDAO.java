package data;


import models.Attraction;
import models.Customer;
import models.Employee;
import utils.PostgresConnectionUtil;
import java.io.*;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

public class GenerationDAO {

    public GenerationDAO()
    {
    }


    public ArrayList makeAday(int i) throws IOException {
        if (i>95) i= 95;
        System.out.println("generating "+ i+ " days of business!");

        for (int j = 0; j < i; j++) {
            //        FileReader in = new FileReader(".\\resources\\ticketsSoldLAst.txt");
            //        BufferedReader br = new BufferedReader(in);
            //        Integer oldTickets = 0;
            //        //read from file how many tickets were made last time
            //        try
            //        {
            //            oldTickets = new Integer(br.readLine());
            //            System.out.println(oldTickets);
            //        }
            //        catch (Exception e)
            //        {
            //            e.printStackTrace();
            //        }
            //        finally
            //        {
            //            if (in != null) in.close();
            //        }
            ArrayList response = new ArrayList();
            Integer oldTickets = 1500;

            Random rand = new Random();
            Integer base, ticketsSold;
            Integer move;
            Integer ticketDiff;
            List<String> newEmails = new ArrayList();
            base = 0;
            List<Attraction> attractions = new ArrayList();
            Integer ticketsNow;
            Attraction temp;
            ticketsSold = 0;
            Integer ticketRating = 0;
            SQLDatabaseIntAttraction intAttractionDB = new SQLDatabaseIntAttraction(new PostgresConnectionUtil());
            SQLDatabaseExtAttractions extAttractionDB = new SQLDatabaseExtAttractions(new PostgresConnectionUtil());
            SQLDatabaseCustomerDAO customerDB = new SQLDatabaseCustomerDAO(new PostgresConnectionUtil());
            SQLDatabaseEmployees employeeDB = new SQLDatabaseEmployees(new PostgresConnectionUtil());
            Attraction attraction;
            Customer customer;
            Employee employee;
            List<Customer> list = customerDB.findAll();
            List<Employee> elist = employeeDB.findAll();
            Integer iterationBound = 10;
            while (base++ < iterationBound) {
                //System.out.println(base);
                move = rand.nextInt(10_000);
                if ((move <= 10_000) & (move > 6_333)) {
                    continue;
                } else if ((move <= 6_333) & (move > 3_333)) //Old Customer buys 1-9 tickets
                {
                    ticketsNow = rand.nextInt(10);
                    //select random customer from list
                    customer = list.get(rand.nextInt(list.size()));
                    //send message to customerTickets to make customer tickets.;
                    //Messaging goes here
                    makeTickets(customer, ticketsNow);

                    ticketsSold += ticketsNow;
                } else if (move <= 3_333) //New Customer come to park
                {
                    ticketsNow = rand.nextInt(10);
                    //Manager findbyID Customers to make sure new customer is not in DB
                    Customer temporaryCustomer = makeCustomer();
                    newEmails.add(temporaryCustomer.getEmail());
                    //send message to customerTickets to make customer tickets.;


                    //Messaging goes here
                    makeTickets(temporaryCustomer, ticketsNow);

                    ticketsSold += ticketsNow; //# of tickets for Tickets sent for customer

                }

            }


            ticketDiff = oldTickets - ticketsSold;
            if ((ticketDiff > 89) || (ticketDiff < -89)) ticketRating = 10;
            else if ((ticketDiff > 69) || (ticketDiff < -69)) ticketRating = 9;
            else if ((ticketDiff > 49) || (ticketDiff < -49)) ticketRating = 8;
            else if ((ticketDiff > 39) || (ticketDiff < -39)) ticketRating = 7;
            else if ((ticketDiff > 29) || (ticketDiff < -29)) ticketRating = 6;
            else if ((ticketDiff > 19) || (ticketDiff < -19)) ticketRating = 5;
            else ticketRating = 4;

            if (ticketDiff > 0) {
                int wait = 0;
                attractions = extAttractionDB.findAll();
                Iterator it = attractions.iterator();
                while (it.hasNext()) {
                    temp = (Attraction) it.next();
                    if (temp.getRating() == ticketRating) {
                        extAttractionDB.add(temp);
                        break;
                    }
                }
                int iters = 0;
                //Add # of employees == ticket rating
                while (iters < ticketRating) {
                    employeeDB.remove(elist.get(rand.nextInt()).getId());
                    iters++;
                }
            } else if (ticketDiff < 0) {
                int wait = 0;
                attractions = intAttractionDB.findAll();
                Iterator it = attractions.iterator();
                while (it.hasNext()) {
                    temp = (Attraction) it.next();
                    if (temp.getRating() == ticketRating) {
                        intAttractionDB.remove(temp.getId());
                        break;
                    }
                }
                //Remove # of employees = to ticketRating
                int iters = 0;
                while (iters < ticketRating) {
                    employeeDB.add(makeEmployee());
                    iters++;
                }
            }


//            FileWriter win = new FileWriter("resources\\ticketsSoldLAst.txt");
//
//            try
//            {
//                PrintWriter write = new PrintWriter(win);
//                write.println(ticketsSold);
//                write.close();
//                System.out.println(ticketDiff);
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//            finally
//            {
//                if (in != null) in.close();
//
//            }
//            //Return a Breakdown of what happened in the Generation for write-back to
//            //Servlet

        }
        return null;
        //Response: response.add(newEmails.size());
        //            return response;
        // [0]: # new Customers
        // [1]: # return customers,
        // [2]: new Attraction ID / Removed attraction ID
        // [3]: # of employees hired or fired
        // [4]: ticket Diff
    }

    public Customer makeCustomer()
    {
        String startUp = null;
        while (startUp == null)
        {
            startUp = indicativeFoul();
        }
        String[] values =  startUp.split("!");
        Customer newCustomer = new Customer(values[0],values[1],values[2],"password");
        SQLDatabaseCustomerDAO customerDB = new SQLDatabaseCustomerDAO(new PostgresConnectionUtil());
        customerDB.save(newCustomer);
        Customer returnValue = customerDB.findById(values[2]);
        List<Customer> cml = customerDB.findAll();
        if (returnValue != null)
        {
            return returnValue;
        }
        else
        {
            return cml.get(new Random().nextInt(cml.size()));
        }


    }

    public Employee makeEmployee()
    {
        String startUp = null;
        while (startUp == null)
        {
                startUp = indicativeFoul();
        }
        String[] values =  startUp.split("!");
        Employee newEmployee = new Employee(values[0],values[1],"9999999999",values[2],"password",12,false);
        SQLDatabaseEmployees employeeDB = new SQLDatabaseEmployees(new PostgresConnectionUtil());
        if (employeeDB.add(newEmployee))
        {
            Employee returnValue = employeeDB.findByID(newEmployee.getId());

            return returnValue;
        }
        else
        {
            List<Employee> eml = employeeDB.findAll();
            return eml.get(new Random().nextInt(eml.size()));
        }
    }

    public String indicativeFoul()
    {
        String url = "https://randomuser.me/api/";
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        String item = "";
        String item1 = "";
        String item2 = "";
        try {
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + method.getStatusLine());
            }
            byte[] responseBody = method.getResponseBody();
            String rezzy = new String(responseBody);
            String[] stack = rezzy.split("\".\"");
            int holder = 1;
            for (String i : stack)
            {
                if (i.trim().equals("first"))
                {
                    item = (stack[holder]).toString();
                }
                else if (i.trim().contains("email"))
                {
                    item1 =(stack[holder]).toString();
                }
                else if (i.trim().contains("last"))
                {
                    item2 = (stack[holder]).split("\"")[0];
                }
                //System.out.println(holder-1 +"   "+i);
                holder++;
            }
            if ((item.trim() == null) || (item2.trim() == null) || (item1.trim() ==null)) return null;

        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
        if ((item.trim() == "") || (item2.trim() == "") || (item1.trim() == "")) return null;
        return item.trim()+"!"+item2.trim()+"!"+item1.trim();
    }

//    public void sendMessage(Customer c, int i)
//    {
//        InetAddress ip;
//        try
//        {
//            ip = InetAddress.getLocalHost();
//            Jedis jedis = new Jedis("redis-clusterip", 6379);
//            jedis.publish("TicketGeneration",c.toString()+'!'+String.valueOf(i));
//        }
//        catch (UnknownHostException e)
//        {
//            e.printStackTrace();
//        }
//    }

    public  void makeTickets(Customer c, int number)
    {
        SQLDatabaseCustomerDAO og = new SQLDatabaseCustomerDAO(new PostgresConnectionUtil());
        //System.out.println("GOT A MESSAGE");
        Random rand = new Random();
        int randomMonth = rand.nextInt(12);
        if (randomMonth < 6) randomMonth = 6;
        int randomDay = rand.nextInt(30);
        int randomStay = rand.nextInt(21);
        LocalDateTime ldt = LocalDateTime.now();
        ldt.plusMonths(randomMonth);
        ldt.plusDays(randomDay);
//        int access = rand.nextInt(3);
//        if (access == 0) access += 1;
        for (int i = 0; i < number; i++)
        {
            save(new Ticket(c.getCustomerID(),1 , ldt, ldt.plusDays(randomStay)));
        }
        System.out.println(String.format("Ticket Generation Event:\n" +
                "Created: %d tickets \n" +
                "for customer: %s", number, c.getEmail()));

    }


    public void save(Ticket obj) {
        PostgresConnectionUtil connectionUtil = new PostgresConnectionUtil();
        Connection connection = null;
        int success = -1;

        String startdate = obj.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
        String enddate = obj.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));

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
        //System.out.println("saved ticket");
    }

//    public void sendMessage(Customer c, int i) {
//        String currenturl = "http://172.17.199.5:31515/TicketServlet";
//        HttpClient client = new HttpClient();
//        PostMethod method = new PostMethod(currenturl);
//        method.addParameter("number",String.valueOf(i));
//        method.addParameter("customerID",String.valueOf(c.getCustomerID()));
//        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(2, false));
//        try {
//                int statusCode = client.executeMethod(method);
//                if (statusCode != HttpStatus.SC_OK)
//                {
//                    System.err.println("Method failed: " + method.getStatusLine());
//                }
//                byte[] responseBody = method.getResponseBody();
//            System.out.println(responseBody);
//
//        }
//        catch (HttpException e)
//        {
//            System.err.println("Fatal protocol violation: " + e.getMessage());
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            System.err.println("Fatal transport error: " + e.getMessage());
//            e.printStackTrace();
//        }
//        finally
//        {
//            method.releaseConnection();
//        }
//    }
}
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
class Ticket {//Start of Ticket Class
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
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}