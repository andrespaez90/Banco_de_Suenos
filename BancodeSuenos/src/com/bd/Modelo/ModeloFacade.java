package com.bd.Modelo;

import java.util.ArrayList;
import java.util.Iterator;

import com.bd.persistencia.PersistManager;

import android.util.Pair;

public class ModeloFacade {

	private static User User = new User();
	private static ArrayList<Dream> Dreams = new ArrayList<Dream>();
	private static ArrayList<GoalsFirends> FriendsGoals = new ArrayList<GoalsFirends>();
	
	public static void setUser(User user){
		User = user;
	}
	
	public static User getUser(){
		return User;
	}
	
	public static int getUserPassNumber(){
		return User.getPassnumber();
	}
	
	public static ArrayList<Dream> getDreams(){
		return Dreams;
	}
	
	public static void deleteDream(int id) {
		Iterator<Dream> iter = Dreams.iterator();
		while (iter.hasNext()) {
		    Dream g = iter.next();
		    if (g.getId()==id)
		        iter.remove();
		}   
	}
	
	public static void setDreams(ArrayList<Dream> dreams){
		 Dreams = dreams;
	}

	public static ArrayList<GoalsFirends> getFriendsGoals() {
		// TODO Auto-generated method stub
		return FriendsGoals;
	}

	
	
}
