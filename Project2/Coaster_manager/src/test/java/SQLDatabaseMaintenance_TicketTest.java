import data.SQLDatabaseEmployees;
import data.SQLDatabaseIntAttraction;
import data.SQLDatabaseMaintenance_Ticket;
import models.Attraction;
import models.Employee;
import models.Maintenance_Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import utils.ConnectionUtil;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


/**
 * authored by Ray Vakil
 *
 *
 * 5/19/2020 @ 1506est:
 * Jean updated w/ new maintenance ticket DAO specifications
 */


@RunWith(MockitoJUnitRunner.class)
public class SQLDatabaseMaintenance_TicketTest {


    @Mock
    Connection mockConn;
    @Mock
    PreparedStatement mockPreparedStmnt;
    @Mock
    ResultSet mockResultSet;
    @Mock
    ConnectionUtil connectionUtil;



    @Before
    public void setUp() throws SQLException {

        when(connectionUtil.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockConn.createStatement()).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStmnt.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.getDate(anyString())).thenReturn(new Date(2020));

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
    }

    @Test
    public void testFindAll() throws SQLException {

        SQLDatabaseMaintenance_Ticket instance = new SQLDatabaseMaintenance_Ticket(connectionUtil);
        instance.findAll();

        //verify and assert
        verify(mockConn, times(1)).createStatement();
        verify(mockPreparedStmnt,times(1)).executeQuery(anyString());

        verify(mockResultSet, times(2)).getString(anyString());
        verify(mockResultSet, times(3)).getInt(anyString());
        verify(mockResultSet,times(3)).getDate(anyString());
    }


    @Test
    public void testFindById() throws SQLException {

        SQLDatabaseMaintenance_Ticket instance = new SQLDatabaseMaintenance_Ticket(connectionUtil);
        instance.findByID(1);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt,times(1)).executeQuery();

        verify(mockResultSet, times(2)).getString(anyString());
        verify(mockResultSet, times(3)).getInt(anyString());
        verify(mockResultSet,times(3)).getDate(anyString());

    }

    @Test
    public void testFindByAttraction() throws SQLException {

        SQLDatabaseMaintenance_Ticket instance = new SQLDatabaseMaintenance_Ticket(connectionUtil);
        try {
            instance.add(new Maintenance_Ticket(1,1,1,"testStatus","testDescription",LocalDateTime.now(),LocalDateTime.now(), true));
            instance.findByAttraction(1);
        }

        catch(Exception e) {
            //verify and assert
            verify(mockConn, times(1)).prepareStatement(anyString());
            verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
            verify(mockPreparedStmnt, times(1)).executeQuery();

            verify(mockResultSet, times(2)).getString(anyString());
            verify(mockResultSet, times(3)).getInt(anyString());
            verify(mockResultSet, times(3)).getDate(anyString());
        }
    }

    @Test
    public void testAddWithExisting() throws SQLException {
        SQLDatabaseMaintenance_Ticket instance = new SQLDatabaseMaintenance_Ticket(connectionUtil);
        try {
            instance.add(new Maintenance_Ticket(1,1,1,"testStatus","testDescription",LocalDateTime.now(),LocalDateTime.now(), true));
        }
        catch (Exception ex){
            verify(mockConn, times(1)).prepareStatement(anyString());
            verify(mockPreparedStmnt, times(3)).setInt(anyInt(), anyInt());
            verify(mockPreparedStmnt,times(1)).executeQuery();

        }
    }


    @Test
    public void testUpdate() throws SQLException {

        SQLDatabaseMaintenance_Ticket  instance = new SQLDatabaseMaintenance_Ticket (connectionUtil);
        when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        boolean result = instance.update(1,new Maintenance_Ticket(1,1,1,"testStatus","testDescription",LocalDateTime.now(),LocalDateTime.now(),false));
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).setDate(anyInt(), anyObject());
        verify(mockPreparedStmnt,times(1)).executeUpdate();

        assertEquals(result,Boolean.TRUE);
    }

    @Test
    public void testRemove() throws SQLException {

        SQLDatabaseMaintenance_Ticket  instance = new SQLDatabaseMaintenance_Ticket (connectionUtil);
        boolean result = instance.remove(1);
        assertEquals(result,Boolean.FALSE);
    }

}
