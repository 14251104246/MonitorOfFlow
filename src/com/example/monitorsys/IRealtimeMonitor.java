package com.example.monitorsys;

import android.content.Context;

public interface IRealtimeMonitor {
	/**
	 * 异步运行，不要运行在UI线程中
	 * @return
	 */
	public long getTrafficTotalRxSpeed();
	public long getTrafficTotalTxSpeed();
	public boolean isWifi();
	public boolean isWifiConnected(Context context);

}
