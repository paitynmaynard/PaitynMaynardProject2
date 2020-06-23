/**
 * Initialized by Jean Aldoph II, This testing set will be
 * used to MOCK generate employees for the Management Service of the
 * theme park. These tests will only determine the functionality
 * of the tests, they DO NOT need to be called during service.
 */

//

import data.GenerationDAO;
import data.SQLDatabaseCustomerDAO;
import models.Customer;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.junit.Test;
import java.time.LocalDateTime;

import java.io.*;


import org.apache.commons.httpclient.params.HttpMethodParams;
import utils.PostgresConnectionUtil;

public class GenerationTests {
//
//    ScriptEngineManager manager = new ScriptEngineManager();
//    ScriptEngine engine = manager.getEngineByName("JavaScript");
//    // read script file
//    engine.eval(Files.newBufferedReader(Paths.get("/EmployeeGeneration.js"), StandardCharsets.UTF_8)));
//
//    Invocable inv = (Invocable) engine;
// //call function from script file
// inv.invokeFunction("yourFunction", "param");

    @Test
    public void testGen()
    {

        GenerationDAO genDao = new GenerationDAO();
        try
        {
            genDao.makeAday(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void testCall()
    {
        // Fake example transaction ID: 3YC00XQKNVMZ
        String url = "https://randomuser.me/api/";

        // Create an instance of HttpClient.
        HttpClient client = new HttpClient();

        // Create a method instance.
        GetMethod method = new GetMethod(url);

        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));

        try
        {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK)
            {
                System.err.println("Method failed: " + method.getStatusLine());
            }


            // Read the response body.
            byte[] responseBody = method.getResponseBody();
            String rezzy = new String(responseBody);
            String[] stack = rezzy.split("\".\"");
            int holder = 1;
            String item = "";
            String item1 = "";
            String item2 = "";
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
            if (item2 == "") item2 ="Smith";
            if (item1 == "") item1 = "John";
            System.out.println("first Name: " + item);
            System.out.println("Last Name:  "+item2);
            System.out.println("email:  "+item1);


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
    }

    @Test
    public void makeCustomers()
    {
        int i = 0;
        while (i++ < 1) new GenerationDAO().makeCustomer();
    }

    @Test
    public void makeEmployees()
    {
        int i = 0;
        while (i++ < 1) new GenerationDAO().makeEmployee();
    }

    @Test
    public void ldtPare()
    {
        LocalDateTime ldt;
    }

    @Test
    public void indciativeFoulTest()
    {
        new GenerationDAO().indicativeFoul();
    }

    @Test    public  void makeTicketsTest()
    {
        Customer c = new SQLDatabaseCustomerDAO(new PostgresConnectionUtil()).findById("supermario@nintendo.com");
        new GenerationDAO().makeTickets(c,1);
    }

}






