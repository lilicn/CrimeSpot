package com.BLXY.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.BLXY.shared.CrimeEvent;

public class Utils {
	public static ArrayList<CrimeEvent> getCrimeList(String url){
		ArrayList<CrimeEvent> list = new ArrayList<CrimeEvent>();
	
		
		
		try {
			URL surl = new URL(url);
	        Scanner in = new Scanner(surl.openStream());
	        String data= in.nextLine();
			
			data = data.substring(2,data.length()-1);
			
			System.out.println(data.toString());
			try {
				JSONObject json = new JSONObject(data);
				JSONArray crimes = json.getJSONArray("crimes");
				for(int i=0; i < crimes.length();i++){
					JSONObject crime = crimes.getJSONObject(i);
					String type = crime.getString("type");
					String date = crime.getString("date");
					String address = crime.getString("address");
					Double lat = crime.getDouble("lat");
					Double lon = crime.getDouble("lon");

					CrimeEvent crimeEvt = new CrimeEvent(type,address,new Date(date),lat,lon);
					//System.out.println(crimeEvt);
					list.add(crimeEvt);
				}
				System.out.println(crimes.length());
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			//Elements des = doc.select(".description");
			//Elements date = doc.select(".date");
			
			
			
/*			if(des.size()==date.size()){
				for(Element e:des){
					e.toString();
				}
				for(Element e:date){
					e.toString();
				}
			}*/
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*					CrimeType crimeType;
		switch(type){
		case "Arrest":
			crimeType = CrimeType.Arrest;
		case "Arson":
			crimeType = CrimeType.Arson;
		case "Assault":
			crimeType = CrimeType.Assault;
		case "Burglary":
			crimeType = CrimeType.Burglary;
		case "Robbery":
			crimeType = CrimeType.Robbery;
		case "Shooting":
			crimeType = CrimeType.Shooting;
		case "Theft":
			crimeType = CrimeType.Theft;
		case "Vandalism":
			crimeType = CrimeType.Vandalism;
		default:
			crimeType = CrimeType.Other;
		}*/
		return list;
	}
}
