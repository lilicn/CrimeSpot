package edu.vanderbilt.cs278.crimespot.server.data;

import java.util.HashMap;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Zone is to save the crime data 
 * @author Li
 *
 */
@PersistenceCapable(detachable = "true")
public class Zone {
	@PrimaryKey
	@Persistent
	private long id;
	@Persistent
	private double point;
	@Persistent
	private long num;
	@Persistent 
	private HashMap<String, Long> crimeMap = new HashMap<String,Long>();

	public void setID(long id) {
		this.id = id;
	}

	public long getID() {
		return this.id;
	}

	/**
	 * set the safety point by total number of crime
	 * @param total total number of crime
	 */
	public void setPoint(int total) {
		this.point = Util.getPoint(num,total);
	}

	public double getPoint() {
		return this.point;
	}


	public long getNum() {
		return this.num;
	}
	
	public HashMap<String,Long> getCrimeMap(){
		return this.crimeMap;
	}

	public void addCrime(String type) {
		num++;
		if(crimeMap.containsKey(type)){
			crimeMap.put(type, crimeMap.get(type)+1);
		}else{
			crimeMap.put(type, (long) 1);
		}
	}
}
