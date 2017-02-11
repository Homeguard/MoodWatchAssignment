package main;


import java.io.IOException;

import net.IMoodWatch;
import net.MoodWatchFacade;

// Copy & Paste from class
public class Facade {
private IMoodWatch facade;
	
	public IMoodWatch getFacade() {
		return facade;
	}

	public void setFacade(IMoodWatch facade) {
		this.facade = facade;
	}

	public Facade() throws IOException {
		facade = MoodWatchFacade.instance();
	}

}
