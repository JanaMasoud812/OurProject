package models;

public class IndividualRule implements BookingRuleStrategy {
    public String validate(Booking booking) {
        if (booking.getDuration() > 60)
            return "Individual: max duration is 60 min";
        if (booking.getParticipants() != 1)
            return "Individual: only 1 participant allowed";
        return null;
    }
}