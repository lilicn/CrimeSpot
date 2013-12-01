package edu.vanderbilt.cs278.safespot.test;

import junit.framework.TestCase;
import edu.vanderbilt.cs278.safespot.crimedata.DataCollectorThread;
import edu.vanderbilt.cs278.safespot.crimedata.GPSData;
import static org.mockito.Mockito.*;

public class DataCollectorThreadTest extends TestCase {
	GPSData gps = null; 
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gps = mock(GPSData.class);
	}
	
	public void testGetCrimeNearby(){
		when(gps.lat).thenReturn(0.0);
		when(gps.lon).thenReturn(0.0);
		String data = DataCollectorThread.getCrimeNearby(gps);
		verify(gps.lat);
		verify(gps.lon);
		assertNotNull(data);
	}
	
	

}
