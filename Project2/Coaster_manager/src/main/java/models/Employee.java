package models;

/**
 *  Project 2:<br>
 * <br>
 *  Employee is used to model an Employee Model
 *
 *  <br> <br>
 *  Created: <br>
 *     May 11, 2020 Paityn Maynard<br>
 *     With assistance from: <br>
 *  Modifications: <br>
 *       <hr>
 *     Added toString for JSON RESPONSES
 *        Jean Aldoph II
 *        <hr>
 *     <hr>
 *         Added Constructors 2,3,4
 *     <hr>
 * <br>
 *  @author
 *  @version 11 May 2020
 */
public class Employee {//Start of Employees Class
//Instance Variables
    String fname, lname, phoneNum, email, pword;
    int id, bossid;
    float payRate;
    boolean yearlyRate,isAdmin;

//Constructors
    public Employee(){}

    /**
     * Used to create a new Employee object
     * @param fname
     * @param lname
     * @param phoneNum
     * @param email
     * @param pword
     * @param id
     * @param bossid
     * @param isAdmin
     */
    public Employee(String fname, String lname, String phoneNum, String email,  int id, String pword, int bossid, boolean isAdmin ){//This is a constructors for Employees Constructor 1
        this.fname=fname;
        this.lname=lname;
        this.phoneNum=phoneNum;
        this.email=email;
        this.pword=pword;
        this.id=id;
        this.bossid=bossid;
        this.isAdmin=isAdmin;
    }

    /**
     * @param fname
     * @param lname
     * @param phoneNum
     * @param email
     * @param id
     * @param pword
     * @param isAdmin
     */
    public Employee(String fname, String lname, String phoneNum, String email, int id, String pword, boolean isAdmin){//This is a constructors for Admin Constructor 2
        this.fname=fname;
        this.lname=lname;
        this.phoneNum=phoneNum;
        this.email=email;
        this.pword=pword;
        this.id=id;
        this.isAdmin=isAdmin;
    }

    /**
      * @param fname
     * @param lname
     * @param phoneNum
     * @param email
     * @param pword
     * @param bossid
     * @param isAdmin
     */
    public Employee(String fname, String lname, String phoneNum, String email, String pword, int bossid, boolean isAdmin){//This is a constructors for Employees Constructor 3
        this.fname=fname;
        this.lname=lname;
        this.phoneNum=phoneNum;
        this.email=email;
        this.pword=pword;
        this.bossid=bossid;
        this.isAdmin=isAdmin;
    }

    /**
     * @param fname
     * @param lname
     * @param phoneNum
     * @param email
     * @param pword
     * @param isAdmin
     */
    public Employee(String fname, String lname, String phoneNum, String email, String pword, boolean isAdmin ){//This is a constructors for Employees Constructor 4
        this.fname=fname;
        this.lname=lname;
        this.phoneNum=phoneNum;
        this.email=email;
        this.pword=pword;
        this.isAdmin=isAdmin;
    }
//Getters
    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public int getBossid() {
        return bossid;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

//Setters
    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBossid(int bossid) {
        this.bossid = bossid;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPword() { return pword; }

    public void setPword(String pword) {this.pword = pword;}

    @Override
    public String toString() {
        return "{"+
                "fname='"+fname+
                ",lname='"+ lname+
                ",phoneNum='"+ phoneNum+
                ",email='" +email+
                ",id=" +id+
                ",bossid=" +bossid+
                ",isAdmin=" + isAdmin+
                '}';
    }
}//End of Employees Class
