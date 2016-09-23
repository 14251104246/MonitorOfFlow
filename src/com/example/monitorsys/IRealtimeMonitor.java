package com.example.monitorsys;

import android.content.Context;

public interface IRealtimeMonitor {
	/**
	 * �첽���У���Ҫ������UI�߳���
	 * @return
	 */
	public long getTrafficTotalRxSpeed();
	public long getTrafficTotalTxSpeed();
	public boolean isWifi();
	public boolean isWifiConnected(Context context);

}
