package models;

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
public class Attraction {//Start of Attraction Class
// Instance Variables
    private String name, status, imageurl;
    private int id,rating;

// Constructor
    public Attraction (String name, String status, String imageurl, int id, int rating){
        this.name=name;
        this.status=status;
        this.imageurl = imageurl;
        this.id=id;
        this.rating=rating;
    }

// Getters
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


// Setters
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

}//End of Attraction Class
