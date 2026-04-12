package services;
import models.*; //this

import java.time.LocalTime;
import java.util.*;  // and this


public class BookingService {
	//from this
	private FileServices fileService = new FileServices();
    private final List<BookingRuleStrategy> rules;
    private final NotificationService notificationService;
    private final List<AppointmentObserver> observers = new ArrayList<>();
    public BookingService(NotificationService notificationService) {
        this.notificationService = notificationService;
        this.rules = new ArrayList<>();
        this.rules.add(new MaxParticipantsRule(5));
        this.rules.add(new MaxDurationRule(60));
       
        this.addObserver(new NotificationObserver(notificationService));
    }
    
    private List<AppointmentSlot> appointments;
    

    public void setMockAppointments(List<AppointmentSlot> mockAppointments) {
        this.appointments = mockAppointments;
    }
    
	
    public void addObserver(AppointmentObserver observer) {
        observers.add(observer);
    }

    private void notifyConfirmed(Booking booking) {
        for (AppointmentObserver o : observers) {
            o.onBookingConfirmed(booking);
        }
    }

    private void notifyCancelled(Booking booking) {
        for (AppointmentObserver o : observers) {
            o.onBookingCancelled(booking);
        }
    }
	
	public String bookAppointment(Booking booking) {
		
		for (BookingRuleStrategy rule : rules) {
		    String error = rule.validate(booking);
		    if (error != null) return error;
		}
		
		if (booking.getType() == AppointmentType.URGENT) {
		    System.out.println("Applying URGENT rules");
		}
		List<String> slots = fileService.readFile("src/main/resources/appointments.txt");
		List<String> updated = new ArrayList<>();
		
		boolean booked = false;
		
		for (String slot : slots) {

    if (slot.startsWith("Time")) { 
        updated.add(slot);
        continue;
    }

    String[] parts = slot.split(",");

    String time = parts[0].trim();
    String status = parts[1].trim();
    int duration = Integer.parseInt(parts[2].trim());
    int current = Integer.parseInt(parts[3].trim());
    int max = Integer.parseInt(parts[4].trim());

    if (time.equals(booking.getTime()) && status.equals("Available")) {

        if (booking.getDuration() > duration) {
            return "Booking Failed: Duration Exceeded";
        }

        if (current + booking.getParticipants() > max) {
            return "Booking Failed: Max participants exceeded";
        }

        current += booking.getParticipants();

        if (current == max) {
            status = "Unavailable";
        }

        updated.add(time + "," + status + "," + duration + "," + current + "," + max);

        booking.confirmBooking();
        booked = true;
        
        notifyConfirmed(booking);

    } else {
        updated.add(slot);
    }
}
		
		if(!booked) {
			return "Booking Failed: Slot not available";
		}
		
		fileService.writeFile("src/main/resources/appointments.txt", updated);
		
		
		
	   fileService.appendFile("src/main/resources/booking.txt",
               booking.getUsername() + "," +
               booking.getTime() + "," +
               booking.getStatus() + "," +
               booking.getDuration() + "," +
               booking.getParticipants());
	   
	   return "Booking Success";
	
	}
	
	public void cancelBooking(Booking booking) {
	    booking.cancelBooking();
	    notifyCancelled(booking);

	    List<String> slots = fileService.readFile("src/main/resources/appointments.txt");
	    List<String> updated = new ArrayList<>();

	    for (String slot : slots) {

	        if (slot.startsWith("Time")) {
	            updated.add(slot);
	            continue;
	        }

	        String[] parts = slot.split(",");

	        String time = parts[0].trim();
	        String status = parts[1].trim();
	        int duration = Integer.parseInt(parts[2].trim());
	        int current = Integer.parseInt(parts[3].trim());
	        int max = Integer.parseInt(parts[4].trim());

	        if (time.equals(booking.getTime())) {

	            current -= booking.getParticipants();
	            if (current < 0) current = 0;

	            status = "Available";

	            updated.add(time + "," + status + "," + duration + "," + current + "," + max);
	        } else {
	            updated.add(slot);
	        }
	    }

	    fileService.writeFile("src/main/resources/appointments.txt", updated);
	    List<String> bookings = fileService.readFile("src/main/resources/booking.txt");
	    List<String> updatedBookings = new ArrayList<>();
	    for (String line : bookings) {
	        if (!line.startsWith(booking.getUsername() + "," + booking.getTime())) {
	            updatedBookings.add(line);
	        }
	    }
	    fileService.writeFile("src/main/resources/booking.txt", updatedBookings);
	}
	/*
	public String modifyBooking(Booking oldBooking, String newTime) {
		//future check
		LocalTime now = LocalTime.now();
		LocalTime appointmentTime = LocalTime.parse(oldBooking.getTime());
		
		if(appointmentTime.isBefore(now)) {
			return "Modify Failed: Cannot modify past appointments";
		}
		
		
		
		
		cancelBooking(oldBooking);
		
		Booking newBooking = new Booking(
				
				oldBooking.getUsername(),
				newTime,
				"Pending",
				oldBooking.getDuration(),
				oldBooking.getParticipants()	
				);
		
		
		
		return bookAppointment(newBooking);
			
	}
	
	*/
	
	public String modifyBooking(Booking oldBooking, String newTime, LocalTime currentTime) {
	    if(LocalTime.parse(oldBooking.getTime()).isBefore(currentTime)) {
	        return "Modify Failed: Cannot modify past appointments";
	    }
	    cancelBooking(oldBooking);
	    Booking newBooking = new Booking(
	        oldBooking.getUsername(),
	        newTime,
	        "Pending",
	        oldBooking.getDuration(),
	        oldBooking.getParticipants()
	    );
	    return bookAppointment(newBooking);
	}

	public String modifyBooking(Booking oldBooking, String newTime) {
	    return modifyBooking(oldBooking, newTime, LocalTime.now());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//to this

}