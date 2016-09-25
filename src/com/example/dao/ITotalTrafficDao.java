package com.example.dao;

import com.example.model.TotalTraffic;

public interface ITotalTrafficDao {
	public TotalTraffic queryTotalTraffic();
	public boolean updateTotalTraffic(TotalTraffic totalTraffic);
}
