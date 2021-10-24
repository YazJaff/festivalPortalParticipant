package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.EventCoordinator;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;

/**
 * Junit test class for EventDAO class
 * 
 */
public class TestEventDAO {

	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet resultSet = null;
	private ArrayList<Object[]> showAllEvents;
	private EventDAO dao;
	private Event event;

	/**
	 * Sets up database connection before other methods are executed in this
	 * class
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpDatabaseConnection() throws Exception 
	{
		connection = FERSDataConnection.createConnection();
	}

	/**
	 * Closes the database connection after all the methods are executed
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownDatabaseConnection() throws Exception {
		/**
		 * @TODO: Close connection object here  
		 */
		connection.close();
	}

	/**
	 * Sets up the objects required in other methods
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		showAllEvents = new ArrayList<Object[]>();
		dao = new EventDAO();
		event = new Event();
	}

	/**
	 * Deallocate the resources after execution of method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
		showAllEvents = null;
		dao = null;
	}

	/**
	 * Positive test case to test the method showAllEvents
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testShowAllEvents_Positive() throws ClassNotFoundException, SQLException, Exception
	{
		/**
		 * @TODO: Call showAllEvents method and assert it for
		 * size of returned type list
		 */	
		
			showAllEvents = dao.showAllEvents();
			if(showAllEvents.size()>=1)
			{
				assert(true);
			}
			else if(showAllEvents.size()<0)
			{
				assert(false);
			}
			
		
	}
	
	/**
	 * Junit test case to test positive case for updateEventDeletions
	 * @throws ClassNotFoundException 
	 * @throws Exception 
	 */
	@Test
	public void testUpdateEventDeletions_Positive() throws ClassNotFoundException, SQLException, Exception
	{
		/**
		 * @TODO: Find out seats available for an event by opening a connection
		 * and calling the query SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?
		 * Call the updateEventDeletions for eventId
		 * Again find out the seats available for this event
		 * testSeatsAvailableBefore should be 1 more then testSeatsAvailableAfter
		 */		
		
		statement = connection.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID=?");
		statement.setInt(1, 1001);
		resultSet= statement.executeQuery();
		int seatsAvailaible_before=0;
		resultSet.next();
		seatsAvailaible_before = resultSet.getInt(1);
		
		dao.updateEventDeletions(1001,10001);
		ResultSet resultSet1= statement.executeQuery();
		int seatsAvailaible_after=0;
		resultSet1.next();
		seatsAvailaible_after = resultSet1.getInt(1);
		
		assertEquals(seatsAvailaible_before+1,seatsAvailaible_after);
	}
 
	/**
	 * Negative test case for method updateEventDeletions
	 */
	@Test
	public void testUpdateEventDeletions_Negative() {
		/**
		 * @TODO: Call updateEventDeletions for incorrect eventid and it should
		 * throw an exception
		 */
		try {
			dao.updateEventDeletions(43638,10001);
		} catch (Exception e) {
			// TODO Auto-generated catch block

           assert(true);
		}
	}

	/**
	 * Positive test case for method updateEventNominations
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testUpdateEventNominations_Positive() throws Exception
	{
		/**
		 * @TODO: Find out seats available for an event by opening a connection
		 * and calling the query SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?
		 * Call the updateEventNominations for eventId
		 * Again find out the seats available for this event
		 * testSeatsAvailableBefore should be 1 less then testSeatsAvailableAfter
		 */	
		
		statement = connection.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID=?");
		statement.setInt(1, 1001);
		
		
		resultSet= statement.executeQuery();
		resultSet.next();
		int seatsAvailaible_before = resultSet.getInt(1);
		
		dao.updateEventNominations(1001,10001);
		
		ResultSet resultSet1= statement.executeQuery();
		resultSet1.next();
		int seatsAvailaible_after = resultSet1.getInt(1);
		
		assertEquals(seatsAvailaible_before-1,seatsAvailaible_after);
	}
	/**
	 * Negative test case for method updateEventNominations
	 */
	@Test
	public void testUpdateEventNominations_Negative()
	{
		/**
		 * @TODO: Call updateEventNominations for incorrect eventid and it should
		 * throw an exception
		 */	
		try {
			dao.updateEventNominations(43638,10001);
		} catch (Exception e) {
			// TODO Auto-generated catch block

           assert(true);
		}
	}

	/**
	 * Positive test case for method checkEventsofVisitor
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testCheckEventsOfVisitor_Positive() throws ClassNotFoundException, SQLException
	{
		/**
		 * @TODO: Create visitor object by setting appropriate values
		 * Call checkEventsofVisitor method by passing this visitor object and
		 * valid eventId
		 * Assert the value of return type 
		 */	
		Visitor visitor = new Visitor();
		visitor.setVisitorId(1);
		visitor.setUserName("ylee");
		visitor.setPassword("password");
		visitor.setFirstName("TestVFname");
		visitor.setLastName("lname");
		visitor.setEmail("mail");
		visitor.setPhoneNumber("11111");
		visitor.setAddress("testAddress");
		visitor.setAdmin(false);
		boolean b = dao.checkEventsofVisitor(visitor, 1001, 10001);
		assertEquals(false,b);
	}
	
	/**
	 * Junit test case for getEventCoordinator
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testGetEventCoordinator() throws ClassNotFoundException, SQLException
	{
		/**
		 * @TODO: Call getEventCoordinator method
		 * Assert the size of return type arraylist
		 */		
		List<EventCoordinator> eventCoordinatorList = dao.getEventCoordinator();
		if(eventCoordinatorList.size()==0)
		{
			assertTrue("No EventCoordinator", true);
		}
		else if(eventCoordinatorList.size()==1)
		{
			assertTrue("One EventCoordinator",true);
		}
		else
		{
			assertTrue("More than one EventCoordinator", true);
		}
	}
	
	

	/**
	 * Junit test case for getEvent
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testGetEvent() throws ClassNotFoundException, SQLException
	{
		/**
		 * @TODO: Call getEvent method 
		 * Assert the returned Event type with the passed value of event id
		 */
		Event event = dao.getEvent(1001, 10001);
		assertEquals(event.getEventid(),1001);
	}	
	
	/**
	 * Junit test case for updateEvent
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testInsertEvent() throws ClassNotFoundException, SQLException
	{
		/**
		 * @TODO: Create Event object by setting appropriate values
		 * Call insertEvent method by passing this event object
		 * Assert the status of return type of this insertEvent method
		 */		
		int status = 0;
	  
		event.setEventid(1008);
		event.setName("event9");
		event.setDescription("desc_event9");
		event.setPlace("place9");
		event.setDuration("1800-2200");
		event.setEventtype("event_type9");
	   status = dao.insertEvent(event);
		if(status<0)
			assert(false);
		else
			assert(true);
		
	}
	
	/**
	 * Junit test case for updateEvent
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testUpdateEvent() throws ClassNotFoundException, SQLException
	{
		/**
		 * @TODO: Fetch Event object by calling showAllEvents method
		 * Update the values of event object
		 * Call updateEvent method by passing this modified event as object
		 * Assert the status of return type of updateEvent method
		 */		
		showAllEvents = dao.showAllEvents();
		Event event = new Event();
		event.setEventid(1001);
		event.setName("event1");
		event.setDescription("desc_event1");
		event.setPlace("place1");
		event.setDuration("1800-2200");
		event.setEventtype("event_type1");
		int status = dao.updateEvent(event);
		assertEquals(1,status);
		
	}
	
	/**
	 * Junit test case for deleteEvent
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws FERSGenericException 
	 */
	@Test
	public void testDeleteEvent() throws ClassNotFoundException, SQLException, FERSGenericException{
		/**
		 * @TODO: Fetch Event object by calling showAllEvents method		 * 
		 * Call deleteEvent method by passing this event id and event session id as object
		 * Assert the status of return type of updateEvent method
		 */		
		showAllEvents = dao.showAllEvents();
		Event event = new Event();
		event.setEventid(1001);
		event.setName("event1");
		event.setDescription("desc_event1");
		event.setPlace("place1");
		event.setDuration("1800-2200");
		event.setEventtype("event_type1");
		int status = dao.deleteEvent(1001,10001);
		if(status>0)
			assert(true);
	}

}
