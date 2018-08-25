package com.cqu.doctor.dao;

import com.cqu.doctor.model.DoctorAdivce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DoctorAdviceDAO {
	private final static String TABLE_NAME04 = "doctor_advice";
	public final static String PATIENT_HPONE = "patient_phone";
	public final static String DOCTORADVICE = "advice";
	
	private PatientDB helper;
	private SQLiteDatabase db;
	public DoctorAdviceDAO(Context context){
		helper = new PatientDB(context);// ��ʼ��DBOpenHelper����
	}
	
	//����ҽ��ҽ������
	public void insert(DoctorAdivce adivce){
		//����һ����д�����ݿ�
		db = helper.getWritableDatabase();
		//ContentValues�Լ�ֵ�Ե���ʽ������� 
		ContentValues cv = new ContentValues();
		cv.put(PATIENT_HPONE, adivce.getPatient_phone());
		cv.put(DOCTORADVICE, adivce.getAdvice());
		db.insert(TABLE_NAME04, null, cv);
	}
	
	//����ҽ��
	public DoctorAdivce find(int id){
				db = helper.getWritableDatabase();
				Cursor cursor = db.rawQuery(
	                    "select * from doctor_advice where id = ?",
	                  new String[] {String.valueOf(id)});//���ݱ�Ų��ҵ��Ĳ�����Ϣ�����洢��Cursor��
				//�������ҵ��Ĳ�����Ϣ
				if(cursor.moveToNext()){
					//���������ҵ��Ĳ�����Ϣ�洢��PatientInformation����
					return new DoctorAdivce(
		                    cursor.getString(cursor.getColumnIndex("patient_phone")),
		                    cursor.getString(cursor.getColumnIndex("advice"))
		                    );
				}
				return null;	
			}
	//��ȡ�ܼ�¼��
		public long getCount() {
			 
	        db = helper.getWritableDatabase();
	        Cursor cursor = db
	                .rawQuery("select count(id) from doctor_advice", null);//��ȡ������Ϣ��¼��
	        if (cursor.moveToNext())//�ж�Cursor���Ƿ�������
	        {
	 
	            return cursor.getLong(0);//�����ܼ�¼��
	         
	        }
	        return 0;//û�������򷵻�0
	     
		}
}

