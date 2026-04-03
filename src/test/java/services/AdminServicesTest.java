package services;
import java.nio.file.*;
import models.Booking;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import services.*;


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

	//@Test
	//void test() {
		//fail("Not yet implemented");
	//}

	

	
	//login

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

	    admin.removeSlots(time);   

	    admin.addSlots(time);

	    FileServices file = new FileServices();
	    List<String> slots = file.readFile("src/main/resources/appointments.txt");

	    boolean found = slots.stream().anyMatch(s -> s.equals(time + ",Available,30,0,5"));
	    assertTrue(found);

	    admin.removeSlots(time); 
	}
	
	@Test
	void testRemoveSlot() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String time = "19:00";

	    admin.addSlots(time);

	    FileServices file = new FileServices();
	    List<String> slotsBefore = file.readFile("src/main/resources/appointments.txt");
	    assertTrue(slotsBefore.contains(time + ",Available,30,0,5"));

	    admin.removeSlots(time);
	    List<String> slotsAfter = file.readFile("src/main/resources/appointments.txt");
	    assertFalse(slotsAfter.contains(time + ",Available,30,0,5"));
	}
	
	
	@Test
	void testModifySlot() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String time = "20:00";

	    admin.addSlots(time);

	    admin.modifySlots(time, "Unavailable");

	    FileServices file = new FileServices();
	    List<String> slots = file.readFile("src/main/resources/appointments.txt");
	    assertTrue(slots.stream().anyMatch(s -> s.startsWith(time + ",Unavailable")));
	    assertFalse(slots.stream().anyMatch(s -> s.startsWith(time + ",Available")));

	    admin.removeSlots(time);
	}
	
	
	
	@Test
	void testViewSlots() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);

	    String time = "21:00";
	    admin.addSlots(time);

	    assertDoesNotThrow(() -> admin.viewSlots());

	    admin.removeSlots(time);
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
	    Booking booking = new Booking("testUser", "12:30", "Pending", 30, 1);
	    bookingService.bookAppointment(booking);

	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    admin.cancelUserBooking(booking);

	    assertEquals("canceled", booking.getStatus());
	}

	@Test
	void testAdminModifyUserBooking() {
	    BookingService bookingService = new BookingService(new MockNotificationService());
	    Booking booking = new Booking("testUser", "12:30", "Pending", 30, 1);
	    bookingService.bookAppointment(booking);

	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
	    String result = admin.modifyUserBooking(booking, "13:00");
	    assertEquals("Booking Success", result);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

