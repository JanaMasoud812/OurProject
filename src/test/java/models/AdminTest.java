package models;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import services.*;


class AdminTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// No global setup required for this test class
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		 // No global teardown required for this test class
	}

	@BeforeEach
	void setUp() throws Exception {
		 // No setup required before each test
	}

	@AfterEach
	void tearDown() throws Exception {
		 // No cleanup required after each test
	}



	@Test
	void testConstructor() {
	    AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);
		assertEquals("admin", admin.getUsername());
		assertEquals("1234", admin.getPassword());
		assertEquals("admin@email.com", admin.getEmail());
		assertEquals(6, admin.getId());
		assertFalse(admin.isLoggedIn());
		
		
		
		
		
	}
}
	
	
	
	