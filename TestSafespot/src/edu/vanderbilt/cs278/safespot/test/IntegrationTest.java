package edu.vanderbilt.cs278.safespot.test;

import java.io.IOException;
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
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import edu.vanderbilt.cs278.safespot.SpotReviewActivity;
import edu.vanderbilt.cs278.safespot.Util;

/**
 * Integration test for Android client and Google app engine server
 * 
 * @author Li
 * 
 */
@SuppressLint("NewApi")
public class IntegrationTest extends
		ActivityInstrumentationTestCase2<SpotReviewActivity> {
	private SpotReviewActivity activity;
	private double latitude = 36.14513101;
	private double longitude = -86.80215537;
	private String url = "http://1.tidal-analogy-395.appspot.com/crimespotserver";
	private HttpPost post;
	private List<NameValuePair> nameValuePairs;
	private final static String TESTREVIEW = "test review 001";

	public IntegrationTest() {
		super(SpotReviewActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
	}

	/**
	 * Test send review to server
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public void testSenReview() throws ClientProtocolException, IOException,
			JSONException, InterruptedException {
		int addText = edu.vanderbilt.cs278.safespot.R.id.ratingBar2;
		final EditText editAddr = (EditText) activity.findViewById(addText);

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				editAddr.setText(TESTREVIEW);
			}
		});
		getInstrumentation().waitForIdleSync();
		assertEquals("Text incorrect", TESTREVIEW, editAddr.getText()
				.toString());

		int buttonId = edu.vanderbilt.cs278.safespot.R.id.comment_submit;
		assertNotNull(activity.findViewById(buttonId));
		final Button submit = (Button) activity.findViewById(buttonId);
		assertEquals("Send", submit.getText());
		// assertTrue("submit onClick", submit.callOnClick());
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				assertTrue("submit onClick", submit.performClick());
			}
		});
		getInstrumentation().waitForIdleSync();

		Thread.sleep(2000);
		checkReview();
	}

	/**
	 * check the latest review is correct
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public void checkReview() throws ClientProtocolException, IOException,
			JSONException {
		// get new review from server
		post = new HttpPost(url);
		nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair(Util.TYPE, Util.GEO));
		nameValuePairs.add(new BasicNameValuePair(Util.LAT, latitude + ""));
		nameValuePairs.add(new BasicNameValuePair(Util.LON, longitude + ""));

		nameValuePairs.add(new BasicNameValuePair(Util.REQUEST_TYPE,
				Util.GET_LIST + ""));

		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String responseText = EntityUtils.toString(entity);
		JSONObject obj = new JSONObject(responseText);
		assertNotNull(responseText, responseText);
		for (int i = 1; i <= 10; i++) {
			assertNotNull(obj.getString(i + "") != null);
		}

		// check latest review
		assertTrue(responseText, obj.getString("10").toString()
				.startsWith(TESTREVIEW));
	}

}
