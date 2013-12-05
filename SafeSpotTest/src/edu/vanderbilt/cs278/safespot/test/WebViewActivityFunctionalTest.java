package edu.vanderbilt.cs278.safespot.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.webkit.WebView;
import edu.vanderbilt.cs278.safespot.WebViewActivity;

public class WebViewActivityFunctionalTest extends
		ActivityInstrumentationTestCase2<WebViewActivity> {

	private WebViewActivity activity;

	public WebViewActivityFunctionalTest() {
		super(WebViewActivity.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
	}

	public void testMenu0OnClick() {
		final WebView webview = (WebView) activity
				.findViewById(edu.vanderbilt.cs278.safespot.R.id.webView);
		// Click menu 0 -- heapMap
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(activity,
				edu.vanderbilt.cs278.safespot.R.id.webOption, 0);

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				assertEquals("HeapMap", "file:///android_asset/heatmap.html",
						webview.getUrl());
			}
		});

	}
	/*
	public void testMenu1OnClick() {
		final WebView webview = (WebView) activity
				.findViewById(edu.vanderbilt.cs278.safespot.R.id.webView);
		// Click menu 1 -- heapMapWithWeight
		// getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(activity,
				edu.vanderbilt.cs278.safespot.R.id.webOption, 1);
		getInstrumentation().waitForIdleSync();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {

				assertEquals("HeapMapWithWeight",
						"file:///android_asset/heatmapWeight.html",
						webview.getUrl());
			}
		});

	}

	public void testMenu2OnClick() {
		final WebView webview = (WebView) activity
				.findViewById(edu.vanderbilt.cs278.safespot.R.id.webView);
		// Click menu 2 -- map with grid
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(activity,
				edu.vanderbilt.cs278.safespot.R.id.webOption, 2);
		getInstrumentation().waitForIdleSync();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				assertEquals("MapwithGrid", "file:///android_asset/map.html",
						webview.getUrl());
			}
		});

	}*/

}
