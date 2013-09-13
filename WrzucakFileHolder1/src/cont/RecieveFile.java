package cont;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;

public class RecieveFile implements Runnable{

	String sessionID;
	ServerSocketChannel channel;
	Map <String,String> sessionSet;
	
	private static int getPort(){
		return 12346;
	}
	public RecieveFile(String s,Map <String,String> session) {
		sessionSet = session;
		sessionID = s;
		try {
			channel = ServerSocketChannel.open();
			channel.socket().bind(new InetSocketAddress(getPort()));
			
			System.out.println("File serwer wystartowa�");
			
		} catch (IOException e) {
			System.out.println("B��d przy zak�adaniu file gniazda.");
			System.exit(-1);
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				SocketChannel socket = channel.accept();
				System.out.println("Jest file.");
				new Thread(new ClientRequest(socket,sessionSet)).start();
				
			}catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}