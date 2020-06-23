import dao.AttractionDAO;
import dao.TicketDAO;
import models.Attraction;
import models.Ticket;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

public class AttractionTests {
    // Instance Variables
    // Initialized anything needed for mocking, storage, etc.
    ArrayList<Attraction> attractions = new ArrayList();

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
    public void testAttractionCreationGetters() {

        Attraction a = new Attraction("ShiveringTimbers", "Open", "atotallyrealurlforanimage.com", 2010, 7);
        String output = "" + a.getName() + " " + a.getStatus() + " " + a.getImageurl() + " " + a.getId() + " " + a.getRating();
        assertEquals("ShiveringTimbers Open atotallyrealurlforanimage.com 2010 7", output);

    }

    @Test
    public void testAttractionCreationSetters() {

        Attraction a1 = new Attraction("ShiveringTimbers", "Closed", "atotallyrealurlforanimage.com", 2010, 6);
        Attraction a2 = new Attraction("ShiveringTimbers", "Open", "atotallyrealurlforanimage.com", 2010, 7);
        a1.setName(a2.getName());
        a1.setStatus(a2.getStatus());
        a1.setImageurl(a2.getImageurl());
        a1.setId(a2.getId());
        a1.setRating(a2.getRating());
        String output = "" + a1.getName() + " " + a1.getStatus() + " " + a1.getImageurl() + " " + a1.getId() + " " + a1.getRating();
        assertEquals("ShiveringTimbers Open atotallyrealurlforanimage.com 2010 7", output);

    }

    // The following Unit Tests are meant for checking database-involved functionalities through
    // AttractionDAO instance with mocked components to avoid using the existing postgresql db.

    @Test
    public void testFindAll() throws SQLException {

        AttractionDAO attractionDAO = new AttractionDAO(connectionUtil);
        attractionDAO.findAll();

        // Verify and Assert
        Mockito.verify(mockConn, Mockito.times(1)).createStatement();
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeQuery(anyString());

        Mockito.verify(mockResultSet, Mockito.times(3)).getString(anyString());
        Mockito.verify(mockResultSet, Mockito.times(2)).getInt(anyString());
    }

    // TODO Determine if it is worth implementing this test case as customers should only be able to find Attractions.
    @Test
    public void testSave() throws SQLException {

        AttractionDAO attractionDAO = new AttractionDAO(connectionUtil);
        Mockito.when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        Attraction a = new Attraction("ShiveringTimbers", "Open", "atotallyrealurlforanimage.com", 2010, 7);
        int result = attractionDAO.save(a);

        // Verify and Assert
        Mockito.verify(mockConn, Mockito.times(2)).prepareStatement(anyString());
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeQuery();
        //Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeUpdate();

        assertEquals(1,result);
    }

    @Test
    public void testFindById() throws SQLException {

        AttractionDAO attractionDAO = new AttractionDAO(connectionUtil);
        Attraction result = attractionDAO.findById(2010);

        // Verify and Assert
        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(mockPreparedStmnt,Mockito.times(1)).executeQuery();

        Mockito.verify(mockResultSet, Mockito.times(3)).getString(anyString());
        Mockito.verify(mockResultSet, Mockito.times(2)).getInt(anyString());
    }

}
