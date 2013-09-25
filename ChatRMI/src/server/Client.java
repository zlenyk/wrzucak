package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInt, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6703197188949060569L;
	private SerwerInt mycs;

	public Client(SerwerInt cs) throws RemoteException {
		mycs = cs;
		mycs.register(this);
	}

	public synchronized void receive(String s) throws RemoteException {
		System.out.println("Message: " + s);
	}

	public void run() {
		Scanner in = new Scanner(System.in);
		String msg;

		while (true) {
			try {
				msg = in.nextLine();
				mycs.broadcast(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		String url = "rmi:localhost/SerwerInt";
		try {
			SerwerInt cs = (SerwerInt) Naming.lookup(url);
			new Thread(new Client(cs)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
