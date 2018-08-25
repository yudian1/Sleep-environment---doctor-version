package com.cqu.doctor.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.cqu.doctor.dao.PatientInformationDAO;
import com.cqu.doctor.model.PatientInformation;
import com.example.doctorclient.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements View.OnClickListener {

	private Intent intent;	
	private List<HashMap<String, Object>> listdata;
	// 设置为全局变量,将patientphone设置为主键
	private String patient_phone;
	private PatientInformationDAO pi;
	private ImageButton update_btn;

	private String wsdlURL, webMethod, namespace, soapAction;
	private SoapObject soapObject;
	private HttpTransportSE se;
	private String data;
	private GridView gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);

		gridView = (GridView) findViewById(R.id.gridview);
		update_btn = (ImageButton) findViewById(R.id.update_btn);
		// 更新患者信息
		update_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isNetworkAvailable(MainActivity.this) == true) {
					UpdatePatientInfoThread ut = new UpdatePatientInfoThread();
					ut.start();
					System.out.println("已点击");
				} else {
					new AlertDialog.Builder(MainActivity.this).setTitle("温馨提示")
							.setMessage("请连接网络！").setPositiveButton("确定", null)
							.show();
					return;
				}				
			}
		});

		// 获得患者列表
		PatientList();

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// 获得选中的item的信息
				Map<String, Object> getId = new HashMap<String, Object>();
				getId = listdata.get(position);

				System.out.println("id------" + getId);
				Object[] str = getId.values().toArray();
				// 获得选中的主键
				patient_phone = str[3].toString();

				System.out.println("patientphone------" + patient_phone);
				// //传递到PatientInfoManage去
				intent = new Intent(MainActivity.this, MedicalRecord.class);
				intent.putExtra("patientphone", patient_phone);
				startActivity(intent);
			}
		});
		initVIew();
		
		
	}

	private void initVIew() {
		findViewById(R.id.patient).setOnClickListener(this);		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.patient:
			intent = new Intent(MainActivity.this, MedicalRecord.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private void PatientList() {
		String patientInfos[] = null;
		pi = new PatientInformationDAO(MainActivity.this);
		List<PatientInformation> listpatients = pi.getScrollData(0,
				(int) pi.getCount());
		System.out.println("数组大小" + listpatients.size());
		patientInfos = new String[listpatients.size()];
		listdata = new ArrayList<HashMap<String, Object>>();
		int i = 0;// 定义一个开始标识
		for (PatientInformation patient : listpatients) {// 遍历List泛型集合
			// 将支出相关信息组合成一个字符串，存储到字符串数组的相应位置
			patientInfos[i] = patient.getCurrent_date() + "   "
					+ patient.getPatient_name() + "  "
					+ patient.getPatient_gender() + "   "
					+ patient.getPatient_age();
			i++;// 标识加1
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("id", patient.getId());
			item.put("currentdate", patient.getCurrent_date());
			item.put("name", patient.getPatient_name());
			
			if (patient.getPatient_gender().equals("女")) {
				item.put("gender", R.drawable.woman);	
			} else {
				item.put("gender", R.drawable.man);
			}
			item.put("age", patient.getPatient_age());
			item.put("patientphone", patient.getPatient_phone());
			listdata.add(item);
		}
				SimpleAdapter adapter = new SimpleAdapter(this, listdata,
				R.layout.patient_items,
				new String[] { "name", "gender"}, new int[] { R.id.textView1,
						R.id.imageView1});
		gridView.setAdapter(adapter);// 为ListView列表设置数据源
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {

		} else {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}			}
			}
		}
		return false;
	}

	class UpdatePatientInfoThread extends Thread {
		@Override
		public void run() {
			Looper.prepare();

			// 步骤1： 设置Webservice 的调用参数必须静态的IP地址localhost不认？必须静态IP地址
			wsdlURL = "http://m167x89974.iask.in:52327/CloudServer/services/UserService";
			webMethod = "get";
			namespace = "http://service.cloud.chinasoft.com";
			soapAction = namespace + webMethod;

			// 步骤2:创建一个对象SoapObject
			soapObject = new SoapObject(namespace, webMethod);
			// 步骤3：传递参数

			// 步骤4：创建一个SoapSer
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			// 步骤5:信封设置一下传回对象
			envelope.bodyOut = soapObject;

			// 步骤6：创建邮递员
			se = new HttpTransportSE(wsdlURL);
			// 步骤7发送请求：
			try {
				se.call(soapAction, envelope);
				// 步骤8：获取从互联网返回的结果
				if (envelope.getResponse() != null) {
					Object resout = envelope.getResponse();
					System.out.println("result----" + resout.toString());
					System.out.println(resout instanceof Vector);
					// 解析集合resoult
					@SuppressWarnings("unchecked")
					Vector<SoapObject> vec = (Vector<SoapObject>) resout;
					System.out.println("vec--" + vec);
					// 循环显示
					StringBuffer buffer = new StringBuffer();
					for (SoapObject soapObject : vec) {
						buffer.append(soapObject.getProperty("current_date")
								+ "_");
						buffer.append(soapObject.getProperty("patient_name")
								+ "_");
						buffer.append(soapObject.getProperty("patient_gender")
								+ "_");
						buffer.append(soapObject.getProperty("patient_age")
								+ "_");
						buffer.append(soapObject.getProperty("patient_postion")
								+ "_");
						buffer.append(soapObject.getProperty("patient_scde")
								+ "_");
						buffer.append(soapObject
								.getProperty("patient_insomnia") + "_");
						buffer.append(soapObject
								.getProperty("patient_mhistory") + "_");
						buffer.append(soapObject.getProperty("patient_phone")
								+ "#");
					}
					buffer.deleteCharAt(buffer.length() - 1);
					data = new String(buffer);
					jiexiPatientInfo(data);
					// 测试：显示结果
					Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_LONG)
							.show();
					System.out.println("结果----" + data);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			/*
			 * //获取测试数据 UpdatePatientTest(); UpdatePatientSleepDiary();
			 */

			Looper.loop();
		}
	}

	public void jiexiPatientInfo(String str) {
		String patient[] = null;
		String patientInfo[] = null;
		// 按照“#”分割
		patient = str.split("#");
		pi = new PatientInformationDAO(MainActivity.this);
		// 清空表格
		pi.deletetable();
		for (int i = 0; i < patient.length; i++) {
			// 获得病人信息
			System.out.println("patient----" + patient[i]);
			patientInfo = patient[i].split("_");
			// 封装
			PatientInformation patientInfos = new PatientInformation(
					patientInfo[0], patientInfo[1], patientInfo[2],
					patientInfo[3], patientInfo[4], patientInfo[5],
					patientInfo[6], patientInfo[7], patientInfo[8]);
			// Log.v("创建表格在哪里", "哪里？");
			// 插入数据
			pi.insert(patientInfos);
		}
	}
}
