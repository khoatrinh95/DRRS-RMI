package Authenticate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AuthenticationServer {
    public static void main(String args[]) {
        AuthenOperations authOperations;
        // start auth server
        try {
            Registry authRegistry = LocateRegistry.createRegistry(7000);
            authOperations = new AuthenOperations();
            authRegistry.bind("authentication", authOperations);
            System.out.println("Authentication server started on port 7000");
            System.out.println("Authentication server running...");
        } catch (RemoteException | AlreadyBoundException e) {
            System.out.println("Cannot start Authentication Server.\nMessage: " + e.getMessage());
        }
    }

}
