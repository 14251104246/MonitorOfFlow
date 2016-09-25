package com.example.dao;

import android.database.sqlite.SQLiteDatabase;

public class DbDao {
	DatabaseHelper dbhelp;
	SQLiteDatabase db;
	
	public DbDao(DatabaseHelper Helper) {
		super();
		dbhelp=Helper;
		
	}
}
