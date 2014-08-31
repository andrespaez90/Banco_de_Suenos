package com.bd.Modelo;

import java.util.ArrayList;

import android.util.Pair;

public class User {

	private String Name;
	private int isPassnumber;
	private int PassNumber;
	
	    
	public User(String name, int ispassnumber, int passNumber) {
		Name = name;
		isPassnumber = ispassnumber;
		PassNumber = passNumber;
	}	
	
    public User() {
		Name = "Dreamer";
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String nombre) {
		Name = nombre;
	}
	
	public int getPassnumber(){
		return PassNumber;
	}
	
	public void setPassNumber(int passnumber){
		PassNumber = passnumber;
	}

	public int isPassnumber(){
		return isPassnumber;
	}	
	
}
