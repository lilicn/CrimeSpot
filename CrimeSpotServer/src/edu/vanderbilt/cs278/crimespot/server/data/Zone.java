package edu.vanderbilt.cs278.crimespot.server.data;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Zone is to save the crime data
 * 
 * @author Li
 * 
 */
@PersistenceCapable(detachable = "true")
public class Zone {
	@PrimaryKey
	@Persistent
	private long id;
	@Persistent
	private double totalVal;
	@Persistent
	private double aveRev = -1;
	@Persistent
	private double userRev = 0;
	@Persistent
	private long revNum = 0;

	// store the score for different crime type
	// @Persistent
	// private HashMap<String, Double> crimes = new HashMap<String, Double>();

	public void saveZone(PersistenceManager pm) {
		pm.makePersistent(this);
	}

	public void setID(long id) {
		this.id = id;
	}

	public long getID() {
		return this.id;
	}

	public void setTotalVal(double val) {
		this.totalVal = val;
	}

	public double getTotalVal() {
		return this.totalVal;
	}

	public void setAveRev() {
		this.aveRev = revNum > 0 ? userRev / revNum : -1;
	}

	public double getAveRev() {
		return this.aveRev;
	}

	public void addUserRev(double rev) {
		this.userRev += rev;
		this.revNum++;
		setAveRev();
	}

	public static Zone byID(long ID, PersistenceManager pm) {
		List<Zone> results = null;
		Query query = pm.newQuery("select from " + Zone.class.getName()
				+ " where id==ID");
		query.declareParameters("long ID");

		results = (List<Zone>) query.execute(ID);
		System.out.println(results==null);
		
		return (results != null && results.size() == 1) ? results.get(0) : null;
	}
}
