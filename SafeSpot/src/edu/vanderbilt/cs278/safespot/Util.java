package edu.vanderbilt.cs278.safespot;

import com.google.android.gms.maps.model.LatLng;

/**
 * Utility class to provide final static variables and methods
 * 
 * @author Li
 * 
 */
public class Util {
	public final static int RESPONSE = 2;
	public final static int RESPONSE_CRIME = 5;
	public final static String CRIMENEARBY = "CRIME_NEARBY";
	public final static String MESSENGER = "MESSENGER";
	public final static String LATLNG = "LatLng";
	public final static String INFO = "info";
	public final static String TITLE = "Safety Score:";
	public final static String LAT = "lat";
	public final static String LON = "lon";
	public final static String TYPE = "type";
	public final static String GEO = "geo";
	// http://localhost:8888/crimespotserver
	public final static String URL = "http://1.tidal-analogy-395.appspot.com/crimespotserver";
	public final static String ID = "zone id";
	public final static String SCORE = "safety point";
	public final static String REVIEW = "user review";
	public final static String REQUEST_TYPE = "request type";
	public enum Request_Type {
		GET_REVIEW, SEND_REVIEW, GET_LIST
	}

	/**
	 * change geocode to zone number
	 * 
	 * @param current
	 * @return
	 */
	public static long getZoneFromGEO(LatLng current) {
		return 1;
	}
	
	public static String getChannelByZone(long zone){
		return "edu_vanderbilt_cs278_lili_pubnub_channel_2013_"+zone;
	}

}
