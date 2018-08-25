package com.cqu.doctor.model;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class PatientTest implements KvmSerializable{
	int id;
	String pqsi;
	String sleepness;
	String hama;
	String hamd;
	String isi;
	String patient_phone;
	public String getPatient_phone() {
		return patient_phone;
	}
	public void setPatient_phone(String patient_phone) {
		this.patient_phone = patient_phone;
	}
	public String getIsi() {
		return isi;
	}
	public void setIsi(String isi) {
		this.isi = isi;
	}
	public PatientTest() {
		super();
	}
	public PatientTest(String pqsi, String sleepness, String hama, String hamd,String patient_phone) {
		super();
		this.pqsi = pqsi;
		this.sleepness = sleepness;
		this.hama = hama;
		this.hamd = hamd;
		this.patient_phone = patient_phone;
	}
	public PatientTest(int id, String pqsi, String sleepness, String hama,
			String hamd,String isi) {
		super();
		this.id = id;
		this.pqsi = pqsi;
		this.sleepness = sleepness;
		this.hama = hama;
		this.hamd = hamd;
		this.isi = isi;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPqsi() {
		return pqsi;
	}
	public void setPqsi(String pqsi) {
		this.pqsi = pqsi;
	}
	public String getSleepness() {
		return sleepness;
	}
	public void setSleepness(String sleepness) {
		this.sleepness = sleepness;
	}
	public String getHama() {
		return hama;
	}
	public void setHama(String hama) {
		this.hama = hama;
	}
	public String getHamd() {
		return hamd;
	}
	public void setHamd(String hamd) {
		this.hamd = hamd;
	}

	@Override
	public Object getProperty(int index) {
		// TODO Auto-generated method stub
		Object obj = null;
		switch (index) {
		case 0:
			obj = this.patient_phone;
			break;
		case 1:
			obj = this.pqsi;
			break;
		case 2 :
			obj = this.sleepness;
			break;
		case 3:
			obj = this.hama;
			break;
		case 4:
			obj = this.hamd;
			break;
		default:
			break;
		}
		return obj;
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 5;
	}
	@Override
	public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			info.name = "patient_phone";
			info.type = PropertyInfo.INTEGER_CLASS;
			break;
		case 1:
			info.name = "pqsi";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 2:
			info.name = "sleepness";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 3:
			info.name = "hama";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 4:
			info.name = "hamd";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		default:
			break;
		}
	}
	@Override
	public void setProperty(int index, Object obj) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			this.patient_phone = obj.toString();
			break;
		case 1:
			this.pqsi = obj.toString();
			break;	
		case 2:
			this.sleepness = obj.toString();
			break;
		case 3:
			this.hama = obj.toString();
			break;	
		case 4:
			this.hamd= obj.toString();
			break;
		
		default:
			break;
		}
	}

}
