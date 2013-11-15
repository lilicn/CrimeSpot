package edu.vanderbilt.cs278.crimespot.collectdata;

public class InitMain {

	/**
	 * get history data
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DataStore.getHistoryDataByName("Nashville_TN");
//		DataStore.getHistoryDataByName("SanFrancisco");
//		DataStore.getHistoryDataByName("Bellevue");
	}
}
