import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.AdminServices;

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


	//@Test
	//void test() {
		//fail("Not yet implemented");
	//}

	@Test
	void testLoginSuccess() {
		AdminServices admin = new AdminServices("admin","1234");
		String result = admin.login("admin", "1234");
		assertEquals("Success", result);
	}
 
}
