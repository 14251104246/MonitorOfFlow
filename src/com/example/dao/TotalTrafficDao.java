package com.example.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.model.TotalTraffic;

public class TotalTrafficDao implements ITotalTrafficDao{
	private DatabaseHelper dbhelp;
	private SQLiteDatabase db;
	
	public TotalTrafficDao(DatabaseHelper Helper) {
		super();
		dbhelp=Helper;
		
	}

	@Override
	public TotalTraffic queryTotalTraffic() {
		db=dbhelp.getWritableDatabase();
		//table totalTraffic(monthlyTraffic real not null,yesterdayMTraffic real not null,todayTraffic real not null,today integer not null,wifiMonthlyTraffic real not null)
		String rawSql="select * from totalTraffic";
		Cursor cursor= db.rawQuery(rawSql, null);
		cursor.moveToFirst();
		TotalTraffic totaltraffic = new TotalTraffic();
		totaltraffic.setMonthlyTraffic(cursor.getFloat(0));
		totaltraffic.setYesterdayMTraffic(cursor.getFloat(1));
		totaltraffic.setTodayTraffic(cursor.getFloat(2));
		totaltraffic.setToday(cursor.getInt(3));
		totaltraffic.setWifiMonthTraffic(cursor.getFloat(4));
		totaltraffic.setEndday(cursor.getInt(5));
		totaltraffic.setId(cursor.getInt(6));
		totaltraffic.setYesterdayWTraffic(cursor.getFloat(7));
		cursor.close();
		db.close();
		return totaltraffic;
	}

	@Override
	public boolean updateTotalTraffic(TotalTraffic totalTraffic) {
		/*
		 * 
ContentValues values = new ContentValues();   
//在values中添加内容   
values.put("snumber","101003");   
//修改条件   
String whereClause = "id= ";   
//修改添加参数   
String[] whereArgs={String.valuesOf(1)};   
//修改   
db.update("usertable",values,whereClause,whereArgs); 
		 */
//table totalTraffic(monthlyTraffic real not null,yesterdayMTraffic real not null,todayTraffic real not null,today integer not null,wifiMonthlyTraffic real not null)
		
		db=dbhelp.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("monthlyTraffic", totalTraffic.getMonthlyTraffic());
		values.put("yesterdayMTraffic", totalTraffic.getYesterdayMTraffic());
		values.put("today", totalTraffic.getToday());
		values.put("wifiMonthlyTraffic", totalTraffic.getWifiMonthTraffic());
		values.put("yesterdayWTraffic", totalTraffic.getYesterdayWTraffic());
		String whereClause="id=?";
		String[] whereArgs={String.valueOf(1)};
		int num=db.update("totalTraffic", values, whereClause, whereArgs);
		
		db.close();
		if(num>0)return true;
		
		return false;
	}



}
