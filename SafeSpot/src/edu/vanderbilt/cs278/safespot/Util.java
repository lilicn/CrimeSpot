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
	public final static int GET_REVIEW = 10;
	public final static int SEND_REVIEW = 20;
	public final static int GET_LIST = 30;
	public final static int SEND_STAR = 40;
	public final static String ZONE = "zone";
	
	public final static double MAX_LAT = 36.19;
	public final static double MIN_LAT = 36.138;
	public final static double MAX_LNG = -86.72;
	public final static double MIN_LNG = -86.85;
	public final static double LATSTEP = 0.004;
	public final static double LNGSTEP = 0.005;
	
	/**
	 * change geocode to zone number
	 * 
	 * @param current
	 * @return
	 */
	public static long getZoneFromGEO(LatLng current) {
		if(current.latitude<=MAX_LAT && current.latitude>=MIN_LAT && current.longitude<=MAX_LNG && current.longitude >= MIN_LNG){
		    double row = Math.ceil(Math.abs(current.latitude-MAX_LAT)/LATSTEP);
		    double column = Math.ceil(Math.abs(current.longitude-MIN_LNG)/LNGSTEP);
		    double n = Math.round((MAX_LNG-MIN_LNG)/LNGSTEP);
			return (long) ((row-1)*n + column);
		}
	
		else
			return 55;
	}
	
	public static String getChannelByZone(long zone){
		return "edu_vanderbilt_cs278_lili_pubnub_channel_2013_"+zone;
	}

}
