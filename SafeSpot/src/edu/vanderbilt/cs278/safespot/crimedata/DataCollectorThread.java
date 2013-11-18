package edu.vanderbilt.cs278.safespot.crimedata;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class DataCollectorThread {
	public static String getCrimeNearby(GPSData gps) {
		String url = "http://api.spotcrime.com/crimes.json?lat=" + gps.lat
				+ "&lon=" + gps.lon + "&radius=" + 1 + "&callback=?&key=MLC2";	
		String data = null;
		try {
			URL surl = new URL(url);
			Scanner in = new Scanner(surl.openStream());
			data = in.nextLine();
			data = data.substring(2, data.length() - 1);

		}catch (IOException e) {

			e.printStackTrace();
		}
		return data;
	}

}
