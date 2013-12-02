package edu.vanderbilt.cs278.safespot.data;

public class Zone {
	private int id;
	private int zoneID;
	private double score;
	private double aveRev;
	private int review;
	
	// initially set to -1
	public Zone(){
		this.score = -1;
		this.aveRev = -1;
		this.review = -1;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return this.id;
	}
	
	public void setZoneID(int zoneid){
		this.zoneID = zoneid;
	}
	
	public long getZoneID(){
		return zoneID;
	}
	
	public void setScore(double score){
		this.score = score;
	}
	
	public double getScore(){
		return this.score;
	}

	public double getAveRev(){
		return this.aveRev;
	}
	
	public void setAveRev(double averev){
		this.aveRev = averev;
	}
	
	public int getReview(){
		return this.review;
	}
	
	public void setReview(int review){
		this.review = review;
	}
	
}
