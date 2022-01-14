package Client;

import Authenticate.AuthenInterface;
import Business.Campus;
import Server.DRRSServerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class StudentClient {
    public static void main(String args[]) {
        String studentId = null;
        AuthenInterface authInterface = null;
        DRRSServerInterface drrsServer = null;
        Scanner scanner = new Scanner(System.in);
        Campus campus = null;

        // Ask if they want to see timeslots or book
        // if timeslot -> establish udp here
        boolean signIn = false;
        while(!signIn){
            System.out.println("What would you like to do?");
            System.out.println("\t1. Get available time slots");
            System.out.println("\t2. Sign in");
            System.out.println("\t3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    // establish udp
                    try{
                        getAvailableTimeSlots(scanner);
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    signIn = true;
                    break;
                case 3:
                    System.out.println("System shutting down...");
                    System.exit(0);
                default:
                    System.out.println("Invalid input. System shut down...");
            }
        }

        // connect to authentication server
        try {
            Registry registry = LocateRegistry.getRegistry(7000);
            authInterface = (AuthenInterface) registry.lookup("authentication");

        } catch (RemoteException | NotBoundException e) {
            System.out.println("Cannot connect to the authentication server");
        }

        StudentOperations studentOperations = new StudentOperations(authInterface);

        // authenticate student
        do {
            try {
                System.out.println("Please enter your student ID: ");
                studentId = scanner.nextLine();
                campus = studentOperations.authenticateStudent(studentId);
            } catch (RemoteException e) {
                System.out.println("Cannot authenticate student");
            }

            if (campus == null)
                System.out.println("cannot find campus associated with this student");
            else {
                try {
                    String port = Integer.toString(campus.getPort());
                    String address = "rmi://localhost:" + port +"/drrs";
                    Registry registry = LocateRegistry.getRegistry(campus.getPort());
                    drrsServer = (DRRSServerInterface) registry.lookup(address);
                } catch (RemoteException | NotBoundException e) {
                    System.out.println("Cannot connect to campus");
                    return;
                }
            }
        }
        while (campus == null);

        // Ask actions from student
        studentOperations = new StudentOperations(drrsServer, studentId);
        try {
            studentOperations.askActionFromStudent(studentId, campus);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    } //end main

    private static void getAvailableTimeSlots(Scanner scanner) throws IOException {
        DatagramSocket ds = new DatagramSocket();

        InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = null;

        System.out.println("Please enter the date (MM/DD/YYYY): ");
        String input = scanner.nextLine();

        buf = input.getBytes();

        // send the packet
        DatagramPacket dpSend = new DatagramPacket(buf, buf.length, ip, 8000);
        ds.send(dpSend);

        // receive the packet
        byte[] incoming = new byte[65535];
        DatagramPacket incomingPacket = new DatagramPacket(incoming, incoming.length);
        ds.receive(incomingPacket);

        StringBuilder result = data(incoming);

        if (result.isEmpty())
            System.out.println("No record found on " + input + "\n");
        else {
            System.out.println("Here are the records of available time slots on "+ input +": ");
            System.out.println(result);
        }

    }

    private static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
