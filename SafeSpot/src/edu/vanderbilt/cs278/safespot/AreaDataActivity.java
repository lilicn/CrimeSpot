package edu.vanderbilt.cs278.safespot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AreaDataActivity extends Activity {

	private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_data);

		webview = (WebView) findViewById(R.id.webView);
		loadWebView();
	}

	private void loadWebView() {
		AssetManager assetManager = getAssets();
		InputStream is;
		Scanner scanner=null;
		StringBuilder text=null;
		try {
			is = assetManager.open("heatmap.html");
		text = new StringBuilder();
		scanner = new Scanner(is);
			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			scanner.close();
		}
		
		if(text !=null){
			Log.v("webview", text.toString());

			webview.getSettings().setJavaScriptEnabled(true);
			
			final Activity activity = this;
			 webview.setWebChromeClient(new WebChromeClient() {
			   public void onProgressChanged(WebView view, int progress) {
			     // Activities and WebViews measure progress with different scales.
			     // The progress meter will automatically disappear when we reach 100%
			     activity.setProgress(progress * 1000);
			   }
			 });
			 webview.setWebViewClient(new WebViewClient() {
			   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			     Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
			   }
			 });
			 
			webview.loadData(text.toString(), "text/html", null);
			
		}else{
			webview.loadData("No data available", "text/html", null);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.area_data, menu);
		return true;
	}

}
