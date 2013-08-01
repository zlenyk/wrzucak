import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;


public class NioServer implements Runnable{
	ServerSocketChannel channel;
	ByteBuffer std;
	File fileHolder;
	public static int getPort(){
		return 12345;
	}
	
	public NioServer(){
		try {
			
			fileHolder = new File("fileHolder");
			fileHolder.mkdir();
			
			channel = ServerSocketChannel.open();
			channel.socket().bind(new InetSocketAddress(getPort()));
			
			System.out.println("Serwer wystartowa³");
			std = ByteBuffer.allocate(8192);
			
		} catch (IOException e) {
			System.out.println("B³¹d przy zak³adaniu gniazda.");
			System.exit(-1);
		}
	}
	private String makeStringFromBuffer(ByteBuffer buffer){
		StringBuffer stringBuffer = new StringBuffer();
		Charset charset = Charset.forName("UTF-8");
		stringBuffer.append(charset.decode(buffer));
		return stringBuffer.toString();
	}
	private void putStringIntoBuffer(String s){
		std.clear();
		std.put(s.getBytes());
	}
	private void sendMessage(String message,SocketChannel socket){
		putStringIntoBuffer(message);
		while(std.hasRemaining()){
			try {
				socket.write(std);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void wyslijListe(SocketChannel socket){
		File[] fileList = fileHolder.listFiles();
		String msgList = "";
		for(File f: fileList)
			msgList += f.toString() + '\n';
		
		sendMessage(msgList,socket);
	}
	private void wyslijPlik(int number){}
	
	private int getFileNumber(String data)
	{
		return Integer.parseInt(data.substring(4));
	}
	private void sendBuffer(){}
	public void run(){
		while(true){
			try {
				SocketChannel socket = channel.accept();
				System.out.println("Jest klient.");

				while(true)
				{
					
					int ret = socket.read(std);
					if(ret == -1) break;
					
					String data = makeStringFromBuffer(std);
					
					if(data == "List") wyslijListe(SocketChannel socket);
					else if(data == "Exit") break;
					else wyslijPlik(getFileNumber(data));
				}
				channel.close();
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
