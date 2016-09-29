package com.example.monitorsys;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;

import com.example.dao.ITrafficData;
import com.example.dao.TrafficData;
import com.example.model.Program;

public class ProgramMonitor implements IProgramMonitor{
	private PackageManager pm;
	private List<PackageInfo> packageinfos =new ArrayList<PackageInfo>();
	private List<ApplicationInfo> applicationinfos = new ArrayList<ApplicationInfo>();
	private List<Program> apps =new ArrayList<Program>() ; 
	private ITrafficData trafficData = new TrafficData();
	
	
	public ProgramMonitor(PackageManager packmanager) {
		super();
		this.pm = packmanager;
		this.packageinfos = packmanager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
	}



	@Override
	public List<Program> getPrograms() {
		for(PackageInfo info : packageinfos){
			applicationinfos.add(info.applicationInfo);
			ApplicationInfo ainfo =info.applicationInfo;
			
			String[] premissions=info.requestedPermissions;  
			if(premissions!=null && premissions.length>0){  

				for(String premission : premissions){  
			            if("android.permission.INTERNET".equals(premission)){  
			    			Program app=new Program();
			    			
			    			 
			    			app.setName(ainfo.loadLabel(pm).toString());
			    			app.setUid(ainfo.uid);
			    			app.setIcon(ainfo.loadIcon(pm));
			    			app.setTraffic(trafficData.getUidRxBytesFromBoot(ainfo.uid)+trafficData.getUidTxBytesFromBoot(ainfo.uid));
			    			
			    			apps.add(app);

			             }  
				} 
			}
		}

		return apps;
	}
	
}
