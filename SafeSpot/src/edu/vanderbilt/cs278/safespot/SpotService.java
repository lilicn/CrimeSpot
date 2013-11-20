package edu.vanderbilt.cs278.safespot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * an IntentService to interact with the server to get the safety information of
 * the current place
 * 
 * @author Li
 * 
 */
public class SpotService extends IntentService {
	private final static String TAG = "SpotService";

	public SpotService() {
		super("SpotService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "handler start");
		int type = intent.getExtras().getInt(Util.REQUEST_TYPE);
		if(type == Util.Request_Type.GET_REVIEW.ordinal()){
			LatLng latlng = (LatLng) intent.getExtras().get(Util.LATLNG);
			Log.d(TAG, latlng.latitude + "," + latlng.longitude);
			Messenger messenger = (Messenger) intent.getExtras()
					.get(Util.MESSENGER);
	
			HttpPost post = new HttpPost(Util.URL);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair(Util.TYPE, Util.GEO));
			nameValuePairs.add(new BasicNameValuePair(Util.LAT, latlng.latitude
					+ ""));
			nameValuePairs.add(new BasicNameValuePair(Util.LON, latlng.longitude
					+ ""));
			try {
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				String responseText = EntityUtils.toString(entity);
	
				Message msg = Message.obtain();
				msg.what = Util.RESPONSE;
				Bundle bundle = new Bundle();
				bundle.putString(Util.INFO, responseText);
				bundle.putDouble(Util.LAT, latlng.latitude);
				bundle.putDouble(Util.LON, latlng.longitude);
				msg.setData(bundle);
				Log.d(TAG, responseText);
				// send message
				messenger.send(msg);
	
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, e.toString() + ":" + e.getMessage());
			} catch (ClientProtocolException e) {
				Log.e(TAG, e.toString() + ":" + e.getMessage());
			} catch (IOException e) {
				Log.e(TAG, e.toString() + ":" + e.getMessage());
			} catch (RemoteException e) {
				Log.e(TAG, e.toString() + ":" + e.getMessage());
			}
		}else if(type == Util.Request_Type.SEND_REVIEW.ordinal()){
			LatLng latlng = (LatLng) intent.getExtras().get(Util.LATLNG);
			Log.d(TAG, latlng.latitude + "," + latlng.longitude);
			Messenger messenger = (Messenger) intent.getExtras()
					.get(Util.MESSENGER);
			Double rating = intent.getExtras().getDouble(Util.REVIEW);
			
			HttpPost post = new HttpPost(Util.URL);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair(Util.TYPE, Util.GEO));
			nameValuePairs.add(new BasicNameValuePair(Util.LAT, latlng.latitude
					+ ""));
			nameValuePairs.add(new BasicNameValuePair(Util.LON, latlng.longitude
					+ ""));
			nameValuePairs.add(new BasicNameValuePair(Util.REVIEW,rating+ ""));
			try {
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				String responseText = EntityUtils.toString(entity);
				
				
				Message msg = Message.obtain();
				msg.what = Util.RESPONSE;
				Bundle bundle = new Bundle();
				bundle.putString(Util.INFO, responseText);
				
				msg.setData(bundle);
				Log.d(TAG, responseText);
				// send message
				messenger.send(msg);
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, e.toString() + ":" + e.getMessage());
			} catch (ClientProtocolException e) {
				Log.e(TAG, e.toString() + ":" + e.getMessage());
			} catch (IOException e) {
				Log.e(TAG, e.toString() + ":" + e.getMessage());
			} catch (RemoteException e) {
				Log.e(TAG, e.toString() + ":" + e.getMessage());
			}
		}

	}

}
