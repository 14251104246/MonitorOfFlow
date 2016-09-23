package com.example.service;

import com.example.dao.ITrafficData;
import com.example.dao.TrafficData;
import com.example.monitorsys.IMonitorManager;
import com.example.monitorsys.IRealtimeMonitor;
import com.example.monitorsys.MonitorManager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TrafficMonitorService extends Service{
	private long TrafficTotalRxSpeed;
	private ITrafficData trafficdata;
	private Intent intent = new Intent("com.example.monitorofflow.RECEIVER");
	
	//流量数据获取
	private IMonitorManager manager;
	private IRealtimeMonitor monitor;
	private Intent Smallintent = new Intent("com.example.service.RECEIVER");
	
	public long getTrafficTotalRxSpeed() {
		
		return TrafficTotalRxSpeed;
	}
	
	public void startTotalRxSpeedMonitor(){
		if(trafficdata==null){
			trafficdata=new TrafficData();
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					long begin=trafficdata.getTotalRxFromBoot();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					TrafficTotalRxSpeed=trafficdata.getTotalRxFromBoot()-begin;
					
					intent.putExtra("rx", TrafficTotalRxSpeed);
					sendBroadcast(intent);
				}
			}
		}).start();
	}
	
	private void sendRealTimeTrafficData() {
		manager = MonitorManager.getInstance();
		monitor = manager.getTrafficMonitor();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//
				String type;
				String text = null;
				String padding=" k/s";
				float rx = 0;
				float tx = 0;
				
				for(int i = 0;true;i++){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					
					rx=(float) ((float) (monitor.getTrafficTotalRxSpeed())/1000.0);
					tx=(float) ((float) (monitor.getTrafficTotalTxSpeed())/1000.0);
					
					if(monitor.isWifiConnected((Context)TrafficMonitorService.this)==true){
						type="wifi";
					}else{
						type="gprs";
					}
					if(i%2==0){
						text=type;
					}else{
						if(rx>tx){
							text="v "+rx+padding;
						}else{
							text="^"+tx+padding;
						}
					}
					if(i%5==0){
						text="^"+tx+padding;
					}
					i=i%100;
					
				//	mFloatView.setText(text);
					Smallintent.putExtra("RealTimeTrafficData", text);
					sendBroadcast(Smallintent);
				}
				
			}
		}).start();
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startTotalRxSpeedMonitor();
		sendRealTimeTrafficData();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		if(trafficdata==null){
			trafficdata=new TrafficData();
		}
		return new TrafficMonitorBinder();
	}
	
	public class TrafficMonitorBinder extends Binder{
		public TrafficMonitorService getService(){
			return TrafficMonitorService.this;
		}
	}

}
