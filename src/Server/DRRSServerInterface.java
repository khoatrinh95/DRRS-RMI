package Server;

import Business.TimeSlot;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DRRSServerInterface extends Remote {
    public String createRoom(String room_Number, String date, String beginTime, String endTime) throws RemoteException;
    public String deleteRoom(String room_Number, String date, List<String> listOfTimeSlotsIndexFromInputString) throws RemoteException;
    public String bookRoom(String studentID, String campusName, String roomNumber, String date, int timeslot) throws RemoteException;
    public boolean overLimit (String studentID) throws RemoteException;
    List<String> getTimeSlots (String date, String room, String campus) throws RemoteException;
    public String cancelBooking(String bookingID, String studentID) throws RemoteException;
    public void authorize(String studentID) throws RemoteException;
}
