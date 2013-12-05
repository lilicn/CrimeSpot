package edu.vanderbilt.cs278.crimespot.collectdata;

import org.json.JSONException;

/**
 * 
 * @author Li
 *
 */
public class InitMain {

	/**
	 * get history data
	 * 
	 * @param args
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException {
		DataStore.getHistoryDataByName("Nashville_TN_USA");
//		DataStore.getHistoryDataByName("SanFrancisco");
//		DataStore.getHistoryDataByName("Bellevue");
	}
}
