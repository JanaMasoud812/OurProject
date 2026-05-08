package services;

import models.*;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The BookingService class is responsible for managing all booking operations
 * within the scheduling system.
 *
 * <p>It contains the core business logic for creating, cancelling, and modifying
 * appointments while ensuring that all bookings follow predefined validation rules.</p>
 *
 * <p>This service integrates multiple design patterns:</p>
 * <ul>
 *   <li><b>Strategy Pattern</b> for applying flexible booking validation rules.</li>
 *   <li><b>Observer Pattern</b> for notifying external components about booking events.</li>
 * </ul>
 *
 * <p>It also handles persistence through FileServices to store and retrieve
 * appointment and booking data from files.</p>
 */
public class BookingService {

    private FileServices fileService = new FileServices();
    private final List<BookingRuleStrategy> rules;
    private static final String AVAILABLE = "Available";
    private static final String UNAVAILABLE = "Unavailable";
    private final NotificationService notificationService;
    private final List<AppointmentObserver> observers = new ArrayList<>();
    private List<AppointmentSlot> appointments;
    
    /** * Constructs a BookingService with the required NotificationService. * * @param notificationService the service used to send notifications */

    public BookingService(NotificationService notificationService) {
        this.notificationService = notificationService;
        this.rules = new ArrayList<>();
        this.rules.add(new MaxParticipantsRule(5));
        this.rules.add(new MaxDurationRule(60));
        this.addObserver(new NotificationObserver(notificationService));
    }
    
    /** * Sets mock appointment data for testing purposes. * * <p>This method is mainly used in testing environments to inject * predefined appointment slots instead of reading from files.</p> * * @param mockAppointments the list of appointment slots to be used as test data */

    public void setMockAppointments(List<AppointmentSlot> mockAppointments) {
        this.appointments = mockAppointments;
    }
    
    /** * Adds a new observer to receive booking events. * * @param observer the observer to be registered */

    public void addObserver(AppointmentObserver observer) {
        observers.add(observer);
    }
    
    /** * Notifies all registered observers that a booking has been confirmed. * * @param booking the booking that was successfully confirmed */

    private void notifyConfirmed(Booking booking) {
        for (AppointmentObserver o : observers) {
            o.onBookingConfirmed(booking);
        }
    }
    
    
    /** * Notifies all registered observers that a booking has been cancelled. * * @param booking the booking that was cancelled */

    private void notifyCancelled(Booking booking) {
        for (AppointmentObserver o : observers) {
            o.onBookingCancelled(booking);
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return false;
        }

        return email.endsWith("@gmail.com") ||
               email.endsWith("@yahoo.com") ||
               email.endsWith("@hotmail.com");
    }

    /** * Attempts to create a new booking for an appointment slot. * * <p>The method performs multiple steps:</p> * <ul> * <li>Validates the booking using all defined rules.</li> * <li>Checks appointment availability.</li> * <li>Updates slot status and participant count.</li> * <li>Persists booking data to files.</li> * <li>Notifies observers upon successful booking.</li> * </ul> * * @param booking the booking request containing user and appointment details * @return a success message or failure reason */

    public String bookAppointment(Booking booking) {
        String validation = validateBooking(booking);
        if (validation != null) return validation;

        handleTypeLogic(booking);

        List<String> slots = loadSlots();

        return processSlots(booking, slots);
    }

   

    private String validateBooking(Booking booking) {
        if (!isValidEmail(booking.getUsername())) {
            return "Booking Failed: Invalid Email Format";
        }

        for (BookingRuleStrategy rule : rules) {
            String error = rule.validate(booking);
            if (error != null) return error;
        }

        return null;
    }

    private void handleTypeLogic(Booking booking) {
        if (booking.getType() == AppointmentType.URGENT) {
            System.out.println("URGENT rules applied");
            System.out.println("Applying URGENT rules");
        } else if (booking.getType() == AppointmentType.GROUP) {
            System.out.println("GROUP rules applied");
        }
    }

    private List<String> loadSlots() {
        if (appointments != null) {
            List<String> slots = new ArrayList<>();

            for (AppointmentSlot slot : appointments) {
                String status = slot.getAvailable() ? AVAILABLE : UNAVAILABLE;
                slots.add(slot.getDate() + "," +
                          slot.getTime() + "," +
                          status + ",60,0,5");
            }
            return slots;
        }

        return fileService.readFile("src/main/resources/appointments.txt");
    }

    private boolean isMatchingSlot(String date, String time, Booking booking, String status) {
        return date.equals(booking.getDate())
                && time.equals(booking.getTime())
                && status.equals(AVAILABLE);
    }

