package services;
import java.nio.file.*;
import models.Booking;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import services.AdminServices;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import services.BookingService;
import services.MockNotificationService;


//import services.*;


class AdminServicesTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
		appointmentsBackup = Files.readString(appointmentsPath);
	    bookingsBackup = Files.readString(bookingsPath);
		
	}

	@AfterEach
	void tearDown() throws Exception {
		Files.writeString(appointmentsPath, appointmentsBackup);
	    Files.writeString(bookingsPath, bookingsBackup);
	}

	private Path appointmentsPath = Paths.get("src/main/resources/appointments.txt");
	private Path bookingsPath = Paths.get("src/main/resources/booking.txt");
	private String appointmentsBackup;
	private String bookingsBackup;

	

	@Test
	void testLoginSuccess() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String result = admin.login("admin", "1234");
	    assertEquals("Success", result); 
	    assertTrue(admin.isLoggedIn()); 
	}

 

	
	
	@Test
	void testLoginWrongPassword() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String result = admin.login("admin", "0000");
	    assertEquals("Failed", result);
	    assertFalse(admin.isLoggedIn()); 
	}
	
	
	@Test
	void testLoginUserNotFound() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String result = admin.login("unknown", "1234");
	    assertEquals("Failed", result); 
	    assertFalse(admin.isLoggedIn());
	}
	
	
	@Test
	void testLoginInvalidData() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String result = admin.login("abc", "0000");
	    assertEquals("Failed", result); 
	    assertFalse(admin.isLoggedIn());
	}
	
	//username
	@Test
	void testSetUsername() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String original = admin.getUsername();

	    admin.setUsername("newAdmin"); 
	    assertEquals("newAdmin", admin.getUsername());

	    admin.setUsername(original);
	}
	
	@Test
	void testGetUsername() {
	    AdminServices admin =new AdminServices("admin", "1234", "admin@email.com", 6);
	    assertEquals("admin", admin.getUsername());
	}
	
	
	
	//password
	@Test
	void testSetPassword() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String original = admin.getPassword();

	    admin.setPassword("9999");
	    assertEquals("9999", admin.getPassword());

	    admin.setPassword(original);
	}
	
	@Test
	void testGetPassword() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    assertEquals("1234", admin.getPassword());
	}
	
	
	@Test
	void testLogout() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    admin.login("admin", "1234");
	    assertTrue(admin.isLoggedIn());

	    admin.logout();
	    assertFalse(admin.isLoggedIn()); 
	}
	
	
	@Test
	void testAddSlot() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String time = "18:00";

	    admin.removeSlots("2026-05-01", time);   

	    admin.addSlots("2026-05-01", time);

	    FileServices file = new FileServices();
	    List<String> slots = file.readFile("src/main/resources/appointments.txt");

	    boolean found = slots.stream().anyMatch(s -> s.equals("2026-05-01," + time + ",Available,30,0,5"));
	    assertTrue(found);

	    admin.removeSlots("2026-05-01", time); 
	}

	@Test
	void testRemoveSlot() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String time = "19:00";

	    admin.removeSlots("2026-05-01", time); 
	    admin.addSlots("2026-05-01", time);

	    FileServices file = new FileServices();
	    List<String> slotsBefore = file.readFile("src/main/resources/appointments.txt");
	    assertTrue(slotsBefore.stream()
	        .anyMatch(s -> s.trim().equals("2026-05-01," + time + ",Available,30,0,5")));

	    admin.removeSlots("2026-05-01", time);
	    List<String> slotsAfter = file.readFile("src/main/resources/appointments.txt");
	    assertFalse(slotsAfter.stream()
	        .anyMatch(s -> s.trim().equals("2026-05-01," + time + ",Available,30,0,5")));
	}
	
	@Test
	void testModifySlot() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String time = "20:00";

	    admin.removeSlots("2026-05-01", time);
	    admin.addSlots("2026-05-01", time);

	    admin.modifySlots("2026-05-01", time, "Unavailable");

	    FileServices file = new FileServices();
	    List<String> slots = file.readFile("src/main/resources/appointments.txt");
	    assertTrue(slots.stream()
	        .anyMatch(s -> s.trim().startsWith("2026-05-01," + time + ",Unavailable")));
	    assertFalse(slots.stream()
	        .anyMatch(s -> s.trim().startsWith("2026-05-01," + time + ",Available")));

	    admin.removeSlots("2026-05-01", time);
	}
	
	@Test
	void testViewSlots() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);

	    String time = "21:00";
	    admin.addSlots("2026-05-01", time);

	    assertDoesNotThrow(() -> admin.viewSlots());

	    admin.removeSlots("2026-05-01", time);
	}
	
	
	@Test
	void testSetEmail() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);

	    admin.setEmail("new@email.com");
	    assertEquals("new@email.com", admin.getEmail());

	    admin.setEmail("admin@email.com");
	}
	
	@Test
	void testGetEmail() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    assertEquals("admin@email.com", admin.getEmail());
	}
	
	
	@Test
	void testSetId() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);

	    admin.setId(99);
	    assertEquals(99, admin.getId());

	    admin.setId(6);
	}
	
	
	@Test
	void testGetId() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    assertEquals(6, admin.getId());
	}
	
	
	@Test
	void testAdminCancelUserBooking() {
	    BookingService bookingService = new BookingService(new MockNotificationService());
	    Booking booking = new Booking("test@gmail.com", "2026-05-03", "12:30", "Pending", 30, 1);
	    bookingService.bookAppointment(booking);

	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    admin.cancelUserBooking(booking);

	    assertEquals("canceled", booking.getStatus());
	}
	

	
	
	@Test
	void shouldRejectPastTime() {
	    BookingService bookingService = new BookingService(new MockNotificationService());
	    Booking booking = new Booking("testUser@example.com", "2026-04-01", "07:00", "Pending", 30, 1);
	    bookingService.bookAppointment(booking);
	    String result = bookingService.modifyBooking(booking, "2026-05-04", "13:00", LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)));
	    assertTrue(result.contains("Cannot modify past"));
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void testAdminModifyUserBooking() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    Booking booking = new Booking("testUser@gmail.com", "2026-05-02", "11:30", "Pending", 30, 1);

	    admin.getBookingService().bookAppointment(booking);

	    String result = admin.modifyUserBooking(booking, "2026-05-04", "13:00");

	    assertEquals("Booking Success", result);
	}

    @Test
    void testAdminCancelBooking_Unauthorized() {
        AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
        Booking booking = new Booking("fawzia@gmail.com", "2026-05-03", "12:30", "Pending", 30, 1);

        admin.getBookingService().bookAppointment(booking);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            admin.adminCancelBooking(booking, 5);
        });

        assertEquals("Unauthorized: Only admins can cancel bookings.", exception.getMessage());
    }

    @Test
    void testAdminModifyBooking_Success() {
        AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
        Booking booking = new Booking("fawzia@gmail.com", "2026-05-02", "11:30", "Pending", 30, 1);

        admin.getBookingService().bookAppointment(booking);

        String result = admin.adminModifyBooking(booking, "2026-05-04", "13:00", 6);

        assertEquals("Booking Success", result);
    }

    @Test
    void testAdminModifyBooking_Unauthorized() {
        AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
        Booking booking = new Booking("fawzia@gmail.com", "2026-05-01", "10:00", "Pending", 30, 1);

        admin.getBookingService().bookAppointment(booking);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            admin.adminModifyBooking(booking, "2026-05-04", "13:00", 5);
        });

        assertEquals("Unauthorized: Only admins can modify bookings.", exception.getMessage());
    }

    @Test
    void testAdminCancelBooking_Success() {
        AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
        Booking booking = new Booking("fawzia@gmail.com", "2026-05-03", "12:30", "Pending", 30, 1);

        admin.getBookingService().bookAppointment(booking);

        admin.adminCancelBooking(booking, 6);

        assertEquals("canceled", booking.getStatus());
    }
	
		

}

