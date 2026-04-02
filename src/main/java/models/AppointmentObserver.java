package models;

public interface AppointmentObserver {
	void onBookingConfirmed(Booking booking);
    void onBookingCancelled(Booking booking);
}
