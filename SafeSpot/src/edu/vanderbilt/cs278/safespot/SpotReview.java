package edu.vanderbilt.cs278.safespot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SpotReview extends Activity {
	private static final String TAG = "SpotReviewActivity";
	private LocationReview currentLoc = null;
	private GoogleMap map;
	private RatingBar ratingBar_people;
	private RatingBar ratingBar_light;
	private RatingBar ratingBar_facility;
	private Button btn_sumbit;
	private ArrayAdapter<LocationReview> listAdapter;
	private LocationReview[] locationsreivews = null;
	private Spinner crime_type;
	private ListView list;
	private Context mContext;
	private static Handler handler;

	private class MyHandler extends Handler {
		private Context ctx;
		private final static String TAG = "MyHandler";

		public MyHandler(Context ctx) {
			this.ctx = ctx;
		}

		@Override
		public void handleMessage(Message msg) {
			Log.d(TAG, "get msg");
			switch (msg.what) {
			case Util.RESPONSE: {
				Bundle data = msg.getData();
				String responseTxt = data.getString(Util.INFO);
				Log.i(TAG, responseTxt);
				// Toast.makeText(mContext, "Server: "+ responseTxt,
				// Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				throw new IllegalArgumentException();

			}

		}
	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spot_review);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		ratingBar_facility = (RatingBar) findViewById(R.id.ratingBar1);
		ratingBar_light = (RatingBar) findViewById(R.id.ratingBar2);
		ratingBar_people = (RatingBar) findViewById(R.id.ratingBar3);
		btn_sumbit = (Button) findViewById(R.id.btn_submit);
		crime_type = (Spinner) findViewById(R.id.spinner1);
		list = (ListView) findViewById(R.id.ListView1);

		Bundle bundle = getIntent().getExtras();
		locationsreivews = getListData();
		listAdapter = new ArrayAdapterItem(this, R.layout.list_item,
				locationsreivews);

		list.setAdapter(listAdapter);

		currentLoc = (LocationReview) bundle.getSerializable("locationReview");
		initializeMap();
		handler = new MyHandler(SpotReview.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spot_review, menu);

		return true;
	}

	private LocationReview[] getListData() {
		LocationReview[] reviews = new LocationReview[5];
		for (int i = 0; i < 5; i++) {
			reviews[i] = new LocationReview();
		}
		return reviews;
	}

	private void initializeMap() {
		LatLng current = new LatLng(currentLoc.lat, currentLoc.lon);
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
		map.addMarker(new MarkerOptions().position(current));
	}

	public void onSubmitClick(View v) {
		Toast.makeText(this, "Sending Review to Server", Toast.LENGTH_SHORT)
				.show();

		Double rating = (double) ((ratingBar_facility.getRating()
				+ ratingBar_light.getRating() + ratingBar_people.getRating()) / 3);

		Log.d(TAG, "start service");
		Intent intent = new Intent(this, SpotService.class);
		intent.putExtra(Util.REQUEST_TYPE,
				Util.Request_Type.SEND_REVIEW.ordinal());
		intent.putExtra(Util.MESSENGER, new Messenger(handler));
		intent.putExtra(Util.LATLNG, new LatLng(currentLoc.lat, currentLoc.lon));
		intent.putExtra(Util.REVIEW, rating);
		startService(intent);

	}
}
