package Client;

import Authenticate.AuthenInterface;
import Business.Campus;
import Server.DRRSServer;
import Server.DRRSServerInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AdminClient {

    public static void main(String args[]) {
        AuthenInterface authInterface = null;
        DRRSServerInterface drrsServer = null;
        Scanner scan = new Scanner(System.in);
        Campus campus = null;
        // connect to authentication server
        try {
            Registry registry = LocateRegistry.getRegistry(7000);
            authInterface = (AuthenInterface) registry.lookup("authentication");

        } catch (RemoteException | NotBoundException e) {
            System.out.println("Cannot connect to the authentication server");
        }

        AdminOperations adminOperations = new AdminOperations(authInterface);

        // authenticate admin
        do {
            try {
                campus = adminOperations.authenticateAdmin();
            } catch (RemoteException e) {
                System.out.println("Cannot authenticate admin");
            }

            if (campus == null)
                System.out.println("cannot find campus associated with this admin");
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

        String adminID = adminOperations.getAdminId();
        // Ask actions from admin
        adminOperations = new AdminOperations(drrsServer, adminID);
        try {
            adminOperations.askActionFromAdmin(campus);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    } //end main
}
