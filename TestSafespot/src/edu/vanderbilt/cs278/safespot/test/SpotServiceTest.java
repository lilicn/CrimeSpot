package edu.vanderbilt.cs278.safespot.test;

import android.content.Intent;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import edu.vanderbilt.cs278.safespot.SpotService;

/**
 * Test SpotService
 * 
 * @author Li
 * 
 */
public class SpotServiceTest extends ServiceTestCase<SpotService> {

	public SpotServiceTest() {
		super(SpotService.class);
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
		startIntent.setClass(getContext(), SpotService.class);
		startService(startIntent);
	}
}
