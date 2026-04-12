package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VirtualRuleTest {

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
		booking.setDuration(70);
		booking.setParticipants(1);
		VirtualRule rule = new VirtualRule();
		String resault = rule.validate(booking);
		assertEquals("Virtual: max duration is 60 min" , resault);
		
		
	}
	
	@Test
	void shouldFailWhenParticipantsExceedsLimit() {
		Booking booking = new Booking();
		booking.setDuration(50);
		booking.setParticipants(2);
		
		VirtualRule rule = new VirtualRule();
		String resault = rule.validate(booking);
		
		assertEquals("Virtual: only 1 participant allowed" , resault);
		
		
	}
	
	
	@Test
	void shouldPassWhenValidBooking() {
		Booking booking = new Booking();
		booking.setDuration(50);
		booking.setParticipants(1);
		
		VirtualRule rule = new VirtualRule();
		String resault = rule.validate(booking);
		
		assertNull(resault);
		
		
	}
	
	@Test
	void shouldReturnDurationErrorFirstWhenMultipleIssues() {
		Booking booking = new Booking();
		booking.setDuration(70);
		booking.setParticipants(2);
		
		VirtualRule rule = new VirtualRule();
		String resault = rule.validate(booking);
		
		assertEquals("Virtual: max duration is 60 min" , resault);
		
		
	}

}
