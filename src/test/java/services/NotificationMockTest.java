package services;

import models.AppointmentSlot;
import models.Booking;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class NotificationMockTest {
	




	private BookingService service;
	private MockNotificationService mockNotification;
	private final String bookingsPath = "src/main/resources/booking.txt";
	private final String appointmentsPath = "src/main/resources/appointments.txt";
	private String appointmentsBackup;
	
	
	@BeforeEach
	void setUp()  throws Exception {
		
		 mockNotification = new MockNotificationService();
	     service = new BookingService(mockNotification);
	     appointmentsBackup = Files.readString(Paths.get(appointmentsPath));

	     Files.writeString(Paths.get(bookingsPath), "");

		

}
	
	@Test
	void testSuccessfulBooking() {
		Booking booking = new Booking("fawzia", "2026-05-01", "10:00", "Pending",30,1);
		service.bookAppointment(booking);
		
		List<String>messages=mockNotification.getSentMessages();
		assertEquals(1, messages.size());
        assertTrue(messages.get(0).contains("fawzia"));
        assertTrue(messages.get(0).contains("10:00"));
        assertTrue(messages.get(0).toLowerCase().contains("confirmed"));
		
		
		
	}
	
	@Test
	void testUnavailableSlot() {
		Booking booking = new Booking("user1", "2026-05-01", "10:30","Pending",30,1);
		service.bookAppointment(booking);
		List<String>messages=mockNotification.getSentMessages();
		assertEquals(0, messages.size());

		
		
	}
	
	@Test
	void testForMaxParticipation() {
		Booking booking = new Booking("user2", "2026-05-01", "10:30","Pending",30,10);
		service.bookAppointment(booking);
		
		List<String> messages = mockNotification.getSentMessages();
		assertEquals(0,messages.size());
		
		
		
	}
	
	@Test
	void testForDuration() {
		Booking booking = new Booking("user3", "2026-05-01", "10:00","Pending",120,1);
		service.bookAppointment(booking);
		
		List<String> messages = mockNotification.getSentMessages();
		assertEquals(0,messages.size());
		
		
		
	}
	
	
	@Test 
	void testMultipleNOtificationForMultipleBookings() {
		Booking booking1= new Booking("user1", "2026-05-03", "12:30","Pending",30,1);
		Booking booking2 = new Booking("user2", "2026-05-02", "11:30","Pending",30,1);
		
		service.bookAppointment(booking1);
		service.bookAppointment(booking2);
		
		List<String> messages = mockNotification.getSentMessages();
		assertEquals(2,messages.size());
		assertTrue(messages.get(0).contains("user1"));
		assertTrue(messages.get(1).contains("user2"));

	}
	
	
	@Test
	void testNotificationCorrectContent() {
		Booking booking = new Booking("fawzia", "2026-05-03", "12:30","Pending",30,1);
		service.bookAppointment(booking);
		
		List<String> messages = mockNotification.getSentMessages();
		assertEquals(1,messages.size());
		
		String msg= messages.get(0);
		assertTrue(msg.contains("fawzia"));
		assertTrue(msg.contains("12:30"));
		assertTrue(msg.toLowerCase().contains("confirmed"));
		
		
		
	}
	
	
	
	@AfterEach
    void tearDown() throws IOException {
        Files.writeString(Paths.get(appointmentsPath), appointmentsBackup);
    }

}
