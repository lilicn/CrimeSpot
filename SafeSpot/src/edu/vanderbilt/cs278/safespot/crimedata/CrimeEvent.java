package edu.vanderbilt.cs278.safespot.crimedata;
import java.util.Date;

/**
 * 
 * @author  Di & Li
 *
 */
public class CrimeEvent {
	public String crimeType;
	public String address;
	public Date datetime;
	public Double lat;
	public Double lon;
	
	public CrimeEvent(String crimeType, String address, Date datetime,Double lat, Double lon) {
		super();
		this.crimeType = crimeType;
		this.address = address;
		this.datetime = datetime;
		this.lat=lat;
		this.lon=lon;
	}

	@Override
	public String toString() {
		return "CrimeEvent [crimeType=" + crimeType + ", address=" + address
				+ ", datetime=" + datetime + ", lat=" + lat + ", lon=" + lon
				+ "]";
	}	

}
