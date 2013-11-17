package edu.vanderbilt.cs278.safespot;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
	private LatLng LASTGEO = new LatLng(36.1667, -86.7833);
	private final static String LASTLAT = "LASTLAT";
	private final static String LASTLON = "LASTLON";
	private static final String TAG = "MainActivity";
	private EditText editAddr;
	private GoogleMap map;
	private LocationManager locationManager;
	private boolean isListenGPS = false;
	private LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			LatLng current = new LatLng(location.getLatitude(),
					location.getLongitude());
			Log.d(TAG, "get current geocode");
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
		if (savedInstanceState != null) {
			this.LASTGEO = new LatLng(savedInstanceState.getDouble(LASTLAT),
					savedInstanceState.getDouble(LASTLON));
		}

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		handler = new MyHandler(MainActivity.this, map);
		editAddr = (EditText) findViewById(R.id.address);
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		moveMap(LASTGEO);
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putDouble(LASTLAT, LASTGEO.latitude);
		savedInstanceState.putDouble(LASTLON, LASTGEO.longitude);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isGpsAvail() && !isListenGPS) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 5000, 10,
					locationListener);
			isListenGPS = true;
			Log.d(TAG, "requestlocationupdates");
		} else {
			setMap(LASTGEO);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		removeGPSLis();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void runSearch(View v) {
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		Log.d(TAG,"get geocoder");
		try {
			List<Address> addresses = geoCoder.getFromLocationName(editAddr.getText().toString(),1);
			if(addresses!=null && addresses.size()>0){
				Double lat = (double) (addresses.get(0).getLatitude());
	            Double lon = (double) (addresses.get(0).getLongitude());
	            setMap(new LatLng(lat,lon));
			}else{
				Log.e(TAG,"cannot get geocode from input address");
				Toast.makeText(this, "cannot get geocode from input address", Toast.LENGTH_LONG)
				.show();
			}
			
		} catch (IOException e) {
			Log.e(TAG,e.toString()+": "+e.getMessage());
		}
	}

	public void removeGPSLis() {
		if (isListenGPS) {
			locationManager.removeUpdates(locationListener);
			isListenGPS = false;
		}
		Log.d(TAG, "removelocationupdates");
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
		LASTGEO = current;
		moveMap(LASTGEO);
		// removeGPSLis();		
		Log.d(TAG, "start service");
		Intent intent = new Intent(MainActivity.this, SpotService.class);
		intent.putExtra(Util.MESSENGER, new Messenger(handler));
		intent.putExtra(Util.LATLNG, current);
		startService(intent);
	}

	
	public void moveMap(LatLng current){
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
	}
}
