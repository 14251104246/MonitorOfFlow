package com.example.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dao.ConfigDao;
import com.example.dao.DatabaseHelper;
import com.example.dao.IConfigDao;
import com.example.dao.ITotalTrafficDao;
import com.example.dao.TotalTrafficDao;
import com.example.dao.TrafficData;
import com.example.model.Config;
import com.example.model.TotalTraffic;
import com.example.monitorofflow.R;
import com.example.service.TrafficMonitorService;

/**
 * A placeholder fragment containing a simple view.
 */
public class ParamFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	

	private EditText feeday;
	private EditText warntraffic;
	private EditText usedtraffic;
	private EditText maxttraffic;
	private Button save;
	private View rootView;
	private IConfigDao configdao;
	private DatabaseHelper dbhelper;
	private ITotalTrafficDao tafficdao;
	private TotalTraffic trafficbeen;
	
	Config feedayconfig;
	Config warnconfig;
	Config usedconfig;
	Config maxconfig;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ParamFragment newInstance(int sectionNumber) {
		ParamFragment fragment = new ParamFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ParamFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_param, container,
				false);
		initdao();
		initView();
		updateView();
		return rootView;
	}

	private void initdao() {
		DatabaseHelper helper= new DatabaseHelper(getActivity());
		dbhelper=helper;
		configdao= new ConfigDao(helper);
		tafficdao=new TotalTrafficDao(dbhelper);
	}

	private void updateView() {
		trafficbeen = tafficdao.queryTotalTraffic();
		
		feedayconfig = configdao.queryConfig("balanceSheetDate");
		feeday.setText(feedayconfig.getConfigValue());
		
		maxconfig = configdao.queryConfig("gprsMaximum");
		maxttraffic.setText(maxconfig.getConfigValue());
		
		warnconfig= configdao.queryConfig("warnTraffic");
		warntraffic.setText(warnconfig.getConfigValue());
		
//		usedconfig=configdao.queryConfig("usedTraffic");
//		usedtraffic.setText(usedconfig.getConfigValue());
		
		//保存已使用流量的数据有冗余，这是数据库设计问题，想到解决办法再重构
		usedconfig=configdao.queryConfig("usedTraffic");
		usedconfig.setConfigValue((long) ((trafficbeen.getMonthlyTraffic()+trafficbeen.getYesterdayMTraffic())/1000/1000)+"");;
		usedtraffic.setText(usedconfig.getConfigValue());
	}

	private void initView() {
		feeday = (EditText) rootView.findViewById(R.id.feeday);
		warntraffic = (EditText) rootView.findViewById(R.id.warntraffic);
		usedtraffic = (EditText) rootView.findViewById(R.id.usedtraffic);
		maxttraffic = (EditText) rootView.findViewById(R.id.maxttraffic);
		save=(Button) rootView.findViewById(R.id.save);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean success=true;
				feedayconfig.setConfigValue(feeday.getText().toString());
				success &= configdao.updateConfig(feedayconfig);
				
				warnconfig.setConfigValue(warntraffic.getText().toString());
				success &=configdao.updateConfig(warnconfig);
				
				maxconfig.setConfigValue(maxttraffic.getText().toString());
				success &=configdao.updateConfig(maxconfig);
				
				//保存已使用流量的数据有冗余，这是数据库设计问题，想到解决办法再重构
				usedconfig.setConfigValue(usedtraffic.getText().toString());
				success &=configdao.updateConfig(usedconfig);
				success &=tafficdao.updateGprsTraffic(Long.parseLong(usedconfig.getConfigValue())*1000*1000, 0.0);
				
				if(success){
					Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	

	

	
}