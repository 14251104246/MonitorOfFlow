package com.example.monitorsys;

public interface ITrafficMonitor {
	/**
	 * �첽���У���Ҫ������UI�߳���
	 * @return
	 */
	public long getTrafficTotalRxSpeed();
	public long getTrafficTotalTxSpeed();
	public boolean isWifi();
}
