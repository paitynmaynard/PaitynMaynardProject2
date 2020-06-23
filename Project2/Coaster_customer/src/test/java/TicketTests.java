import dao.TicketDAO;
import models.Ticket;
import org.junit.Assert;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class TicketTests {
    // Instance Variables
    // Initialized anything needed for mocking, storage, etc.
    ArrayList<Ticket> tickets = new ArrayList();

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
    public void testTicketCreation1() {

        Ticket t = new Ticket(123, 5, 1, LocalDateTime.now(), LocalDateTime.now());
        String output = "" + t.getTicketID() + " " + t.getCustomerID() + " " + t.getAccessLevel() + " " + t.getStartDate() + " " + t.getEndDate();
        LocalDateTime start = t.getStartDate();
        LocalDateTime end = t.getEndDate();
        assertEquals("123 5 1 "+ start + " " + end, output);

    }

    @Test
    public void testTicketCreation2() {

        Ticket t = new Ticket(5, 1, LocalDateTime.now(), LocalDateTime.now());
        String output = "" + t.getCustomerID() + " " + t.getAccessLevel() + " " + t.getStartDate() + " " + t.getEndDate();
        LocalDateTime start = t.getStartDate();
        LocalDateTime end = t.getEndDate();
        assertEquals("5 1 "+ start + " " + end, output);

    }

    @Test
    public void testTicketCreationSetters() {

        Ticket t1 = new Ticket(4, 2, LocalDateTime.now(), LocalDateTime.now());
        Ticket t2 = new Ticket(5, 1, LocalDateTime.now(), LocalDateTime.now());
        t1.setCustomerID(t2.getCustomerID());
        t1.setAccessLevel(t2.getAccessLevel());
        t1.setStartDate(t2.getStartDate());
        t1.setEndDate(t2.getEndDate());
        String output = "" + t1.getCustomerID() + " " + t1.getAccessLevel() + " " + t1.getStartDate() + " " + t1.getEndDate();
        LocalDateTime start = t1.getStartDate();
        LocalDateTime end = t1.getEndDate();
        assertEquals("5 1 "+ start + " " + end, output);

    }

    // The following Unit Tests are meant for checking database-involved functionalities through
    // TicketDAO instance with mocked components to avoid using the existing postgresql db.

    // TODO Find a means to verify for LocalDateTime instances
//    @Test
//    public void testFindAll() throws SQLException {
//
//        TicketDAO ticketDAO = new TicketDAO(connectionUtil);
//        ticketDAO.findAll();
//
//        // Verify and Assert
//        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
//        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeQuery();
//
//        Mockito.verify(mockResultSet, Mockito.times(2)).getInt(anyString());
//        Mockito.verify(mockResultSet, Mockito.times(2)).getString(anyString());
//    }

    @Test
    public void testSave() throws SQLException {

        TicketDAO ticketDAO = new TicketDAO(connectionUtil);
        Mockito.when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        Ticket t = new Ticket(123, 5, 1, LocalDateTime.now(), LocalDateTime.now());
        int result = ticketDAO.save(t);

        // Verify and Assert
        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeUpdate();

        assertEquals(1,result);
    }

    // TODO Find a means to verify for LocalDateTime instances
//    @Test
//    public void testFindById() throws SQLException {
//
//        TicketDAO ticketDAO = new TicketDAO(connectionUtil);
//        Ticket result = ticketDAO.findById(123);
//
//        // Verify and Assert
//        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
//        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeQuery();
//
//        Mockito.verify(mockResultSet, Mockito.times(2)).getInt(anyString());
//        Mockito.verify(mockResultSet, Mockito.times(2)).getString(anyString());
//    }

    @Test
    public void testUpdate() throws SQLException {

        TicketDAO ticketDAO = new TicketDAO(connectionUtil);
        Mockito.when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        Ticket t = new Ticket(123, 5, 2, LocalDateTime.now(), LocalDateTime.now());
        ticketDAO.update(t,123);

        // Verify and Assert
        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeUpdate();
    }


}
