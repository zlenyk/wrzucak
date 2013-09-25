package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements Runnable,ClientInt, server.ClientInt {
   private static final long serialVersionUID = 1L;
   private SerwerInt server;
   private String name;
   private server.ClientInt friend;
   private static final String LIST = "LIST";
   private static final String QUIT = "QUIT";
   private static final String HELLO = "Hello ";
   public Client(SerwerInt cs, String name) throws RemoteException {
       this.name = name;
       this.server = cs;
       server.connect(name, this);
   }

   public synchronized void update(String name, String s) throws RemoteException {
       if (! this.name.equals(name)) {
           System.out.println(name + ": " + s);
       }
   }

   public void run() {
       Scanner in=new Scanner(System.in);
       String msg;

       while(true) {
           try {
               msg=in.nextLine();
               msg = msg.trim();
               if (QUIT.equals(msg)) {
                   server.disconnect(this);
                   in.close();
                   System.exit(0);
               } else if (LIST.equals(msg)){
                   server.list(this);
               } else if (msg.startsWith(HELLO) && msg.contains(",")) {
                   String s[] = msg.substring(0, msg.indexOf(",")).split(" ");
                   String user = s[s.length-1].trim();
                   friend = server.lookup(user);
                   if (null != friend) {
                       friend.update(name, msg);
                   } else {
                       server.broadcast(name, msg);
                   }
               } else if (! "".equals(msg)) {
                   server.broadcast(name, msg);
               }
           } catch(Exception e) {
               e.printStackTrace();
           }
       }
   }

   public static void main(String[] args) {
       if (3 != args.length) {
           System.out.println("Usage: java Client <server_ip> <server_port> <user_name>");
           System.out.println("Example: java Client 127.0.0.1 2001 user1");
           return;
       }
       String host = args[0];
       int port = Integer.parseInt(args[1]);
       String name = args[2];

       try {
           Registry registry = LocateRegistry.getRegistry(host, port);
           SerwerInt server = (SerwerInt) registry.lookup("Serwer");
           Thread t = new Thread(new Client(server, name));
           t.start();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   public String getName() {
       return name;
   }
}