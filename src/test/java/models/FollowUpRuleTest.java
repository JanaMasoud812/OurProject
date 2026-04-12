package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FollowUpRuleTest {

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
	// test if appointment duration greater than 45 min 
	void shouldFailWhenDurationExceedsLimit() {
		
		Booking booking = new Booking();
		booking.setDuration(50);
		booking.setParticipants(1);
		
		FollowUpRule rule = new FollowUpRule();
		
		String resault = rule.validate(booking);
		assertEquals("Follow-up: max duration is 45 min", resault);
		
	}
	
	
	@Test
	void shouldFailWhenParticipantsNotOne() {
		Booking booking = new Booking();
		booking.setDuration(30);
		booking.setParticipants(2);
		FollowUpRule rule= new FollowUpRule();
		String resault = rule.validate(booking);
		assertEquals("Follow-up: only 1 participant allowed", resault);
		
	}
	
	
	@Test
	void shouldPassWhenValidBooking() {
		Booking booking = new Booking();
		booking.setDuration(30);
		booking.setParticipants(1);
		FollowUpRule rule= new FollowUpRule();
		String resault = rule.validate(booking);
		assertNull(resault);
		
	}
	
	
	@Test 
	void shouldReturnDurationErrorFirstWhenMultipleIssues() {
		Booking booking = new Booking();
		booking.setDuration(50);
		booking.setParticipants(2);
		FollowUpRule rule= new FollowUpRule();
		String resault = rule.validate(booking);
		assertEquals("Follow-up: max duration is 45 min", resault);
		
	
	}
	
	

	

}
