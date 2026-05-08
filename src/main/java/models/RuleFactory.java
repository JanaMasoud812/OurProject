package models;

import java.util.ArrayList;
import java.util.List;

public class RuleFactory {
	
	 // Prevent instantiation
    private RuleFactory() {
        
    }

    public static List<BookingRuleStrategy> getRules(AppointmentType type) {
        List<BookingRuleStrategy> rules = new ArrayList<>();

        if (type == null)
            return rules;

        switch (type) {
            case URGENT:
                rules.add(new UrgentRule());
                break;
            case FOLLOW_UP:
                rules.add(new FollowUpRule());
                break;
            case ASSESSMENT:
                rules.add(new AssessmentRule());
                break;
            case VIRTUAL:
                rules.add(new VirtualRule());
                break;
            case IN_PERSON:
                rules.add(new InPersonRule());
                break;
            case INDIVIDUAL:
                rules.add(new IndividualRule());
                break;
            case GROUP:
                rules.add(new GroupRule());
                break;
        }

        return rules;
    }
}