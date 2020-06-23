//import com.sun.deploy.util.StringUtils;
import data.SQLDatabaseEmployees;
import data.SQLDatabaseExtAttractions;
import models.Attraction;
import models.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SQLDatabaseExtAttractionsTest {

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

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
    }

    @Test
    public void testFindAll() throws SQLException {

        SQLDatabaseExtAttractions instance = new SQLDatabaseExtAttractions(connectionUtil);
        instance.findAll();

        //verify and assert
        verify(mockConn, times(1)).createStatement();
        verify(mockPreparedStmnt,times(1)).executeQuery(anyString());

        verify(mockResultSet, times(3)).getString(anyString());
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockPreparedStmnt, times(0)).setString(anyInt(), anyString());
    }

    @Test
    public void testAddWithExisting() throws SQLException {
        SQLDatabaseExtAttractions  instance = new SQLDatabaseExtAttractions(connectionUtil);
        try {
            instance.add(new Attraction("testName","testStatus","testImageUrl",1,5));
        }
        catch (Exception ex){
            verify(mockConn, times(1)).prepareStatement(anyString());
            verify(mockPreparedStmnt, times(13)).setInt(anyInt(), anyInt());
            verify(mockPreparedStmnt,times(1)).executeQuery();

        }
    }

    @Test
    public void testFindByIdWithOperationalStatus() throws SQLException {

        SQLDatabaseExtAttractions instance = new SQLDatabaseExtAttractions(connectionUtil);
        when(mockResultSet.getString("status")).thenReturn(null);
        Attraction attraction = instance.findByID(1);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt,times(1)).executeQuery();

        verify(mockResultSet, times(3)).getString(anyString());
        verify(mockResultSet, times(2)).getInt(anyString());
        assertEquals(attraction.getStatus(),"Operational");

    }

    @Test
    public void testRemove() throws SQLException {

        SQLDatabaseExtAttractions instance = new SQLDatabaseExtAttractions(connectionUtil);
        when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        boolean result = instance.remove(1);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(0)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(0)).setBoolean(anyInt(), anyBoolean());
        verify(mockPreparedStmnt,times(1)).executeUpdate();

        assertEquals(result,Boolean.TRUE);
    }

}
