package models;

public class InPersonRule implements BookingRuleStrategy {
    public String validate(Booking booking) {
        if (booking.getDuration() > 60)
            return "In-person: max duration is 60 min";
        if (booking.getParticipants() != 1)
            return "In-person: only 1 participant allowed";
        return null;
    }
}