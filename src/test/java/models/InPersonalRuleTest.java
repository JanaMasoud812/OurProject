package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InPersonalRuleTest {

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
		InPersonRule rule= new InPersonRule();
		String  resualt = rule.validate(booking);
		assertEquals("In-person: max duration is 60 min" , resualt);
		
		
	}
	
	@Test
	void shouldFailWhenParticipantsExceedsLimit() {
		Booking booking = new Booking();
		booking.setDuration(30);
		booking.setParticipants(2);
		InPersonRule rule= new InPersonRule();
		String  resualt = rule.validate(booking);
		assertEquals("In-person: only 1 participant allowed" , resualt);
		
	}
	
	@Test
	void shouldPassWhenValidBooking() {
		Booking booking = new Booking();
		booking.setDuration(30);
		booking.setParticipants(1);
		InPersonRule rule= new InPersonRule();
		String  resualt = rule.validate(booking);
		assertNull(resualt);
		
	}
	
	@Test
	void shouldPassWhenValidBooking_edgeCase() {
		Booking booking = new Booking();
		booking.setDuration(60);
		booking.setParticipants(1);
		
		InPersonRule rule= new InPersonRule();
		String  resualt = rule.validate(booking);
		
		assertNull(resualt);
		
	}
	
	@Test
	void shouldReturnDurationErrorFirstWhenMultipleIssues() {
		Booking booking = new Booking();
		booking.setDuration(100);
		booking.setParticipants(2);
		
		InPersonRule rule= new InPersonRule();
		String  resualt = rule.validate(booking);
		
		assertEquals("In-person: max duration is 60 min",resualt);	
		
		
	}

}
