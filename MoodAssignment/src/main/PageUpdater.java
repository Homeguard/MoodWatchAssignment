package main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;


// Contains various methods to retrieve records from the DB and show them in a PrimeFaces table
//(PrimeFaces because we used in our Big Data Project)
public class PageUpdater {

	public PageUpdater(){
		
	}

public ArrayList<Page> DBPageRetriever(){
	Session session = HibernateSession.getSessionFactory().openSession();
	session.beginTransaction();
	SQLQuery query = session.createSQLQuery("SELECT * from pages JOIN sites ON pages.page_id = sites.page_id");
	
	//Deprecated in hibernate 5.2+, use older version instead (5.0)
	query.addEntity(Page.class);
	ArrayList <Page> pages = (ArrayList<Page>) query.list();
	session.close();
	return pages;
}


public void addPage(String pageToAdd, String siteToAdd){
	
	Session session = HibernateSession.getSessionFactory().openSession();
	//Note to self: Foreign key constraint on page_id so that all sites of the same page_id are associated with the correct page
	SQLQuery query = session.createSQLQuery("SELECT * from pages JOIN sites ON pages.page_id = sites.page_id");
	
	//Deprecated in hibernate 5.2+, use older version instead (5.0), did not want to test around with the newer implementation methods
	query.addEntity(Page.class);
	ArrayList<Page>pages = (ArrayList<Page>) query.list();

	if (pages.size()==0){
		session.beginTransaction();
		Page page = new Page();
		page.setPage(pageToAdd);
		//Set initial mood for the page to 0 until Main is executed to update value
		page.setMood(0);
		ArrayList<String> sites = new ArrayList();
		page.setSites(sites);
		page.getSites().add(siteToAdd);
		session.persist(page);
		session.getTransaction().commit();
	}


	Map<String, Page> results = new HashMap();
	
	if (pages.size() !=0){
		for (Page p : pages){
			results.put(p.getPage(), p);
		}
	}

	if (results.containsKey(pageToAdd)){
		session.beginTransaction();
		results.get(pageToAdd).getSites().add(siteToAdd);
		session.saveOrUpdate(results.get(pageToAdd));
		session.getTransaction().commit();
	}
	
	else if (pages.size()>0){
		session.beginTransaction();
		Page page = new Page();
		page.setPage(pageToAdd);
		//Set initial mood for the page to 0 until Main is executed to update value
		page.setMood(0);
		ArrayList<String> sites = new ArrayList();
		page.setSites(sites);
		page.getSites().add(siteToAdd);
		session.persist(page);
		session.getTransaction().commit();
	}

	try{     
	}
	
	catch (HibernateException e){
	}
	
	finally{
		session.close();
	}
}

public void updatePage(String pageToAdd, int mood) throws ParseException{
	Session session = HibernateSession.getSessionFactory().openSession();
	SQLQuery query = session.createSQLQuery("SELECT * from pages JOIN sites ON pages.page_id = sites.page_id");
	
	//Deprecated in hibernate 5.2+, use older version instead (5.0)
	query.addEntity(Page.class);
	ArrayList<Page>pages = (ArrayList<Page>) query.list();
	Map<String, Page> results = new HashMap();
	
	if (pages.size() !=0){
		for (Page p : pages){
		results.put(p.getPage(), p);
		}
	}

	if (results.containsKey(pageToAdd)){
		// get handle to Page
		Page tempPage = results.get(pageToAdd);
		session.beginTransaction();
		tempPage.setMood(tempPage.getMood()+mood);
		session.saveOrUpdate(tempPage);
		session.getTransaction().commit();
	}

	session.close();
	
}



public Map<Page, List<String>> getAll(){
	Map<Page, List<String>> allSites = new HashMap();
	Session session = HibernateSession.getSessionFactory().openSession();
	session.beginTransaction();
	SQLQuery query = session.createSQLQuery("SELECT * from pages JOIN sites ON pages.page_id = sites.page_id");
	
	//Deprecated in hibernate 5.2+, use older version instead (5.0)
	query.addEntity(Page.class);
	ArrayList <Page> pages = (ArrayList<Page>) query.list();
	for (Page p : pages){
		allSites.put(p, p.getSites());
		session.saveOrUpdate(p);
	}

	session.close();
	return allSites;
}


// Retrieves all the Pages/Sites from the DB and pushes them to the desktop client
// The main class needs to be re-run as a java application after adding pages on the web portal
public Collection<String> sitesToClient(){
	
	ArrayList <Page> pages = DBPageRetriever();
	ArrayList<String> allSites = new ArrayList();
	
	for (Page page : pages){
		allSites.add(page.getPage());
	}
	return allSites;
}

public Map<String, List<String>> threadsToClient(){
	Map<String, List<String>> allThreads = new HashMap();
	ArrayList <Page> pages =  DBPageRetriever();
	for (Page p : pages){
		allThreads.put(p.getPage(), p.getSites());
	}
	
	return allThreads;
}

//Gets the Pages from the DB to display them on the web portal
public List<Page> getPages(){

	List<Page> pages = null;
	Session session = HibernateSession.getSessionFactory().openSession();
	//HQL to return all instances of Page
	String hql = "from Page";
	Query q = session.createQuery(hql);
	pages =  q.list();
	
	return pages;	
}

//PrimeFaces seems to have trouble with HashMaps, so converting the HashMap to an ArrayList for visualization
public ArrayList<Entry<Page, List<String>>> convertForPrimeFaces(){
	 Set<Entry<Page, List<String>>> convertedSet = getAll().entrySet();
	 return new ArrayList<Map.Entry<Page, List<String>>>(convertedSet);
}

}