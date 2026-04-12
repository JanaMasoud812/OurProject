package models;

public class GroupRule implements BookingRuleStrategy{
	
	 public String validate(Booking booking) {
	        if (booking.getDuration() > 120)
	            return "Group: max duration is 120 min";
	        if (booking.getParticipants() > 10)
	            return "Group: max participants is 10";
	        return null;
	    }

}
