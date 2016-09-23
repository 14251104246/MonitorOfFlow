package com.example.dao;

import android.content.Context;

public interface ITrafficData {
	public long getTotalRxFromBoot();
	public long getTotalTxFromBoot();
	public long getGprsRxFromBoot();
	public long getGprsTxFromBoot();
	public long getUidRxBytesFromBoot(int uid);
	public long getUidTxBytesFromBoot(int uid);
	/**
	 * 获得指定app从第一次打开到现在消耗的流量总数
	 * @param uid
	 * @return
	 */
	public long getUidRxBytes(int uid);
	public long getUidTxBytes(int uid);
	/**
	 * 获取从开机到现在消耗的wifi流量总数
	 * @return
	 */
	public long getTotalWifiFromBoot();
	public boolean iswifi();
	public boolean isWifiConnected(Context context);
}
