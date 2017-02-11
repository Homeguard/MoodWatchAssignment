package main;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import net.IMoodWatch;
import net.MoodWatchFacade;



@Startup
@ManagedBean (name="testbean")
@ApplicationScoped
public class Main {
public static ServerConnection serverconnection;

@ManagedProperty(value = "#{clients}")
public  ArrayList<DesktopClient> clients;

public static void main(String[] args) throws IOException, InterruptedException {

Main main = new Main();	
		try {
			serverconnection = new ServerConnection (6066);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(serverconnection).start();
	
// Generate multiple clients for testing
main.createClients();
// start the desktop client
IMoodWatch facade = MoodWatchFacade.instance();
facade.runDesktop();
	
}	
	
@PostConstruct
public void createClients() throws InterruptedException, UnknownHostException, IOException{
	clients = new ArrayList<DesktopClient>();
	ExecutorService executor = Executors.newFixedThreadPool(5);
	//Generates some test clients
	for (int i = 0; i<2; i++){
		DesktopClient desktopClient = new DesktopClient();
		desktopClient.setId(i);
		executor.submit(desktopClient);
		desktopClient.setStatus("Running");
		clients.add(desktopClient);
	}	
}

	
public ArrayList<DesktopClient> getClients(){
	return clients;
}
	
public void setClients(ArrayList<DesktopClient> clients){
	this.clients = clients;
}
	
public void startClient(String client){
	int get = Integer.parseInt(client);
	clients.get(get).setRunning(true);
}
	
public void stopClient(String client) throws IOException{
	int get = Integer.parseInt(client);
	clients.get(get).stopRunning();
}
		
public void deleteClient(String client) throws IOException{
	int get = Integer.parseInt(client);
	clients.get(get).stopRunning();
	clients.remove(get);	
}
	
}