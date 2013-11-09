package edu.vanderbilt.cs278.crimespot.server.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Utility class to provide static variables and methods
 * @author Li
 *
 */
public class Util {
	public final static String[] CRIME_TYPES = {"WEAPONS","VEHICLE BREAK-IN/THEFT","VANDALISM","THEFT/LARCENY","SEX CRIMES","ROBBERY","MOTOR VEHICLE THEFT","HOMICIDE","FRAUD","DUI","DRUGS/ALCOHOL VIOLATIONS","DISTURBING THE PEACE","BURGLARY","ASSAULT","ARSON"};
	
	/**
	 * need modified
	 * get zone id from address
	 * @param add address 
	 * @return zone id 
	 */
	public static long getZoneFromAdd(String add){
		return 1;
	}
	
	/**
	 * need modified
	 * get zone id from latitude and longitude
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static long getZoneFromGeo(double latitude, double longitude){
		return 1;
	}
	
	/**
	 * fetch data from url and same them into a zone object 
	 * @param src url to get the source crime data
	 * @param zone zone object to store
	 * @throws IOException
	 */
	public static void saveDataFromSrc(String src, Zone zone) throws IOException{
		URL url = new URL(src);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		// remove the first 5 lines
		for (int i = 0; i < 5; i++)
			in.readLine();
		String inputLine;
		while ((inputLine = in.readLine()) != null){	
			zone.addCrime(makeCrime(inputLine).getType());
		}		
		in.close();
	}
	
	/**
	 * make crime object from a line of string
	 * @param line
	 * @return
	 */
	public static Crime makeCrime(String line){
		String[] items = line.split("\t");
		return new Crime(items[1],items[2],items[3]);	
	}
	
	public static double getPoint(long num, long total){
		return num/total;
	}
	
}
