package com.bd.Modelo;

import java.util.ArrayList;

import android.util.Pair;

public class User {

	private String Name;
	private String Mail;
	private String Password;
	
	private ArrayList<Goal> Goals; 
	    
	public User(String mail, String password) {
		super();
		Mail = mail;
		Password = password;
		Name = "Soñador";
		Goals = new ArrayList<Goal>();
	}	
	
	public User(String id) {
		super();
		Mail = id;
		Goals = new ArrayList<Goal>();
	}
	
	public void setAttribute(Pair<String, String> data) {
		 if(data.first.equals("Contrasena")){
             return;
	     }
	     if(data.first.equals("Name")) {
	    	 Name = data.second;
	             return;
	     }
	    
		
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String nombre) {
		Name = nombre;
	}
	
	public String getId() {
		return Mail;
	}
	
	public String getMail() {
		return Mail;
	}
	public void setCorreo(String mail) {
		Mail = mail;
	}
	public String getContrasena() {
		return Password;
	}
	public void setContrasena(String password) {
		Password = password;
	}


		
	
}
