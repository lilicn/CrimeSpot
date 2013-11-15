package edu.vanderbilt.cs278.crimespot.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import edu.vanderbilt.cs278.crimespot.server.data.PMF;
import edu.vanderbilt.cs278.crimespot.server.data.Util;
import edu.vanderbilt.cs278.crimespot.server.data.Zone;

/**
 * Servlet to doget and dopost 
 * Go to http://localhost:8888/crimespotserver
 * 
 * @author Li
 *
 */
public class CrimeSpotServerServlet extends HttpServlet {
	private PersistenceManager pm;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			initData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * need modified ( now take them as 1 zone)
	 * initialize data store from crime report stored in s3
	 * @throws IOException
	 */
	public void initData() throws IOException {
		pm = PMF.get().getPersistenceManager();
		Zone zone = new Zone();
		zone.setID(1);
		Util.saveDataFromSrc(
				"https://s3.amazonaws.com/mycrimedata/nashville.txt", zone);
		zone.setPoint(800);
		pm.makePersistent(zone);
		pm.close();
		System.out.println("Zone "+zone.getPoint()+" has created!");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println(makeResp(1));
	}


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println(makeResp(getZone(req)));
	}

	/**
	 * get zone id according to the parameter of request
	 * @param req
	 * @return
	 */
	public long getZone(HttpServletRequest req) {
		long zone = 1;
		String addType = req.getParameter("type");
		switch (addType) {
		case "geo":
			double latitude = Double.parseDouble(req.getParameter("lat"));
			double longitude = Double
					.parseDouble(req.getParameter("lon"));
			zone = Util.getZoneFromGeo(latitude, longitude);
			break;
		case "add":
			zone = Util.getZoneFromAdd(req.getParameter("add"));
			break;

		default:
			break;
		}
		return zone;
	}

	/**
	 * make response according to the zone id
	 * @param id
	 * @return
	 */
	public String makeResp(long id) {
		pm = PMF.get().getPersistenceManager();
		Zone res = pm.getObjectById(Zone.class, id);
		Zone result = pm.detachCopy(res);
		HashMap<String, Long> crimeMap = res.getCrimeMap();
		pm.close();
		return makeObj(result, crimeMap).toString();
	}
	
	/**
	 * make JSONObject to send to client
	 * @param result detached zone object
	 * @param crimeMap hashmap to store the number of different kinds of crime
	 * @return
	 */
	public JSONObject makeObj(Zone result, HashMap<String, Long> crimeMap){
		JSONObject obj = new JSONObject();
		try {			
			obj.put("zone id", result.getID());
			obj.put("safety point", result.getPoint());
			obj.put("total num", result.getNum());
			Set<String> set = crimeMap.keySet();
			for(String key:set){
				obj.put(key, crimeMap.get(key));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return obj;
	}
}
