import org.junit.jupiter.api.Test;

import controller.AppointmentSlotService;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentSlotTest {
	
	 @Test
	    void testConstructorAndGetters() {
	        AppointmentSlotService slot = new AppointmentSlotService("10:00", true);
	        assertEquals("10:00", slot.getTime());
	        assertTrue(slot.getAvailable());
            System.out.println(slot.getAvailable());               
           


	        AppointmentSlotService slot2 = new AppointmentSlotService("15:30", false);
	        assertEquals("15:30", slot2.getTime());
	        assertFalse(slot2.getAvailable());
            System.out.println(slot.getAvailable());              


	        
	    }
	
	 @Test
	    void testSetTimeNormal() {
	        AppointmentSlotService slot = new AppointmentSlotService("10:00", true);
	        slot.setTime("11:30");
	        assertEquals("11:30", slot.getTime());
            System.out.println(slot.getAvailable());     

	    }
	
	 @Test
	    void testSetTimeEmptyString() {
	        AppointmentSlotService slot = new AppointmentSlotService("10:00", true);
	        slot.setTime("");
	        assertEquals("", slot.getTime());
            System.out.println(slot.getAvailable());       


	    }

	 @Test
	    void testSetTimeNull() {
	        AppointmentSlotService slot = new AppointmentSlotService("10:00", true);
	        slot.setTime(null);
	        assertNull(slot.getTime());
            System.out.println(slot.getTime());

	    }
	 
	 @Test
	    void testSetAvailableFalse() {
	        AppointmentSlotService slot = new AppointmentSlotService("10:00", true);
	        slot.setAvailable(false);
	        assertFalse(slot.getAvailable());
            System.out.println(slot.getAvailable());

	    }

	    @Test
	    void testSetAvailableTrueAfterFalse() {
	        AppointmentSlotService slot = new AppointmentSlotService("10:00", false);
	        slot.setAvailable(true);
	        assertTrue(slot.getAvailable());
            System.out.println(slot.getAvailable());

	    }

	    @Test
	    void testSetAvailableFalseAfterTrue() {
	        AppointmentSlotService slot = new AppointmentSlotService("10:00", true);
	        slot.setAvailable(false);
	        assertFalse(slot.getAvailable());
            System.out.println(slot.getAvailable());

	    }
	

}
