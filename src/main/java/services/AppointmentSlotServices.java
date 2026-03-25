package services;
import models.AppointmentSlot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AppointmentSlotServices extends AppointmentSlot{

	public AppointmentSlotServices(String time, boolean isAvailable) {
		super(time, isAvailable);
	}

	@Override
	public boolean getAvailable() {
		return isAvailable;
		}

	@Override
	public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
	}

	@Override
	public void setTime(String time) {
		this.time =time;
		
	}

	@Override
	public String getTime() {
		return  time;
	}

	@Override
	public List<AppointmentSlot> viewAvailableSlots(List<AppointmentSlot> slots) {
		 List<AppointmentSlot> availableSlots = new ArrayList<>();

		    try {
		        // قراءة الملف من resources
		        InputStream inputStream = getClass().getClassLoader()
		                .getResourceAsStream("appointments.txt"); 
		        
		        if (inputStream == null) {
		            System.out.println("appointments.txt not found");
		            return availableSlots;
		        }

		        
		        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		        String line;

		        while ((line = reader.readLine()) != null) {
		        	 if(line.startsWith("Time")) continue;
		        	 
		            String[] parts = line.split(",");

		            if (parts.length < 2) continue;  

		            String time = parts[0].trim();
		            boolean available = parts[1].trim().equalsIgnoreCase("Available");

		            if (available) {
		                availableSlots.add(new AppointmentSlotServices(time,true ));
		                
		            }
		        }

		        reader.close();

		    } catch (Exception e) {
		        System.out.println("Error reading appointments file: " + e.getMessage());
		    }

		    return availableSlots;
	}

	
	
	

}








