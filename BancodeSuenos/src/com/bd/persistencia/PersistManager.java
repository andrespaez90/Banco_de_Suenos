package com.bd.persistencia;

import java.util.ArrayList;
import java.util.Vector;

import com.bd.Modelo.Dream;
import com.bd.Modelo.Saving;
import com.bd.Modelo.User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.EventLogTags.Description;

public class PersistManager extends SQLiteOpenHelper{

	public PersistManager (Context context){
		super(context,"Dream_DataBase",null,1);
	}

	@Override   
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE User (Name TEXT, isPassNumber INTEGER, PassNumber INTEGER); ");
		db.execSQL("CREATE TABLE Dream (idDream INTEGER, NameDream TEXT, isURL Inetger, URL TEXT, idPhoto INTEGER, Date TEXT, TSave INTEGER);");		
		db.execSQL("CREATE TABLE Dream_Save (idDream INTEGER, Type INTEGER, Value INTEGER, Date TEXT); ");
		db.execSQL("CREATE TABLE Account_Save (id INTEGER, Type INTEGER, Description TEXT, Date TEXT, Value INTEGER); ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void SaveUser(String nombre, int isPass, int PassNumber){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO User VALUES ('"+nombre+"',"+isPass+","+PassNumber+")");
	}
	
	public Vector<User> getUser(){
		Vector<User> result = new Vector<User>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursosr = db.rawQuery("SELECT * FROM User", null);
		while(cursosr.moveToNext()){
			User u = new User(cursosr.getString(0), cursosr.getInt(1), cursosr.getInt(2));
			result.add(u);
		}
		cursosr.close();
		return result;
	}

	
	public void SaveDream(Dream d){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO Dream VALUES ("+d.getId()+",'"+d.getDream()+"',"+d.getIsURL()+",'"+d.getIsURL()+"',"+d.getIdImg()+",'"+d.getDate()+"',"+0+")");
		db.close();
	}
	
	public ArrayList<Dream> getDreams(){
		ArrayList<Dream> result = new ArrayList<Dream>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursosr = db.rawQuery("SELECT * FROM Dream", null);
		while(cursosr.moveToNext()){
			Dream u = new Dream(cursosr.getInt(0), cursosr.getString(1), cursosr.getInt(2), cursosr.getString(3), cursosr.getInt(4), cursosr.getString(5),cursosr.getInt(6));
			result.add(u);
		}
		cursosr.close();
		return result;
	}

	public void deleteDream(int id) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM Dream Where idDream = "+id+" ;");
		db.close();
	}

	public void createSave_Dream(int id, int type, int value, String date,int total) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO Dream_Save VALUES ("+id+","+type+","+value+",'"+date+"')");
		if(total < 0)
			total = 0;
		db.execSQL("UPDATE Dream SET TSave ="+total+" WHERE  idDream = "+id+";");
		db.close();
	}

	public void EditDream(int id, String name, int isURL, String url,int id_Image, String date) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("UPDATE Dream SET NameDream = '"+name+"', isURL = "+isURL+", URL = '"+url+"', idPhoto ="+id_Image+", Date = '"+date+"' WHERE idDream ="+id+";");
		db.close();
	}

	public ArrayList<Saving> getSave_account() {
		ArrayList<Saving> result = new ArrayList<Saving>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursosr = db.rawQuery("SELECT * FROM Account_Save", null);
		Saving s;
		while(cursosr.moveToNext()){
			 s = new Saving(cursosr.getInt(0), cursosr.getInt(1), cursosr.getString(2), cursosr.getString(3), cursosr.getInt(4));
			result.add(0, s);
		}
		cursosr.close();
		return result;
	}

	public void saveSving(int id, int type, String description, String date, int value) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO Account_Save VALUES ("+id+","+type+",'"+description+"','"+date+"',"+value+")");
		db.close();
	}

	public void deleteGeneralSaving(int id) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM Account_Save Where id = "+id+" ;");
		db.close();
		
	}

	public void editGeneralSaving(int id, int type, String description, int value) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("UPDATE Account_Save SET Type = "+type+", Description = '"+description+"', Value = "+value+" WHERE id ="+id+";");
		db.close();
	}
	
	
	
}
