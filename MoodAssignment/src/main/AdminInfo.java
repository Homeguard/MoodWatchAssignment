package main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Hibernate to Admin DB table

@Entity
@Table(name = "Admins")
public class AdminInfo {
	

	 public AdminInfo() {
				
		}
	 
	//Auto-generated getters/setters below

	public int getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "admin_id")
	   private int admin_id;
	
	@Column(name="username",nullable=false)
	private String username; 

	@Column(name="password",nullable=false)
	private String password; 
	
		
}
