package Server;

import java.util.ArrayList;

public class testDriver {
    public static void main(String[] args) {
        try{
//            DRRSServer drrsServer = new DRRSServer("WESTMOUNT");
//            System.out.println("OK");
//            String message = drrsServer.createRoom("001", "08/21/2021", "07:00", "11:00");
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("OK");
//            message = drrsServer.createRoom("002", "08/21/2021", "07:00", "11:00");
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("OK");
//            message = drrsServer.createRoom("001", "08/22/2021", "07:00", "11:00");
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("OK");
//            message = drrsServer.createRoom("001", "08/21/2021", "03:00", "06:00");
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("OK");
//            message = drrsServer.createRoom("001", "08/21/2021", "11:00", "15:00");
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("OK");
//            message = drrsServer.getAvailableTimeSlot("08/21/2021");
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Creation same time");
//            message = drrsServer.createRoom("001", "08/22/2021", "07:00", "11:00");
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Creation overlap time");
//            message = drrsServer.createRoom("001", "08/21/2021", "10:00", "12:00");
//            System.out.println(message);
//            System.out.println();
//
//
//
//            System.out.println("OK");
//            message = drrsServer.bookRoom("StudentA", "WESTMOUNT", "001", "08/21/2021",1);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Booking already booked slot");
//            message = drrsServer.bookRoom("StudentA", "WESTMOUNT", "001", "08/21/2021",1);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("OK");
//            message = drrsServer.bookRoom("StudentA", "WESTMOUNT", "002", "08/21/2021",1);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Booking non-exist date");
//            message = drrsServer.bookRoom("StudentA", "WESTMOUNT", "002", "08/23/2021",1);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Booking non-exist room");
//            message = drrsServer.bookRoom("StudentA", "WESTMOUNT", "004", "08/21/2021",1);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Booking non-exist timeslot");
//            message = drrsServer.bookRoom("StudentA", "WESTMOUNT", "001", "08/21/2021",10);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Booking non-exist campus");
//            message = drrsServer.bookRoom("StudentA", "WESTMOUNT4", "001", "08/21/2021",1);
//            System.out.println(message);
//            System.out.println();
//
//
//            System.out.println("OK");
//            message = drrsServer.getAvailableTimeSlot("08/21/2021");
//            System.out.println(message);
//            System.out.println();
//
//
//            ArrayList<Integer> inputindex = new ArrayList<>();
//            inputindex.add(1);
//            inputindex.add(2);
//            inputindex.add(4);
//
//            System.out.println("OK");
//            message = drrsServer.deleteRoom("001", "08/21/2021", inputindex);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Deletion non-exist timeslot");
//            message = drrsServer.deleteRoom("001", "08/21/2021", inputindex);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Deletion non-exist room");
//            message = drrsServer.deleteRoom("003", "08/21/2021", inputindex);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("**NOPE** Deletion non-exist date");
//            message = drrsServer.deleteRoom("001", "08/25/2021", inputindex);
//            System.out.println(message);
//            System.out.println();
//
//            System.out.println("OK");
//            message = drrsServer.getAvailableTimeSlot("08/21/2021");
//            System.out.println(message);
//            System.out.println();
        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }
    }
}
