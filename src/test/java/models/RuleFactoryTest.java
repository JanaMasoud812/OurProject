package models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RuleFactoryTest {

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
	    void shouldReturnGroupRuleWhenTypeIsGroup() {
	        List<BookingRuleStrategy> rules = RuleFactory.getRules(AppointmentType.GROUP);
	        assertEquals(1, rules.size());
	        assertTrue(rules.get(0) instanceof GroupRule);
	    }
	 
	 
	 @Test
	    void shouldReturnIndividualRuleWhenTypeIsIndividual() {
	        List<BookingRuleStrategy> rules =RuleFactory.getRules(AppointmentType.INDIVIDUAL);

	        assertEquals(1, rules.size());
	        
	        assertTrue(rules.get(0) instanceof IndividualRule);
	    }
	 
	 @Test
	    void shouldReturnUrgentRuleWhenTypeIsUrgent() {
	        List<BookingRuleStrategy> rules =RuleFactory.getRules(AppointmentType.URGENT);

	        assertEquals(1, rules.size());
	        assertTrue(rules.get(0) instanceof UrgentRule);
	    }
	 
	 
	 @Test
	    void shouldReturnEmptyListWhenTypeIsNull() {
	        List<BookingRuleStrategy> rules = RuleFactory.getRules(null);
	        
	        assertTrue(rules.isEmpty());
	    }
	 
	 
	 
	 

}
