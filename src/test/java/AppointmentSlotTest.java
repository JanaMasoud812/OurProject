import org.junit.jupiter.api.Test;

import services.AppointmentSlotServices;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentSlotTest {
	
	 @Test
	    void testConstructorAndGetters() {
	        AppointmentSlotServices slot = new AppointmentSlotServices("10:00", true);
	        assertEquals("10:00", slot.getTime());
	        assertTrue(slot.getAvailable());
            System.out.println(slot.getAvailable());               
           


	        AppointmentSlotServices slot2 = new AppointmentSlotServices("15:30", false);
	        assertEquals("15:30", slot2.getTime());
	        assertFalse(slot2.getAvailable());
            System.out.println(slot.getAvailable());              


	        
	    }
	
	 @Test
	    void testSetTimeNormal() {
	        AppointmentSlotServices slot = new AppointmentSlotServices("10:00", true);
	        slot.setTime("11:30");
	        assertEquals("11:30", slot.getTime());
            System.out.println(slot.getAvailable());     

	    }
	
	 @Test
	    void testSetTimeEmptyString() {
	        AppointmentSlotServices slot = new AppointmentSlotServices("10:00", true);
	        slot.setTime("");
	        assertEquals("", slot.getTime());
            System.out.println(slot.getAvailable());       


	    }

	 @Test
	    void testSetTimeNull() {
	        AppointmentSlotServices slot = new AppointmentSlotServices("10:00", true);
	        slot.setTime(null);
	        assertNull(slot.getTime());
            System.out.println(slot.getTime());

	    }
	 
	 @Test
	    void testSetAvailableFalse() {
	        AppointmentSlotServices slot = new AppointmentSlotServices("10:00", true);
	        slot.setAvailable(false);
	        assertFalse(slot.getAvailable());
            System.out.println(slot.getAvailable());

	    }

	    @Test
	    void testSetAvailableTrueAfterFalse() {
	        AppointmentSlotServices slot = new AppointmentSlotServices("10:00", false);
	        slot.setAvailable(true);
	        assertTrue(slot.getAvailable());
            System.out.println(slot.getAvailable());

	    }

	    @Test
	    void testSetAvailableFalseAfterTrue() {
	        AppointmentSlotServices slot = new AppointmentSlotServices("10:00", true);
	        slot.setAvailable(false);
	        assertFalse(slot.getAvailable());
            System.out.println(slot.getAvailable());

	    }
	

}
