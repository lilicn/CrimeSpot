package edu.vanderbilt.cs278.crimespot.server.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.jdo.PersistenceManager;

/**
 * Utility class to provide static variables and methods
 * @author Li
 *
 */
public class Util {
	public final static String[] CRIME_TYPES = {"WEAPONS","VEHICLE BREAK-IN/THEFT","VANDALISM","THEFT/LARCENY","SEX CRIMES","ROBBERY","MOTOR VEHICLE THEFT","HOMICIDE","FRAUD","DUI","DRUGS/ALCOHOL VIOLATIONS","DISTURBING THE PEACE","BURGLARY","ASSAULT","ARSON"};
	public final static int GET_REVIEW = 10;
	public final static int SEND_REVIEW = 20;
	public final static int GET_LIST = 30;
	public final static int SEND_STAR = 40;
	public final static String REQUEST_TYPE = "request type";
	public final static String REVIEW = "user review";
	public final static String ZONE = "zone";
	/**
	 * need modified
	 * get zone id from latitude and longitude
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static long getZoneFromGeo(double latitude, double longitude){
		return 2;
	}
	
	/**
	 * fetch data from url and same them into a zone object 
	 * @param src url to get the source crime data
	 * @param zone zone object to store
	 * @throws IOException
	 */
	public static void saveDataFromSrc(String src, PersistenceManager pm) throws IOException{
		File folder = new File(src);
		File[] files = folder.listFiles();
		System.out.println("Get data from "+folder.getAbsolutePath());
		for (File file : files) {
			BufferedReader br = new BufferedReader(new FileReader(
					file.getAbsolutePath()));
			String line;
			while ((line = br.readLine()) != null) {
				String[] strs = line.split(",");
				long id = Long.parseLong(strs[0]);
				double val = Double.parseDouble(strs[1]);
//				Zone z = Zone.byID(id, pm);
				Zone z = null;
				Comment comm = null;
				if(z==null){
					z = new Zone();
					z.setID(id);					
					comm = new Comment();
					comm.setID(id);
				}		
				z.setTotalVal(val);
				z.saveZone(pm);
				comm.saveComment(pm);
				System.out.println("Zone "+z.getID()+" has created/updated!");
			}
		}
	}
	
}
