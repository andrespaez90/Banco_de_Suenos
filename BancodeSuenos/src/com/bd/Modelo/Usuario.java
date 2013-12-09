package com.bd.Modelo;

import java.util.ArrayList;

public class Usuario {

	private String Name;
	private String Mail;
	private String Password;
	
	private ArrayList<Goal> Goals; 
	
	
	public Usuario(String name, String mail, String password) {
		super();
		Name = name;
		Mail = mail;
		Password = password;
		Goals = new ArrayList<Goal>();
	}	
	
	public String getName() {
		return Name;
	}
	public void setName(String nombre) {
		Name = nombre;
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
