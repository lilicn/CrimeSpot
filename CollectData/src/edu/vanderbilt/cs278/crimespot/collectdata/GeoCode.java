package edu.vanderbilt.cs278.crimespot.collectdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * get geocode
 * @author Li
 *
 */
public class GeoCode {
	private final static String START = "http://maps.googleapis.com/maps/api/geocode/json?address=";
	private final static String END = "&sensor=true";
	private final static String PATH = "data/";
	private static HashMap<String, String> geoHM = new HashMap<String, String>();

	public static String getGeoByAddr(String addr) throws MalformedURLException,
			IOException, JSONException {
		if (geoHM.containsKey(addr)) {
			return geoHM.get(addr);
		}
		String url = getURL(START, addr, END);
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			JSONArray arr = (JSONArray) json.get("results");
			if(arr.length()>0){
				JSONObject result = arr.getJSONObject(0);

				JSONObject geometry = (JSONObject) result.get("geometry");
				JSONObject location = (JSONObject) geometry.get("location");
				String geo = location.getString("lat") + ","
						+ location.getString("lng");
				geoHM.put(addr, geo);
				return geo;
			}
//			else{
//				System.out.println(jsonText);
//				if(jsonText.contains("\"location\" :")){
//					jsonText = jsonText.split("\"location\" :")[1];
//					String geo = jsonText.split("\"lat\" : ")[1].split(",")[0];
//					geo += jsonText.split("\"lng\" : ")[1].split("\r")[0];
//					return geo;
//				}
//				
//			}			
			return "not found";
		} finally {
			is.close();
		}
	}

	public double[] getGeoByAddress(String addr) throws MalformedURLException,
			IOException, JSONException {
		double[] dou = new double[2];
		String s = getGeoByAddr(addr);
		dou[0] = Double.parseDouble(s.split(",")[0]);
		dou[1] = Double.parseDouble(s.split(",")[1]);

		return dou;
	}

	/**
	 * read all content into a string
	 * 
	 * @param rd
	 * @return
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * get url to geocode
	 * 
	 * @param start
	 * @param addr
	 * @param end
	 * @return
	 */
	public static String getURL(String start, String addr, String end) {
		return start + addr + end;
	}

	public void changeGEO() throws JSONException, InterruptedException {
		File folder = new File(PATH);
		File[] files = folder.listFiles();
		for (File file : files) {
			geoHM = new HashMap<String, String>();
			String oldName = file.getName();
			String name = oldName + "_GEO";
			System.out.println("start change geo for " + oldName);
			try {
				PrintWriter pw = DataStore.getPWByName(name);
				BufferedReader br = new BufferedReader(new FileReader(
						file.getAbsolutePath()));
				String line;
				while ((line = br.readLine()) != null) {
					Thread.sleep(1000);
					String[] strs = line.split(",");
					String geo = getGeoByAddr(strs[2]);
					System.out.println("get geo: " + geo);
					pw.println(strs[0] + "," + strs[1] + "," + geo + "," +strs[2]);
				}
				pw.close();
				br.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws MalformedURLException,
			IOException, JSONException, InterruptedException {
		new GeoCode().changeGEO();
//		System.out.println(new GeoCode().getGeoByAddr("800_BLOCK_2ND_AVE_S_Nashville_TN_USA"));
	}

}
