package com.example.model;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class PackagesInfo {
	private List<ApplicationInfo> applist;
	PackagesInfo(Context context){
		
	}
	public List<ApplicationInfo> getApplist() {
		return applist;
	}
	public void setApplist(List<ApplicationInfo> applist) {
		this.applist = applist;
	}
	
}
