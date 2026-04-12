package models;

public class UrgentRule implements BookingRuleStrategy {
    public String validate(Booking booking) {
        if (booking.getDuration() > 30)
            return "Urgent: max duration is 30 min";
        if (booking.getParticipants() != 1)
            return "Urgent: only 1 participant allowed";
        return null;
    }
}