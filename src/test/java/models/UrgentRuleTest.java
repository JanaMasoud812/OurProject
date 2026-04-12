package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrgentRuleTest {

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
		booking.setDuration(60);
		booking.setParticipants(1);
		UrgentRule rule = new UrgentRule(); 
		
		
		String resault = rule.validate(booking);
		assertEquals("Urgent: max duration is 30 min", resault);
		
	}
	
	@Test
	void shouldFailWhenParticipantsExceedsLimit() {
		Booking booking = new Booking();
		booking.setDuration(20);
		
		booking.setParticipants(3);
		UrgentRule rule = new UrgentRule();
		String resault = rule.validate(booking);
		
		assertEquals("Urgent: only 1 participant allowed", resault);
		
	}
	
	
	@Test
	void shouldPassWhenValidBooking() {
		Booking booking = new Booking();
		
		booking.setDuration(20);
		booking.setParticipants(1);
		
		UrgentRule rule = new UrgentRule();
		String resault = rule.validate(booking);
		assertNull(resault);
		
	}
	
	
	@Test
	void shouldPassAtBoundaryDuration() {
		Booking booking = new Booking();
		booking.setDuration(30);
		booking.setParticipants(1);
		UrgentRule rule = new UrgentRule();
		String resault = rule.validate(booking);
		assertNull(resault);
		
	}
	
	@Test
	void shouldReturnDurationErrorFirstWhenMultipleIssues() {
		Booking booking = new Booking();
		booking.setDuration(40);
		booking.setParticipants(3);
		UrgentRule rule = new UrgentRule();
		String resault = rule.validate(booking);
		
		assertEquals("Urgent: max duration is 30 min", resault);
		
	}
	
	
	
	
	

}
