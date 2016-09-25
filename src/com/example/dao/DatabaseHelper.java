package com.example.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "traffic.db"; 
	public static final int version_ = 2; 
	
	
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHelper(Context context) {
		this(context, DB_NAME,null, version_);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql;
		//
		sql = "create table appTraffic(appUID integer not null unique,monthlyTraffic real not null);";
		db.execSQL(sql);
		//
		sql = "create table totalTraffic(monthlyTraffic real not null,yesterdayMTraffic real not null,todayTraffic real not null,today integer not null,wifiMonthlyTraffic real not null,endDay integer not null unique,id integer not null unique,yesterdayWTraffic real not null);";
		db.execSQL(sql);
		//
		sql = "create table Config(configKey text not null unique,configValue text not null);";
		db.execSQL(sql);
		//初始化总流量
		sql = "insert into totalTraffic values(2.0,4.0,6.0,1,3.1,0,1,0)";
		db.execSQL(sql);
		//初始化配置
		sql = "insert into Config values('balanceSheetDate','2016-12-1')";
		db.execSQL(sql);
		
		sql = "insert into Config values('gprsMaximum','30')";
		db.execSQL(sql);
		
		sql = "insert into Config values('warnTraffic','25')";
		db.execSQL(sql);
		
		sql = "insert into Config values('usedTraffic','0')";
		db.execSQL(sql);
		

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
