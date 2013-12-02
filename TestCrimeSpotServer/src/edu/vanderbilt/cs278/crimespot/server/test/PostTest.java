package edu.vanderbilt.cs278.crimespot.server.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.junit.Before;
import org.junit.Test;

/**
 * doPost test for CrimeSpotServer
 * 
 * @author Li
 * 
 */
public class PostTest {
	private final static double latitude = 36.14513101;
	private final static double longitude = -86.80215537;
	private HttpPost post;
	private List<NameValuePair> nameValuePairs;

	@Before
	public void before() {
		post = new HttpPost(Util.URL);
		nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair(Util.TYPE, Util.GEO));
		nameValuePairs.add(new BasicNameValuePair(Util.LAT, latitude + ""));
		nameValuePairs.add(new BasicNameValuePair(Util.LON, longitude + ""));

	}

	@Test
	public void getReviewTest() throws ClientProtocolException {
		nameValuePairs.add(new BasicNameValuePair(Util.REQUEST_TYPE,
				Util.GET_REVIEW + ""));
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
			JSONObject obj = new JSONObject(responseText);
			assertTrue("default zone id is 2", obj.getInt(Util.ID) == 2);
			assertNotNull(obj.getDouble(Util.SCORE));
			assertNotNull(obj.getDouble(Util.REVIEW));
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sendReviewTest() {
		nameValuePairs.add(new BasicNameValuePair(Util.REQUEST_TYPE,
				Util.SEND_REVIEW + ""));
		nameValuePairs.add(new BasicNameValuePair(Util.REVIEW, Util.TESTREVIEW ));
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
			assertTrue(responseText, responseText.startsWith(Util.SUCCESS));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sendStarTest() {
		nameValuePairs.add(new BasicNameValuePair(Util.REQUEST_TYPE,
				Util.SEND_STAR + ""));
		double star = 4;
		nameValuePairs.add(new BasicNameValuePair(Util.REVIEW, star + ""));
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
			System.out.println(responseText);
			assertTrue(responseText, responseText.startsWith(Util.SUCCESS));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getListTest() throws InterruptedException {
		nameValuePairs.add(new BasicNameValuePair(Util.REQUEST_TYPE,
				Util.GET_LIST + ""));
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
			System.out.println(responseText);
			JSONObject obj = new JSONObject(responseText);
			assertNotNull(responseText, responseText);
			for (int i = 1; i <= 10; i++) {
				assertNotNull(obj.getString(i + "") != null);
			}
			// send review
			before();
			sendReviewTest();
			// check latest review
			assertTrue(obj.getString("10").toString(), obj.getString("10")
					.toString().startsWith(Util.TESTREVIEW ));
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}
