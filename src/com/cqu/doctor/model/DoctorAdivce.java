package com.cqu.doctor.model;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class DoctorAdivce implements KvmSerializable{
	String patient_phone;
	String advice;

	public DoctorAdivce(String patient_phone, String advice) {
		super();
		this.patient_phone = patient_phone;
		this.advice = advice;
	}

	public String getPatient_phone() {
		return patient_phone;
	}

	public void setPatient_phone(String patient_phone) {
		this.patient_phone = patient_phone;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public Object getProperty(int index) {
		// TODO Auto-generated method stub
		Object obj = null;
		switch (index) {
		case 0:
			obj = this.patient_phone;
			break;
		case 1 :
			obj = this.advice;
			break;
		default:
			break;
		}
		return obj;
	}

	public int getPropertyCount() {
		// TODO Auto-generated method stub
		//这个实体类有几个属性
		return 2;
	}

	public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			info.name = "patient_phone";
			info.type = PropertyInfo.STRING_CLASS;
			break;
		case 1:
			info.name = "advice";
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
			this.patient_phone = obj.toString();
			break;	
		case 1:
			this.advice = obj.toString();
			break;
		default:
			break;
		}
	}

	public DoctorAdivce() {
		super();
	}
}