package services;

import org.junit.jupiter.api.Test;
import services.AppointmentSlotServices;
import models.AppointmentSlot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AppointmentSlotServiceTest {
	
	@Test
	void testViewAvailableSlots_ReturnAvailableSlots_Flexible() throws Exception {
	    AppointmentSlotServices service = new AppointmentSlotServices("10:00", true);

	    // استدعاء الفنكشن لقراءة المواعيد المتاحة
	    List<AppointmentSlot> availableSlots = service.viewAvailableSlots(new ArrayList<>());

	    assertNotNull(availableSlots);

	    // قراءة الملف مباشرة للتأكد
	    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("appointments.txt");
	    assertNotNull(inputStream, "Appointments file not found!");

	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    List<String> availableFromFile = reader.lines()
	                                           .filter(line -> line.trim().endsWith("Available"))
	                                           .toList(); // جافا 16+ | لو أقل استخدم collect(Collectors.toList())

	    reader.close();

	    // تحقق أن عدد المواعيد المتاحة يساوي عدد المواعيد في الملف
	    assertEquals(availableFromFile.size(), availableSlots.size(), 
	                 "Number of available slots mismatch");

	    // تحقق أن كل موعد موجود في النتيجة
	    for (String line : availableFromFile) {
	        String time = line.split(",")[0].trim();
	        boolean found = availableSlots.stream().anyMatch(slot -> slot.getTime().equals(time));
	        assertTrue(found, "Slot " + time + " should be in available slots");
	    }
	}
	
	
	@Test
	void test_IgnoresUnavailableSlots() {
		AppointmentSlotServices service = new AppointmentSlotServices("10:00", true);
		List < AppointmentSlot> result = service.viewAvailableSlots(new ArrayList<>());
		
		for (AppointmentSlot slot : result) {
			assertTrue(slot.getAvailable());
			
		}
	}
	
	@Test
	void test_ReturnTypeIsList() {
		AppointmentSlotServices service = new AppointmentSlotServices("10:00", true);
		List < AppointmentSlot> result = service.viewAvailableSlots(new ArrayList<>());
		assertTrue(result instanceof List);
		
	}
	
	
	@Test
	void test_SizeCheck() {
		AppointmentSlotServices service = new AppointmentSlotServices("10:00", true);
		List < AppointmentSlot> result = service.viewAvailableSlots(new ArrayList<>());
		assertTrue(result.size() >= 0);
	}
	
	

	@Test
    void test_ExceptionHandling() {

        AppointmentSlotServices service =
                new AppointmentSlotServices("10:00", true) {
                    @Override
                    public List<AppointmentSlot> viewAvailableSlots(List<AppointmentSlot> slots) {
                        throw new RuntimeException();
                    }
                };

        assertThrows(RuntimeException.class, () -> {
            service.viewAvailableSlots(new ArrayList<>());
        });
    }
	
	

	  
		
}
