package edu.vanderbilt.cs278.safespot;

import java.io.Serializable;

/**
 * 
 * @author Di & Li
 *
 */
public class LocationReview implements Serializable {
	private static final long serialVersionUID = -2085477349929885008L;

	public LocationReview() {
		super();
		name = "hello";
		lon = -86.80215537;
		lat = 36.14513101;
		score = 2.0;
		review = 0;
		comment = "comment";
	}

	public LocationReview(String com) {
		this();
		comment = com;
	}

	private String name;
	private double lon;
	private double lat;
	private double score;
	private double review;
	private String comment;

	public void setComment(String comm) {
		this.comment = comm;
	}

	public String getComment() {
		return this.comment;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLon() {
		return this.lon;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLat() {
		return this.lat;
	}

	public void setScore(double sco) {
		this.score = sco;
	}

	public double getScore() {
		return this.score;
	}

	public void setReview(double rev) {
		this.review = rev;
	}

	public double getReview() {
		return this.review;
	}
}
