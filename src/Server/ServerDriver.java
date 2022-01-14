package Server;

import Authenticate.UdpOperations;
import Server.DRRSServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class ServerDriver {
    public static void main(String[] args) throws IOException {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String portNum, registryURL;
        Registry registry = null;
        DRRSServer exportedObj1 = null, exportedObj2 = null, exportedObj3 = null;
        // Starting the RMI server
        try{
            portNum = "10";
            int RMIPortNum = Integer.parseInt(portNum);
            registry = startRegistry(RMIPortNum);
            exportedObj1 = new DRRSServer("WESTMOUNT");
            registryURL = "rmi://localhost:" + portNum + "/drrs";
            registry.rebind(registryURL, exportedObj1);
            System.out.println("Hello Server Westmount ready.");

            portNum = "20";
            RMIPortNum = Integer.parseInt(portNum);
            registry = startRegistry(RMIPortNum);
            exportedObj2 = new DRRSServer("KIRKLAND");
            registryURL = "rmi://localhost:" + portNum + "/drrs";
            registry.rebind(registryURL, exportedObj2);
            System.out.println("Hello Server Kirkland ready.");

            portNum = "30";
            RMIPortNum = Integer.parseInt(portNum);
            registry = startRegistry(RMIPortNum);
            exportedObj3 = new DRRSServer("DORVAL");
            registryURL = "rmi://localhost:" + portNum + "/drrs";
            registry.rebind(registryURL, exportedObj3);
            System.out.println("Hello Server Dorval ready.");

        }
        catch (Exception re) {
            System.out.println("Exception in DRRSServer.main: " + re); } // end catch

        // Starting UDP server
        try{
            DatagramSocket ds = new DatagramSocket(8000);
            byte[] receive = new byte[65535];
            DatagramPacket dpReceive = null;
            System.out.println("The UDP server started on port 8000");
            System.out.println("UDP server running...");
            while(true) {
                dpReceive = new DatagramPacket(receive, receive.length);
                try {
                    ds.receive(dpReceive);
                    String date = new String(receive, StandardCharsets.UTF_8);
                    List<DRRSServer> listOfServers = DRRSServer.getServerList();
                    UdpOperations udpOperations = new UdpOperations(ds, dpReceive, date, listOfServers);
                    udpOperations.start();
                } catch (IOException e) {
                    System.out.println("Error receiving packet.\nMessage: " + e.getMessage());
                }
            }
        } catch (SocketException e){
            System.out.println("Error starting UDP server. \nMessage: " + e.getMessage());
        }

    }

    private static Registry startRegistry(int RMIPortNum) throws RemoteException {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list( );  // This call will throw an exception
            // if the registry does not already exist
        }
        catch (RemoteException e) {
            // No valid registry at that port.
            registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }
        return registry;
    }
}
