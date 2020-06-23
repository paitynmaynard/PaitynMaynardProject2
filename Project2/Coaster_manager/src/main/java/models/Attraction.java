package models;

/**
 *  Project 2:<br>
 * <br>
 *  Attraction is used to model an Attraction object
 *
 *  <br> <br>
 *  Created: <br>
 *     May 11, 2020 Paityn Maynard<br>
 *     With assistance from: <br>
 *  Modifications: <br>
 *
 *      <hr>
 *   Added toString for JSON RESPONSES
 *      Jean Aldoph II
 *      <hr>
 *
 * <br>
 *  @author
 *  @version 11 May 2020
 *
 */
public class Attraction {//Start of Attractions class
//Instance Variables
    String name, status, imageurl;
    int id, rating;

//Constructors
    public Attraction()
    {
        this.status=null;
    }

    /**
     * Used to create a new Attraction object
     * @param name
     * @param status
     * @param imageurl
     * @param id
     * @param rating
     */
    public Attraction(String name, String status, String imageurl, int id, int rating){
        this.name=name;
        this.status=status;
        this.imageurl = imageurl;
        this.id=id;
        this.rating=rating;
    }
//Getters
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getImageurl() {
        return imageurl;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

//Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "{name=" +name+
                ",status=" +status+
                ",imageurl=" +imageurl+
                ",id=" +id+
                ",rating="+rating+'}';
    }
}//End of Attractions Class
