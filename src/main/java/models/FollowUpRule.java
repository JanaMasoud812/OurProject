package models;

public class FollowUpRule implements BookingRuleStrategy {
    public String validate(Booking booking) {
        if (booking.getDuration() > 45)
            return "Follow-up: max duration is 45 min";
        if (booking.getParticipants() != 1)
            return "Follow-up: only 1 participant allowed";
        return null;
    }
}