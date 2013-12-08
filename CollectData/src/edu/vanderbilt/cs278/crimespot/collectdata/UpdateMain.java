package edu.vanderbilt.cs278.crimespot.collectdata;

import org.json.JSONException;

/**
 * 
 * @author Li
 *
 */
public class UpdateMain {

	/**
	 * update data everyday
	 * 
	 * @param args
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException {
		DataStore.getDataByName("Nashville_TN_USA");
//		DataStore.getDataByName("SanFrancisco");
//		DataStore.getDataByName("Bellevue");
	}
}
