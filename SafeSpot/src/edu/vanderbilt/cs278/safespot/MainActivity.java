package edu.vanderbilt.cs278.safespot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private static final LatLng NASHVILLE = new LatLng(36.1667, -86.7833);
	private LatLng current;
	private GoogleMap map;
	private Marker marker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		setLocation();
	}

	public void setLocation() {
		if (isGpsAvail()) {
			getLocation();
		} else {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(NASHVILLE, 15));
			marker = map.addMarker(new MarkerOptions().position(
					NASHVILLE).title("Nashville").snippet("Safety score: 10"));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void getLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				current = new LatLng(location.getLatitude(), location.getLongitude());
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
				marker = map.addMarker(new MarkerOptions().position(
						current).title("You place").snippet("Safety score: 10"));
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

	/*----Method to Check GPS is enable or disable ----- */
	private Boolean isGpsAvail() {
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		return Settings.Secure.isLocationProviderEnabled(contentResolver,
				LocationManager.GPS_PROVIDER);
	}

}
