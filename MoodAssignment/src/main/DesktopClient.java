
package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;

import net.IMoodWatch;
import net.MoodWatchFacade;


@ManagedBean (name="client")
public class DesktopClient implements Runnable {
    private IMoodWatch facade;
	private Socket clientSocket;
    private int id;
    private String status;
    private String host;
    private String ip;
    private int portNumber;
    private PrintWriter out = null;
    private volatile boolean running = false;
     
    public String getStatus() {
       	if (running){
       		status = "Running";
       	}
       	else{
       		status = "Stopped";
       	}
		return status;
	}

    	
	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setId(int id) {
		this.id = id;
	}
		
        
    public DesktopClient() throws UnknownHostException, IOException {
		this.facade = MoodWatchFacade.instance();
		this.host = "localhost";
		this.portNumber = 6066;
    }
            
       
    public void run() {
       	running = true;
        	
    try {
       	clientSocket = new Socket(host, portNumber);
       	// try with this stackoverflow adaption, not tested so not sure if it will work (inetAddress vs InetSocketAddress)
       	// nothing showing up in the web portal atm because theres nothing incoming
		ip =(((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
    }	
        
    catch (IOException e1) {
    	e1.printStackTrace();
    }
  
    // Push all pages and sites to the client
    PageUpdater pageupdater = new PageUpdater();
    Collection<String> sites = pageupdater.sitesToClient();
    Map<String, List<String>> threads = pageupdater.threadsToClient();
       	
    facade.startEngine();
        	
    for (String a : sites){	    		
    	String b = facade.addSite(a);
        facade.addAllThreads(b, threads.get(a));
        Map<String, Integer> siteMood = facade.getMood(b);
        for (Map.Entry<String, Integer> entry : siteMood.entrySet()){
        	try {
        		pageupdater.updatePage(a, entry.getValue());
        	} 
        	        			
          	catch (ParseException e) {
          		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	        			
        }
        	        		
    }
    try {
    	out = new PrintWriter(clientSocket.getOutputStream(),true);
    } 
    catch (IOException e) {
    	e.printStackTrace();
    }
        	      	     	
    }
        		        
    public Socket getClientSocket() {
		return clientSocket;
	}

	public IMoodWatch getFacade() {
		return facade;
	}

	public void stopRunning() throws IOException{
		this.running = false;
	}
        	
        	
}
        
    