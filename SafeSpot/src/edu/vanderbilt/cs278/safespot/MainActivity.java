package edu.vanderbilt.cs278.safespot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

/**
 * Mainactivity will show the current address in the gmap and the current safety
 * score
 * 
 * @author Li
 * 
 */
@SuppressLint("NewApi")
public class MainActivity extends LogActivity {
	private Context mainCtx;
	private long subZone = 1;
	private final static String SUBZONE = "SUBZONE";
	// pubnub object with attributes PUBLISH_KEY(optional),
	// SUBSCRIBE_KEY(Required), SECRET_KEY(optional), CIPHER_KEY(optional),
	// SSL_ON?
	private Pubnub pubnub = new Pubnub("demo", "demo", "", false);
	private boolean isSub = false;
	private LatLng CURRENTGRO = new LatLng(36.14513101,-86.80215537);
	private final static String LASTLAT = "LASTLAT";
	private final static String LASTLON = "LASTLON";
	private final String TAG = getClass().getSimpleName();
	private EditText editAddr;
	private GoogleMap map;
	private LocationManager locationManager;
	private boolean isListenGPS = false;
	private LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			CURRENTGRO = new LatLng(location.getLatitude(),
					location.getLongitude());
			Log.d(TAG, "updata CURRENTGRO: " + location.getLatitude() + ","
					+ location.getLongitude());
			long temp = Util.getZoneFromGEO(CURRENTGRO);
			if (temp != subZone) {
				if (isSub) {
					runUnSubscribe();
					subZone = temp;
					runSubscribe();
				}
				subZone = temp;
			}

			Log.d(TAG, "get current geocode");
			setMap(CURRENTGRO);
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
	private LocationReview curDisplayLoc = new LocationReview();

	private class MyHandler extends Handler {
		private Context ctx;
		private GoogleMap map;
		private final static String TAG = "MyHandler";
		private Marker marker;
		private List<Marker> list;

		public MyHandler(Context ctx, GoogleMap map) {
			this.ctx = ctx;
			this.map = map;
		}

