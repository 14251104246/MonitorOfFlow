package com.example.util;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.telephony.TelephonyManager;

public class SIMUtil {
	/**
	 * 服务商编号
	 */
	public static final int YIDONG=1;
	public static final int LIANTONG=2;
	public static final int DIANGXING=3;
	
	public static int getSIMServer(Context context){
		int ProvidersName = 0;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String IMSI; // 返回唯一的用户ID;就是这张卡的编号神马的
		IMSI = telephonyManager.getSubscriberId();
		if (IMSI == null)
		return 0;
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。其中
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
