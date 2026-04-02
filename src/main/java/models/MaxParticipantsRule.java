package models;

public class MaxParticipantsRule implements BookingRuleStrategy{
	private final int maxParticipants;
	
	public MaxParticipantsRule(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	@Override
	public String validate(Booking booking) {
		if (booking.isFull(maxParticipants))
            return "Booking Failed: Max participants exceeded";
        return null;
	}
	
	
	
}
