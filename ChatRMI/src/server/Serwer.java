package server;

import java.io.PrintStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Serwer implements SerwerInt, Remote {

   private static final long serialVersionUID = 1L;
   private List<ClientInt> myclients;
   private List<String> clientNames;
   private static final String name = "server";

   public Serwer() throws RemoteException {
       myclients = new ArrayList();
       clientNames = new ArrayList();
   }

   public synchronized void disconnect(ClientInt c) throws RemoteException {
       myclients.remove(c);
       clientNames.remove(c.getName());
       writeLog(c.getName() + " disconnected at {0}");
       for(ClientInt client: myclients) {
           client.update(name, c.getName() + " has left.");
       }
   }

   public synchronized void list(ClientInt c) throws RemoteException {
       c.update(name, "Active users: " + clientNames.toString());
   }

   public synchronized ClientInt lookup(String name) throws RemoteException {
       ClientInt c = null;
       int index = clientNames.indexOf(name);
       if (-1 != index) {
           c = myclients.get(index);
       }
       return c;
   }

   public synchronized void connect(String n, ClientInt c) throws RemoteException {
       for(ClientInt client: myclients) {
           client.update(name, n + " is joining now...");
       }
       clientNames.add(n);
       myclients.add(c);
       int count = myclients.size();
       StringBuffer wcmMsg = new StringBuffer("Welcome ").append(n).append(", ");
       wcmMsg.append("There ").append((1 == count)? "is " : "are ").append(
           count).append((1 == count)? " user: " : " users: ");
       wcmMsg.append(clientNames.toString());
       c.update(name, wcmMsg.toString());
       writeLog(n + " connected at {0}");
   }

   public synchronized void broadcast(String name, String s) throws RemoteException {
       for(ClientInt client: myclients) {
           client.update(name, s);
       }
       writeLog("{0}: " + name + ": " + s);
   }

   public static void main (String[] args) {
       if (1 != args.length) {
           System.out.println("Usage: java Serwer <server_port>");
           System.out.println("Example: java Serwer 2001");
           return;
       }
       int port = Integer.parseInt(args[0]);

       try {
           System.setOut(new PrintStream("server.log"));
           Serwer server = new Serwer();
           LocateRegistry.getRegistry(port).bind("Serwer",
               UnicastRemoteObject.exportObject(server, 0));
           writeLog("Server started at {0}, waiting for connections...");
       } catch(Exception e) {
           e.printStackTrace();
       }
   }

   private static void writeLog(String log) {
       System.out.println(MessageFormat.format(log, new Date().toString()));
   }
}