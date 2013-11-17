package edu.vanderbilt.cs278.safespot;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Mainactivity will show the current address in the gmap and the current safety
 * score
 * 
 * @author Li
 * 
 */
@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private static final LatLng NASHVILLE = new LatLng(36.1667, -86.7833);
	private static final String TAG = "MainActivity";
	private GoogleMap map;
	private LocationManager locationManager;
	private boolean isListenGPS = false;
	private LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			LatLng current = new LatLng(location.getLatitude(),
					location.getLongitude());
			Log.d(TAG,"get current geocode");
			setMap(current);
		}

		@Override
		public void onProviderDisabled(String arg0) {
		}

		@Override
		public void onProviderEnabled(String arg0) {
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		}

	};
	private static Handler handler;

	private class MyHandler extends Handler {
		private Context ctx;
		private GoogleMap map;
		private final static String TAG = "MyHandler";
		private Marker marker;

		public MyHandler(Context ctx, GoogleMap map) {
			this.ctx = ctx;
			this.map = map;
		}

		@Override
		public void handleMessage(Message msg) {
			Log.d(TAG, "get msg");
			switch (msg.what) {
			case Util.RESPONSE: {
				Bundle data = msg.getData();
				LatLng current = new LatLng(data.getDouble(Util.LAT),
						data.getDouble(Util.LON));
				String info = data.getString(Util.INFO);
				// get score from info
				String score = null;
				try {
					JSONObject obj = new JSONObject(info);
					score = obj.getString(Util.SCORE);
				} catch (JSONException e) {
					Log.e(TAG, e.toString() + ":" + e.getMessage());
				}
				map.clear();
				// now only show the total safety score
				marker = map.addMarker(new MarkerOptions().position(current)
						.title(Util.TITLE).snippet("safety score:" + score));
				break;
			}
			default:
				throw new IllegalArgumentException();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		handler = new MyHandler(MainActivity.this, map);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Log.d(TAG,"onCreate called");
	}
	
	@Override
	public void onResume(){
		super.onResume();	
		if (isGpsAvail() && !isListenGPS) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					5000, 10, locationListener);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					5000, 10, locationListener);
			isListenGPS  = true;
			Log.d(TAG,"requestlocationupdates");
		} else {
			setMap(NASHVILLE);
		}	
	}
	
	@Override
	public void onPause(){
		super.onPause();
        removeGPSLis();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void removeGPSLis(){
		if(isListenGPS){
        	locationManager.removeUpdates(locationListener);
        	isListenGPS = false;
        }
        Log.d(TAG,"removelocationupdates");
	}

	/**
	 * Method to Check GPS is enable or disable
	 * 
	 * @return
	 */
	private Boolean isGpsAvail() {
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		return Settings.Secure.isLocationProviderEnabled(contentResolver,
				LocationManager.GPS_PROVIDER);
	}

	/**
	 * call the spot service to get the safety score from internet and set a
	 * marker in the map
	 * 
	 * @param current
	 */
	public void setMap(LatLng current) {	
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
//		removeGPSLis();
		
		Log.d(TAG, "start service");
		Intent intent = new Intent(MainActivity.this, SpotService.class);
		intent.putExtra(Util.MESSENGER, new Messenger(handler));
		intent.putExtra(Util.LATLNG, current);
		startService(intent);
	}

}
