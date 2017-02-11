package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean (name ="server")
@ApplicationScoped
public class ServerConnection extends Thread {
    
private ServerSocket serverSocket = null;
private Socket clientSocket = null;
private ExecutorService threadPool; 
private boolean running = false;
private ArrayList<ClientManager> connections;
private int portNumber;
public static HibernateSession dbConn;
private BufferedReader in = null;

public ServerConnection(int portNumber) throws IOException {
    this.portNumber = portNumber;
    this.serverSocket = new ServerSocket(this.portNumber);
    initDB();
    System.out.println("call test");
}

public void initDB(){
  	HibernateSession.buildSessionFactory();
   	
  	//Creates an admin account in the database with username admin and password admin123
   	AdminAuthenticator adminauthenticator = new AdminAuthenticator();
   	adminauthenticator.addAdmin("admin", "admin123");
}
    
public void stopServer() throws IOException{
	serverSocket.close();
	clientSocket.close();
	running = false;
}

@Override
public void run() {
	running = true;
 	threadPool = Executors.newFixedThreadPool(5);

   	try {
		acceptClients();
	} 
   	
   	catch (IOException e) {
		e.printStackTrace();
	}
   
  }
   		
public void acceptClients() throws IOException{
	connections = new ArrayList<ClientManager>();
   	while (running){	                  
   	   	try {
   	 		clientSocket = serverSocket.accept();
   	 	} 
   	   	catch (IOException e) {
   	 		e.printStackTrace();
   	 	}
   	   	// Add new clients to the threadpool
   	   	ClientManager connection = new ClientManager(clientSocket);
   		connections.add(connection);
		threadPool.execute(connection);			   
   	}		
}
    	
class ClientManager implements Runnable{
private Socket connection;
ClientManager(Socket connection) {
	this.connection = connection;
}

public void run() {
String clientinput;
          
	try{
		//Input and output from clients
        BufferedReader buffreader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        PrintStream outputstream = new PrintStream(connection.getOutputStream());
   
        //Read input from client
        while((clientinput = buffreader.readLine()) != null){
        	System.out.println("Still receiving input from client: " + clientinput);
        }
               
        //Disconnects the client
        outputstream.flush();
        outputstream.close();
        buffreader.close();
        connection.close();
	}     
    catch (IOException e) {
    	e.printStackTrace();
    }
}

}
  
}
 