package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SerwerInt extends Remote{
	void register(ClientInt c) throws RemoteException;
	void broadcast(String s) throws RemoteException;
}
