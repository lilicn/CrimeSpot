package edu.vanderbilt.cs278.safespot;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * web view to show crime distribution in Nashville
 * @author Di & Li & Yao
 *
 */
public class WebViewActivity extends Activity {

	private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		webview = (WebView) findViewById(R.id.webView);		
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setSupportZoom(true);
		showHeapMap();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_view, menu);
		return true;
	}
	
	/**
	 * Override method for onOptionsItemSelected
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.Heapmap:
			showHeapMap();
			return true;
			
		case R.id.HeapmapWithWeight:
			showHeapMapWithWeight();
			return true;
			
		case R.id.map:
			showMap();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void showHeapMap(){
		webview.loadUrl("file:///android_asset/heatmap.html");
	}
	
	public void showHeapMapWithWeight(){
		webview.loadUrl("file:///android_asset/heatmapWeight.html");
	}
	
	public void showMap(){
		webview.loadUrl("file:///android_asset/map.html");
	}
	
}
