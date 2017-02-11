package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

//Hibernate to DB table for Sites

@Entity
@Table(name = "Pages")
public class Site {
	
@ElementCollection (targetClass=String.class)
@CollectionTable(name="Site") @JoinColumn (name="sites")
private List sites;
		
public Site() {
			
}
	 
public Site(String page, String siteToAdd){
	this.page = page;
	this.sites = Collections.synchronizedList(new ArrayList());	 	
}
	 
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "page_id")
private int page_id;
	
	
@Column(name="pagename",nullable=false, unique=true)
private String page;
	
@Column(name="mood",nullable=false)
private int mood;

//Auto-generated getters/setters below
	
public String getPage() {
	return page;
}

public void setPage(String page) {
	this.page = page;
}
	
public List getSites() {
	return this.sites;
}

public void setSites(List sites) {
	this.sites = sites;
}

public int getMood() {
	return mood;
}

public void setMood(int mood) {
	this.mood = mood;
}

}