package services;

import models.AppointmentObserver;
import models.Booking;

public class NotificationObserver implements AppointmentObserver {	
	  private final NotificationService notificationService;

	    public NotificationObserver(NotificationService notificationService) {
	        this.notificationService = notificationService;
	    }

	    @Override
	    public void onBookingConfirmed(Booking booking) {
	        notificationService.sendNotification(
	            booking.getUsername(),
	            "Your appointment at " + booking.getTime() + " is confirmed"
	        );
	    }

	    @Override
	    public void onBookingCancelled(Booking booking) {
	        notificationService.sendNotification(
	            booking.getUsername(),
	            "Your appointment at " + booking.getTime() + " has been cancelled"
	        );
	    }

}
