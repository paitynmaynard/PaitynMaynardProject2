package servlets;

import com.google.gson.Gson;
import dao.AttractionDAO;
import models.Attraction;
import utils.ConnectionUtils;
import utils.PostgresConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Initialized by Jean Aldoph II
 * The Servlet for all API calls involving Attractions that can be seen by the Customer.
 * This Servlet only implements one Method, doGet
 * This is the case because our customers should have no updating or posting privileges to our DB
 *
 *version 0.0: Jean D Aldoph II
 */



public class AttractionsServlet extends HttpServlet
{
    /**
     * This Method is the only method implemented by Customer for attractions.
     * This method takes an httpRequest and calls to the DAO for our Attractions.
     * The customer Attraction DAO can only be used to view our Current Attractions, to alleviate confusion,
     * Or allow customers to view a Specific attraction.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        {
            //System.out.println("");
            AttractionDAO repo = new AttractionDAO(new PostgresConnectionUtil());
            if (req.getHeader("find").equals("all"))
            {
                String json = null;
                List<Attraction> all = repo.findAll();
                Map<String, List> options = new LinkedHashMap<>();
                options.put("attraction", all);
                //System.out.println(options);
                json = new Gson().toJson(options);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
            }
            else if (!(req.getHeader("find") == null))
            {
                try
                {
                    String json = null;
                    Attraction temp = repo.findById(new Integer(req.getHeader("find")));
                    Map<String, Attraction> options = new LinkedHashMap<>();
                    options.put("attraction", temp);
                    //System.out.println(options);
                    json = new Gson().toJson(options);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(json);
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

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

    /**
     * Not Implemented
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    /**
     * Not Implemented
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    /**
     * Not implemented
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
     * Not implemented
     */
    @Override
    public void destroy() {
        super.destroy();
    }
    /**
     * Not implemented
     */
    @Override
    public void init() throws ServletException {
        super.init();
    }


}
