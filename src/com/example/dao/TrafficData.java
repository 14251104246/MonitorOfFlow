package com.example.dao;

import com.example.util.FileUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
/**
 * ×îµ×²ãÊý¾Ý
 * @author 123
 *
 */
public class TrafficData implements ITrafficData{

	
	@Override
	public long getTotalRxFromBoot() {
		
		return TrafficStats.getTotalRxBytes();
	}
	@Override
	public long getTotalTxFromBoot() {
		// TODO Auto-generated method stub
		return TrafficStats.getTotalTxBytes();
	}
	public long getUidRxBytesFromBoot(int uid){
		return TrafficStats.getUidRxBytes(uid);
	}
	public long getUidTxBytesFromBoot(int uid){
		return TrafficStats.getUidTxBytes(uid);
	}
	
	public boolean iswifi(){		
		return FileUtil.wlan0FileIsNotNull();		
	}
	@Override
	public long getTotalWifiFromBoot() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long getUidRxBytes(int uid) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long getUidTxBytes(int uid) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long getGprsRxFromBoot() {
		// TODO Auto-generated method stub
		return TrafficStats.getMobileRxBytes();
	}
	@Override
	public long getGprsTxFromBoot() {
		// TODO Auto-generated method stub
		return TrafficStats.getMobileTxBytes();
	}
	
	
	public boolean isWifiConnected(Context context)
	{
	        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	        if(wifiNetworkInfo.isConnected())
	        {
	            return true ;
	        }
	      
	        return false ;
	 }
}
