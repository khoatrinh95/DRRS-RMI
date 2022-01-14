package Authenticate;

import Business.TimeSlot;
import Server.DRRSServer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class UdpOperations implements Runnable{
    private Thread thread;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private String date;
    private List<DRRSServer> listOfServers;

    public UdpOperations(DatagramSocket socket, DatagramPacket packet, String date, List<DRRSServer> listOfServers) {
        this.socket = socket;
        this.packet = packet;
        this.date = date;
        this.listOfServers = listOfServers;
    }
    @Override
    public void run() {
        try {
            date = date.substring(0,10);
            String result = getAvailableTimeSlots(date);

            byte buf[] = null;

            buf = result.getBytes();

            // send the packet
            DatagramPacket sendingPacket = new DatagramPacket(buf, buf.length, this.packet.getAddress(), this.packet.getPort());
            socket.send(sendingPacket);
        } catch (IOException e){
            System.out.println("Error sending packet back to client");
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, "Udp Process");
            thread.start();
        }
    }

    public String getAvailableTimeSlots(String date) {
        String result ="";

        if (date.isBlank())
            return "Please enter valid date";

        for (DRRSServer server : DRRSServer.getServerList()){
            int count = 0;
            HashMap<String, List<TimeSlot>> roomMap = server.getDataMap().get(date);


            // if no room in this date
            if (roomMap==null) {
                continue;
            }
            else {
                for (Map.Entry<String,List<TimeSlot>> room : roomMap.entrySet()){
                    List<TimeSlot> timeSlotList = room.getValue();
                    for (TimeSlot timeslot : timeSlotList){
                        if (timeslot.getBookedByID()==null){
                            count++;
                        }
                    }
                }
            }
            result += server.getCampusName() + ": " + count + "\n";
        }
        return result;
    }
}
