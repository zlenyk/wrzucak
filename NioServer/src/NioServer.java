import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public class NioServer implements Runnable{
	ServerSocket  socket;
	ByteBuffer std;
	File folder;
	public static int getPort(){
		return 12345;
	}
	
	public NioServer(){
		try {
			folder = new File("fileHolder");
			socket = new ServerSocket(getPort());
			System.out.println("Serwer wystartowa�");
			std = ByteBuffer.allocate(2048);
		} catch (IOException e) {
			System.out.println("B��d przy zak�adaniu gniazda.");
			System.exit(-1);
		}
	}
	private String makeStringFromBuffer(ByteBuffer buffer){
		StringBuffer stringBuffer = new StringBuffer();
		Charset charset = Charset.forName("UTF-8");
		stringBuffer.append(charset.decode(buffer));
		return stringBuffer.toString();
	}
	void wyslijListe(){}
	void wyslijPlik(int number){}
	
	private int getFileNumber(String data)
	{
		return Integer.parseInt(data.substring(4));
	}
	
	public void run(){
		while(true){
			try {
				Socket client = socket.accept();
				System.out.println("Jest klient.");
				std.allocate(8192);
				BufferedInputStream inFromClient = new BufferedInputStream(client.getInputStream());
				while(true)
				{
					int b = inFromClient.read();
					if(b == -1) break;
					std.put((byte) b);
					String data = makeStringFromBuffer(std);
					if(data == "List") wyslijListe();
					else if(data == "Exit") break;
					else wyslijPlik(getFileNumber(data));
				}
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	public static void main(String[] args){
		new Thread(new NioServer()).start();
	}
}
