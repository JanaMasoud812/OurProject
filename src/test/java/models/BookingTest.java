package models;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

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
	    void testConstructorAndGetters() {
	        Booking booking = new Booking("Jana", "2026-05-01", "10:00", "Confirmed", 30, 2);
	        assertEquals("Jana", booking.getUsername());
	        assertEquals("10:00", booking.getTime());
	        assertEquals("Confirmed", booking.getStatus());
	        assertEquals(30, booking.getDuration());
	        assertEquals(2, booking.getParticipants());
	    }

	    @Test
	    void testConfirmBooking() {
	        Booking booking = new Booking("Jana", "2026-05-01", "10:00", "canceled", 30, 2);
	        booking.confirmBooking();
	        assertEquals("Confirmed", booking.getStatus());
	    }

	    @Test
	    void testCancelBooking() {
	        Booking booking = new Booking("Jana", "2026-05-01", "10:00", "Confirmed", 30, 2);
	        booking.cancelBooking();
	        assertEquals("canceled", booking.getStatus());
	    }

	    @Test
	    void testIsFull() {
	        Booking booking = new Booking("Jana", "2026-05-01", "10:00", "Confirmed", 30, 5);
	        assertTrue(booking.isFull(5));
	        assertFalse(booking.isFull(6));
	    }

	    @Test
	    void testSetters() {
	        Booking booking = new Booking("Jana", "2026-05-01", "10:00", "Confirmed", 30, 2);

	        booking.setUsername("Lana");
	        booking.setTime("11:00");
	        booking.setStatus("canceled");
	        booking.setDuration(45);
	        booking.setParticipants(3);

	        assertEquals("Lana", booking.getUsername());
	        assertEquals("11:00", booking.getTime());
	        assertEquals("canceled", booking.getStatus());
	        assertEquals(45, booking.getDuration());
	        assertEquals(3, booking.getParticipants());
	    }
	
}
