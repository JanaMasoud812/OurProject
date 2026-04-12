package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MaxParticipantsRuleTest {

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
	void shouldFailWhenParticipantsExceedsMax() {
		Booking booking = new Booking();
		booking.setParticipants(2);
		
		MaxParticipantsRule rule = new MaxParticipantsRule(1);
		String resault = rule.validate(booking);
		assertEquals("Booking Failed: Max participants exceeded", resault);
		
	}
	
	@Test
	void shouldPassWhenParticipantsWithinLimit() {
		Booking booking = new Booking();
		booking.setParticipants(5);
		
		MaxParticipantsRule rule = new MaxParticipantsRule(10);
		String resault = rule.validate(booking);
		assertNull(resault);
		
	}
	
	@Test
	void shouldPassAtBoundary_edgeCase() {
		Booking booking = new Booking();
		booking.setParticipants(10);
		
		MaxParticipantsRule rule = new MaxParticipantsRule(10);
		String resault = rule.validate(booking);
		assertEquals("Booking Failed: Max participants exceeded",resault);
		
	}

}
