package edu.vanderbilt.cs278.safespot;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import edu.vanderbilt.cs278.safespot.crimedata.DataCollectorThread;
import edu.vanderbilt.cs278.safespot.crimedata.GPSData;

/**
 * it is used to get nearby crimes happened recently
 * 
 * @author Li
 * 
 */
public class CrimeService extends IntentService {
	private final String TAG = getClass().getSimpleName();

	public CrimeService() {
		super("CrimeService");
	}

	/**
	 * Override method for onHandleIntent
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		LatLng current = (LatLng) intent.getExtras().get(Util.LATLNG);
		Log.d(TAG, current.latitude + "," + current.longitude);

		GPSData gps = new GPSData(current.latitude, current.longitude);
		String crimeNearby = DataCollectorThread.getCrimeNearby(gps);

		Messenger messenger = (Messenger) intent.getExtras()
				.get(Util.MESSENGER);
		Message msg = Message.obtain();
		msg.what = Util.RESPONSE_CRIME;
		Bundle bundle = new Bundle();
		bundle.putString(Util.CRIMENEARBY, crimeNearby);
		msg.setData(bundle);
		// send message
		try {
			messenger.send(msg);
		} catch (RemoteException e) {
			Log.e(TAG, e.toString() + ": " + e.getMessage());
		}
	}

}
