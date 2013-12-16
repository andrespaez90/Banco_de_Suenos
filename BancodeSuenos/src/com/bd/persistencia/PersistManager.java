package com.bd.persistencia;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersistManager extends SQLiteOpenHelper{

	public PersistManager (Context context){
		super(context,"DataBase",null,1);
	}

	@Override   
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE User (name TEXT, mail TEXT, pass TEXT);" +
				"CREATE TABLE UserFacebook(Id TEXT, name TEXT)");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void SaveUser(String nombre, String correo, String pass){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO User VALUES ('"+nombre+"',"+"'"+correo+"'"+"'"+pass+"')");
	}
	
	public void SaveUseFacebook(String Id, String nombre){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO UserFacebook VALUES ('"+Id+"',"+"'"+nombre+"')");
	}
	
	public Vector<String> getAllRegisters(String Table){
		Vector<String> result = new Vector<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursosr = db.rawQuery("SELECT * FROM "+Table, null);
		while(cursosr.moveToNext()){
			result.add(cursosr.getString(0));
		}
		cursosr.close();
		return result;
	}

	
	
	
}
