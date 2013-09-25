package server;

import java.rmi.RemoteException;

public interface ClientInt {
	void receive (String s) throws RemoteException;
}