		@Override
		public void handleMessage(Message msg) {
			Log.d(TAG, "get msg " + msg.what);
			switch (msg.what) {
			case Util.RESPONSE: {
				Bundle data = msg.getData();
				LatLng current = new LatLng(data.getDouble(Util.LAT),
						data.getDouble(Util.LON));
				String info = data.getString(Util.INFO);
				// get score from info
				double score = 0;
				double review = 0;
				try {
					JSONObject obj = new JSONObject(info);
					score = obj.getDouble(Util.SCORE);
					review = obj.getDouble(Util.REVIEW);
					review = review==-1?0:review;
				} catch (JSONException e) {
					Log.e(TAG, e.toString() + ":" + e.getMessage());
				}			
				map.clear();
				// now only show the total safety score
				marker = map.addMarker(new MarkerOptions().position(current)
						.title(Util.TITLE).snippet("Safety score:" + score));
				// set current display location
				curDisplayLoc.setLat(current.latitude);
				curDisplayLoc.setLon(current.longitude);
				curDisplayLoc.setScore(score);
				curDisplayLoc.setReview(review);
				break;
			}
			case Util.RESPONSE_CRIME: {
				Bundle data = msg.getData();
				String nearby = data.getString(Util.CRIMENEARBY);
				list = new ArrayList<Marker>();
				try {
					JSONObject json = new JSONObject(nearby);
					JSONArray crimes = json.getJSONArray("crimes");
					for (int i = 0; i < crimes.length(); i++) {
						addMarker((JSONObject) crimes.get(i));
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
			default:
				break;
			}
		}

		/**
		 * add marker for nearby crimes happened recently
		 * 
		 * @param crime
		 */
		public void addMarker(JSONObject crime) {
			try {
				String type = crime.getString("type");
				String date = crime.getString("date");
				LatLng temp = new LatLng(crime.getDouble("lat"),
						crime.getDouble("lon"));
				list.add(map.addMarker(new MarkerOptions()
						.position(temp)
						.title(type)
						.snippet(date)
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
						.alpha(0.6f)));
				Log.d(TAG, "add crime marker");
			} catch (JSONException e) {
				Log.e(TAG, e.toString() + ": " + e.getMessage());
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		handler = new MyHandler(MainActivity.this, map);
		editAddr = (EditText) findViewById(R.id.address);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		if (savedInstanceState != null) {
			this.CURRENTGRO = new LatLng(savedInstanceState.getDouble(LASTLAT),
					savedInstanceState.getDouble(LASTLON));
			subZone = Util.getZoneFromGEO(CURRENTGRO);
			Log.d(TAG, "onCreate CURRENTGRO: " + CURRENTGRO.latitude + ","
					+ CURRENTGRO.longitude);			
		}
		moveMap(CURRENTGRO);
		setWindowAdp();
	}

	/**
	 * set onclick listerner for marker
	 */
	public void setWindowAdp() {
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						SpotReviewActivity.class);
				intent.putExtra("locationReview", curDisplayLoc);
				startActivity(intent);

			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putDouble(LASTLAT, CURRENTGRO.latitude);
		savedInstanceState.putDouble(LASTLON, CURRENTGRO.longitude);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (isGpsAvail() && !isListenGPS) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 5000, 10,
					locationListener);
			isListenGPS = true;
		} else {
			setMap(CURRENTGRO);
		}
		if (isSub)
			runSubscribe();
	}

	@Override
	public void onStop() {
		super.onStop();
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
		Log.d(TAG, "get geocoder");
		try {
			List<Address> addresses = geoCoder.getFromLocationName(editAddr
					.getText().toString(), 1);
			if (addresses != null && addresses.size() > 0) {
				Double lat = (double) (addresses.get(0).getLatitude());
				Double lon = (double) (addresses.get(0).getLongitude());
				setMap(new LatLng(lat, lon));
			} else {
				Log.e(TAG, "cannot get geocode from input address");
				Toast.makeText(this, "cannot get geocode from input address",
						Toast.LENGTH_LONG).show();
			}

		} catch (IOException e) {
			Log.e(TAG, e.toString() + ": " + e.getMessage());
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
		moveMap(current);
		// start spotservice
		startServiceByClass(SpotService.class, current);
		// start crimeservice
		// show recent crime nearby
		startServiceByClass(CrimeService.class, current);
		Log.d(TAG, "start two services");
		// removeGPSLis();
		removeGPSLis();
	}

	public void startServiceByClass(Class c, LatLng current) {
		Intent intent = new Intent(MainActivity.this, c);
		intent.putExtra(Util.REQUEST_TYPE, Util.GET_REVIEW);
		intent.putExtra(Util.MESSENGER, new Messenger(handler));
		intent.putExtra(Util.LATLNG, current);
		startService(intent);
	}

	public void moveMap(LatLng current) {
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
	}

	/**
	 * Override method for onOptionsItemSelected
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.subOption:
			runSubscribe();
			isSub = true;
			return true;
		case R.id.unsubOption:
			runUnSubscribe();
			isSub = false;
			return true;
		case R.id.pubOption:
			showDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * runSubscribe will subscribe our channel via subnub when use choose
	 * subOption in Menu. It will let the handler to handle the received message
	 * whenever there is a callback.
	 */
	public void runSubscribe() {
		Hashtable<String, String> args = new Hashtable<String, String>(1);
		args.put("channel", Util.getChannelByZone(subZone));
		try {
			pubnub.subscribe(args, new Callback() {
				@Override
				public void connectCallback(String channel, Object message) {
					notifyUser("Successfully SUBSCRIBE!");
				}

				@Override
				public void successCallback(String channel, Object message) {
					if (message instanceof String) {
						notifyUser("RECEIVE MSG: " + message.toString());
					}
				}
			});
		} catch (Exception e) {
			Log.e(TAG, e.toString() + ": " + e.getMessage());
		}
	}

	/**
	 * runUnSubscribe will unsubscribe our channel when use chooses the
	 * unsubOption in the Menu.
	 */
	public void runUnSubscribe() {
		pubnub.unsubscribe(Util.getChannelByZone(subZone));
		notifyUser("Successfully UNSUBSCRIBE");
	}

	public void showDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_warn);
		dialog.setTitle("Send Warn");
		final EditText warning = (EditText) dialog.findViewById(R.id.warning);

		Button send = (Button) dialog.findViewById(R.id.sendButton);
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				publish(warning.getText().toString() + "@"
						+ CURRENTGRO.latitude + "," + CURRENTGRO.longitude);
			}
		});

		Button cancel = (Button) dialog.findViewById(R.id.cancelButton);
		// if button is clicked, close the custom dialog
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/**
	 * publish the message via pubnub.
	 * 
	 * @param msg
	 */
	public void publish(final String msg) {
		Hashtable<String, String> args = new Hashtable<String, String>(2);
		args.put("message", msg);
		args.put("channel", Util.getChannelByZone(subZone));
		pubnub.publish(args, new Callback() {
			@Override
			public void successCallback(String channel, Object message) {
				notifyUser("PUBLISH : " + msg);
			}

			@Override
			public void errorCallback(String channel, PubnubError error) {
				notifyUser("Find crime: " + error);
				Log.e(TAG, error.toString());
			}
		});
	}

	/**
	 * notifyUser will show a message as a toast in the UI
	 * 
	 * @param message
	 */
	private void notifyUser(String message) {
		final String obj = (String) message;
		this.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), obj, Toast.LENGTH_LONG)
						.show();
				Log.i("Received msg : ", obj.toString());
			}
		});
	}
}
