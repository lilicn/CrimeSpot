package edu.vanderbilt.cs278.crimespot.collectdata;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Help DataStore to get url to download the data
 * 
 * @author Li
 * 
 */
public class Url {
	private final static String BASIC_URL = "http://www.crimemapping.com/DetailedReport.aspx?db=";
	private final static String CRIME_TYPES = "ccs=AR,AS,BU,DP,DR,DU,FR,HO,VT,RO,SX,TH,VA,VB,WE";
	private final static List<String> TIMELIST = TimePara.getTimeList();
	private final static HashMap<String, String> zoneMap = getZoneMap();

	/**
	 * get one url by police name, eg. nashville
	 * 
	 * @param name
	 * @return
	 */
	public static String getUrlbyName(String name) {
		String time = TimePara.getCurrentTime();
		String zoneGeo = zoneMap.get(name);
		return getURL(BASIC_URL, CRIME_TYPES, time, zoneGeo);
	}

	/**
	 * get a list of urls by police name, eg. nashville
	 * 
	 * @param name
	 * @return
	 */
	public static List<String> getUrlListbyName(String name) {
		List<String> list = new LinkedList<String>();
		String zoneGEO = zoneMap.get(name);
		for (String time : TIMELIST) {
			list.add(getURL(BASIC_URL, CRIME_TYPES, time, zoneGEO));
		}
		return list;
	}

	/**
	 * get final URL to download data
	 * 
	 * @param basic
	 * @param types
	 * @param time
	 * @param zoneGEO
	 * @return
	 */
	public static String getURL(String basic, String types, String time,
			String zoneGEO) {
		return basic + time + "&" + types + "&" + zoneGEO;
	}
	
	/**
	 * get zone map which will change police name into geocode
	 * 
	 * @return
	 */
	public static HashMap<String, String> getZoneMap() {
		HashMap<String, String> zoneHM = new HashMap<String, String>();
		zoneHM.put(
				"Nashville_TN_USA",
				"xmin=-9667325.480312608&ymin=4321245.467651127&xmax=-9654273.857732933&ymax=4325697.924548732");
//		zoneHM.put(
//				"SanFrancisco",
//				"xmin=-13629278.699822504&ymin=4547159.987887784&xmax=-13626015.794177495&ymax=4548273.102112216");
//		zoneHM.put(
//				"Bellevue",
//				"xmin=-13615226.27533097&ymin=6039829.462910019&xmax=-13589447.887541829&ymax=6046039.971458182");
		return zoneHM;
	}
}
