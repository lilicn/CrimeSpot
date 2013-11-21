package edu.vanderbilt.cs278.safespot;

import java.io.Serializable;

public class LocationReview implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2085477349929885008L;


	public LocationReview() {
		super();
		name = "hello";
		lon = 0.0;
		lat = 0.0;
		score = "2";
	}
	public String name;
	public Double lon;
	public Double lat;
	public String score;
}
