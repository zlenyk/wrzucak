import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Host {
	private static int getPort(){
		return 12345;
	}
	
	public Host(){}
	
	public static void Main(String[] args){
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(getPort());
			System.out.println("Serwer wystartowa�");
			
		} catch (IOException e) {
			System.out.println("B��d przy zak�adaniu gniazda.");
			System.exit(-1);
		}
		Socket client = null;
		while(true){
			try {
				client = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			new Thread(new Client(map)).start();
		}
		
	}
}
