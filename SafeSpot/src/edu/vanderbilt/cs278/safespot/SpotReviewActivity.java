package edu.vanderbilt.cs278.safespot;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * details information to display
 * 
 * @author Di & Li
 * 
 */
public class SpotReviewActivity extends Activity {
	private static final String TAG = "SpotReviewActivity";
	private LocationReview currentLoc = new LocationReview();
	private GoogleMap map;
	private RatingBar ratingBar;
	private ArrayAdapter<String> listAdapter;
	private List<String> reviewLists = new ArrayList<String>();
	private ListView list;
	private static Handler handler;
	private EditText text;

	private class MyHandler extends Handler {
		private final static String TAG = "MyHandler";
		private long zone;
		private List<String> reviewLists;

		public MyHandler(long zone, List<String> reviewLists) {
			this.zone = zone;
			this.reviewLists = reviewLists;
		}

		@Override
		public void handleMessage(Message msg) {
			Log.d(TAG, "get msg");
			Bundle data = msg.getData();
			String res = data.getString(Util.INFO);
			try {
				JSONObject json = new JSONObject(res);
				switch (msg.what) {
				case Util.GET_LIST: {
					long temp = json.getLong(Util.ZONE);
					if (temp == zone) {
						// from newest to oldest
						for (int i = 1; i <= 10; i++) {
							String com = null;
							if (!(com = json.getString(i + "")).equals("")) {
								reviewLists.add(com);
							}
						}
						showList();
					}
					break;
				}
				case Util.SEND_REVIEW: {
					// do nothing now
					Log.d(TAG, "send review successfully");
					break;
				}
				case Util.SEND_STAR: {
					// do nothing now
					Log.d(TAG, "send star successfully");
					break;
				}
				default:
					throw new IllegalArgumentException();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		list = (ListView) findViewById(R.id.ListView1);
		text = (EditText) findViewById(R.id.ratingBar2);

		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			currentLoc = (LocationReview) bundle.getSerializable("locationReview");
		}
		
		initializeMap();
		handler = new MyHandler(Util.getZoneFromGEO(new LatLng(currentLoc
				.getLat(), currentLoc.getLon())), reviewLists);
		getListData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spot_review, menu);

		return true;
	}

	/**
	 * start service to get list data
	 */
	private void getListData() {
		Intent intent = makeIntentByType(Util.GET_LIST);
		startService(intent);
	}

	/**
	 * initilize the map location
	 */
	private void initializeMap() {
		LatLng current = new LatLng(currentLoc.getLat(), currentLoc.getLon());
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
		map.addMarker(new MarkerOptions().position(current));
	}

	/**
	 * submit review score
	 * 
	 * @param v
	 */
	public void onSubmitClick(View v) {
		Toast.makeText(this, "Sending Review to Server", Toast.LENGTH_SHORT)
				.show();
		Double rating = (double) (ratingBar.getRating());
		Intent intent = makeIntentByType(Util.SEND_STAR);
		intent.putExtra(Util.REVIEW, rating);
		startService(intent);

	}

	/**
	 * submit comment
	 * 
	 * @param v
	 */
	public void onSubmitComment(View v) {
		String comment = text.getText().toString();
		if (comment != null) {
			LocationReview temp = new LocationReview();
			reviewLists.add(0, comment);
			showList();
			Intent intent = makeIntentByType(Util.SEND_REVIEW);
			intent.putExtra(Util.REVIEW, comment);
			startService(intent);
		} else
			Toast.makeText(this, "Comment cannot be null", Toast.LENGTH_SHORT)
					.show();
	}

	/**
	 * get intent by send type
	 * 
	 * @param SEND_TYPE
	 * @return
	 */
	public Intent makeIntentByType(int SEND_TYPE) {
		Intent intent = new Intent(this, SpotService.class);
		intent.putExtra(Util.REQUEST_TYPE, SEND_TYPE);
		intent.putExtra(Util.MESSENGER, new Messenger(handler));
		intent.putExtra(Util.LATLNG,
				new LatLng(currentLoc.getLat(), currentLoc.getLon()));
		return intent;
	}

	/**
	 * show list in the UI
	 */
	public void showList() {
		listAdapter = new ArrayAdapterItem(this, R.layout.list_item,
				reviewLists);
		list.setAdapter(listAdapter);
	}
}
