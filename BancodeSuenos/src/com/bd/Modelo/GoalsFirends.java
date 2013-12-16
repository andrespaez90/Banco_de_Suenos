package com.bd.Modelo;

import android.util.Pair;

public class GoalsFirends {
	
	private String Id;
	private String idGoal;
	private String MailFriend;
	private String NameGoal;
	private String MailDreamer;
	
	public GoalsFirends(String ID){
		Id = ID;
	}

	public void setAttribute(Pair<String, String> pareja) {
		 if(pareja.first.equals("idGoal")){
			 idGoal = pareja.second;
             return;
	     } if(pareja.first.equals("MailFriend")){
	    	 MailFriend = pareja.second;
             return;
	     } if(pareja.first.equals("NameGoal")){
             NameGoal = pareja.second;
	    	 return;
	     } if(pareja.first.equals("MailDreamer")){
             MailDreamer = pareja.second;
	    	 return;
	     }
		
	}

	public String getMailFriend() {
		return MailFriend;
	}

	public void setMailFriend(String mailFriend) {
		MailFriend = mailFriend;
	}

	public String getNameGoal() {
		return NameGoal;
	}

	public void setNameGoal(String nameGoal) {
		NameGoal = nameGoal;
	}

	public String getMailDreamer() {
		return MailDreamer;
	}

	public void setMailDreamer(String mailDreamer) {
		MailDreamer = mailDreamer;
	}

	
	
	
}
