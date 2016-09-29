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
	 * ��ֹ��Ϊ�ػ���������������û����
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
		//���ж��Ƿ����½���,today!=trafficbeen.getToday()��֤��ִֻ��һ��
		if(today==trafficbeen.getEndday()&&today!=trafficbeen.getToday()){
			trafficbeen.setMonthlyTraffic(0);
			trafficbeen.setWifiMonthTraffic(0);
			trafficbeen.setTodayTraffic(0);
			trafficbeen.setYesterdayMTraffic(0);
		}
		
		//�ж����ݿ����today�Ƿ��Ѿ����£���û���������monthlyTraffic��wifiMonthlyTraffic��
		if(today!=trafficbeen.getToday()){
			updateWhnBoot();
			trafficbeen.setToday(today);
		}
		
		//��ֹ��Ϊ�ػ���������������û����
		float today=((trafficdata.getTotalGprsFromBoot())+trafficdata.getTotalWifiFromBoot());
		float yesterday=trafficbeen.getYesterdayMTraffic()+trafficbeen.getYesterdayWTraffic();
		if(today<yesterday){
			updateWhnBoot();
		}
		//������������󣬽�����и��²���
		trafficbeen.setYesterdayMTraffic((float)trafficdata.getTotalGprsFromBoot());
		trafficbeen.setYesterdayWTraffic((float)trafficdata.getTotalWifiFromBoot());
		
		
		
		//��������º�
		
		trafficdao.updateTotalTraffic(trafficbeen);
		
	}
	
	private void initdb() {
		dbhelper = new DatabaseHelper(DbUpdateService.this);
		trafficdao=new TotalTrafficDao(dbhelper);
		
	}
	
}
