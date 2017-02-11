package main;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class AdminAuthenticator {

	//creates an admin account in the DB
	public void addAdmin(String username, String password){
		Session session = HibernateSession.getSessionFactory().openSession();
		session.beginTransaction();
	
		try{
			AdminInfo admin = new AdminInfo();
			admin.setUsername(username);
			admin.setPassword(password);
			session.persist(admin);
			session.getTransaction().commit();
		}
	
		catch (HibernateException e){
		}
	
		finally{
			session.close();
		}
	}
	
public boolean authenticate(String user, String password){
Session session = HibernateSession.getSessionFactory().openSession();
session.beginTransaction();
	
// Stackoverflow adaption, test if it works
String hql = "from AdminInfo a where a.username=:user and a.password=:password";
Query q = session.createQuery(hql);
q.setString("user", user);
q.setString("password", password);
List<AdminInfo> list = q.list();
	
if(list.size() > 0){
	session.close();
	return true;
}
	
else{
	return false;
}

}

}
