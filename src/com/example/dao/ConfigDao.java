package com.example.dao;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Helpers;

import com.example.model.Config;

public class ConfigDao extends DbDao implements IConfigDao{
	public ConfigDao(DatabaseHelper Helper) {
		super(Helper);
		// TODO Auto-generated constructor stub
	}
//Config(configKey text not null unique,configValue text not null)
	@Override
	public Config queryConfig(String Name) {
		db=dbhelp.getWritableDatabase();
		String sql="select * from Config where configKey=?";
		String[] selectionArgs={Name};
		Cursor cursor=db.rawQuery(sql, selectionArgs);
		cursor.moveToFirst();
		Config configbeen = new Config();
		configbeen.setConfigKey(Name);
		configbeen.setConfigValue(cursor.getString(1));
		cursor.close();
		db.close();
		return configbeen;
	}

	@Override
	public boolean updateConfig(Config config) {
		db=dbhelp.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("configValue", config.getConfigValue());
		String whereClause ="configKey=?";
		String[] whereArgs ={config.getConfigKey()};
		int num=db.update("Config", values, whereClause, whereArgs);
		db.close();
		if(num>0)return true;
		return false;
	}

	@Override
	public List<Config> queryAllConfigs() {
		// TODO Auto-generated method stub
		return null;
	}

}
