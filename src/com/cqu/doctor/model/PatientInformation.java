package com.cqu.doctor.model;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class PatientInformation implements KvmSerializable{
	private int id;
	private String patient_name;
	private String patient_gender;
	private String patient_age;
	private String patient_postion;
	private String patient_scde;
	private String patient_insomnia;
	private String patient_mhistory;
	private String patient_phone;
	private String current_date;
	private int patient_hamd;
	public PatientInformation() {
		super();
	}
	public PatientInformation(int patient_hamd) {
		super();
		this.patient_hamd = patient_hamd;
	}
	public PatientInformation(String current_date,String patient_name, String patient_gender,
			String patient_age, String patient_postion, String patient_scde,
			String patient_insomnia, String patient_mhistory,
			String patient_phone) {
		super();
		this.current_date = current_date;
		this.patient_name = patient_name;
		this.patient_gender = patient_gender;
		this.patient_age = patient_age;
		this.patient_postion = patient_postion;
		this.patient_scde = patient_scde;
		this.patient_insomnia = patient_insomnia;
		this.patient_mhistory = patient_mhistory;
		this.patient_phone = patient_phone;
		
	}
	public PatientInformation(int id, String current_date,String patient_name, String patient_gender,
			String patient_age, String patient_postion, String patient_scde,
			String patient_insomnia, String patient_mhistory,
			String patient_phone) {
		super();
		this.id = id;
		this.current_date = current_date;
		this.patient_name = patient_name;
		this.patient_gender = patient_gender;
		this.patient_age = patient_age;
		this.patient_postion = patient_postion;
		this.patient_scde = patient_scde;
		this.patient_insomnia = patient_insomnia;
		this.patient_mhistory = patient_mhistory;
		this.patient_phone = patient_phone;
		
	}
	public int getPatient_hamd() {
		return patient_hamd;
	}
	public void setPatient_hamd(int patient_hamd) {
		this.patient_hamd = patient_hamd;
	}
	public String getPatient_age() {
		return patient_age;
	}
	public void setPatient_age(String patient_age) {
		this.patient_age = patient_age;
	}
	public String getCurrent_date() {
		return current_date;
	}
	public void setCurrent_date(String currentdate) {
		this.current_date = currentdate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getPatient_gender() {
		return patient_gender;
	}
	public void setPatient_gender(String patient_gender) {
		this.patient_gender = patient_gender;
	}
	public String getPatient_postion() {
		return patient_postion;
	}
	public void setPatient_postion(String patient_postion) {
		this.patient_postion = patient_postion;
	}
	public String getPatient_scde() {
		return patient_scde;
	}
	public void setPatient_scde(String patient_scde) {
		this.patient_scde = patient_scde;
	}
	public String getPatient_insomnia() {
		return patient_insomnia;
	}
	public void setPatient_insomnia(String patient_insomnia) {
		this.patient_insomnia = patient_insomnia;
	}
	public String getPatient_mhistory() {
		return patient_mhistory;
	}
	public void setPatient_mhistory(String patient_mhistory) {
		this.patient_mhistory = patient_mhistory;
	}
	public String getPatient_phone() {
		return patient_phone;
	}
	public void setPatient_phone(String patient_phone) {
		this.patient_phone = patient_phone;
	}
	
	public Object getProperty(int index) {
		// TODO Auto-generated method stub
		Object obj = null;
		switch (index) {
		case 0:
			obj = this.current_date;
			break;
		case 1 :
			obj = this.patient_name;
			break;
		case 2:
			obj = this.patient_gender;
			break;
		case 3:
			obj = this.patient_age;
			break;
		case 4 :
			obj = this.patient_postion;
			break;
		case 5 :
			obj = this.patient_scde;
			break;
		case 6:
			obj = this.patient_insomnia;
			break;
		case 7:
			obj = this.patient_mhistory;
			break;
		case 8:
			obj = this.patient_phone;
			break;
		default:
			break;
		}
		return obj;
	}

	public int getPropertyCount() {
		// TODO Auto-generated method stub
		//这个实体类有几个属性
		return 9;
	}

	public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			info.name = "current_date";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 1:
			info.name = "patient_name";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 2:
			info.name = "patient_gender";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 3:
			info.name = "patient_age";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 4:
			info.name = "patient_postion";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 5:
			info.name = "patient_scde";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 6:
			info.name = "patient_insomnia";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 7:
			info.name = "patient_mhistory";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 8:
			info.name = "patient_phone";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		default:
			break;
		}
	}

	public void setProperty(int index, Object obj) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			this.current_date = obj.toString();
			break;	
		case 1:
			this.patient_name = obj.toString();
			break;
		case 2:
			this.patient_gender = obj.toString();
			break;	
		case 3:
			this.patient_age= obj.toString();
			break;
		case 4:
			this.patient_postion = obj.toString();
			break;	
		case 5:
			this.patient_scde = obj.toString();
			break;
		case 6:
			this.patient_insomnia = obj.toString();
			break;
		case 7:
			this.patient_mhistory = obj.toString();
			break;
		case 8:
			this.patient_phone = obj.toString();
			break;
		default:
			break;
		}
	}
}
