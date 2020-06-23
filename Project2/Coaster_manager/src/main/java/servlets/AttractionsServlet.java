package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import data.SQLDatabaseExtAttractions;
import data.SQLDatabaseIntAttraction;
import models.Attraction;
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
 *  Project 2:<br>
 * <br>
 *  IntAttractionServlet used for all API calls involving attractions for managers.
 *  This servlet implements a doPost, d doGet, and a doDelete method
 *
 *  <br> <br>
 *  Created: <br>
 *     May 13, 2020 Paityn Maynard<br>
 *     With assistance from: <br>
 *  Modifications: <br>
 *
 * <br>
 *  @author
 *  @version 13 May 2020
 *
 */

public class AttractionsServlet extends HttpServlet {
//Instance Variables
    JsonObject data;

//Methods

    /**
     * doGet method is used for the find methods of both the internal and external attractions to allow all or one attraction to be found
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//Start of doGet method
        String json = null;
        SQLDatabaseIntAttraction intAttract = new SQLDatabaseIntAttraction(new PostgresConnectionUtil());
        SQLDatabaseExtAttractions extAttract = new SQLDatabaseExtAttractions(new PostgresConnectionUtil());

        if(req.getHeader("find").equals("id")){//Start of first if statement
            Attraction attraction = null;
            Map<String,String> options = new LinkedHashMap<>();

            try{//Start of first try
                int id = req.getIntHeader("id");
                try {//Start of second try
                    attraction = intAttract.findByID(id);
                    if (attraction == null) {//Start of second if statement
                        attraction = extAttract.findByID(id);
                    }//End of second if statement
                }//End of second try
                catch (Exception e){//Start of second catch
                    e.printStackTrace();
                }//End of second catch

                    options.put("name",(String.valueOf(attraction.getName())));
                    options.put("id",(String.valueOf(attraction.getId())));
                    options.put("rate",(String.valueOf(attraction.getRating())));
                    options.put("url",(String.valueOf(attraction.getImageurl())));
                    options.put("status",(String.valueOf(attraction.getStatus())));
                json = new Gson().toJson(options);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);

            }//End of first try

            catch (Exception e){//Start of first catch
                e.printStackTrace();
            }//End of catch

        }//End of first if statement

        else if(req.getHeader("find").equals("all")){//Start of first else if statement
            List<Attraction> attractions = intAttract.findAll();
                attractions.addAll(extAttract.findAll());
            Map<String, ArrayList> options = new LinkedHashMap<>();
                options.put("attractions", (ArrayList) attractions);
            json = new Gson().toJson(options);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }//End of first else if statement

        else if(req.getHeader("find").equals("internal")){//Start of second else if statement
            List<Attraction> attractions = intAttract.findAll();
            Map<String, ArrayList> options = new LinkedHashMap<>();
            options.put("attractions", (ArrayList) attractions);
            json = new Gson().toJson(options);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }//End of second else if statement

        else if(req.getHeader("find").equals("external")){//Start of third else if statement
            List<Attraction> attractions = extAttract.findAll();
            Map<String, ArrayList> options = new LinkedHashMap<>();
            options.put("attractions", (ArrayList) attractions);
            json = new Gson().toJson(options);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }//End of third else if statement
    }//End of doGet method

    /**
     *doPost method is used to add an attraction to the internal database table and remove it from the external database table
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//Start of doPost method
        SQLDatabaseIntAttraction intAttract = new SQLDatabaseIntAttraction(new PostgresConnectionUtil());
        SQLDatabaseExtAttractions extAttract = new SQLDatabaseExtAttractions(new PostgresConnectionUtil());
        data = new Gson().fromJson(req.getReader(), JsonObject.class);
        if(!(data.get("add") ==null)) {//Start of first if statement
            String json = null;

            try {//Start of try statement
                int id = data.get("id").getAsInt();
                    Attraction attraction = extAttract.findByID(id);
                System.out.println(attraction);

                if (intAttract.add(attraction)){//Start of if statement
                    extAttract.remove(id);
                    Map<String,String> options = new LinkedHashMap<>();
                    options.put("name",(String.valueOf(attraction.getName())));
                    options.put("id",(String.valueOf(attraction.getId())));
                    options.put("rate",(String.valueOf(attraction.getRating())));
                    options.put("url",(String.valueOf(attraction.getImageurl())));
                    options.put("status",(String.valueOf(attraction.getStatus())));

                    json = new Gson().toJson(options);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(json);
                }//End of if statement
                else{//Start of else statement
                        resp.getWriter().write("Couldn't add item");
                    }//End of else statement
            }//End of try statement

            catch (Exception e){//Start of catch statement
                e.printStackTrace();
            }//End of catch statement

        }//End of first if statement

        else{//Start of first else statement

        }//End of first else statement
    }//End of doPost method

    /**
     * doDelete method is used to remove an attraction fro
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//Start of doDelete method
        data = new Gson().fromJson(req.getReader(), JsonObject.class);

        if(!(data.get("delete") ==null)){//Start of first if statement
            String json = null;
            try {//Start of try statement
                int id = data.get("id").getAsInt();

                SQLDatabaseIntAttraction intAttract = new SQLDatabaseIntAttraction(new PostgresConnectionUtil());
                SQLDatabaseExtAttractions extAttract = new SQLDatabaseExtAttractions(new PostgresConnectionUtil());
                    Attraction attraction = intAttract.findByID(id);
                        extAttract.add(attraction);
                        intAttract.remove(id);

                Map<String,String> options = new LinkedHashMap<>();
                options.put("name",(String.valueOf(attraction.getName())));
                options.put("id",(String.valueOf(attraction.getId())));
                options.put("rate",(String.valueOf(attraction.getRating())));
                options.put("url",(String.valueOf(attraction.getImageurl())));
                options.put("status",(String.valueOf(attraction.getStatus())));

                json = new Gson().toJson(options);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
            }//End of try statement

            catch (Exception e){//Start of catch statement
                e.printStackTrace();
            }//End of catch statement
        }//End of first if statement

        else {//Start of first else statement

        }//End of first else statement
    }//End of doDelete method
}
