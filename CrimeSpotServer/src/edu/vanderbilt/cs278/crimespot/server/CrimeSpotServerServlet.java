package edu.vanderbilt.cs278.crimespot.server;

import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import edu.vanderbilt.cs278.crimespot.server.data.Comment;
import edu.vanderbilt.cs278.crimespot.server.data.PMF;
import edu.vanderbilt.cs278.crimespot.server.data.Util;
import edu.vanderbilt.cs278.crimespot.server.data.Zone;

/**
 * Servlet to doget and dopost Go to http://localhost:8888/crimespotserver
 * 
 * @author Li
 * 
 */
public class CrimeSpotServerServlet extends HttpServlet {
	private PersistenceManager pm;
	private final static String PATH = "data/";

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
	 * need modified ( now take them as 1 zone) initialize data store from crime
	 * report stored in s3
	 * 
	 * @throws IOException
	 */
	public void initData() throws IOException {
		pm = PMF.get().getPersistenceManager();
		Util.saveDataFromSrc(PATH, pm);
		pm.close();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println(sendBasic(1));
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		int type = Integer.parseInt(req.getParameter(Util.REQUEST_TYPE));
		resp.setContentType("text/plain");
		switch (type) {
		case Util.GET_REVIEW:
			resp.getWriter().println(sendBasic(getZone(req)));
			break;
		case Util.SEND_STAR:
			// save star
			double star =Double.parseDouble(req.getParameter(Util.REVIEW));
			resp.getWriter().println(saveStar(getZone(req),star));
			break;

		case Util.SEND_REVIEW:
			// save review
			String revi = req.getParameter(Util.REVIEW);
			resp.getWriter().println(saveReview(getZone(req),revi));
			break;
		case Util.GET_LIST:
			// send all review
			resp.getWriter().println(sendList(getZone(req)));
			break;
		default:
			break;
		}
	}

	public String sendBasic(long id) {
		pm = PMF.get().getPersistenceManager();
		Zone res = pm.getObjectById(Zone.class, id);
		Zone result = pm.detachCopy(res);
		pm.close();
		return makeObj(result).toString();
	}

	public String saveReview(long id, String rev) {
		pm = PMF.get().getPersistenceManager();
		Comment comm = pm.getObjectById(Comment.class,id);
		comm.addComment(rev);
		comm.saveComment(pm);
		pm.close();		
		return "success";
	}

	public String saveStar(long id, double rev) {
		pm = PMF.get().getPersistenceManager();
		Zone res = pm.getObjectById(Zone.class, id);
		res.addUserRev(rev);
		res.saveZone(pm);
		pm.close();
		return "success";
	}

	public String sendList(long id) {
		pm = PMF.get().getPersistenceManager();
		Comment comm = pm.getObjectById(Comment.class,id);
		ArrayList<String> list = comm.getList();
		JSONObject json = new JSONObject();
		try {
			json.put(Util.ZONE, comm.getID());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pm.close();
		int size = list.size();
		
		for(int i = size; i>=0; i--){
			// only return the last ten comments
			if(size-i<10){
				try {
					json.put(i-size+"", list.get(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return json.toString();
	}

	/**
	 * get zone id according to the parameter of request
	 * 
	 * @param req
	 * @return
	 */
	public long getZone(HttpServletRequest req) {
		long zone = 1;
		String addType = req.getParameter("type");
		switch (addType) {
		case "geo":
			double latitude = Double.parseDouble(req.getParameter("lat"));
			double longitude = Double.parseDouble(req.getParameter("lon"));
			zone = Util.getZoneFromGeo(latitude, longitude);
			break;
		case "zone":
			zone = Long.parseLong(req.getParameter("zone"));
			break;

		default:
			break;
		}
		return zone;
	}

	/**
	 * make JSONObject to send to client
	 * 
	 * @param result
	 *            detached zone object
	 * @return
	 */
	public JSONObject makeObj(Zone result) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("zone id", result.getID());
			obj.put("safety point", result.getTotalVal());
			obj.put("user review", result.getAveRev());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return obj;
	}
}
