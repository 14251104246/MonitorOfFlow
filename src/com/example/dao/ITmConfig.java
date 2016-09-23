package com.example.dao;

import com.example.model.Config;

public interface ITmConfig {
	public Config queryConfig(String Name);
	public String updateConfig(Config config);
}
