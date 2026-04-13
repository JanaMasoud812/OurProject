package services;

import java.util.ArrayList;
import java.util.List;

public class MockNotificationService implements NotificationService {
	
	    private List<String> sentMessages = new ArrayList<>();

	    @Override
	    public void sendNotification(String username, String message) {

	        String notification = username + " - " + message;

	        sentMessages.add(notification);  

	        System.out.println("Mock notification sent: " + notification);
	    }
		
		
		public List<String> getSentMessages(){
			return sentMessages;
			
		}
		
		public void clearMessaagges() {
			sentMessages.clear();
		}
	    
	    
}
