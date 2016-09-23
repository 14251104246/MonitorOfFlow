package com.example.monitorsys;

import android.content.Context;

import com.example.dao.ITrafficData;
import com.example.dao.TrafficData;
/**
 * 通过MonitorcManager获得对象
 * 
 */
public class TrafficMonitor implements IRealtimeMonitor{

	private long trafficTotalRxSpeed;
	private long trafficTotalTxSpeed;
	private ITrafficData trafficdata = new TrafficData();
	private Thread speedMonitorThread = new Thread(new SpeedMonitorRunnable());
	private boolean isStart = false;
	@Override
	public long getTrafficTotalRxSpeed() {
		MonitorIsStart();
		return trafficTotalRxSpeed;
	}

	@Override
	public long getTrafficTotalTxSpeed() {
		MonitorIsStart();
		return trafficTotalTxSpeed;
	}


	private void MonitorIsStart() {
		// TODO Auto-generated method stub
		if(isStart){
			return;
		}
		if(speedMonitorThread.isAlive()!=true){
			startSpeedMonitor();
			isStart=true;
		}
	}

	@Override
	public boolean isWifi() {
		// TODO Auto-generated method stub
		return trafficdata.iswifi();
	}
	public boolean isWifiConnected(Context context) {
		// TODO Auto-generated method stub
		return trafficdata.isWifiConnected(context);
	}
	/**
	 * 开始流量监控
	 * （其他方法的根本）
	 */
	public void startSpeedMonitor(){
		speedMonitorThread.start();
	}
	public void stopSpeedMonitor(){
		speedMonitorThread.stop();
	}
	
	public class SpeedMonitorRunnable implements Runnable{
		long rbegin;
		long tbegin;
		@Override
		public void run() {
			while(true){
				rbegin=trafficdata.getTotalRxFromBoot();
				tbegin=trafficdata.getTotalTxFromBoot();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				trafficTotalRxSpeed=trafficdata.getTotalRxFromBoot()-rbegin;
				trafficTotalRxSpeed=trafficdata.getTotalTxFromBoot()-tbegin;
			}
		}
		
		

	}



}
