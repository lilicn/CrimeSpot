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
	// url for crimespotserver
	public final static String URL = "http://1.tidal-analogy-395.appspot.com/crimespotserver";
	public final static String ID = "zone id";
	public final static String SCORE = "safety point";
	public final static String REVIEW = "user review";
	public final static String REQUEST_TYPE = "request type";
	public final static int GET_REVIEW = 10;
	public final static int SEND_REVIEW = 20;
	public final static int GET_LIST = 30;
	public final static int SEND_STAR = 40;
	public final static String ZONE = "zone";

	/**
	 * change geocode to zone number
	 * 
	 * @param current
	 * @return
	 */
	public static long getZoneFromGEO(LatLng current) {
		return 2;
	}

	/**
	 * get channel for pubnub by zone id
	 * 
	 * @param zone
	 * @return
	 */
	public static String getChannelByZone(long zone) {
		return "edu_vanderbilt_cs278_lili_pubnub_channel_2013_" + zone;
	}

}
