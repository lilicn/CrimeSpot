package edu.vanderbilt.cs278.crimespot.server.data;

/**
 * Class to store crime event object
 * @author Li
 *
 */
public class Crime {	
	private String type;
	private long zone;
	private String time;
	private String address;
	
	public Crime(String type, String time,String address ){
		this.address = address;
		this.time = time;
		this.type = type;
		this.zone = Util.getZoneFromAdd(address);
	}


	public String getType() {
		return this.type;
	}

	public String getAdd() {
		return this.address;
	}

	
	public String getTime() {
		return this.time;
	}

	
	public long getZone() {
		return this.zone;
	}
}
