package com.BLXY.server.data;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.BLXY.shared.CrimeEvent;
import com.BLXY.shared.GPSData;
import com.BLXY.util.Utils;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GPSData  gps = new GPSData(36.142788, -86.81513);
		TimerTask timerTask = new DataCollectorThread(gps);
		Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 1*1000);
        while(true){}
	}

}
