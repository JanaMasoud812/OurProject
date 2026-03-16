import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.*;


class TestTest {

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

	
	//login
	@Test
	void testLoginSuccess() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    String result = admin.login("admin", "1234");
	    assertEquals("Success", result); 
	    assertTrue(admin.isLoggedIn()); 
	}
	
	
	
	@Test
	void testLoginWrongPassword() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    String result = admin.login("admin", "0000");
	    assertEquals("Failed", result);
	    assertFalse(admin.isLoggedIn()); 
	}
	
	
	@Test
	void testLoginUserNotFound() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    String result = admin.login("unknown", "1234");
	    assertEquals("Failed", result); 
	    assertFalse(admin.isLoggedIn());
	}
	
	
	@Test
	void testLoginInvalidData() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    String result = admin.login("abc", "0000");
	    assertEquals("Failed", result); 
	    assertFalse(admin.isLoggedIn());
	}
	
	//username
	@Test
	void testSetUsername() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    String original = admin.getUsername();

	    admin.setUsername("newAdmin"); 
	    assertEquals("newAdmin", admin.getUsername());

	    admin.setUsername(original);
	}
	
	@Test
	void testGetUsername() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    assertEquals("admin", admin.getUsername());
	}
	
	
	
	//password
	@Test
	void testSetPassword() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    String original = admin.getPassword();

	    admin.setPassword("9999");
	    assertEquals("9999", admin.getPassword());

	    admin.setPassword(original);
	}
	
	@Test
	void testGetPassword() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    assertEquals("1234", admin.getPassword());
	}
	
	
	@Test
	void testLogout() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    admin.login("admin", "1234");
	    assertTrue(admin.isLoggedIn());

	    admin.logout();
	    assertFalse(admin.isLoggedIn()); 
	}
	
	
	@Test
	void testAddSlot() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    String time = "18:00";

	    admin.removeSlots(time);   

	    admin.addSlots(time);

	    FileService file = new FileService();
	    List<String> slots = file.readFile("src/main/resources/appointments.txt");

	    boolean found = slots.stream().anyMatch(s -> s.equals(time + ",Available"));
	    assertTrue(found);

	    admin.removeSlots(time); 
	}
	
	@Test
	void testRemoveSlot() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    String time = "19:00";

	    admin.addSlots(time);

	    FileService file = new FileService();
	    List<String> slotsBefore = file.readFile("src/main/resources/appointments.txt");
	    assertTrue(slotsBefore.contains(time + ",Available"));

	    admin.removeSlots(time);
	    List<String> slotsAfter = file.readFile("src/main/resources/appointments.txt");
	    assertFalse(slotsAfter.contains(time + ",Available"));
	}
	
	
	@Test
	void testModifySlot() {
	    AdminServices admin = new AdminServices("admin", "1234");
	    String time = "20:00";

	    admin.addSlots(time);

	    admin.modifySlots(time, "Booked");

	    FileService file = new FileService();
	    List<String> slots = file.readFile("src/main/resources/appointments.txt");
	    assertTrue(slots.contains(time + ",Booked"));
	    assertFalse(slots.contains(time + ",Available"));

	    admin.removeSlots(time);
	}
	
	
	
	@Test
	void testViewSlots() {
	    AdminServices admin = new AdminServices("admin", "1234");

	    String time = "21:00";
	    admin.addSlots(time);

	    assertDoesNotThrow(() -> admin.viewSlots());

	    admin.removeSlots(time);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
