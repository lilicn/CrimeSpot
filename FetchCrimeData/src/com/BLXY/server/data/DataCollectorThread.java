package com.BLXY.server.data;
import java.util.ArrayList;
import java.util.TimerTask;

import com.BLXY.shared.CrimeEvent;
import com.BLXY.shared.GPSData;
import com.BLXY.util.Utils;

public class DataCollectorThread extends TimerTask {
	GPSData gps;
	ArrayList<CrimeEvent> crimeEvents = null;
	public DataCollectorThread(GPSData gps){
		this.gps = gps;
	}
	
	public void run(){
		System.out.println("asdf");
			//String url = "http://www.mylocalcrime.com/#"+gps.lat+", "+gps.lon;
			String url = "http://api.spotcrime.com/crimes.json?lat="+gps.lat+"&lon="+gps.lon+"&radius="+1+"&callback=?&key=MLC2";

			ArrayList<CrimeEvent> list = Utils.getCrimeList(url);
			for(CrimeEvent e:list){
				System.out.println(e.toString());
			}
		
	}


}
