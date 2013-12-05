package edu.vanderbilt.cs278.crimespot.collectdata.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.vanderbilt.cs278.crimespot.collectdata.GeoCode;

public class testGeoCode {
	String testFileAbsolutePath;
	String testGeoFileAbsolutePath;

	@Before
	public void setUp() throws IOException {
		// create file
		FileWriter fw = new FileWriter("data/test");
		File testFile = new File("data/test");
		testFileAbsolutePath = testFile.getAbsolutePath();
		// crime type, crime type, geocode
		fw.write("any crime,any time,VanderbiltNashville\n");
		fw.close();
		
	}

	@After
	public void tearDown() {
		// delete create file
		File deleteOldFile = new File(testFileAbsolutePath);
		deleteOldFile.delete();

		File newFile = new File("data/test_GEO");
		testGeoFileAbsolutePath = newFile.getAbsolutePath();
		File deleteGeoFile = new File(testGeoFileAbsolutePath);
		deleteGeoFile.delete();

	}

	@Test
	public void testGetGeoByAddr() throws MalformedURLException, IOException,
			JSONException {
		// random selected
		// the combine URL will be
		// http://maps.googleapis.com/maps/api/geocode/json?address=VanderbiltNashville&sensor=true
		String testGeoCode = GeoCode.getGeoByAddr("VanderbiltNashville");
		assertEquals("36.1443747,-86.8027437", testGeoCode);
	}

	@Test
	public void testChangeGEO() throws JSONException, InterruptedException,
			IOException {

		new GeoCode().changeGEO("data/");

		File testGeoFile = new File("data/test_GEO");
		
		StringBuffer sb = new StringBuffer();
		InputStream is = new FileInputStream("data/test_GEO");
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine();
		sb.append(line);
		reader.close();
		is.close();
		System.out.println(line);

		assertEquals(true, line.contains("36.1443747,-86.8027437"));

	}

}
