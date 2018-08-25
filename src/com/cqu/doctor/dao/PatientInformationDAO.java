package com.cqu.doctor.dao;

import java.util.ArrayList;
import java.util.List;

import com.cqu.doctor.model.PatientInformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class PatientInformationDAO {
	private final static String TABLE_NAME = "patient_table";
	public final static String PATIENT_ID = "id";
	public final static String CURRENT_DATE = "current_date";
	public final static String PATIENT_NAME = "patient_name";
	//private final static String TABLE_NAME02 = "test_table";
	public final static String PATIENT_GENDER = "patient_gender";
	public final static String PATIENT_AGE = "patient_age";
	public final static String PATIENT_POSITION = "patient_postion";
	//文化程度
	public final static String PATIENT_SCDE = "patient_scde";
	//失眠时间
	public final static String PATIENT_INSOMNIA = "patient_insomnia";
	//病史
	public final static String PATIENT_MHISTORY = "patient_mhistory";
	public final static String PATIENT_HPONE = "patient_phone";
	public final static String PATIENT_HAMD = "patient_hamd";
	
	
	private PatientDB helper;
	private SQLiteDatabase db;
	public PatientInformationDAO(Context context){
		helper = new PatientDB(context);// 初始化DBOpenHelper对象
	}
	//增加病人数据
	public void insert(PatientInformation patient){
		//创建一个可写的数据库
		db = helper.getWritableDatabase();
		//ContentValues以键值对的形式存放数据 
		ContentValues cv = new ContentValues();
		cv.put(CURRENT_DATE, patient.getCurrent_date());
		cv.put(PATIENT_NAME, patient.getPatient_name());
		cv.put(PATIENT_GENDER, patient.getPatient_gender());
		cv.put(PATIENT_AGE, patient.getPatient_age());
		cv.put(PATIENT_POSITION, patient.getPatient_postion());
		cv.put(PATIENT_SCDE, patient.getPatient_scde());
		cv.put(PATIENT_INSOMNIA, patient.getPatient_insomnia());
		cv.put(PATIENT_MHISTORY, patient.getPatient_mhistory());
		cv.put(PATIENT_HPONE, patient.getPatient_phone());
		//cv.put(PATIENT_HAMD, patient.getPatient_hamd());
		db.insert(TABLE_NAME, null, cv);
		//return row;	
		
//		PatientTestDAO ptdao = new PatientTestDAO(con);
//		PatientTest patienttest = new PatientTest();
//		ptdao.insert(patienttest);
		
		//创建测试表格
//		String sql02 = "CREATE TABLE " + TABLE_NAME02 + " (" + PATIENT_ID + INTEGER primary key autoincrement,  PQSI varchar(30), )";
//				db.execSQL(sql02);
//		db.execSQL("CREATE TABLE  test_table(id integer primary key autoincrement, pqsi varchar(30), sleepness varchar(30),hama varchar(60), hamd varchar(60))");
//		应该是在这里插入数据才对。。。。
//				System.out.println("创建测试表成功！！");
				
	}
	//修改病人信息
	public void updatePatientInformation(int id ,PatientInformation patient){
		//获得一个可写的数据库
		SQLiteDatabase db = helper.getWritableDatabase();
		//修改条件
		String where = PATIENT_ID + " = ?";
		//修改条件的参数  Integer.toString(id)，把数字用字符串表示
		String[] whereValue = { Integer.toString(id) };
				 
		ContentValues cv = new ContentValues();
		cv.put(CURRENT_DATE, patient.getCurrent_date());
		cv.put(PATIENT_NAME, patient.getPatient_name());
		cv.put(PATIENT_GENDER, patient.getPatient_gender());
		cv.put(PATIENT_POSITION, patient.getPatient_postion());
		cv.put(PATIENT_SCDE, patient.getPatient_scde());
		cv.put(PATIENT_INSOMNIA, patient.getPatient_insomnia());
		cv.put(PATIENT_MHISTORY, patient.getPatient_mhistory());
		cv.put(PATIENT_HPONE, patient.getPatient_phone());
		//cv.put(PATIENT_HAMD, patient.getPatient_hamd());
		db.update(TABLE_NAME, cv, where, whereValue);
		}
	//查找病人信息
		public PatientInformation find(String patient_phone){
			db = helper.getWritableDatabase();
//			Cursor cursor = db.rawQuery(
//	                        "select id,current_date,patient_name,patient_gender,patient_age,patient_postion,patient_scde,patient_insomnia,patient_mhistory,patient_phone from patient_table where patient_phone = ?",
//	                        new String[] {patient_phone});//根据编号查找到的病人信息，并存储到Cursor中
//			Cursor cursor = db.rawQuery("select * from patient_table where patient_phone=?",
//					new Stirng[]{patient_phone});
			Cursor cursor = db.rawQuery(
                    "select * from patient_table where patient_phone = ?",
                  new String[] {patient_phone});//根据编号查找到的病人信息，并存储到Cursor中
			//遍历查找到的病人信息
			if(cursor.moveToNext()){
				System.out.println("date---"+ cursor.getString(cursor.getColumnIndex("current_date")));
				//将遍历查找到的病人信息存储到PatientInformation类中
				return new PatientInformation(
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
	                    );
			}
			return null;	
		}
	//选择
	public List<PatientInformation> select(){
	//创建一个可读的数据库
	db = helper.getReadableDatabase();
	List<PatientInformation> patient = new ArrayList<PatientInformation>();// 创建集合对象
	System.out.println("这句话成功？？？");
	Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
	//遍历查找到的病人信息
			if(cursor.moveToNext()){
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
	return null;	
	}
	public void deletetable(){
//		//创建一个可读的数据库
		db = helper.getReadableDatabase();
		//清空一个表格
		db.execSQL("DELETE FROM patient_table");
//		//删除一个表格
//		String sql=" DROP TABLE IF EXISTS "+TABLE_NAME;
//		db.execSQL(sql);
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
		Cursor cursor = db.rawQuery( "select * from patient_table",null);
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
