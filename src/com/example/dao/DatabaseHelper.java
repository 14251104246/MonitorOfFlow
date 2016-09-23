package com.example.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "traffic.db"; 
	private static final int version_ = 1; 
	
	
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, factory, version_);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql;
		//
		sql = "create table appTraffic(appUID integer not null unique,monthlyTraffic real not null);";
		db.execSQL(sql);
		//
		sql = "create table totalTraffic(monthlyTraffic real not null,yesterdayMTraffic real not null,todayTraffic real not null);";
		db.execSQL(sql);
		//
		sql = "create table Config(configKey text not null unique,configValue text not null);";
		db.execSQL(sql);
		//初始化总流量
//		sql = "insert into totalTraffic value(0.0)";
//		db.execSQL(sql);
		//初始化配置
		sql = "insert into Config value('balanceSheetDate'.'2016-12-1')";
		db.execSQL(sql);
		
		sql = "insert into Config value('gprsMaximum'.'30')";
		db.execSQL(sql);
		
		sql = "insert into Config value('warnTraffic'.'25')";
		db.execSQL(sql);
		
		sql = "insert into Config value('usedTraffic'.'0')";
		db.execSQL(sql);
		

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
