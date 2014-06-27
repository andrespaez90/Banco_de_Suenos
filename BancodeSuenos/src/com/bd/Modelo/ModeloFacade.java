package com.bd.Modelo;

import java.util.ArrayList;

import android.util.Pair;

public class ModeloFacade {

	private static User User;
	private static ArrayList<Dream> Goals;
	private static ArrayList<GoalsFirends> FriendsGoals;
	
	public static void setUser(User user){
		User = user;
	}
	
	public static User getUser(){
		return User;
	}
	
	public static ArrayList<Dream> getGoals(){
		return Goals;
	}
	
	
	public static void addGoal(Dream g){
		Goals.add(g);
	}
	
	public static ArrayList<GoalsFirends> getFriendsGoals(){
		return FriendsGoals;
	}
	
	public static void  UserInterpretate(String userdata){
		if(!userdata.equals("Error")){
			Goals = new ArrayList<Dream>();
            String datauser[] = userdata.split("\"name\" :");  
            User u;
            for(int i=1; i<datauser.length ;i++){
                    String datos[] = datauser[i].split(",");
                    String id = datos[0].substring(2,datos[0].lastIndexOf("\""));
                    u = new User(id);
                    for(int j = 1; j<datos.length;j++){
                            try{
                                String[] info = datos[j].split("\"");
                                Pair<String, String> pareja = new Pair<String, String>(info[1], (info[info.length-1]));
                                u.setAttribute(pareja);
                            }catch(Exception ex){
                            }
                    }
                    ModeloFacade.setUser(u);
            }
            return;
		}
	}
	
	public static void GoalsInterpretate(String goals){
		if(!goals.equals("Error")){
			Goals = new ArrayList<Dream>();
            String goal[] = goals.split("\"name\" :");  
            Dream g;
            for(int i=1; i<goal.length ;i++){
                    String datos[] = goal[i].split(",");
                    String id = datos[0].substring(2,datos[0].lastIndexOf("\""));
                    g = new Dream(id);
                    for(int j = 1; j<datos.length;j++){
                            try{
                                String[] info = datos[j].split("\"");
                                Pair<String, String> pareja = new Pair<String, String>(info[1], (info[info.length-1]));
                                g.setAttribute(pareja);
                            }catch(Exception ex){
                            }
                    }
                    ModeloFacade.addGoal(g);
            }
            return;
		}
	}
	
	public static void FriendsGoalsInterpretate(String friendgoals){
		FriendsGoals = new ArrayList<GoalsFirends>();
		if(!friendgoals.equals("Error")){
            String goal[] = friendgoals.split("\"name\" :");  
            GoalsFirends g;
            for(int i=1; i<goal.length ;i++){
                    String datos[] = goal[i].split(",");
                    String id = datos[0].substring(2,datos[0].lastIndexOf("\""));
                    g = new GoalsFirends(id);
                    for(int j = 1; j<datos.length;j++){
                            try{
                                String[] info = datos[j].split("\"");
                                Pair<String, String> pareja = new Pair<String, String>(info[1], (info[info.length-1]));
                                g.setAttribute(pareja);
                            }catch(Exception ex){
                            }
                    }
                    ModeloFacade.FriendsGoals.add(g);
            }
            return;
		}
		
	}
	
}
