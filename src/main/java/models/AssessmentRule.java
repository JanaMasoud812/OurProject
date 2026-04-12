package models;

public class AssessmentRule implements BookingRuleStrategy {
    public String validate(Booking booking) {
        if (booking.getDuration() > 60)
            return "Assessment: max duration is 60 min";
        if (booking.getParticipants() != 1)
            return "Assessment: only 1 participant allowed";
        return null;
    }
}