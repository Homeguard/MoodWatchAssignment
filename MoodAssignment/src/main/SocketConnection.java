package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketConnection implements Runnable {
private Socket socket;
 
public SocketConnection(Socket socket) {
	this.socket = socket;
}
  
// Gets client input, exits when the client disconnects
// Big Data Project code adaption from Enoch about sockets with some stackoverflow help mixed into it, seems to work locally? 
@Override
public void run() {
	BufferedReader reader = null;
    PrintWriter writer = null;
    try {
    	reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	writer = new PrintWriter(socket.getOutputStream(), true);
 
    	while(true) {
    		String line = reader.readLine();
    		if(line == null) break;
    		writer.println("Echo: " + line);
    	}
    } 
    catch (IOException e) {
    	throw new RuntimeException(e);
    }
    finally {
    	try {
    		if(reader != null) reader.close();
    		if(writer != null) writer.close();
    	} 
    	catch (IOException e) {
    		throw new RuntimeException(e);
    	}
   }
    
}
 
}