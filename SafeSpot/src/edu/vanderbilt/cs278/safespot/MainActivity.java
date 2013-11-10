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
	private static Handler handler;

	private class MyHandler extends Handler {
		private Context ctx;
		private GoogleMap map;
		private final static String TAG = "MyHandler";

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
				String place = Util.TITLE;
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
				map.addMarker(new MarkerOptions().position(current)
						.title(place).snippet("safety score:" + score));
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
		setLocation();
	}

	/**
	 * Set location according to the gps data, if no gps set to nashville
	 */
	public void setLocation() {
		if (isGpsAvail()) {
			getLocation();
		} else {
			setMap(NASHVILLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * get Location via gps
	 */
	public void getLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				LatLng current = new LatLng(location.getLatitude(),
						location.getLongitude());
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
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				5000, 10, locationListener);
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
		Log.d(TAG, "start service");
		Intent intent = new Intent(MainActivity.this, SpotService.class);
		intent.putExtra(Util.MESSENGER, new Messenger(handler));
		intent.putExtra(Util.LATLNG, current);
		startService(intent);
	}

}
