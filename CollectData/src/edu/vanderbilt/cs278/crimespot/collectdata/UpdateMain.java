package edu.vanderbilt.cs278.crimespot.collectdata;

public class UpdateMain {

	/**
	 * update data everyday
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DataStore.getDataByName("Nashville_TN");
//		DataStore.getDataByName("SanFrancisco");
//		DataStore.getDataByName("Bellevue");
	}
}
