package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.model.Program;
import com.example.monitorofflow.R;
import com.example.monitorsys.IProgramMonitor;
import com.example.monitorsys.ProgramMonitor;
import com.example.service.TrafficMonitorService;

/**
 * A placeholder fragment containing a simple view.
 */
public class NetFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	private TrafficMonitorService monitorService;
	private View rootView;
	private TextView textview;
	private ListView listview;
	private IProgramMonitor monitor;
	private List<Program> apps ;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static NetFragment newInstance(int sectionNumber) {
		NetFragment fragment = new NetFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public NetFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_net, container,
				false);
		initview();
		updateView();
		return rootView;
	}
	
	

	private void updateView() {
		textview.setText("设备共启动"+apps.size()+"个进程,已过滤其中0个进程");
		
	}

	private void initview() {
		textview= (TextView) rootView.findViewById(R.id.textView1);
		listview= (ListView) rootView.findViewById(R.id.listView1);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(),R.layout.list_item,
                new String[]{"title","info","img"},
                new int[]{R.id.title,R.id.info,R.id.img});
        listview.setAdapter(adapter);
	}
	
	
	private List<Map<String, Object>> getData() {
		monitor= new ProgramMonitor(getActivity().getPackageManager());
		apps = monitor.getPrograms();
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(Program app:apps){
			Map<String, Object> map = new HashMap<String, Object>();
			
	        map.put("title", app.getName());
	        map.put("info", "已使用流量："+app.getTraffic()/1000+"KB");
	        map.put("img", app.getIcon());
	        list.add(map);
		}
		return list;
	}




	
}