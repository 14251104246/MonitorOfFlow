package com.example.monitorsys;


public class MonitorManager implements IMonitorManager{
	private static MonitorManager manager=null;
	private IRealtimeMonitor monitor=null;
	
	private MonitorManager() {
		
	}

	@Override
	public IRealtimeMonitor getTrafficMonitor() {
		if(monitor==null){
			monitor=new RealtimeMonitor();
		}
		return monitor;
	}

	public static IMonitorManager getInstance() {
		if(manager==null){
			manager=new MonitorManager();
		}
		return manager;
	}

}
