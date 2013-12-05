package edu.vanderbilt.cs278.safespot.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.KeyEvent;
import android.webkit.WebView;
import edu.vanderbilt.cs278.safespot.WebViewActivity;

public class WebViewActivityTest extends ActivityUnitTestCase<WebViewActivity> {
	
	private WebViewActivity activity;
	private int webviewId; 
	
	public WebViewActivityTest() {
		super(WebViewActivity.class);
	}
	
/*	@Override
	  protected void setUp() throws Exception {
		//super.setUp();
	    Intent intent = new Intent(getInstrumentation().getTargetContext(),
	    		WebViewActivity.class);
	    startActivity(intent, null, null);       
	    activity = getActivity();
	  }*/
    @Override
    protected void setUp() throws Exception {
            super.setUp();
            startActivity(new Intent(getInstrumentation().getTargetContext()
                            , WebViewActivity.class), null, null);
            activity = getActivity();
    }
	
	public void testLayout() {
	    Intent intent = new Intent(getInstrumentation().getTargetContext(),
	    		WebViewActivity.class);
	    startActivity(intent, null, null);       
	    activity = getActivity();
		webviewId = edu.vanderbilt.cs278.safespot.R.id.webView;
	    assertNotNull(activity.findViewById(webviewId));
	  }
	
	public void testDefaultURL() {
		final WebView webview = (WebView) activity
				.findViewById(edu.vanderbilt.cs278.safespot.R.id.webView);

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				assertEquals("HeapMap", "file:///android_asset/heatmap.html",
						webview.getUrl());
			}
		});
	}
}
