package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import data.GenerationDAO;
import data.SQLDatabaseEmployees;
import models.Employee;
import utils.PostgresConnectionUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Project 2
 *
 * This servlet is used for random generation of the Parks structure.
 * It has Custom DAO and is only used to call the messaging and other functions
 * for the generation of:
 * Customers, Tickets, Attraction relocation, Maintenance tickets, and
 * Employees
 *
 *
 *
 * @author Jean Aldoph
 * @version 05/13/2020
 */
public class Generation_servlet extends HttpServlet {
    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenerationDAO genDao = new GenerationDAO();
        String json = null;
        genDao.makeAday(req.getIntHeader("days"));
    }


}