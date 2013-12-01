package edu.vanderbilt.cs278.safespot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class HistoryAnalysisActivity extends Activity {

	private final String TAG = "HistoryAnalysis";
	private WebView webview;
	private long zone_id;

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

		Bundle bundle = getIntent().getExtras();
		zone_id = bundle.getLong(Util.ID);

		showBarChart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history_menu, menu);
		return true;
	}

	/**
	 * Override method for onOptionsItemSelected
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.crimetrend:
			showTimeSeries();
			return true;

		case R.id.crimetype:
			showBarChart();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showTimeSeries() {
		List<Integer> series = loadDatabase("frequency.db");

		String content = null;
		try {
			AssetManager assetManager = getAssets();
			InputStream in = assetManager.open("crimetrend.html");
			byte[] bytes = IOUtils.toByteArray(in);
			content = new String(bytes, "UTF-8");
		} catch (IOException e) {
			Log.e(TAG, "An error occurred.");
		}

		final String formattedContent = String.format(content, series.get(0),
				series.get(1), series.get(2), series.get(3), series.get(4),
				series.get(5));
		webview.loadDataWithBaseURL("file:///android_asset/", formattedContent,
				"text/html", "utf-8", null);

	}


	public void showBarChart() {
		List<Integer> series = loadDatabase("type.db");

		String content = null;
		try {
			AssetManager assetManager = getAssets();
			InputStream in = assetManager.open("crimetype.html");
			byte[] bytes = IOUtils.toByteArray(in);
			content = new String(bytes, "UTF-8");
		} catch (IOException e) {
			Log.e(TAG, "An error occurred.");
		}
		Log.d(TAG, "size:" + series.size() + " get(1):" + series.get(1));
		
		final String formattedContent = String.format(content, series.get(0),
				series.get(1), series.get(2), series.get(3), series.get(4),
				series.get(5), series.get(6), series.get(7), series.get(8),
				series.get(9), series.get(10), series.get(11), series.get(12),
				series.get(13), series.get(14));
		webview.loadDataWithBaseURL("file:///android_asset/", formattedContent,
				"text/html", "utf-8", null);

	}
	
	private List<Integer> loadDatabase(String filename) {
		
		HashMap<Long, List<Integer>> series = new HashMap<Long, List<Integer>>();
		try {
			Scanner s = new Scanner((getAssets().open(filename)));
			long id = 1;

			while (s.hasNextLine()) {
				List<Integer> temp = new ArrayList<Integer>();

				String[] array = s.nextLine().split(" ");
				for (String ss : array) {
					temp.add(Integer.parseInt(ss));
				}

				series.put(id, temp);
				id++;
			}
			s.close();

		} catch (IOException e) {
			Log.e(TAG, "load database file fail.");
		}
		
		Log.i(TAG, "zone_id:" + zone_id);
		if(series.get(zone_id).get(0)+ series.get(zone_id).get(1) + series.get(zone_id).get(2) ==0)
			zone_id = 55;
		
		return series.get(zone_id);
	}

}
