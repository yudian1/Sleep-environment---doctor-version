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
	// ����Ϊȫ�ֱ���,��patientphone����Ϊ����
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
		// ���»�����Ϣ
		update_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isNetworkAvailable(MainActivity.this) == true) {
					UpdatePatientInfoThread ut = new UpdatePatientInfoThread();
					ut.start();
					System.out.println("�ѵ��");
				} else {
					new AlertDialog.Builder(MainActivity.this).setTitle("��ܰ��ʾ")
							.setMessage("���������磡").setPositiveButton("ȷ��", null)
							.show();
					return;
				}				
			}
		});

		// ��û����б�
		PatientList();

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// ���ѡ�е�item����Ϣ
				Map<String, Object> getId = new HashMap<String, Object>();
				getId = listdata.get(position);

				System.out.println("id------" + getId);
				Object[] str = getId.values().toArray();
				// ���ѡ�е�����
				patient_phone = str[3].toString();

				System.out.println("patientphone------" + patient_phone);
				// //���ݵ�PatientInfoManageȥ
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
		System.out.println("�����С" + listpatients.size());
		patientInfos = new String[listpatients.size()];
		listdata = new ArrayList<HashMap<String, Object>>();
		int i = 0;// ����һ����ʼ��ʶ
		for (PatientInformation patient : listpatients) {// ����List���ͼ���
			// ��֧�������Ϣ��ϳ�һ���ַ������洢���ַ����������Ӧλ��
			patientInfos[i] = patient.getCurrent_date() + "   "
					+ patient.getPatient_name() + "  "
					+ patient.getPatient_gender() + "   "
					+ patient.getPatient_age();
			i++;// ��ʶ��1
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("id", patient.getId());
			item.put("currentdate", patient.getCurrent_date());
			item.put("name", patient.getPatient_name());
			
			if (patient.getPatient_gender().equals("Ů")) {
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
		gridView.setAdapter(adapter);// ΪListView�б���������Դ
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

			// ����1�� ����Webservice �ĵ��ò������뾲̬��IP��ַlocalhost���ϣ����뾲̬IP��ַ
			wsdlURL = "http://m167x89974.iask.in:52327/CloudServer/services/UserService";
			webMethod = "get";
			namespace = "http://service.cloud.chinasoft.com";
			soapAction = namespace + webMethod;

			// ����2:����һ������SoapObject
			soapObject = new SoapObject(namespace, webMethod);
			// ����3�����ݲ���

			// ����4������һ��SoapSer
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			// ����5:�ŷ�����һ�´��ض���
			envelope.bodyOut = soapObject;

			// ����6�������ʵ�Ա
			se = new HttpTransportSE(wsdlURL);
			// ����7��������
			try {
				se.call(soapAction, envelope);
				// ����8����ȡ�ӻ��������صĽ��
				if (envelope.getResponse() != null) {
					Object resout = envelope.getResponse();
					System.out.println("result----" + resout.toString());
					System.out.println(resout instanceof Vector);
					// ��������resoult
					@SuppressWarnings("unchecked")
					Vector<SoapObject> vec = (Vector<SoapObject>) resout;
					System.out.println("vec--" + vec);
					// ѭ����ʾ
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
					// ���ԣ���ʾ���
					Toast.makeText(MainActivity.this, "���سɹ�", Toast.LENGTH_LONG)
							.show();
					System.out.println("���----" + data);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			/*
			 * //��ȡ�������� UpdatePatientTest(); UpdatePatientSleepDiary();
			 */

			Looper.loop();
		}
	}

	public void jiexiPatientInfo(String str) {
		String patient[] = null;
		String patientInfo[] = null;
		// ���ա�#���ָ�
		patient = str.split("#");
		pi = new PatientInformationDAO(MainActivity.this);
		// ��ձ��
		pi.deletetable();
		for (int i = 0; i < patient.length; i++) {
			// ��ò�����Ϣ
			System.out.println("patient----" + patient[i]);
			patientInfo = patient[i].split("_");
			// ��װ
			PatientInformation patientInfos = new PatientInformation(
					patientInfo[0], patientInfo[1], patientInfo[2],
					patientInfo[3], patientInfo[4], patientInfo[5],
					patientInfo[6], patientInfo[7], patientInfo[8]);
			// Log.v("�������������", "���");
			// ��������
			pi.insert(patientInfos);
		}
	}
}
