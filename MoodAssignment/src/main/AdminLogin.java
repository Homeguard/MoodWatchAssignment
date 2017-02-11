package main;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean (name="adminlogin")
@SessionScoped
public class AdminLogin {

private String password;
private String user;
	
public String authenticateAdmin(){
	FacesContext context = FacesContext.getCurrentInstance();
	boolean authenticatedAdmin = new AdminAuthenticator().authenticate(user, password);
	
	// Stackoverflow adapation to avoid bypassing the admin login by manually editing the URL
	if (authenticatedAdmin){ 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		session.setAttribute("username", user);
		return "adminAccess?faces-redirect=true";
	}
		
	else{
		return "/adminAuthentication?faces-redirect=true";
	}
}
	
//after logging out an admin can no longer access the manager websites, even by manually changing the URL
public String logOut() throws IOException{
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		   
     user = null;
     password = null;
	 session.invalidate();
     return "/mainPage.xhtml?faces-redirect=true";
}
	
public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getUser() {
	return user;
}

public void setUser(String user) {
	this.user = user;
}


}
