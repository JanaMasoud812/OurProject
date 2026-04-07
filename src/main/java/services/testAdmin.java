package services;

import models.*;
import java.time.LocalTime;
import java.util.List;

public class testAdmin {

	public static void main(String[] args) {
		 // 1️⃣ إنشاء خدمة الحجز
       /* BookingService bookingService = new BookingService(new MockNotificationService());

        Booking booking1 = new Booking("fawziaorade@gmail.com", "11:30", "Confirmed", 30, 1);
        Booking booking2 = new Booking("fawzia@gmail.com", "13:00", "Confirmed", 30, 1);

        // 3️⃣ إنشاء مسؤول (Admin)
        AdminServices admin = new AdminServices("admin", "1234", "admin@email.com", 6);

        System.out.println("=== Before Operations ===");
        System.out.println("Appointments:");
        admin.viewSlots();
        System.out.println("Bookings:");
        for (String line : new FileServices().readFile("src/main/resources/booking.txt")) {
            System.out.println(line);
        }

        // 4️⃣ تجربة إلغاء حجز المستخدم بواسطة Admin
        System.out.println("\n--- Admin cancels booking1 ---");
        admin.adminCancelBooking(booking1, 6);
        System.out.println("Booking1 Status after cancel: " + booking1.getStatus());

        // 5️⃣ تجربة تعديل حجز المستخدم بواسطة Admin
        System.out.println("\n--- Admin modifies booking2 ---");
        String result = admin.adminModifyBooking(booking2, "12:30", 6);
        System.out.println("Admin modify result: " + result);
        System.out.println("Booking2 Time after modification: " + booking2.getTime());

        // 6️⃣ محاولة تعديل/إلغاء بدون صلاحية
        try {
            admin.adminCancelBooking(booking2, 1);
        } catch (RuntimeException e) {
            System.out.println("Unauthorized Cancel Attempt: " + e.getMessage());
        }
        try {
            admin.adminModifyBooking(booking2, "13:30", 1);
        } catch (RuntimeException e) {
            System.out.println("Unauthorized Modify Attempt: " + e.getMessage());
        }

        // 7️⃣ عرض الملفات بعد العمليات
        System.out.println("\n=== Appointments after operations ===");
        admin.viewSlots();

        System.out.println("\n=== Bookings after operations ===");
        for (String line : new FileServices().readFile("src/main/resources/booking.txt")) {
            System.out.println(line);
        }*/

	}

}
