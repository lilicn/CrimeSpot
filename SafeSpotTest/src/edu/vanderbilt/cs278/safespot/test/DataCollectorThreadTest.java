package edu.vanderbilt.cs278.safespot.test;

import junit.framework.TestCase;
import edu.vanderbilt.cs278.safespot.crimedata.DataCollectorThread;
import edu.vanderbilt.cs278.safespot.crimedata.GPSData;

public class DataCollectorThreadTest extends TestCase {
	GPSData gps = null; 
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gps = new GPSData(0.0,0.0);
	}
	
	public void testGetCrimeNearby(){
		String data = DataCollectorThread.getCrimeNearby(gps);
		assertNotNull(data);
	}
}
