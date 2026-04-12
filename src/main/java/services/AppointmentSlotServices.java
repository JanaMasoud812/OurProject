package services;
import models.AppointmentSlot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AppointmentSlotServices extends AppointmentSlot{

	public AppointmentSlotServices(String date, String time, boolean isAvailable) {
		super(date, time, isAvailable);
	}

	@Override
	public String getDate() { return date; }

	@Override
	public void setDate(String date) { this.date = date; }

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
		        	 if(line.startsWith("Time") || line.startsWith("Date")) continue;
		        	 
		            String[] parts = line.split(",");

		            if (parts.length < 3) continue;  

		            String date = parts[0].trim();
		            String time = parts[1].trim();
		            boolean available = parts[2].trim().equalsIgnoreCase("Available");

		            if (available) {
		                availableSlots.add(new AppointmentSlotServices(date, time,true ));
		                
		            }
		        }

		        reader.close();

		    } catch (Exception e) {
		        System.out.println("Error reading appointments file: " + e.getMessage());
		    }

		    return availableSlots;
	}

	
	
	

}








