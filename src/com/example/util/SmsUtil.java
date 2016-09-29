package com.example.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.example.model.SmsInfo;

public class SmsUtil {
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	
	public static SmsInfo getSms(String phoneNumber,Context context){
		
        String[] projection = new String[] { "_id", "address", "person",
        		  
                "body", "date", "type" };
        
		String whereClause ="address=?";
		String[] whereArgs ={phoneNumber};
        
        Uri uri = Uri.parse(SMS_URI_INBOX);
  
//        Cursor cusor = context.managedQuery(uri, projection, null, null,
  
//                "date desc");
        Cursor cusor = context.getContentResolver().query(uri, projection,  whereClause, whereArgs, "date desc");
        
        int nameColumn = cusor.getColumnIndex("person");
  
        int phoneNumberColumn = cusor.getColumnIndex("address");
  
        int smsbodyColumn = cusor.getColumnIndex("body");
  
        int dateColumn = cusor.getColumnIndex("date");
  
        int typeColumn = cusor.getColumnIndex("type");
        
        //读取来自运营商的最新短信
        cusor.moveToFirst();
        
        SmsInfo smsinfo = new SmsInfo();
        
        smsinfo.setName(cusor.getString(nameColumn));

        smsinfo.setDate(cusor.getString(dateColumn));

        smsinfo.setPhoneNumber(cusor.getString(phoneNumberColumn));

        smsinfo.setSmsbody(cusor.getString(smsbodyColumn));

        smsinfo.setType(cusor.getString(typeColumn));
		return smsinfo;
		
	}
	
	public static boolean sentSms(SmsInfo smsinfo){
		SmsManager smsmanager = SmsManager.getDefault();
		smsmanager.sendTextMessage(smsinfo.getPhoneNumber(), null, smsinfo.getSmsbody(), null, null);
		return false;
		
	}
}
