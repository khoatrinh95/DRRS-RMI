package Client;

import Authenticate.AuthenInterface;
import Business.Campus;
import Server.DRRSServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class StudentOperations {
    private AuthenInterface authenInterface;
    private DRRSServerInterface drrsServerInterface;
    private String studentID;
    private Logger log;

    public StudentOperations(AuthenInterface authenInterface) {
        this.authenInterface = authenInterface;
    }

    public StudentOperations(DRRSServerInterface drrsServerInterface, String studentID) {
        this.drrsServerInterface = drrsServerInterface;
        this.studentID = studentID;
        startLogger(studentID);
    }

    public Campus authenticateStudent(String id) throws RemoteException {
        return authenInterface.getCampus(id, false);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public void askActionFromStudent(String studentId, Campus campus) throws RemoteException{
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        boolean done = false;
        while (!done) {
            System.out.println("Please enter the option you would like to do: ");
            System.out.println("\t1. Book room");
            System.out.println("\t2. Cancel booking");
            System.out.println("\t3. Exit");
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Invalid input");
            }
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println(bookRoom(sc, studentId));
                    break;
                case 2:
                    System.out.println(cancelBooking(sc, studentId));
                    break;
                case 3:
                    done = true;
                    sc.close();
                    return;
                default:
                    continue;
            }
        }
    }

    private String bookRoom(Scanner scanner, String studentId) throws RemoteException{
        if (drrsServerInterface.overLimit(studentId)){
            return "You already have 3 bookings this week. You can't book another slot.";
        }
        String campus = "";
        String room = "";
        String date = "";
        System.out.println("Please enter the campus name (Westmount, Kirkland, Dorval): ");
        campus = scanner.nextLine();

        if (!(campus.equalsIgnoreCase("Westmount") || campus.equalsIgnoreCase("Kirkland") || campus.equalsIgnoreCase("Dorval")))
            return "Invalid Campus name";
        System.out.println("Please enter the room number: ");
        room = scanner.nextLine();
        System.out.println("Please enter the date (MM/DD/YYYY): ");
        date = scanner.nextLine();

        List<String> listOfTimeSlots = drrsServerInterface.getTimeSlots(date, room, campus);

        if (listOfTimeSlots != null) {
            System.out.println("Here are the time slots in the room: ");
            for (String s : listOfTimeSlots){
                System.out.println(s);
            }
            System.out.println("Please enter the time slot number you would like to book: ");
            int timeSlotInt = scanner.nextInt();
            scanner.nextLine();
            log.info("Room booked: " + room +
                    "\n\tCampus: " + campus +
                    "\n\tDate: " + date
            );
            return drrsServerInterface.bookRoom(studentId, campus, room, date, timeSlotInt);
        }

        return "There is no time slots available in the date and room selected";
    }

    private String cancelBooking(Scanner scanner, String studentId) throws RemoteException{
        String bookingId = null;
        System.out.println("Please enter your booking ID: ");
        bookingId = scanner.nextLine();
        log.info("Booking cancellation: " +
                "\n\tBooking ID: " + bookingId
        );
        return drrsServerInterface.cancelBooking(bookingId, studentId);
    }

    private void startLogger(String studentId) {
        try {
            log =  Logger.getLogger(studentId);
            log.setUseParentHandlers(false);
            String loggerFileName = "src/UserLogger/Student/"+studentId+".log";
            FileHandler fileHandler = new FileHandler(loggerFileName, true);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
