package com.example.util;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.telephony.TelephonyManager;

public class SIMUtil {
	/**
	 * �����̱��
	 */
	public static final int YIDONG=1;
	public static final int LIANTONG=2;
	public static final int DIANGXING=3;
	
	public static int getSIMServer(Context context){
		int ProvidersName = 0;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String IMSI; // ����Ψһ���û�ID;�������ſ��ı�������
		IMSI = telephonyManager.getSubscriberId();
		if (IMSI == null)
		return 0;
		// IMSI��ǰ��3λ460�ǹ��ң������ź���2λ00 02���й��ƶ���01���й���ͨ��03���й����š�����
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
		    ProvidersName = YIDONG;
		         } else if (IMSI.startsWith("46001")) {
		     ProvidersName =LIANTONG;
		        } else if (IMSI.startsWith("46003")) {
		    ProvidersName = DIANGXING;
		}
		
		return ProvidersName;
		
	}
}
