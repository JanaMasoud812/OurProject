package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MaxDurationRuleTest {

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
	void shouldFailWhenDurationExceedsMax() {
		Booking booking = new Booking();
		booking.setDuration(100);
		
		MaxDurationRule rule = new MaxDurationRule(60);
		String resault = rule.validate(booking);
		assertEquals("Booking Failed: Duration Exceeded", resault);
		
	}
	
	@Test
	void shouldPassWhenDurationWithinLimit() {
		Booking booking = new Booking();
		booking.setDuration(50);
		
		MaxDurationRule rule = new MaxDurationRule(60);
		String resault = rule.validate(booking);
		assertNull(resault);
		
	}
	
	@Test
	void shouldPassAtBoundary_edgeCase() {
		Booking booking = new Booking();
		booking.setDuration(60);
		
		MaxDurationRule rule = new MaxDurationRule(60);
		String resault = rule.validate(booking);
		assertNull(resault);
		
	}

}
