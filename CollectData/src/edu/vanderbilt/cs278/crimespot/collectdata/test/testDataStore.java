package edu.vanderbilt.cs278.crimespot.collectdata.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.junit.After;
import org.junit.Test;

import edu.vanderbilt.cs278.crimespot.collectdata.DataStore;
import edu.vanderbilt.cs278.crimespot.collectdata.Url;

public class testDataStore {
	private static String OUTPUT = "data/";
	private String testFileName = "test";
	private String testFileAbsolutePath;
	
	public void setUp() {
        
    }

    @After
    public void tearDown() {
    	//File testFile = new File(testFileAbsolutePath);
    	//testFile.delete();
    }
	
	@Test
	public void testGetPWByName() throws IOException {
		DataStore.getPWByName(testFileName);
		File testFile = new File(OUTPUT+testFileName);
		testFileAbsolutePath = testFile.getAbsolutePath();
		assertEquals(true,testFile.isFile());	
	}
	
	@Test
	public void testGetDataByName() throws JSONException{
		DataStore.getDataByName("Nashville_TN_USA");
		File testFile = new File(OUTPUT+"Nashville_TN_USA_8");
		testFileAbsolutePath = testFile.getAbsolutePath();
		assertEquals(true,testFile.isFile());	
	}
}
