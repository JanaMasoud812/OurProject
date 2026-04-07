
package services;

import models.*;

import java.time.LocalTime;
import java.util.*;
import services.*;


public class AdminServices extends Admin {
    private boolean loggedIn = false;
    private FileServices fileservice = new FileServices();
    private BookingService bookingService;
    
   
    
    public AdminServices (String username, String password, String email, int id) {
        super(username, password, email, id);
        this.bookingService = new BookingService(new MockNotificationService());
        }
    //////
    public BookingService getBookingService() {
        return  bookingService;
    }
    
    
    

    @Override
    public String login(String username, String password) {
    
        List<String> admins = fileservice.readFile("src/main/resources/admins.txt");
        for (String line : admins) {
            String[] parts = line.split(",");
            if (parts[0].equals(username) && parts[1].equals(password)) { 
                loggedIn = true;
                this.email = parts[2];
                this.id = Integer.parseInt(parts[3]);

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
        List<String> slots = fileservice.readFile("src/main/resources/appointments.txt");
        for (String slot : slots) {
            System.out.println(slot);
        }
    }
    
    @Override
    public void addSlots(String time) {
        fileservice.appendFile("src/main/resources/appointments.txt", time+ ",Available,30,0,5");
    }
    
    @Override
    public void removeSlots(String time) {

        List<String> slots = fileservice.readFile("src/main/resources/appointments.txt");
        List<String> updated = new ArrayList<>();

        for (String slot : slots) {
            String[] parts = slot.split(",");
            if (!parts[0].equals(time)) {
                updated.add(slot);
            }
        }

        fileservice.writeFile("src/main/resources/appointments.txt", updated);
    }
    
    @Override
    public void modifySlots(String time, String status) {
        List<String> slots = fileservice.readFile("src/main/resources/appointments.txt");
        List<String> updated = new ArrayList<>();

        for (String slot : slots) {
        	
        	if(slot.startsWith("Time")) {
        		updated.add(slot);
                continue;
        			}
        	
            String[] parts = slot.split(",");
            
            String slotTime = parts[0];
            String duration = parts[2];
            String current = parts[3];
            String max = parts[4]; 
            
            if (parts[0].equals(time)) {
                updated.add(slotTime + "," + status + "," + duration + "," + current + "," + max);
            } else {
                updated.add(slot);
            }
        }

        fileservice.writeFile("src/main/resources/appointments.txt", updated);
    }
    
    @Override
    public void setUsername(String username) {

        List<String> admins = fileservice.readFile("src/main/resources/admins.txt");
        List<String> updated = new ArrayList<>();

        for (String line : admins) {
            String[] parts = line.split(",");

            if (parts[0].equals(super.username)) {
                updated.add(username + "," + parts[1] + "," + parts[2] + "," + parts[3]);
            } else {
                updated.add(line);
            }
        }

        fileservice.writeFile("src/main/resources/admins.txt", updated);
        super.username = username;
    }
    
    @Override
    public void setPassword(String password) {

        List<String> admins = fileservice.readFile("src/main/resources/admins.txt");
        List<String> updated = new ArrayList<>();

        for (String line : admins) {
            String[] parts = line.split(",");

            if (parts[0].equals(super.username)) {
                updated.add(parts[0] + "," + password + "," + parts[2] + "," + parts[3]);
            } else {
                updated.add(line);
            }
        }

        fileservice.writeFile("src/main/resources/admins.txt", updated);
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
    
    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }


    @Override
    public void setEmail(String email) {

        List<String> admins = fileservice.readFile("src/main/resources/admins.txt");
        List<String> updated = new ArrayList<>();

        for (String line : admins) {
            String[] parts = line.split(",");

            if (parts[0].equals(super.username)) {
                updated.add(parts[0] + "," + parts[1] + "," + email + "," + parts[3]);
            } else {
                updated.add(line);
            }
        }

        fileservice.writeFile("src/main/resources/admins.txt", updated);
        super.email = email;
    }

    @Override
    public String getEmail() {
        return super.email;
    }

    @Override
    public void setId(int id) {

        List<String> admins = fileservice.readFile("src/main/resources/admins.txt");
        List<String> updated = new ArrayList<>();

        for (String line : admins) {
            String[] parts = line.split(",");

            if (parts[0].equals(super.username)) {
                updated.add(parts[0] + "," + parts[1] + "," + parts[2] + "," + id);
            } else {
                updated.add(line);
            }
        }

        fileservice.writeFile("src/main/resources/admins.txt", updated);
        super.id = id;
    }

    @Override
    public int getId() {
        return super.id;
    }
    
    @Override
    public void cancelUserBooking(Booking booking) {
    	bookingService.cancelBooking(booking);
    }
    
    
    @Override
    public String modifyUserBooking(Booking booking, String newTime) {
    	return bookingService.modifyBooking(booking, newTime, LocalTime.of(9,0));
    	
    }
    
    
    public void adminCancelBooking(Booking booking, int role) {
        if (role != 6) {
            throw new RuntimeException("Unauthorized: Only admins can cancel bookings.");
        }
        bookingService.cancelBooking(booking);
    }

    public String adminModifyBooking(Booking oldBooking, String newTime, int role) {
        if (role != 6) {
            throw new RuntimeException("Unauthorized: Only admins can modify bookings.");
        }
        return bookingService.modifyBooking(oldBooking, newTime, LocalTime.of(9,0)); 
    }
    
    
    
    
    
    
}

