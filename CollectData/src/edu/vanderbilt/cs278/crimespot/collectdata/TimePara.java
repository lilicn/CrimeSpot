package edu.vanderbilt.cs278.crimespot.collectdata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * help to get time parameter in the url (history timelist or current time)
 * 
 * @author Li
 * 
 */
public class TimePara {

	/**
	 * get the history timelist
	 * 
	 * @return
	 */
	public static List<String> getTimeList() {
		List<String> list = new LinkedList<String>();
		list.add("5/18/2013+00:00:00&de=5/31/2013+23:59:00");
		list.add("6/01/2013+00:00:00&de=6/30/2013+23:59:00");
		list.add("7/01/2013+00:00:00&de=7/31/2013+23:59:00");
		list.add("8/01/2013+00:00:00&de=8/31/2013+23:59:00");
		list.add("9/01/2013+00:00:00&de=9/30/2013+23:59:00");
		list.add("10/01/2013+00:00:00&de=10/31/2013+23:59:00");
		list.add("11/01/2013+00:00:00&de=11/12/2013+23:59:00");
		return list;
	}

	/**
	 * get current time
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		// Date date = new Date();
		String yesterday = dateFormat.format(cal.getTime());
		return yesterday + "+00:00:00&de=" + yesterday + "+23:59:00";
	}

}
