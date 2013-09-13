package cont;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendFile {
    
    private Socket connection;
    private ObjectOutputStream outStream;
     
    public SendFile() {
        try {
			connection = new Socket("127.0.0.1",12346);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        try {
            outStream = new ObjectOutputStream(connection.getOutputStream());
            outStream.flush();
             
        } catch (IOException e) {
            System.out.println("Output stream Error!");
        }
    }
 
    public void streamFile(File file) throws IOException {
        long fileSize = file.length();
        long completed = 0;
        int step = 150000;
 
        // creates the file stream
        FileInputStream fileStream = new FileInputStream(file);
 
        // sending a message before streaming the file
       /* outStream.writeObject("SENDING_FILE|" +
                             file.getName() +
                             "|" + fileSize);
 */
        byte[] buffer = new byte[step];
        while (completed <= fileSize) {
            fileStream.read(buffer);
            outStream.write(buffer);
            completed += step;
        }
        //outStream.writeObject("SEND_COMPLETE");
        fileStream.close();
    }
 
}