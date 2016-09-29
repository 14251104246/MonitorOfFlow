package com.example.service;

import java.text.SimpleDateFormat;

import com.example.dao.DatabaseHelper;
import com.example.dao.ITotalTrafficDao;
import com.example.dao.ITrafficData;
import com.example.dao.TotalTrafficDao;
import com.example.dao.TrafficData;
import com.example.model.TotalTraffic;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DbUpdateService extends Service {
	//totalTraffic(monthlyTraffic real not null,yesterdayMTraffic real not null,todayTraffic real not null,today integer not null,wifiMonthlyTraffic real not null)
	private int today;
	private DatabaseHelper dbhelper;
	private ITotalTrafficDao trafficdao;
	private TotalTraffic trafficbeen;
	private ITrafficData trafficdata= new TrafficData();
	private Thread dbThread=new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				updateTotalTraffic();
			}
			
		}
	});
	
	@Override
	public void onCreate() {
		
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initdb();
		startThread();
		
		return Service.START_STICKY;
	}
	/**
	 * 防止因为关机，而导致月流量没更新
	 */
	private void updateWhnBoot() {
		trafficbeen.setMonthlyTraffic(trafficbeen.getMonthlyTraffic()+trafficbeen.getYesterdayMTraffic());
		trafficbeen.setWifiMonthTraffic(trafficbeen.getWifiMonthTraffic()+trafficbeen.getYesterdayWTraffic());
		
		trafficbeen.setYesterdayMTraffic(0);
		trafficbeen.setYesterdayWTraffic(0);
		trafficbeen.setToday(today);
		
	}
	private void startThread() {
		if(!dbThread.isAlive()){
			dbThread.start();
		}
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	private void updateTotalTraffic(){
		SimpleDateFormat format= new SimpleDateFormat("dd");
		String date = format.format(new java.util.Date());
		today = Integer.parseInt(date);
		trafficbeen=trafficdao.queryTotalTraffic();
		//先判断是否到了月结日,today!=trafficbeen.getToday()保证他只执行一次
		if(today==trafficbeen.getEndday()&&today!=trafficbeen.getToday()){
			trafficbeen.setMonthlyTraffic(0);
			trafficbeen.setWifiMonthTraffic(0);
			trafficbeen.setTodayTraffic(0);
			trafficbeen.setYesterdayMTraffic(0);
		}
		
		//判断数据库里的today是否已经更新，若没更新则更新monthlyTraffic和wifiMonthlyTraffic。
		if(today!=trafficbeen.getToday()){
			updateWhnBoot();
			trafficbeen.setToday(today);
		}
		
		//防止因为关机，而导致月流量没更新
		float today=((trafficdata.getTotalGprsFromBoot())+trafficdata.getTotalWifiFromBoot());
		float yesterday=trafficbeen.getYesterdayMTraffic()+trafficbeen.getYesterdayWTraffic();
		if(today<yesterday){
			updateWhnBoot();
		}
		//昨天流量清零后，今天进行更新操作
		trafficbeen.setYesterdayMTraffic((float)trafficdata.getTotalGprsFromBoot());
		trafficbeen.setYesterdayWTraffic((float)trafficdata.getTotalWifiFromBoot());
		
		
		
		//完成所有事后
		
		trafficdao.updateTotalTraffic(trafficbeen);
		
	}
	
	private void initdb() {
		dbhelper = new DatabaseHelper(DbUpdateService.this);
		trafficdao=new TotalTrafficDao(dbhelper);
		
	}
	
}
