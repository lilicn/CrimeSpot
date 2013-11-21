package edu.vanderbilt.cs278.safespot;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SpotReviewActivity extends Activity {
	private static final String TAG = "SpotReviewActivity";
	private LocationReview currentLoc = null;
	private GoogleMap map;
	private RatingBar ratingBar;
	private ArrayAdapter<LocationReview> listAdapter;
	private List<LocationReview> reviewLists = new ArrayList<LocationReview> ();
	private LocationReview[] locationsreivews = null;
	private ListView list;
	private Context mContext;
	private static Handler handler;
	private EditText text;

	private class MyHandler extends Handler {
		private Context ctx;
		private final static String TAG = "MyHandler";
		private long zone;

		public MyHandler(Context ctx, long zone) {
			this.ctx = ctx;
			this.zone = zone;
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
					if(temp==zone){
						for(int i = 10; i>=0; i--){
							
						}
					}
					break;
				}
				case Util.SEND_REVIEW:{
					// do nothing now
					break;
				}
				case Util.SEND_STAR:{				
					// do nothing now
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
		reviewLists = getListData();
		showList();

		currentLoc = (LocationReview) bundle.getSerializable("locationReview");
		initializeMap();
		handler = new MyHandler(SpotReviewActivity.this,Util.getZoneFromGEO(new LatLng(currentLoc.getLat(),currentLoc.getLon())));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spot_review, menu);

		return true;
	}

	private List<LocationReview> getListData() {
		List<LocationReview> reviews = new ArrayList<LocationReview>();
		for (int i = 0; i < 5; i++) {
			reviews.add(new LocationReview());
		}
		return reviews;
	}

	private void initializeMap() {
		LatLng current = new LatLng(currentLoc.getLat(), currentLoc.getLon());
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
		map.addMarker(new MarkerOptions().position(current));
	}

	public void onSubmitClick(View v) {
		Toast.makeText(this, "Sending Review to Server", Toast.LENGTH_SHORT)
				.show();
		Double rating = (double) (ratingBar.getRating());
		Intent intent = makeIntentByType(Util.SEND_STAR);
		intent.putExtra(Util.REVIEW, rating);
		startService(intent);

	}

	public void onSubmitComment(View v) {
		String comment = text.getText().toString();
		if (comment != null) {
			LocationReview temp = new LocationReview();
			temp.setComment(comment);
			reviewLists.add(0,temp);
			showList();		
			Intent intent = makeIntentByType(Util.SEND_REVIEW);
			intent.putExtra(Util.REVIEW, comment);
			startService(intent);
		} else
			Toast.makeText(this, "Comment cannot be null", Toast.LENGTH_SHORT)
					.show();
	}

	public Intent makeIntentByType(int SEND_TYPE) {
		Intent intent = new Intent(this, SpotService.class);
		intent.putExtra(Util.REQUEST_TYPE, SEND_TYPE);
		intent.putExtra(Util.MESSENGER, new Messenger(handler));
		intent.putExtra(Util.LATLNG,
				new LatLng(currentLoc.getLat(), currentLoc.getLon()));
		return intent;
	}
	
	public void showList(){
		listAdapter = new ArrayAdapterItem(this, R.layout.list_item,
				reviewLists);

		list.setAdapter(listAdapter);
	}
}