    private String processSlots(Booking booking, List<String> slots) {

        List<String> updated = new ArrayList<>();
        boolean booked = false;

        for (String slot : slots) {

            if (slot.startsWith("Time") || slot.startsWith("Date")) {
                updated.add(slot);
                continue;
            }

            String[] parts = slot.split(",");

            String date = parts[0].trim();
            String time = parts[1].trim();
            String status = parts[2].trim();
            int duration = Integer.parseInt(parts[3].trim());
            int current = Integer.parseInt(parts[4].trim());
            int max = Integer.parseInt(parts[5].trim());

            if (isMatchingSlot(date, time, booking, status)) {

                if (booking.getDuration() > duration) {
                    return "Booking Failed: Duration Exceeded";
                }

                if (current + booking.getParticipants() > max) {
                    return "Booking Failed: Max participants exceeded";
                }

                current += booking.getParticipants();

                if (current == max) {
                    status = UNAVAILABLE;
                }

                updated.add(date + "," + time + "," + status + "," + duration + "," + current + "," + max);

                booking.confirmBooking();
                booked = true;

                try {
                    notifyConfirmed(booking);
                } catch (Exception e) {
                    return "Booking Failed: Notification Error (" + e.getMessage() + ")";
                }

            } else {
                updated.add(slot);
            }
        }

        if (!booked) {
            return "Booking Failed: Slot not available";
        }

        fileService.writeFile("src/main/resources/appointments.txt", updated);

        fileService.appendFile("src/main/resources/booking.txt",
                booking.getUsername() + "," +
                booking.getDate() + "," +
                booking.getTime() + "," +
                booking.getStatus() + "," +
                booking.getDuration() + "," +
                booking.getParticipants());

        return "Booking Success";
    }

    /** * Cancels an existing booking and updates the system state. * * <p>This includes:</p> * <ul> * <li>Releasing reserved slots</li> * <li>Updating appointment availability</li> * <li>Removing booking from storage</li> * <li>Notifying observers about cancellation</li> * </ul> * * @param booking the booking to be cancelled */

    public void cancelBooking(Booking booking) {

        booking.cancelBooking();
        notifyCancelled(booking);

        List<String> slots = fileService.readFile("src/main/resources/appointments.txt");
        List<String> updated = new ArrayList<>();

        for (String slot : slots) {

            if (slot.startsWith("Time") || slot.startsWith("Date")) {
                updated.add(slot);
                continue;
            }

            String[] parts = slot.split(",");

            String date = parts[0].trim();
            String time = parts[1].trim();
            String status = parts[2].trim();
            int duration = Integer.parseInt(parts[3].trim());
            int current = Integer.parseInt(parts[4].trim());
            int max = Integer.parseInt(parts[5].trim());

            if (date.equals(booking.getDate()) && time.equals(booking.getTime())) {

                current -= booking.getParticipants();
                if (current < 0) current = 0;

                status = AVAILABLE;

                updated.add(date + "," + time + "," + status + "," + duration + "," + current + "," + max);
            } else {
                updated.add(slot);
            }
        }

        fileService.writeFile("src/main/resources/appointments.txt", updated);
    }

    /** * Modifies an existing booking by replacing it with a new one. * * <p>This method prevents modification of past appointments by comparing * the booking time with the current system time.</p> * * <p>If valid, the old booking is cancelled and a new booking is created.</p> * * @param oldBooking the existing booking * @param newDate the updated date * @param newTime the updated time * @param currentDateTime the current system date and time * @return a success or failure message */

    public String modifyBooking(Booking oldBooking, String newDate, String newTime,
                                LocalDateTime currentDateTime) {

        LocalDateTime appointmentDateTime =
                LocalDateTime.of(LocalDate.parse(oldBooking.getDate()),
                                 LocalTime.parse(oldBooking.getTime()));

        if (appointmentDateTime.isBefore(currentDateTime)) {
            return "Modify Failed: Cannot modify past appointments";
        }

        cancelBooking(oldBooking);

        Booking newBooking = new Booking(
                oldBooking.getUsername(),
                newDate,
                newTime,
                "Pending",
                oldBooking.getDuration(),
                oldBooking.getParticipants()
        );

        return bookAppointment(newBooking);
    }

    /** * Modifies an existing booking using the current system time. * * @param oldBooking the existing booking * @param newDate the updated date * @param newTime the updated time * @return a success or failure message */
    public String modifyBooking(Booking oldBooking, String newDate, String newTime) {
        return modifyBooking(oldBooking, newDate, newTime, LocalDateTime.now());
    }
}