package com.example.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dao.ConfigDao;
import com.example.dao.DatabaseHelper;
import com.example.dao.IConfigDao;
import com.example.dao.ITotalTrafficDao;
import com.example.dao.ITrafficData;
import com.example.dao.TotalTrafficDao;
import com.example.dao.TrafficData;
import com.example.model.Config;
import com.example.model.TotalTraffic;
import com.example.monitorofflow.MainActivity;
import com.example.monitorofflow.R;
import com.example.monitorofflow.MainActivity.MsgReceiver;
import com.example.service.TrafficMonitorService;

/**
 * A placeholder fragment containing a simple view.
 */
public class FlowFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	private TrafficMonitorService monitorService;
	private TextView gprsTraffic;
	private TextView wifiTraffic;
	private TextView dayGprs;
	private TextView dayWifi;
	private TextView monthremain;
	private TextView monthlimit;
	private TextView gprsup;
	private TextView gprsdown;
	private TextView wifiup;
	private TextView wifidown;
	private DatabaseHelper dbhelper;
	private ITotalTrafficDao tafficdao;
	private IConfigDao configdao;
	private View rootView;
	private TotalTraffic trafficbeen;
	private Config configbeen;
	private ITrafficData trafficdata= new TrafficData();
	private MsgReceiver receiver ;
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static FlowFragment newInstance(int sectionNumber) {
		FlowFragment fragment = new FlowFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public FlowFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_flow, container,
				false);
		initView();
		initdb();
		initUpdateRealtimeTrafficView();
		return rootView;
	}

	private void initdb() {
		dbhelper = new DatabaseHelper(getActivity());
		tafficdao=new TotalTrafficDao(dbhelper);
		configdao=new ConfigDao(dbhelper);
		
	}

	private void initView() {
		gprsTraffic= (TextView) rootView.findViewById(R.id.gprsTraffic);
		wifiTraffic= (TextView) rootView.findViewById(R.id.wifiTraffic);
		dayGprs = (TextView) rootView.findViewById(R.id.dayGprs);
		dayWifi = (TextView) rootView.findViewById(R.id.dayWifi);
		monthlimit=(TextView) rootView.findViewById(R.id.monthLimit);
		monthremain=(TextView) rootView.findViewById(R.id.monthRemain);
		gprsdown= (TextView) rootView.findViewById(R.id.gprsdown);
		gprsup =  (TextView) rootView.findViewById(R.id.gprsup);
		wifidown= (TextView) rootView.findViewById(R.id.wifidown);
		wifiup = (TextView) rootView.findViewById(R.id.wifiup);
	}

	@Override
	public void onResume() {

		updateView();
		super.onResume();
	}

	private void updateView() {
		
		trafficbeen= tafficdao.queryTotalTraffic();
		long MonthlyTraffic=(long) ((trafficbeen.getMonthlyTraffic()+trafficbeen.getYesterdayMTraffic())/1000.0/1000.0);
		long WifiMonthTraffic=(long) ((trafficbeen.getWifiMonthTraffic()+trafficbeen.getYesterdayWTraffic())/1000.0/1000.0);
		gprsTraffic.setText(MonthlyTraffic+"MB");
		wifiTraffic.setText(WifiMonthTraffic+"MB");
		System.out.println(MonthlyTraffic/1000.0);
		
		dayGprs.setText(trafficbeen.getYesterdayMTraffic()/1000.0+"KB");
		dayWifi.setText(trafficbeen.getYesterdayWTraffic()/1000.0+"KB");
		
		configbeen=configdao.queryConfig("gprsMaximum");
		monthlimit.setText(configbeen.getConfigValue()+"MB");
		
		monthremain.setText((Integer.parseInt(configbeen.getConfigValue())-MonthlyTraffic)+"MB");
		
		
	}
	
	private void initUpdateRealtimeTrafficView(){
		receiver = new MsgReceiver();
		IntentFilter filter= new IntentFilter();
		filter.addAction("com.example.monitorofflow.RECEIVER");
		getActivity().registerReceiver(receiver, filter);
		
	}
	public class MsgReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			float rx=intent.getFloatExtra("frx", (float) 0.0);
			float tx=intent.getFloatExtra("ftx", (float) 0.0);
			if(trafficdata.isWifiConnected(getActivity())){
				gprsdown.setText(0+"KB");
				gprsup.setText(0+"KB");
				wifidown.setText(rx+"KB");
				wifiup.setText(tx+"KB");
			}else{

				gprsdown.setText(rx+"KB");
				gprsup.setText(tx+"KB");
				wifidown.setText(0+"KB");
				wifiup.setText(0+"KB");
			}
			
		}
		
	}
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}

	

	
}