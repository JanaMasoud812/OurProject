
package services;

import models.*;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    public BookingService getBookingService() {
        return  bookingService;
    }
    
    
    private List<String> readAdmins() {
        return fileservice.readFile("src/main/resources/admins.txt");
    }

    private void writeAdmins(List<String> data) {
        fileservice.writeFile("src/main/resources/admins.txt", data);
    }

    @Override
    public String login(String username, String password) {

        List<String> admins = fileservice.readFile("src/main/resources/admins.txt");

        for (String line : admins) {
            String[] parts = line.split(",");

            boolean validUser = parts[0].equals(username);
            boolean validPass = parts[1].equals(password);

            if (validUser && validPass) {
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
    public void addSlots(String date, String time) {
        fileservice.appendFile("src/main/resources/appointments.txt", date + "," + time + ",Available,30,0,5");
    }
    
    @Override
    public void removeSlots(String date, String time) {

        List<String> slots = fileservice.readFile("src/main/resources/appointments.txt");
        List<String> updated = new ArrayList<>();

        for (String slot : slots) {

            if (slot.startsWith("Time") || slot.startsWith("Date")) {
                updated.add(slot);
                continue;
            }

            String[] parts = slot.split(",");

            boolean isTarget = parts[0].equals(date) && parts[1].equals(time);

            if (!isTarget) {
                updated.add(slot);
            }
        }

        fileservice.writeFile("src/main/resources/appointments.txt", updated);
    }
    
    @Override
    public void modifySlots(String date, String time, String status) {

        List<String> slots = fileservice.readFile("src/main/resources/appointments.txt");
        List<String> updated = new ArrayList<>();

        for (String slot : slots) {

            if (slot.startsWith("Time") || slot.startsWith("Date")) {
                updated.add(slot);
                continue;
            }

            String[] parts = slot.split(",");

            if (parts[0].equals(date) && parts[1].equals(time)) {
                updated.add(parts[0] + "," + parts[1] + "," + status + "," +
                            parts[3] + "," + parts[4] + "," + parts[5]);
            } else {
                updated.add(slot);
            }
        }

        fileservice.writeFile("src/main/resources/appointments.txt", updated);
    }
    
    @Override
    public void setUsername(String username) {
        List<String> admins = readAdmins();
        List<String> updated = new ArrayList<>();

        for (String line : admins) {
            String[] parts = line.split(",");

            if (parts[0].equals(this.username)) {
                updated.add(username + "," + parts[1] + "," + parts[2] + "," + parts[3]);
            } else {
                updated.add(line);
            }
        }

        writeAdmins(updated);
        this.username = username;
    }
    @Override
    public void setPassword(String password) {
        List<String> admins = readAdmins();
        List<String> updated = new ArrayList<>();

        for (String line : admins) {
            String[] parts = line.split(",");

            if (parts[0].equals(this.username)) {
                updated.add(parts[0] + "," + password + "," + parts[2] + "," + parts[3]);
            } else {
                updated.add(line);
            }
        }

        writeAdmins(updated);
        this.password = password;
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
        List<String> admins = readAdmins();
        List<String> updated = new ArrayList<>();

        for (String line : admins) {
            String[] parts = line.split(",");

            if (parts[0].equals(this.username)) {
                updated.add(parts[0] + "," + parts[1] + "," + email + "," + parts[3]);
            } else {
                updated.add(line);
            }
        }

        writeAdmins(updated);
        this.email = email;
    }

    @Override
    public String getEmail() {
        return super.email;
    }

    @Override
    public void setId(int id) {
        List<String> admins = readAdmins();
        List<String> updated = new ArrayList<>();

        for (String line : admins) {
            String[] parts = line.split(",");

            if (parts[0].equals(this.username)) {
                updated.add(parts[0] + "," + parts[1] + "," + parts[2] + "," + id);
            } else {
                updated.add(line);
            }
        }

        writeAdmins(updated);
        this.id = id;
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
    public String modifyUserBooking(Booking booking, String newDate, String newTime) {
    	return bookingService.modifyBooking(booking, newDate, newTime, LocalDateTime.of(LocalDate.now(), LocalTime.of(9,0)));
    	
    }
    
    
    public void adminCancelBooking(Booking booking, int role) {
        if (role != 6) {
            throw new RuntimeException("Unauthorized: Only admins can cancel bookings.");
        }
        bookingService.cancelBooking(booking);
    }

    public String adminModifyBooking(Booking oldBooking, String newDate, String newTime, int role) {
        if (role != 6) {
            throw new RuntimeException("Unauthorized: Only admins can modify bookings.");
        }
        return bookingService.modifyBooking(oldBooking, newDate, newTime, LocalDateTime.of(LocalDate.now(), LocalTime.of(9,0))); 
    }
    
    
    
    
    
    
}

