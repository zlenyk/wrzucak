package cont;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class MainHost implements Runnable {

	ServerSocketChannel channel;
	
	private static int getPort(){
		return 12345;
	}
	
	public MainHost(){
		try {
			channel = ServerSocketChannel.open();
			channel.socket().bind(new InetSocketAddress(getPort()));
			
			System.out.println("Serwer wystartowa�");
			
		} catch (IOException e) {
			System.out.println("B��d przy zak�adaniu gniazda.");
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
				new Thread(new ClientRequest(socket)).start();
				
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
