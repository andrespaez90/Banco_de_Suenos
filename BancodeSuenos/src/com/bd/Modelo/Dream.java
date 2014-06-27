package com.bd.Modelo;

import java.util.ArrayList;

import android.util.Pair;

public class Dream {
	
	private String Id;
	private String Type;						//Viajar, Ahorrar,Comprar
	private String Date_Start;					// Paris, Fracia
	private String Date_Done;						//Fecha en el que el sueño se cumple;
	private String Value;							//Costo del Sueño
	private String Saving;							//Cuanto puedo ahorra
	private String T_Saving;							//Diariamente,Mensualmente,Semanalmente, cuando Pueda
	private String Nombre;
	private int Img;
	private String PathImg;
	private String WhyGoal;
	private String UserId;
	private ArrayList<Pair<String, String>> Saves;  // Historial de Ahorros (Fecha y monto)
	
	
	public Dream(String type_Goal, String date_start, String Date_done,
			String value, String save, String type_Save) {
		super();
		Type = type_Goal;
		Date_Start = date_start;
		Date_Done = Date_done;
		Value = value;
		Saving = save;
		T_Saving = type_Save;
		Saves = new ArrayList<Pair<String,String>>();
	}


	public Dream(String Id) {
		super();
		this.Id = Id;
		Saves = new ArrayList<Pair<String,String>>();
	}
	
	public void setAttribute(Pair<String, String> data) {
		 if(data.first.equals("Type")){
			 Type = data.second;
             return;
	     }
	     if(data.first.equals("Date_Start")) {
	    	 Date_Start = data.second;
	             return;
	     }
	     if(data.first.equals("Date_Done")) {
	    	 Date_Done = data.second;
	             return;
	     }
	     if(data.first.equals("Value")) {
	    	 Value = data.second;
	             return;
	     }
	     if(data.first.equals("Saving")) {
	    	 Saving = data.second;
	             return;
	     }
	     if(data.first.equals("T_Saving")){
	    	 T_Saving = data.second;
	            return;
	     } if(data.first.equals("Nombre")){
	    	 Nombre = data.second;
	            return;
	     } if(data.first.equals("img")){
	    	 Img = Integer.parseInt(data.second);
	            return;
	     }if(data.first.equals("PathImg")){
	    	 PathImg = data.second;
	            return;
	     } if(data.first.equals("WhyGoal")){
	    	 WhyGoal = data.second;
	            return;
	     } if(data.first.equals("UserId")){
	    	 UserId = data.second;
	            return;
	     }
		
	}

	
	public String getId() {
		return Id;
	}


	public void setId(String id) {
		Id = id;
	}


	public String getType() {
		return Type;
	}


	public void setType(String type) {
		Type = type;
	}


	public String getDate_Start() {
		return Date_Start;
	}


	public void setDate_Start(String date_Start) {
		Date_Start = date_Start;
	}


	public String getDate_Done() {
		return Date_Done;
	}


	public void setDate_Done(String date_Done) {
		Date_Done = date_Done;
	}


	public String getValue() {
		return Value;
	}


	public void setValue(String value) {
		Value = value;
	}


	public String getSaving() {
		return Saving;
	}


	public void setSaving(String saving) {
		Saving = saving;
	}


	public String getT_Saving() {
		return T_Saving;
	}


	public void setT_Saving(String t_Saving) {
		T_Saving = t_Saving;
	}


	public String getNombre() {
		return Nombre;
	}


	public void setNombre(String nombre) {
		Nombre = nombre;
	}


	public int getImg() {
		return Img;
	}


	public void setImg(int img) {
		Img = img;
	}


	public String getPathImg() {
		return PathImg;
	}


	public void setPathImg(String pathImg) {
		PathImg = pathImg;
	}


	public String getWhyGoal() {
		return WhyGoal;
	}


	public void setWhyGoal(String whyGoal) {
		WhyGoal = whyGoal;
	}


	public String getUserId() {
		return UserId;
	}


	public void setUserId(String userId) {
		UserId = userId;
	}


	public ArrayList<Pair<String, String>> getSaves() {
		return Saves;
	}


	public void setSaves(ArrayList<Pair<String, String>> saves) {
		Saves = saves;
	}


	


	
	
	
	
	
}
