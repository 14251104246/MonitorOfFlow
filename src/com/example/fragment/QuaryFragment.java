package com.example.fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Fragment;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dao.ConfigDao;
import com.example.dao.DatabaseHelper;
import com.example.dao.IConfigDao;
import com.example.dao.ITotalTrafficDao;
import com.example.dao.TotalTrafficDao;
import com.example.model.Config;
import com.example.model.SmsInfo;
import com.example.model.TotalTraffic;
import com.example.monitorofflow.R;
import com.example.service.TrafficMonitorService;
import com.example.util.SIMUtil;
import com.example.util.SmsUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuaryFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	private static final String YIDONG_NUM = "10086";
	private static final String YIDONG_text = "cxll";
	
	private TrafficMonitorService monitorService;
		
	Config usedconfig = new Config();
	private IConfigDao configdao;
	private DatabaseHelper dbhelper;
	private ITotalTrafficDao tafficdao;
	private TotalTraffic trafficbeen;

	View rootView;
	private TextView trafficView;
	private Button postButton;
	private String traffictext;
	
	SmsObserver smsobserver = new SmsObserver(new Handler());
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static QuaryFragment newInstance(int sectionNumber) {
		QuaryFragment fragment = new QuaryFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public QuaryFragment() {
		
	}

	private void init() {
		dbhelper = new DatabaseHelper(getActivity());
		tafficdao=new TotalTrafficDao(dbhelper);
		configdao=new ConfigDao(dbhelper);
	
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_query, container,
				false);
		init();
		initView();
		try {
			updateView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		return rootView;
	}

	private void updateView() throws Exception{
		trafficbeen= tafficdao.queryTotalTraffic();
		usedconfig=configdao.queryConfig("usedTraffic");
		usedconfig.setConfigValue((long) ((trafficbeen.getMonthlyTraffic()+trafficbeen.getYesterdayMTraffic())/1000/1000)+"");;
		trafficView.setText(usedconfig.getConfigValue()+"M");
		
	}

	private void initView() {
		trafficView = (TextView) rootView.findViewById(R.id.trafficView);
		postButton = (Button) rootView.findViewById(R.id.postButton);
		postButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				smstask.execute(getActivity());
				
				
				int serverid=SIMUtil.getSIMServer(getActivity());
				if(serverid==SIMUtil.YIDONG){
					SmsInfo smsinfo=new SmsInfo();
					smsinfo.setPhoneNumber(YIDONG_NUM);
					smsinfo.setSmsbody(YIDONG_text);
					SmsUtil.sentSms(smsinfo);
					
					Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
				}
				if(serverid==SIMUtil.LIANTONG){
					
				}
				if(serverid==SIMUtil.DIANGXING){
					
				}
				
				//监听sms
				getActivity().getContentResolver().registerContentObserver(Uri.parse("content://sms"), true,smsobserver);

			}
		});
	}
	
	
	private void updateDb() throws Exception{
		
		usedconfig.setConfigKey("usedTraffic");
		usedconfig.setConfigValue(traffictext);
		configdao.updateConfig(usedconfig);
		
		double traffic = Double.parseDouble(traffictext)*1000*1000;
		tafficdao.updateGprsTraffic(traffic, 0);
		
	}


	@Override
	public void onDestroy() {
		getActivity().getContentResolver().unregisterContentObserver(smsobserver);
		super.onDestroy();
	}





	private class SmsObserver extends ContentObserver {
		 
		  public SmsObserver(Handler handler) {
		   super(handler);
		   // TODO Auto-generated constructor stub
		  }
		  /**
		   *Uri.parse("content://sms/inbox")表示对收到的短信的一个监听的uri.
		   */
		  @Override
		  public void onChange(boolean selfChange) {
		   // TODO Auto-generated method stub
		   StringBuilder sb = new StringBuilder();
		   Cursor cursor = getActivity().getContentResolver().query(
		     Uri.parse("content://sms/inbox"), null, null, null, null);
		      
		   cursor.moveToNext();
		   
		   sb.append("body=" + cursor.getString(cursor.getColumnIndex("body"))); 
		   Pattern pattern = Pattern.compile("[0-9]+.?[0-9]+M"); //正则表达式.
		   Matcher matcher = pattern.matcher(sb.toString());
		   //先调用find（）函数，不然返回空值
		   matcher.find();
		   System.out.println(sb.toString());
		   String Text = null;
		try {
			Text = matcher.group(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		   
		   //
		   if(Text==null){
			   System.out.println("SMS:"+Text);
			   super.onChange(selfChange);
			   return;
		   }
		   traffictext = Text.substring(0, Text.indexOf('M')-1);
		   
		   trafficView.setText(Text);
		   
		   cursor.close(); 
		   
		   //下面为更新数据库
		   try {
			updateDb();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		   super.onChange(selfChange);
		  }

		 }

	
}