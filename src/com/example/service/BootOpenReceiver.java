package com.example.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootOpenReceiver extends BroadcastReceiver {
	static private String action="android.intent.action.BOOT_COMPLETED";
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction()==action){
			context.startService(new Intent(context,TrafficMonitorService.class));
			context.startService(new Intent(context,SmallWindowService.class));
		}

	}

}
