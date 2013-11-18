package edu.vanderbilt.cs278.safespot.crimedata;

public class GPSData {
	
	public GPSData(String name) {
		this.name = name;
		
	}
		
	
	public GPSData(Double lat, Double lon) {
		this.lat = lat;
		this.lon = lon;
	}


	public GPSData(String name, Double lat, Double lon) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	

	@Override
	public String toString() {
		return "GPSData [name=" + name + ", lat=" + lat + ", lon=" + lon + "]";
	}



	public String name;
    public Double lat;
    public Double lon;
}
