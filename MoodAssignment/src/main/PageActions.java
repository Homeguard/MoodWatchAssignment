package main;

import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


@ManagedBean (name ="pageactions")
public class PageActions {
	
private String page;
private String site;

public PageActions() {
	
}
		
public String getPage() {
	return page;
}

public void setPage(String page) {
	this.page = page;
}

public String getSite() {
	return site;
}

public void setSite(String site) {
	this.site = site;
}

//Submit button to add a new page + site to track
public void submit() throws ParseException{
	 HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
     String pageToAdd = request.getParameter("trackPageForm:page");
     String siteToAdd = request.getParameter("trackPageForm:site");
	     
     PageUpdater pageupdater = new PageUpdater();
     pageupdater.addPage(pageToAdd, siteToAdd);
  
}
	
public List<Page> getPages(){
	PageUpdater pageupdater = new PageUpdater();
	return pageupdater.getPages();
}
	
public ArrayList<Entry<Page, List<String>>> getAll(){
	PageUpdater pageupdater = new PageUpdater();
	return pageupdater.convertForPrimeFaces();
}
	
}