package edu.vanderbilt.cs278.safespot.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import edu.vanderbilt.cs278.safespot.HistoryAnalysisActivity;
import edu.vanderbilt.cs278.safespot.MainActivity;
import edu.vanderbilt.cs278.safespot.WebViewActivity;

/**
 * Functional test for MainActivity
 * 
 * @author Li
 * 
 */
public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity activity;

	public MainActivityTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
	}

	/**
	 * test menu item - webOption onclick
	 */
	public void testWebOptionOnClick() {
		Instrumentation.ActivityMonitor monitor = getInstrumentation()
				.addMonitor(WebViewActivity.class.getName(), null, false);
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(activity,
				edu.vanderbilt.cs278.safespot.R.id.webOption, 0);
		Activity webViewActivity = getInstrumentation()
				.waitForMonitorWithTimeout(monitor, 10000);
		assertNotNull("not null", webViewActivity);
		webViewActivity.finish();
	}

	/**
	 * test menu item - HistoryAnalysisActivity onclick
	 */
	public void testHistoryAnalysisOnClick() {
		Instrumentation.ActivityMonitor monitor = getInstrumentation()
				.addMonitor(HistoryAnalysisActivity.class.getName(), null,
						false);
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(activity,
				edu.vanderbilt.cs278.safespot.R.id.historyAnalysis, 0);
		Activity historyAnalysisActivity = getInstrumentation()
				.waitForMonitorWithTimeout(monitor, 10000);
		assertNotNull("not null", historyAnalysisActivity);
		historyAnalysisActivity.finish();
	}

	/**
	 * test search button
	 */
	public void testButton() {
		int buttonId = edu.vanderbilt.cs278.safespot.R.id.button;
		assertNotNull(activity.findViewById(buttonId));
		Button search = (Button) activity.findViewById(buttonId);
		assertEquals("Incorrect label of the button", "Go", search.getText());
	}

	/**
	 * test editText
	 */
	public void testEditText() {
		int addText = edu.vanderbilt.cs278.safespot.R.id.address;
		final EditText editAddr = (EditText) activity.findViewById(addText);
		assertNotNull("EditText not allowed to be null", editAddr);
		assertEquals("default address is vanderbilt university",
				"vanderbilt university", editAddr.getText().toString());

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				editAddr.setText("Nashville");
			}
		});
		getInstrumentation().waitForIdleSync();
		assertEquals("Text incorrect", "Nashville", editAddr.getText()
				.toString());
	}

}
