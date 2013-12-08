package edu.vanderbilt.cs278.safespot;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * to get log during activity life cycle
 * 
 * @author Li
 * 
 */
public class LogActivity extends Activity {
	private final String TAG = getClass().getSimpleName();

	/**
	 * overrided method for onCreate.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		if (savedInstanceState == null) {
			Log.d(TAG, "activity created a new");
		} else {
			Log.d(TAG, "activity restarted");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.d(TAG, "onSaveInstanceState CURRENTGRO");
	}

	/**
	 * overrided method for onStart.
	 */
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart()");
	}

	/**
	 * overrided method for onRestart.
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart()");
	}

	/**
	 * overrided method for onResume.
	 */
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume()");
	}

	/**
	 * overrided method for onPause.
	 */
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause()");
	}

	/**
	 * overrided method for onStop.
	 */
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop()");
	}

	/**
	 * overrided method for onDestroy
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy()");
	}
}
