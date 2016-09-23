package com.example.dao;

import com.example.model.Program;

public interface IAppTrafficDao {
	//appTraffic(appUID integer not null unique,monthlyTraffic real not null)
	public boolean insertAppTraffic(Program app);
	public Program queryAppTraffic(int UID);
	public boolean updateAppTraffic(Program app);
}
