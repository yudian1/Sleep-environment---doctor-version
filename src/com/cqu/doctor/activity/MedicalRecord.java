package com.cqu.doctor.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.cqu.doctor.dao.DoctorAdviceDAO;
import com.cqu.doctor.dao.PatientInformationDAO;
import com.cqu.doctor.model.DoctorAdivce;
import com.cqu.doctor.model.PatientInformation;
import com.example.doctorclient.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MedicalRecord extends Activity implements View.OnClickListener{
	//����Ϊȫ�ֱ���,��patientphone����Ϊ����
	private String patientphone;
	private String wsdlURL,webMethod,namespace,soapAction;
	private SoapObject soapObject;
	private HttpTransportSE se ;
	private TextView patient_name;
	private TextView patient_tel;
	private LinearLayout other_layout;
	private TextView switch_button;
	private TextView patient_age;
	private TextView patient_job;
	private TextView patient_scade;
	private TextView patient_insomnia;
	private TextView patient_mhistory;
	private Button doctorAdivce;
	private Spinner sp_in_way,sp_in_stat;
	private int selectedPercentPosition;
	private List<String> inwayList,stat_List;
	private ArrayAdapter<String> inwayAdapter,spAdapter2;
	private EditText add_doctor_advice;
	private Button sleepDiary;
	private Button chart_button;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_record_detail1);
        initVIew();
        initParams();
      //��ô��ݹ�����id
      		Intent intent = getIntent();
      		patientphone = intent.getStringExtra("patientphone");
      		System.out.println("patientphone--" + patientphone);
      		
      		PatientInformationDAO pi = new PatientInformationDAO(MedicalRecord.this);
    		//ͨ��patientphone����ѯ���
    		PatientInformation patient = pi.find(patientphone);
    		System.out.println("patient----" + patient);
    		String [] patientInfo = new String [9];
    		patientInfo[0] = patient.getCurrent_date();
    		patientInfo[1] = patient.getPatient_name();
    		patientInfo[2] = patient.getPatient_gender();
    		patientInfo[3] = patient.getPatient_age();
    		patientInfo[4] = patient.getPatient_postion();
    		patientInfo[5] = patient.getPatient_scde();
    		patientInfo[6] = patient.getPatient_insomnia();
    		patientInfo[7] = patient.getPatient_mhistory();
    		patientInfo[8] = patient.getPatient_phone();
    		patient_name.setText(patientInfo[1]);
    		patient_tel.setText(patientInfo[8]);
    		patient_age.setText(patientInfo[3]);
    		patient_job.setText(patientInfo[4]);
    		patient_scade.setText(patientInfo[5]);
    		patient_insomnia.setText(patientInfo[6]);
    		patient_mhistory.setText(patientInfo[7]);
    		
    		sp_in_way.setOnItemSelectedListener(new OnItemSelectedListener() {

    			@Override
    			public void onItemSelected(AdapterView<?> parent, View view,
    					int position, long id) {
    				selectedPercentPosition = position;
    				String spinner = inwayList.get(selectedPercentPosition)
    						.toString();
    				
    			}

    			@Override
    			public void onNothingSelected(AdapterView<?> parent) {
    			}
    		});
    		//��Ժ���
    		sp_in_stat.setOnItemSelectedListener(new OnItemSelectedListener() {

    			private int selPosition2;

				@Override
    			public void onItemSelected(AdapterView<?> parent, View view,
    					int position, long id) {
    				selPosition2 = position;
    				String spinner = stat_List.get(selPosition2)
    						.toString();
    				
    			}

    			@Override
    			public void onNothingSelected(AdapterView<?> parent) {
    			}
    		});
    }

	// ��ʼ��spinner
		private void initParams() {
			inwayList = new ArrayList<String>();
			inwayList.add("����");
			inwayList.add("����");

			inwayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, inwayList);
			inwayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
			sp_in_way.setAdapter(inwayAdapter);
			
			//��Ժ���
			stat_List = new ArrayList<String>();
			stat_List.add("����");
			stat_List.add("��΢");

			spAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stat_List);
			spAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
			sp_in_stat.setAdapter(spAdapter2);
		}

	
    private void initVIew()
    {
    	switch_button=(TextView)findViewById(R.id.switch_button);
    	switch_button.setOnClickListener(this);
        findViewById(R.id.back_btn).setOnClickListener(this);
        other_layout=(LinearLayout)findViewById(R.id.other_patient_info_layout);
        patient_name=(TextView)findViewById(R.id.patient_name);
        patient_tel=(TextView)findViewById(R.id.patient_tel);
        patient_age=(TextView)findViewById(R.id.patient_age);
        patient_job=(TextView)findViewById(R.id.patient_job);
        patient_scade=(TextView)findViewById(R.id.patient_scade);
        patient_insomnia=(TextView)findViewById(R.id.patient_insomnia);
        patient_mhistory=(TextView)findViewById(R.id.patient_mhistory);
        doctorAdivce = (Button) findViewById(R.id.send_advice);
        doctorAdivce.setOnClickListener(this);
        add_doctor_advice = (EditText) findViewById(R.id.add_doctor_advice);
        sleepDiary = (Button) findViewById(R.id.hint_table);
        sleepDiary.setOnClickListener(this);
        chart_button = (Button) findViewById(R.id.chart_button);
        chart_button.setOnClickListener(this);
        sp_in_way = (Spinner) findViewById(R.id.spinner_inway);
        sp_in_stat = (Spinner) findViewById(R.id.spinner_inway2);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.switch_button:
        	if (switch_button.getText().equals("�鿴����")) {
        		other_layout.setVisibility(View.VISIBLE);
            	switch_button.setText("����");	
			}
        	else {
        		other_layout.setVisibility(View.GONE);
            	switch_button.setText("�鿴����");
			}
            break;
        case R.id.send_advice:
			doctor_advice();
			break;
        case R.id.back_btn:
            finish();
            break; 
        case R.id.hint_table:
        	new AlertDialog.Builder(MedicalRecord.this)
			.setTitle("��ܰ��ʾ")
			.setMessage("����û���ύ���ݣ�")
			.setPositiveButton("ȷ��", null)
			.show();
            break;  
        case R.id.chart_button:
        	new AlertDialog.Builder(MedicalRecord.this)
			.setTitle("��ܰ��ʾ")
			.setMessage("����û���ύ���ݣ�")
			.setPositiveButton("ȷ��", null)
			.show();
            break;  
        default:
            break;
    }
	}

	//����ҽ������
	   String doctorAdvice;
	   public void doctor_advice(){
			
			System.out.println("��ȡҽ����Ϣ----");
			
			//String inway = inwayList.get(selectedPercentPosition).toString();
			doctorAdvice = "heihei���"+ "," + add_doctor_advice.getText().toString();
			/*doctorAdvice = sleep_latency.getText().toString() + "," + String.valueOf(a) + "," +
								sleep_time.getText().toString() + "," +String.valueOf(b) + "," +
								sleep_efficient.getText().toString() +"," + String.valueOf(c) + "," +
								today_sleep.getText().toString() + "," + String.valueOf(d) + "," +
								sleep_latency2.getText().toString() + "," + sleep_time2.getText().toString() + 
								"," + add_doctor_advice.getText().toString();*/
			System.out.println("ҽ����Ϣ----" + doctorAdvice);
			if (patientphone != null) {
				//����ҽ����Ϣ
				DoctorAdivce da = new DoctorAdivce(patientphone, doctorAdvice);
				DoctorAdviceDAO dadao = new DoctorAdviceDAO(this);
				dadao.insert(da);
				System.out.println("ҽ�����ݱ���ɹ�");
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Looper.prepare();
						// ����ҽ��
						SendDoctorAdviceThread();
						Looper.loop();
					}
				}).start();
		
			}else {
				new AlertDialog.Builder(MedicalRecord.this)
				.setTitle("��ܰ��ʾ")
				.setMessage("��ѡ����!")
				.setPositiveButton("ȷ��", null)
				.show();
				return;
			}
			
		}
	   private void SendDoctorAdviceThread() {

			DoctorAdivce da = new DoctorAdivce();
			DoctorAdviceDAO dadao = new DoctorAdviceDAO(MedicalRecord.this);
			da = dadao.find((int)dadao.getCount());
//			da = dadao.find(3);
			System.out.println("da----" + da);
			
			//����1�� ����Webservice �ĵ��ò������뾲̬��IP��ַlocalhost���ϣ����뾲̬IP��ַ
			 wsdlURL= "http://m167x89974.iask.in:52327/CloudServer/services/UserService";
			 webMethod = "addDoctor_Service";
			 namespace = "http://service.cloud.chinasoft.com";
			 soapAction = namespace + webMethod;
			
			//����2:����һ������SoapObject
		    soapObject = new SoapObject(namespace, webMethod);
			//����3�����ݲ���
			soapObject.addProperty("doctorAdvice", da);
			
			//����4������һ��SoapSer
			SoapSerializationEnvelope  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			//�Զ���Ķ���������envelop
			//�����ص㣺ָ���������˵ķ����л������
			//u.getClass�ǻ�ö���ķ��䡣
			envelope.addMapping("http://po.cloud.chinasoft.com/xsd", "doctorAdvice", da.getClass());
			//����5:�ŷ�����һ�´��ض���
			envelope.bodyOut = soapObject;
			//����6�������ʵ�Ա
			 se = new HttpTransportSE(wsdlURL);
			//����7��������
			try {
				se.call(soapAction, envelope);
				//����8����ȡ�ӻ��������صĽ��
				if(envelope.getResponse() != null)
				{
					Object resout = envelope.getResponse();
					//���ԣ���ʾ���
					Toast.makeText(MedicalRecord.this, "�����ϴ��ɹ�", Toast.LENGTH_LONG).show();
					System.out.println("���----" + resout.toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}
}
