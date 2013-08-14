package inter;

import java.rmi.Remote;
public interface SerwerInt extends Remote {
	void register();
	void write(String message);
	void privateMessage(String message,String user);
	String showList();
	void close();
}
