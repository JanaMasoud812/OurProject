package controller;
import models.Admin;
import java.util.*;


public class AdminServices extends Admin {
	private boolean loggedIn = false;
	private FileService fileservice = new FileService();
	
	public AdminServices (String username, String password) {
		super(username, password);
	}

	
	@Override
	public String login(String username, String password) {
	
	List<String> admins = fileservice.readFile("admins.txt");
	 for (String line : admins) {
		  String[] parts = line.split(",");
		  if (parts[0].equals(username) && parts[1].equals(password)) {
			  loggedIn = true;
			  return "Success";	  
		  }
		 
		 
	 }
	 
	 return "Failed";
	}
	
	@Override
	public void logout() {
		
		loggedIn = false;
		
	}
	
	
	@Override
	public void viewSlots() {
		List<String> slots = fileservice.readFile("appointments.txt");
		for (String slot : slots) {
			System.out.println(slot);
		}
		
	}
	
	
	@Override
	public void addSlots(String time) {
		fileservice.appendFile("appointments.txt", time+ ",Available");
		
	}
	
	@Override
	public void removeSlots(String time) {
		
		List<String> slots = fileservice.readFile("appointments.txt");
		List<String> updated = new ArrayList<>();
		
		for (String slot : slots) {
			
			if (!slot.startsWith(time)) {
				updated.add(slot);
			}
		}
		
	fileservice.writeFile("appointments.txt", updated);
		
	}
	
	
	@Override
	public void modifySlots(String time, String status) {
		  List<String> slots = fileservice.readFile("appointments.txt");
	        List<String> updated = new ArrayList<>();

	        for (String slot : slots) {

	            String[] parts = slot.split(",");

	            if (parts[0].equals(time)) {
	                updated.add(time + "," + status);
	            } else {
	                updated.add(slot);
	            }
	        }

	        fileservice.writeFile("appointments.txt", updated);
	    }
		
		
		
	
	@Override
	public void setUsername(String username) {
		

		    List<String> admins = fileservice.readFile("admins.txt");
		    List<String> updated = new ArrayList<>();

		    for (String line : admins) {
		        String[] parts = line.split(",");

		        if (parts[0].equals(super.username)) {
		            updated.add(username + "," + parts[1]);
		        } else {
		            updated.add(line);
		        }
		    }

		    fileservice.writeFile("admins.txt", updated);
		    super.username = username;
		
	}
	
	
	@Override
	public void setPassword(String password) {

	    List<String> admins = fileservice.readFile("admins.txt");
	    List<String> updated = new ArrayList<>();

	    for (String line : admins) {

	        String[] parts = line.split(",");

	        if (parts[0].equals(super.username)) {

	            updated.add(parts[0] + "," + password);

	        } else {

	            updated.add(line);
	        }
	    }

	    fileservice.writeFile("admins.txt", updated);

	    super.password = password;
	}
	
	@Override
	public String getUsername() {
		return super.username;
		
	}
	
@Override
public String getPassword() {
	return super.password;
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
