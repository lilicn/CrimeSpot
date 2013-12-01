package edu.vanderbilt.cs278.safespot.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.webkit.WebView;
import edu.vanderbilt.cs278.safespot.WebViewActivity;

public class WebViewActivityTest extends ActivityUnitTestCase<WebViewActivity> {
	
	private WebViewActivity activity;
	private int webviewId; 
	
	public WebViewActivityTest() {
		super(WebViewActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();
	    Intent intent = new Intent(getInstrumentation().getTargetContext(),
	    		WebViewActivity.class);
	    startActivity(intent, null, null);       
	    activity = getActivity();
	  }
	
	public void testLayout() {
		webviewId = edu.vanderbilt.cs278.safespot.R.id.webView;
	    assertNotNull(activity.findViewById(webviewId));
	    WebView view = (WebView) activity.findViewById(webviewId);
	    assertEquals("Incorrect label of the button", "Start", view.getUrl());
	  }
	

}
