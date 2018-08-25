package com.cqu.doctor.dao;

import java.util.ArrayList;
import java.util.List;

import com.cqu.doctor.model.PatientInformation;
import com.cqu.doctor.model.PatientTest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class PatientTestDAO {
	private final static String TABLE_NAME02 = "test_table";
	private String PATIENT_ID = "id";
	private String PATIENT_PQSI = "pqsi";
	private String PATIENT_Sleepness = "sleepness";
	private String PATIENT_HAMA = "hama";
	private String PATIENT_HAMD = "hamd";
	private String PATIENT_ISI = "isi";
	private String PATIENT_PHONE = "patient_phone";
	private PatientDB helper;
	private SQLiteDatabase db;
	public PatientTestDAO(Context context){
		helper = new PatientDB(context);// 初始化DBOpenHelper对象
	}
	//增加病人测试数据
	public void insert(PatientTest patienttest){
		//创建一个可写的数据库
		db = helper.getWritableDatabase();
		//ContentValues以键值对的形式存放数据 
		ContentValues cv = new ContentValues();
		cv.put(PATIENT_PQSI,patienttest.getPqsi());
		cv.put(PATIENT_Sleepness, patienttest.getSleepness());
		cv.put(PATIENT_HAMA, patienttest.getHama());
		cv.put(PATIENT_HAMD, patienttest.getHamd());
		System.out.println("创建ISI");
		cv.put(PATIENT_PHONE, patienttest.getPatient_phone());
		System.out.println("创建ISI成功！！");
		db.insert(TABLE_NAME02, null, cv);
		//return row;	
		
	}
	
	public void deletetable(){
//		//创建一个可读的数据库
		db = helper.getReadableDatabase();
		//清空一个表格
		db.execSQL("DELETE FROM test_table");
//		//删除一个表格
//		String sql=" DROP TABLE IF EXISTS "+TABLE_NAME;
//		db.execSQL(sql);
	}
	//修改病人信息
	public void updatePQSI(int id ,PatientTest patienttest){
		//获得一个可写的数据库
		SQLiteDatabase db = helper.getWritableDatabase();
		//修改条件
		String where = PATIENT_ID + " = ?";
		//修改条件的参数  Integer.toString(id)，把数字用字符串表示
		String[] whereValue = { Integer.toString(id) };
				 
		ContentValues cv = new ContentValues();
		cv.put(PATIENT_PQSI,patienttest.getPqsi());
		System.out.println("失败了？？？？？");
		db.update(TABLE_NAME02, cv, where, whereValue);
		}
	//修改病人信息
		public void updateHAMD(int id ,PatientTest patienttest){
			//获得一个可写的数据库
			SQLiteDatabase db = helper.getWritableDatabase();
			//修改条件
			String where = PATIENT_ID + " = ?";
			//修改条件的参数  Integer.toString(id)，把数字用字符串表示
			String[] whereValue = { Integer.toString(id) };
					 
			ContentValues cv = new ContentValues();
			cv.put(PATIENT_HAMD, patienttest.getHamd());
			db.update(TABLE_NAME02, cv, where, whereValue);
			}
		//修改病人信息
		public void updateSleepness(int id ,PatientTest patienttest){
			//获得一个可写的数据库
			SQLiteDatabase db = helper.getWritableDatabase();
			//修改条件
			String where = PATIENT_ID + " = ?";
			//修改条件的参数  Integer.toString(id)，把数字用字符串表示
			String[] whereValue = { Integer.toString(id) };
					 
			ContentValues cv = new ContentValues();
			cv.put(PATIENT_Sleepness, patienttest.getSleepness());
			db.update(TABLE_NAME02, cv, where, whereValue);
			}
		//修改病人信息
		public void updateHAMA(int id ,PatientTest patienttest){
			//获得一个可写的数据库
			SQLiteDatabase db = helper.getWritableDatabase();
			//修改条件
			String where = PATIENT_ID + " = ?";
			//修改条件的参数  Integer.toString(id)，把数字用字符串表示
			String[] whereValue = { Integer.toString(id) };
					 
			ContentValues cv = new ContentValues();
			cv.put(PATIENT_HAMA, patienttest.getHama());
			db.update(TABLE_NAME02, cv, where, whereValue);
			}
		//修改病人信息
		public void updateISI(int id ,PatientTest patienttest){
			//获得一个可写的数据库
			SQLiteDatabase db = helper.getWritableDatabase();
			//修改条件
			String where = PATIENT_ID + " = ?";
			//修改条件的参数  Integer.toString(id)，把数字用字符串表示
			String[] whereValue = { Integer.toString(id) };
							 
			ContentValues cv = new ContentValues();
			cv.put(PATIENT_ISI, patienttest.getIsi());
			db.update(TABLE_NAME02, cv, where, whereValue);
			}
	//查找病人信息
	public PatientTest find(String patient_phone){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
                        "select id,pqsi,sleepness,hama,hamd,patient_phone from test_table where patient_phone = ?",
                        new String[] {patient_phone});//根据编号查找到的病人信息，并存储到Cursor中
		//遍历查找到的病人信息
		if(cursor.moveToNext()){
			//将遍历查找到的病人信息存储到PatientInformation类中
			return new PatientTest(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("pqsi")),
                    cursor.getString(cursor.getColumnIndex("sleepness")),
                    cursor.getString(cursor.getColumnIndex("hama")),
                    cursor.getString(cursor.getColumnIndex("hamd")),
                    cursor.getString(cursor.getColumnIndex("patient_phone"))
                    );
		}
		return null;	
	}
	//删除数据
	public void delete(Integer... ids){
	/*db = helper.getWritableDatabase();
	String where = PATIENT_ID + " = ?";
	String[] whereValue ={ Integer.toString(id) };
	db.delete(TABLE_NAME, where, whereValue);*/
		if(ids.length > 0){ //判断是否存在要删除的id
			StringBuffer sb = new StringBuffer();
			for(int i = 0;i < ids.length;i++){
				sb.append('?').append(','); //将删除条件添加到StringBuffer中
			}
			sb.deleteCharAt(sb.length() - 1);
			db = helper.getWritableDatabase();
			db.execSQL("delete from patient where _id in (" + sb + ")",
                    (Object[]) ids);
		}
	}
	/**
	 * 该方法主要是从病人信息中的指定索引处获得病人信息
	 * **/
	public List<PatientInformation> getScrollData(int start, int count) {
		 
        List<PatientInformation> patient = new ArrayList<PatientInformation>();// 创建集合对象
        db = helper.getWritableDatabase();
        //获取所有病人信息
        Cursor cursor = db.rawQuery("select * from patient_table limit ?,?",
                new String[] {String.valueOf(start), String.valueOf(count)});
        while (cursor.moveToNext())//遍历所有病人信息
        {
            //将遍历到的所有病人信息添加到集合中
        	patient.add(new PatientInformation(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("current_date")),
                    cursor.getString(cursor.getColumnIndex("patient_name")),
                    cursor.getString(cursor.getColumnIndex("patient_gender")),
                    cursor.getString(cursor.getColumnIndex("patient_age")),
                    cursor.getString(cursor.getColumnIndex("patient_postion")),
                    cursor.getString(cursor.getColumnIndex("patient_scde")),
                    cursor.getString(cursor.getColumnIndex("patient_insomnia")),
                    cursor.getString(cursor.getColumnIndex("patient_mhistory")),
                    cursor.getString(cursor.getColumnIndex("patient_phone"))
                    ));
        	}
        return patient;//返回集合
     
	}
	//得到数据库中的数据
		public Cursor export(){
			db = helper.getWritableDatabase();
			Cursor cursor = db.rawQuery( "select * from test_table",null);
			return cursor;
		}
	//获取总记录数
	public long getCount() {
		 
        db = helper.getWritableDatabase();
        Cursor cursor = db
                .rawQuery("select count(id) from patient_table", null);//获取病人信息记录数
        if (cursor.moveToNext())//判断Cursor中是否有数据
        {
 
            return cursor.getLong(0);//返回总记录数
         
        }
        return 0;//没有数据则返回0
     
	}
	//获取病人的最大编号
	public int getMaxId() {
		 
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select max(id) from patient_table", null);//获取病人信息中的最大编号
        //访问Cursor中的最后一条数据
        while (cursor.moveToLast()) {
            return cursor.getInt(0);//获取访问到的数据，即最大编号
         
}
        return 0;//如果没有数据则返回0
     
}

}
