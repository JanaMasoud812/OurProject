package models;

public class MaxDurationRule implements BookingRuleStrategy {
    private final int maxDuration;

    public MaxDurationRule(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    @Override
    public String validate(Booking booking) {
        if (booking.getDuration() > maxDuration)
            return "Booking Failed: Duration Exceeded";
        return null;
    }
}