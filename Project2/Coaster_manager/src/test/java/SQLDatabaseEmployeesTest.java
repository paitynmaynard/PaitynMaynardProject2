import data.SQLDatabaseEmployees;
import models.Employee;
import org.junit.*;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import utils.ConnectionUtil;

@RunWith(MockitoJUnitRunner.class)
public class SQLDatabaseEmployeesTest {

    @Mock
    Connection mockConn;
    @Mock
    PreparedStatement mockPreparedStmnt;
    @Mock
    ResultSet mockResultSet;
    @Mock
    ConnectionUtil connectionUtil;

    public SQLDatabaseEmployeesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {

        when(connectionUtil.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
/*

        doNothing().when(mockConn).commit();
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStmnt);
        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.execute()).thenReturn(Boolean.TRUE);
        when(mockPreparedStmnt.getGeneratedKeys()).thenReturn(mockResultSet);

 */
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        //  when(mockResultSet.getInt(Fields.GENERATED_KEYS)).thenReturn(userId);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFindAll() throws SQLException {

        SQLDatabaseEmployees instance = new SQLDatabaseEmployees(connectionUtil);
        instance.findAll();

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockResultSet, times(5)).getString(anyString());
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet, times(1)).getBoolean(anyString());
        verify(mockPreparedStmnt, times(0)).setString(anyInt(), anyString());
        /*verify(mockPreparedStmnt, times(6)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).execute();
        verify(mockConn, times(1)).commit();
        verify(mockResultSet, times(2)).next();

         */
        // verify(mockResultSet, times(1)).getInt(Fields.GENERATED_KEYS);
    }

    @Test
    public void testAdd() throws SQLException {

        SQLDatabaseEmployees instance = new SQLDatabaseEmployees(connectionUtil);
        when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        boolean result = instance.add(new Employee("fName","lName","0652145236","fname@gmail.com",1,"pwwd",2,false));
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(5)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(mockPreparedStmnt,times(1)).executeUpdate();

        assertEquals(result,Boolean.TRUE);
    }

    @Test
    public void testFindById() throws SQLException {

        SQLDatabaseEmployees instance = new SQLDatabaseEmployees(connectionUtil);
        instance.findByID(1);
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt,times(1)).executeQuery();

        verify(mockResultSet, times(5)).getString(anyString());
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());

    }

    @Test
    public void testUpdate() throws SQLException {

        SQLDatabaseEmployees instance = new SQLDatabaseEmployees(connectionUtil);
        when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        boolean result = instance.update(1,new Employee("fName","lName","0652145236","fname@gmail.com",1,"pwwd",2,false));
        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStmnt, times(5)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStmnt, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(mockPreparedStmnt,times(1)).executeUpdate();

        assertEquals(result,Boolean.TRUE);
    }

    @Test
    public void testRemove() throws SQLException {

        SQLDatabaseEmployees instance = new SQLDatabaseEmployees(connectionUtil);
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
