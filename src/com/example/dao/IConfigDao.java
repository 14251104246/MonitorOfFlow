package com.example.dao;

import java.util.List;

import com.example.model.Config;

public interface IConfigDao {
	public Config queryConfig(String Name);
	public List<Config> queryAllConfigs();
	public boolean updateConfig(Config config);
}
