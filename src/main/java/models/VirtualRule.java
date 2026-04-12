package models;

public class VirtualRule implements BookingRuleStrategy {
    public String validate(Booking booking) {
        if (booking.getDuration() > 60)
            return "Virtual: max duration is 60 min";
        if (booking.getParticipants() != 1)
            return "Virtual: only 1 participant allowed";
        return null; 
    } 
}