package cont;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;


public class MainHost implements Runnable {

	ServerSocketChannel channel;
	Map <String,String> sessionSet;
	
	private static int getPort(){
		return 12347;
	}
	
	public MainHost(){
		sessionSet = new HashMap<String,String>();
		try {
			channel = ServerSocketChannel.open();
			channel.socket().bind(new InetSocketAddress(getPort()));
			
			System.out.println("Serwer wystartowa³");
			
		} catch (IOException e) {
			System.out.println("B³¹d przy zak³adaniu gniazda.");
			System.exit(-1);
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				SocketChannel socket = channel.accept();
				System.out.println("Jest klient.");
				new Thread(new ClientRequest(socket,sessionSet)).start();
				
			}catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	public static void main(String[] args) {
		new Thread(new MainHost()).start();
	}

}
