package utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ConnectionUtils - This is an abstract class that allows connections to different types of SQL
 * database implementations.
 * 
 * @author Joshua Brewer
 */
public abstract class ConnectionUtils {
  protected String url;
  protected String username;
  protected String password;

  public abstract Connection getConnection() throws SQLException;

}
