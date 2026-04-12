package services;
import io.github.cdimascio.dotenv.Dotenv;
import models.Booking;

public class TestNotification {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String emailUsername = dotenv.get("EMAIL_USERNAME");
        String emailPassword = dotenv.get("EMAIL_PASSWORD");

        EmailService emailService = new EmailService(emailUsername, emailPassword);

        BookingService bookingService = new BookingService(emailService);
        Booking booking = new Booking(
                "fawzia@gmail.com",
                "2026-05-03",
                "12:30",
                "Pending",
                30,  
                1
        );

        String result = bookingService.bookAppointment(booking);
        System.out.println(result);

        if ("Booking Success".equals(result)) {
        } else {
            System.out.println("Cannot cancel: Booking was not successful.");
        }
        
       // bookingService.cancelBooking(booking);
       // System.out.println("Booking cancelled.");
    }
}
