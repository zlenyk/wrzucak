package client;

import java.rmi.RemoteException;

public interface ClientInt {
	void update(String name, String s) throws RemoteException;
	String getName() throws RemoteException;
}