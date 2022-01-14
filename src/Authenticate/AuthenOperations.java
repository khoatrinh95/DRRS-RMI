package Authenticate;

import Business.Campus;
import Business.TimeSlot;
import Server.DRRSServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AuthenOperations extends UnicastRemoteObject implements AuthenInterface {
    private List<Campus> campuses = new ArrayList<>();
    private Logger log;

    public AuthenOperations() throws RemoteException {
        super();
        Campus campusWestmount = new Campus("Westmount", 10, "WST");
        Campus campusKirkland = new Campus("Kirkland", 20, "KKL");
        Campus campusDorval = new Campus("Dorval", 30, "DVL");
        campuses.add(campusWestmount);
        campuses.add(campusDorval);
        campuses.add(campusKirkland);

        startLogger();

        log.info("Authentication Server created");
    }

    @Override
    public Campus getCampus(String id, boolean isAdmin) throws RemoteException{
        if (id.length()!= 8)
            return null;
        String campus = id.substring(0,3);
        String role = id.substring(3,4);
        String number = id.substring(4);
        if (!(campus.equalsIgnoreCase("wst") || campus.equalsIgnoreCase("kkl") || campus.equalsIgnoreCase("dvl")))
            return null;
        if (!(isInteger(number)))
            return null;

        if (isAdmin){
            if (!(role.equalsIgnoreCase("a")))
                return null;
        } else {
            if (!(role.equalsIgnoreCase("s")))
                return null;
        }

        for (Campus c : campuses){
            if (c.getCode().equalsIgnoreCase(campus))
                return c;
        }
        return null;
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

    public String sayHello(){
        return "Hello From Authentication Server";
    }

    private void startLogger() {
        try {
            log =  Logger.getLogger("Authentication Server");
            log.setUseParentHandlers(false);
            String loggerFileName = "src/Logger/AUTHENTICATION.log";
            FileHandler fileHandler = new FileHandler(loggerFileName, true);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
