package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupRuleTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void shouldFailWhenDurationExceedsLimit() {
		Booking booking = new Booking();
		booking.setDuration(150);
		booking.setParticipants(5);
		GroupRule rule= new GroupRule();
		
		String resualt = rule.validate(booking);
		
		assertEquals("Group: max duration is 120 min", resualt);
		
	}
	
	
	
	@Test
	void shouldFailWhenaParticipantsExceedsLimit() {
		Booking booking = new Booking();
		booking.setDuration(100);
		booking.setParticipants(15);
		GroupRule rule= new GroupRule();
		String resualt = rule.validate(booking);
		assertEquals("Group: max participants is 10", resualt);
		
	}
	
	
	@Test
	void shouldPassWhenValidBooking() {
		Booking booking = new Booking();
		booking.setDuration(100);
		booking.setParticipants(5);
		GroupRule rule= new GroupRule();
		String resualt = rule.validate(booking);
		
		assertNull( resualt);
		
	}
	
	
	@Test
	void shouldPassWhenValidBooking_edgeCase() {
		Booking booking = new Booking();
		booking.setDuration(120);
		booking.setParticipants(10);
		GroupRule rule= new GroupRule();
		
		String resualt = rule.validate(booking);
		assertNull( resualt);
		
	}
	
	
	@Test
	void shouldReturnDurationErrorFirstWhenMultipleIssues() {
		Booking booking = new Booking();
		booking.setDuration(150);
		booking.setParticipants(20);
		GroupRule rule= new GroupRule();
		
		String resualt = rule.validate(booking);
		assertEquals("Group: max duration is 120 min", resualt);
		
	}
	
	
	
	
	
	

}
