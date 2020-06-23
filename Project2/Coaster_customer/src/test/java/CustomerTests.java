import dao.CustomerDAO;
import models.Customer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.PostgresConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

public class CustomerTests {
    // Instance Variables
    // Initialized anything needed for mocking, storage, etc.
    ArrayList<Customer> customers = new ArrayList();

    @Mock
    Connection mockConn;
    @Mock
    PreparedStatement mockPreparedStmnt;
    @Mock
    ResultSet mockResultSet;
    @Mock
    PostgresConnectionUtil connectionUtil;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() throws SQLException {
        // TODO Put initial content for jUnit tests, establish mocked dependencies and services
        Mockito.when(connectionUtil.getConnection()).thenReturn(mockConn);
        Mockito.when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        Mockito.when(mockConn.createStatement()).thenReturn(mockPreparedStmnt);
        Mockito.when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        Mockito.when(mockPreparedStmnt.executeQuery(anyString())).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
    }

    // The following section of Unit Tests meant to test Ticket creation and interaction

    @Test
    public void testCustomerCreationGetters() {

        Customer c = new Customer(123, "John", "Doe", "johndoe@emailprovider.com", "thisisabadpassword");
        String output = "" + c.getCustomerID() + " " + c.getFirstname() + " " + c.getLastname() + " " + c.getEmail() + " " + c.getPassword();
        assertEquals("123 John Doe johndoe@emailprovider.com thisisabadpassword", output);

    }

    @Test
    public void testCustomerCreationSetters() {

        Customer c = new Customer(1776, "Rob", "Swanson", "rswanson@emailprovider.com", "youhavemyattention");
        Customer d = new Customer(123, "John", "Doe", "johndoe@emailprovider.com", "thisisabadpassword");
        c.setCustomerID(d.getCustomerID());
        c.setFirstname(d.getFirstname());
        c.setLastname(d.getLastname());
        c.setEmail(d.getEmail());
        c.setPassword(d.getPassword());
        String output = "" + c.getCustomerID() + " " + c.getFirstname() + " " + c.getLastname() + " " + c.getEmail() + " " + c.getPassword();
        assertEquals("123 John Doe johndoe@emailprovider.com thisisabadpassword", output);

    }

    // The following Unit Tests are meant for checking database-involved functionalities through
    // CustomerDAO instance with mocked components to avoid using the existing postgresql db.

    @Test
    public void testFindAll() throws SQLException {

        CustomerDAO customerDAO = new CustomerDAO(connectionUtil);
        customerDAO.findAll();

        // Verify and Assert
        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeQuery();

        Mockito.verify(mockResultSet, Mockito.times(4)).getString(anyString());
        Mockito.verify(mockResultSet, Mockito.times(1)).getInt(anyString());
    }

    @Test
    public void testSave() throws SQLException {

        CustomerDAO customerDAO = new CustomerDAO(connectionUtil);
        Mockito.when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        Customer c = new Customer(1776, "Rob", "Swanson", "rswanson@emailprovider.com", "youhavemyattention");
        String result = customerDAO.save(c);

        // Verify and Assert
        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeUpdate();

        assertEquals("Success", result);
    }

    @Test
    public void testFindById() throws SQLException {

        CustomerDAO customerDAO = new CustomerDAO(connectionUtil);
        Customer result = customerDAO.findById("rswanson@emailprovider.com");

        // Verify and Assert
        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeQuery();

        Mockito.verify(mockResultSet, Mockito.times(4)).getString(anyString());
        Mockito.verify(mockResultSet, Mockito.times(1)).getInt(anyString());
    }

    @Test
    public void testUpdate() throws SQLException {

        CustomerDAO customerDAO = new CustomerDAO(connectionUtil);
        Mockito.when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        Customer c = new Customer(1776, "Rob", "Swanson", "rswanson@emailprovider.com", "ihavechangedmypassword");
        customerDAO.update(c,"rswanson@emailprovider.com");

        // Verify and Assert
        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeUpdate();
    }

}
