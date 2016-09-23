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
	 * ���ָ��app�ӵ�һ�δ򿪵��������ĵ���������
	 * @param uid
	 * @return
	 */
	public long getUidRxBytes(int uid);
	public long getUidTxBytes(int uid);
	/**
	 * ��ȡ�ӿ������������ĵ�wifi��������
	 * @return
	 */
	public long getTotalWifiFromBoot();
	public boolean iswifi();
	public boolean isWifiConnected(Context context);
}
