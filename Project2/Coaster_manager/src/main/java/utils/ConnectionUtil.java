package utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Project 2:<br>
 * <br>
 *  ConnectionUtil
 *
 *  <br> <br>
 *  Created: <br>
 *     May 11, 2020 Ray Vakil<br>
 *     With assistance from: <br>
 *  Modifications: <br>
 *  Paityn Maynard added Comment Block and comment lines-May 20
 * <br>
 *  @author
 *  @version 11 May 2020
 */
public abstract class ConnectionUtil {//Start of ConnectionUtil Class
//Instance Variables
    protected static String url;
    protected static String username;
    protected static String password;
    protected static String defaultSchema;

//Constructors
    public abstract Connection getConnection() throws SQLException;

//Getters
    public String getDefaultSchema() {
        return defaultSchema;
    }

}//End of ConnectionUtil Class
