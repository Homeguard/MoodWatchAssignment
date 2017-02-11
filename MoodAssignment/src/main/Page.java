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

// Hibernate to DB table for Pages

@Entity
@Table(name = "Pages")
public class Page {
	
@ElementCollection (targetClass=String.class)
@CollectionTable(name ="Sites", joinColumns=@JoinColumn(name="page_id"))
private List sites;
		
public Page() {
			
}
	 
public Page(String page, String siteToAdd){
	this.page = page;
	this.sites = Collections.synchronizedList(new ArrayList());	
}
	 
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "page_id")
private int page_id;


@Column(name="pagename",nullable=false)
private String page;
	
public int getPage_id() {
	return page_id;
}
	
//Auto-generated getters/setters below
	
public void setPage_id(int page_id) {
	this.page_id = page_id;
}

@Column(name="mood",nullable=false)
private int mood;

public String getPage() {
	return page;
}

public void setPage(String page) {
	this.page = page;
}

public int compareTo(Page p) {
    return this.page.compareTo(p.page);
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