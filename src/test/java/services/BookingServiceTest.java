package services;

import models.Booking;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

	  private BookingService service;
	    private Path appointmentsPath = Paths.get("src/main/resources/appointments.txt");
	    private Path bookingsPath = Paths.get("src/main/resources/booking.txt");

	    private String appointmentsBackup;
	    private String bookingsBackup;

	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	 @BeforeEach
	    void setUp() throws IOException {
		    service = new BookingService(new MockNotificationService());
	        // backup files content
	        appointmentsBackup = Files.readString(appointmentsPath);
	        bookingsBackup = Files.readString(bookingsPath);
	    }

	    @AfterEach
	    void tearDown() throws IOException {
	        // restore files content
	        Files.writeString(appointmentsPath, appointmentsBackup);
	        Files.writeString(bookingsPath, bookingsBackup);
	    }

	    @Test
	    void testSuccessfulBooking() {
	        Booking booking = new Booking("testUser", "2026-05-01", "10:00", "Confirmed", 30, 1);
	        String result = service.bookAppointment(booking);
	        assertEquals("Booking Success", result);
	        assertEquals("Confirmed", booking.getStatus());

	        // check if booking recorded in bookings.txt
	        try {
	            List<String> bookings = Files.readAllLines(bookingsPath);
	            boolean found = bookings.stream().anyMatch(line -> line.contains("testUser") && line.contains("10:00"));
	            assertTrue(found);
	        } catch (IOException e) {
	            fail("Failed reading bookings file");
	        }
	    }

	    @Test
	    void testBookingMaxParticipantsExceeded() {
	        Booking booking = new Booking("testUser", "2026-05-01", "10:00", "Confirmed", 30, 10);
	        String result = service.bookAppointment(booking);
	        assertTrue(result.contains("Max participants exceeded"));
	    }

	    @Test
	    void testBookingDurationExceeded() {
	        Booking booking = new Booking("testUser", "2026-05-01", "10:00", "Confirmed", 120, 1);
	        String result = service.bookAppointment(booking);
	        assertTrue(result.contains("Duration Exceeded"));
	    }

	    @Test
	    void testBookingUnavailableSlot() {
	        Booking booking = new Booking("testUser", "2026-05-01", "10:30", "Confirmed", 30, 1); // 10:30 is Unavailable in your file
	        String result = service.bookAppointment(booking);
	        assertTrue(result.contains("Slot not available"));
	    }

	    @Test
	    void testCancelBooking() throws IOException {
	        Booking booking = new Booking("testUser", "2026-05-01", "10:00", "Confirmed", 30, 1);
	        service.bookAppointment(booking);

	        service.cancelBooking(booking);
	        assertEquals("canceled", booking.getStatus());

	        // check if appointment slot is available again
	        List<String> appointments = Files.readAllLines(appointmentsPath);
	        boolean found = appointments.stream().anyMatch(line -> line.startsWith("2026-05-01,10:00") && line.contains("Available"));
	        assertTrue(found);
	    }
	    
	    @Test
	    void testModifyBooking() throws IOException {
	        Booking booking = new Booking("testUser", "2026-05-03", "12:30", "Pending", 30, 1);
	        service.bookAppointment(booking);
	        
	        String result = service.modifyBooking(booking, "2026-05-04", "13:00", LocalDateTime.of(LocalDate.of(2026, 5, 2), LocalTime.of(8, 0)));
	        assertEquals("Booking Success", result);
	    }
	    
	    
	    @Test
	    void testModifyPastBooking() {
	    	Booking booking = new Booking("testUser@example.com", "2026-04-01", "00:01", "Pending", 30, 1);
	    	String result = service.modifyBooking(booking, "2026-05-04", "13:00");
	    	assertTrue(result.contains("Cannot modify past appointments"));
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}
