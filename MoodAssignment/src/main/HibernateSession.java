package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {
   
private Session session;	
private Configuration configuration;
private static SessionFactory factory;
	
// Copy&Paste From JBoss documentation and powerpoints
private static SessionFactory sessionFactory=buildSessionFactory();
    
    public static SessionFactory buildSessionFactory(){
         try {
        	// Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure("hibernate.cfg.xml").configure().buildSessionFactory();
         } 
         catch (Throwable ex) {
        	 System.err.println("Initial SessionFactory creation failed." + ex);
	         throw new ExceptionInInitializerError(ex);
	     }
	}
	   
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}