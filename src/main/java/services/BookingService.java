package services;
import models.*; //this
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
            return "Booking Failed: Duration exceeded";
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
	}
	
	
	
	
	
	
	//to this

}