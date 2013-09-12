package cont;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConnectionManager {

	ByteBuffer std;
	SocketChannel socket;
	
	ConnectionManager(SocketChannel s){
		socket = s;
		std = ByteBuffer.allocate(8192);
	}
	
	private void sendBuffer(){
		
		while(std.hasRemaining()){
			try {System.out.println(std.toString());
				socket.write(std);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		std.flip();
		std.clear();
	}
	private void putStringIntoBuffer(String s){
		std.clear();
		std.put(s.getBytes());
		std.flip();
	}
	
	public void sendMessage(String message){
		putStringIntoBuffer(message + "\n");
		sendBuffer();
	}
	public String readMessage(){
		String message = "";
		std.clear();
		int ret = 0;
		try {
			ret = socket.read(std);
		} catch (IOException e) {
			System.out.println("B��d przy odczytaniu wiadomo�ci");
		}
		if(ret == -1){
			System.out.println("NO MESSAGE");
		}
		std.flip();
		byte[] bytes = new byte[100];
		
		for(int i = 0; i<100 && std.hasRemaining(); i++)
			bytes[i] = std.get();
		
		message = new String(bytes);
		
		return message;
	}
	public static class MessageManager{
		
		public static String buildMessage(String ... args){
			String message = "";
			for(String s : args){
				message += s + ";";
			}
			return message;
		}
		public static String hashString(String mes){
			MessageDigest md5;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return null; //nie powinna zdarzy� sie ta sytuacja.bardzo.
			}
			md5.update(mes.getBytes());
			return (new BigInteger(1,md5.digest())).toString(16);
		}
		public static String[] decodeResponse(String response){
			return response.split("\\;");
		}
		public static int whichHostNotUse(String login){
			int result = 0;
			
			return result;
		}
		public static String fillLogin(String login){
			int k = login.length();
			while(k<20){
				login += "A";
				k++;
			}
			return login;
		}
	}
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
