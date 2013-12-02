package edu.vanderbilt.cs278.safespot.test;

import android.content.Intent;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import edu.vanderbilt.cs278.safespot.CrimeService;

/**
 * Test CrimeService
 * 
 * @author Li
 * 
 */
public class CrimeServiceTest extends ServiceTestCase<CrimeService> {

	public CrimeServiceTest() {
		super(CrimeService.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test basic startup of Service
	 */
	@SmallTest
	public void testStartable() {
		Intent startIntent = new Intent();
		startIntent.setClass(getContext(), CrimeService.class);
		startService(startIntent);
	}
}
