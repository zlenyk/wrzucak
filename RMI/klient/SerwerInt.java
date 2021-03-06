
import java.rmi.RemoteException;

public interface SerwerInt{
	void connect(String name,ClientInt c) throws RemoteException;
	void disconnect(ClientInt c) throws RemoteException;
	void broadcast(String name,String message) throws RemoteException;
	void list(ClientInt c) throws RemoteException;
	ClientInt lookup(String name) throws RemoteException;
}
