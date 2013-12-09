package com.bd.Modelo;

import java.util.ArrayList;

import android.util.Pair;

public class Goal {
	
	private String Type_Goal;						//Viajar, Ahorrar,Comprar
	private String EspecificacionS;					// Paris, Fracia
	private String Start_Date;						//Fecha en el que el sueño se cumple;
	private String Value;							//Costo del Sueño
	private String Save;							//Cuanto puedo ahorra
	private int Type_Save;							//Diariamente,Mensualmente,Semanalmente, cuando Pueda
	
	
	
	private String Name;
	private String Image;
	private ArrayList<Pair<String, String>> Saves;  // Historial de Ahorros (Fecha y monto)
	
	
	public Goal(String type_Goal, String especificacionS, String start_Date,
			String value, String save, int type_Save) {
		super();
		Type_Goal = type_Goal;
		EspecificacionS = especificacionS;
		Start_Date = start_Date;
		Value = value;
		Save = save;
		Type_Save = type_Save;
		Saves = new ArrayList<Pair<String,String>>();
	}


	public String getType_Goal() {
		return Type_Goal;
	}


	public void setType_Goal(String type_Goal) {
		Type_Goal = type_Goal;
	}


	public String getEspecificacionS() {
		return EspecificacionS;
	}


	public void setEspecificacionS(String especificacionS) {
		EspecificacionS = especificacionS;
	}


	public String getStart_Date() {
		return Start_Date;
	}


	public void setStart_Date(String start_Date) {
		Start_Date = start_Date;
	}


	public String getValue() {
		return Value;
	}


	public void setValue(String value) {
		Value = value;
	}


	public String getSave() {
		return Save;
	}


	public void setSave(String save) {
		Save = save;
	}


	public int getType_Save() {
		return Type_Save;
	}


	public void setType_Save(int type_Save) {
		Type_Save = type_Save;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getImage() {
		return Image;
	}


	public void setImage(String image) {
		Image = image;
	}


	public ArrayList<Pair<String, String>> getSaves() {
		return Saves;
	}


	public void setSaves(ArrayList<Pair<String, String>> saves) {
		Saves = saves;
	}
	
	
	
	
	
	
	
	
}
