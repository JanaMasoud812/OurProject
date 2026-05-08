package presentation;

import models.*;
import services.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu { 

    private static Dotenv dotenv = Dotenv.load();
    private static String emailUsername = dotenv.get("EMAIL_USERNAME");
    private static String emailPassword = dotenv.get("EMAIL_PASSWORD");

    private static final String CHOICE = "Choice: ";
    private static final String ENTER_DATE = "Enter Date (yyyy-MM-dd): ";

    private static EmailService emailService = new EmailService(emailUsername, emailPassword);
    private static AdminServices adminService = new AdminServices("", "", "", 0);
    private static BookingService bookingService = new BookingService(emailService);
    private static AppointmentSlotServices slotService =
            new AppointmentSlotServices("2026-05-01", "10:00", true);

    private static Scanner scanner = new Scanner(System.in);

    static {
        adminService.setBookingService(bookingService);
    }

    public static void main(String[] args) {
        runMainLoop();
    }

    // ================= MAIN LOOP =================
    private static void runMainLoop() {
        boolean running = true;

        while (running) {
            printMainMenu();
            String choice = scanner.nextLine();
            running = handleMainChoice(choice);
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== Scheduling System Main Menu ===");
        System.out.println("1. Login as Admin");
        System.out.println("2. Continue as User");
        System.out.println("3. Exit");
        System.out.print(CHOICE);
    }

    private static boolean handleMainChoice(String choice) {
        switch (choice) {
            case "1":
                adminFlow();
                return true;

            case "2":
                userFlow();
                return true;

            case "3":
                System.out.println("Exiting...");
                return false;

            default:
                System.out.println("Invalid choice. Try again.");
                return true;
        }
    }

    // ================= ADMIN FLOW =================
    private static void adminFlow() {
        System.out.print("Username: ");
        String user = scanner.nextLine();

        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (!adminService.login(user, pass).equals("Success")) {
            System.out.println("Login Failed.");
            return;
        }

        System.out.println("Login Successful!");

        while (adminService.isLoggedIn()) {
            printAdminMenu();
            handleAdminChoice(scanner.nextLine());
        }
    }

    private static void printAdminMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. View All Slots");
        System.out.println("2. Modify Appointment Status");
        System.out.println("3. Remove Slot");
        System.out.println("4. Add Slot");
        System.out.println("5. Logout");
        System.out.print(CHOICE);
    }

    private static void handleAdminChoice(String choice) {
        switch (choice) {
            case "1":
                adminService.viewSlots();
                break;

            case "2":
                modifySlot();
                break;

            case "3":
                removeSlot();
                break;

            case "4":
                addSlot();
                break;

            case "5":
                adminService.logout();
                System.out.println("Logged out.");
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void modifySlot() {
        System.out.print(ENTER_DATE);
        String d = scanner.nextLine();

        System.out.print("Enter Time (HH:mm): ");
        String t = scanner.nextLine();

        System.out.print("Enter New Status (Available/Unavailable): ");
        String s = scanner.nextLine();

        adminService.modifySlots(d, t, s);
        System.out.println("Status updated.");
    }

    private static void removeSlot() {
        System.out.print(ENTER_DATE);
        String d = scanner.nextLine();

        System.out.print("Enter Time (HH:mm): ");
        String t = scanner.nextLine();

        adminService.removeSlots(d, t);
        System.out.println("Slot removed.");
    }

    private static void addSlot() {
        System.out.print(ENTER_DATE);
        String d = scanner.nextLine();

        System.out.print("Enter Time (HH:mm): ");
        String t = scanner.nextLine();

        adminService.addSlots(d, t);
        System.out.println("Slot added.");
    }

    // ================= USER FLOW =================
    private static void userFlow() {
        boolean back = false;

        while (!back) {
            printUserMenu();
            String choice = scanner.nextLine();
            back = handleUserChoice(choice);
        }
    }

    private static void printUserMenu() {
        System.out.println("\n--- User Menu ---");
        System.out.println("1. View Available Slots");
        System.out.println("2. Book Appointment");
        System.out.println("3. Modify Appointment");
        System.out.println("4. Cancel Appointment");
        System.out.println("5. Back to Main Menu");
        System.out.print(CHOICE);
    }

    private static boolean handleUserChoice(String choice) {
        switch (choice) {
            case "1":
                viewAvailableSlots();
                return false;

            case "2":
                bookAppointment();
                return false;

            case "3":
                modifyAppointment();
                return false;

            case "4":
                cancelAppointment();
                return false;

            case "5":
                return true;

            default:
                System.out.println("Invalid choice.");
                return false;
        }
    }

    // ================= REST =================
    private static void viewAvailableSlots() {
        List<AppointmentSlot> available = slotService.viewAvailableSlots(null);

        if (available.isEmpty()) {
            System.out.println("No available slots found.");
            return;
        }

        System.out.println("Available Slots:");
        for (AppointmentSlot s : available) {
            System.out.println("Date: " + s.getDate() + " | Time: " + s.getTime());
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    private static void bookAppointment() {
        System.out.print("Enter Your Email/Username: ");
        String username = scanner.nextLine();

        if (!isValidEmail(username)) {
            System.out.println("Result: Booking Failed: Invalid Email Format");
            return;
        }

        System.out.print(ENTER_DATE);
        String date = scanner.nextLine();

        System.out.print("Enter Time (HH:mm): ");
        String time = scanner.nextLine();

        System.out.println("Select Appointment Type:");
        System.out.println("1. Urgent | 2. Follow-up | 3. Virtual | 4. In-person | 5. Group | 6. Individual");

        AppointmentType type = mapType(scanner.nextLine());

        System.out.print("Enter Number of Participants: ");
        int participants = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Expected Duration (min): ");
        int duration = Integer.parseInt(scanner.nextLine());

        Booking booking = new Booking(username, date, time, "Pending", duration, participants, type);

        String result = bookingService.bookAppointment(booking);
        System.out.println("Result: " + result);
    }

    private static void modifyAppointment() {
        System.out.print("Enter Your Email/Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Current Date (yyyy-MM-dd): ");
        String oldDate = scanner.nextLine();

        System.out.print("Enter Current Time (HH:mm): ");
        String oldTime = scanner.nextLine();

        if (!isFuture(oldDate, oldTime)) {
            System.out.println("Cannot modify past appointments.");
            return;
        }

        System.out.print("Enter New Date (yyyy-MM-dd): ");
        String newDate = scanner.nextLine();

        System.out.print("Enter New Time (HH:mm): ");
        String newTime = scanner.nextLine();

        Booking oldBooking = new Booking(username, oldDate, oldTime, "Confirmed", 30, 1);

        System.out.println("Result: " +
                bookingService.modifyBooking(oldBooking, newDate, newTime));
    }

    private static void cancelAppointment() {
        System.out.print("Enter Your Email/Username: ");
        String username = scanner.nextLine();

        System.out.print(ENTER_DATE);
        String date = scanner.nextLine();

        System.out.print("Enter Time (HH:mm): ");
        String time = scanner.nextLine();

        if (!isFuture(date, time)) {
            System.out.println("Cannot cancel past appointments.");
            return;
        }

        Booking booking = new Booking(username, date, time, "Confirmed", 30, 1);
        bookingService.cancelBooking(booking);

        System.out.println("Appointment cancelled if it existed.");
    }

    private static AppointmentType mapType(String choice) {
        switch (choice) {
            case "1": return AppointmentType.URGENT;
            case "2": return AppointmentType.FOLLOW_UP;
            case "3": return AppointmentType.VIRTUAL;
            case "4": return AppointmentType.IN_PERSON;
            case "5": return AppointmentType.GROUP;
            case "6": return AppointmentType.INDIVIDUAL;
            default: return AppointmentType.INDIVIDUAL;
        }
    }

    private static boolean isFuture(String date, String time) {
        try {
            LocalDate d = LocalDate.parse(date);
            LocalTime t = LocalTime.parse(time);

            if (d.isAfter(LocalDate.now())) return true;
            if (d.isEqual(LocalDate.now()) && t.isAfter(LocalTime.now())) return true;

        } catch (DateTimeParseException e) {
            return false;
        }
        return false;
    }
}