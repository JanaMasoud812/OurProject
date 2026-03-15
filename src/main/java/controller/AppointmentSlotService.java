package controller;
import models.AppointmentSlot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AppointmentSlotService extends AppointmentSlot{

	public AppointmentSlotService(String time, boolean isAvailable) {
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

		        InputStream inputStream =
		                getClass().getClassLoader().getResourceAsStream("appointments.txt");

		        BufferedReader reader =
		                new BufferedReader(new InputStreamReader(inputStream));

		        String line;

		        while ((line = reader.readLine()) != null) {

		            String[] parts = line.split(",");

		            String time = parts[0];
		            boolean available = Boolean.parseBoolean(parts[1]);

		            if (available) {

		                availableSlots.add(new AppointmentSlotService(time, available));

		            }

		        }

		        reader.close();

		    } catch (Exception e) {

		        System.out.println("Error reading appointments file");

		    }

		    return availableSlots;
	}

	@Override
	public void bookSlot() {
		
	}

	@Override
	public void cancelSlot() {
		
	}
	
	

}













