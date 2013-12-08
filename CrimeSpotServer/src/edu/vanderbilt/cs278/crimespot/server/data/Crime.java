package edu.vanderbilt.cs278.crimespot.server.data;

/**
 * Class to store crime event object
 * 
 * @author Li
 * 
 */
public class Crime {
	private String type;
	private long zone;
	private String time;
	private double lat;
	private double lon;

	public Crime(String type, String time, double lat, double lon) {
		this.time = time;
		this.type = type;
		this.lat = lat;
		this.lon = lon;
		this.zone = Util.getZoneFromGeo(lat, lon);
	}

	public String getType() {
		return this.type;
	}

	public double getLat() {
		return this.lat;
	}
	
	public double getLon() {
		return this.lon;
	}

	public String getTime() {
		return this.time;
	}

	public long getZone() {
		return this.zone;
	}
}
