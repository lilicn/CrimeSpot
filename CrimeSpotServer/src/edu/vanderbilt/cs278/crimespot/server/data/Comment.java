package edu.vanderbilt.cs278.crimespot.server.data;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Comment {
	@PrimaryKey
	@Persistent
	private long id;
	@Persistent
	private ArrayList<String> list = new ArrayList<String>();

	public void saveComment(PersistenceManager pm) {
		pm.makePersistent(this);
	}
	
	public void addComment(String comment){
		list.add(comment);
	}
	
	public ArrayList<String> getList(){
		return list;
	}
	
	public void setID(long id){
		this.id = id;
	}
	
	public long getID(){
		return this.id;
	}
}
