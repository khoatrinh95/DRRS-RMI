package Authenticate;

import Business.Campus;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthenInterface extends Remote {
    Campus getCampus(String adminId, boolean isAdmin) throws RemoteException;
    String sayHello() throws  RemoteException;
}
