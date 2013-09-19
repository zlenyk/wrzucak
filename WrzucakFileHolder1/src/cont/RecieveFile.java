package cont;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class RecieveFile implements Runnable{

	String sessionID;
	int length;
	String login;
	String name;
	ServerSocketChannel channel;
	ConnectionManager cm;
	private static int getPort(){
		return 12346;
	}
	public RecieveFile(String s,String l,int len,String n) {
		sessionID = s;
		login = l;
		length = len;
		name = n;
		try {
			channel = ServerSocketChannel.open();
			channel.socket().bind(new InetSocketAddress(getPort()));
			System.out.println("File serwer wystartowa³");
			
		} catch (IOException e) {
			System.out.println("B³¹d przy zak³adaniu file gniazda.");
			System.exit(-1);
		}
	}
	
	@Override
	public void run() {
		while(true){
			SocketChannel socket;
			try {
				socket = channel.accept();
				cm = new ConnectionManager(socket,length);
					
				System.out.println("Jest file.");
				
				String path = "users/" + login + "/"+ name;

				cm.downloadFile(path);
				
				break;
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;
		}
	}
}
