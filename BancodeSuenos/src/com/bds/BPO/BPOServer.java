package com.bds.BPO;

import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.bd.Modelo.ModeloFacade;

public class BPOServer {
	 private static final String NAMESPACE = "http://appengine.devsmind.com/";
     
     
     //----------------Usuario-----------------------------//
     
     public static String CreateValidateUser(String email,  String pass){
             String methodname = "UserValidator";  
             SoapObject request = new SoapObject(NAMESPACE,methodname);
             request.addProperty("arg0",email);
             request.addProperty("arg1",pass);
             ExecuteMethod em = new ExecuteMethod();
             em.execute(request);
             try{
                     return em.get().toString();
             }catch(InterruptedException IE){
                     return "Error";
             }catch (ExecutionException ee) {
                     return "Error";
             }                
     }
     
     public static boolean CreateUserFacebook(String Id, String Nombre){
    	 String methodname = "UserCreator";
    	 SoapObject request = new SoapObject(NAMESPACE,methodname);
    	 request.addProperty("arg0",Id);
         request.addProperty("arg1",Nombre);
         ExecuteMethod em = new ExecuteMethod();
         em.execute(request);
         try{
                 return Boolean.parseBoolean(em.get().toString());
         }catch(InterruptedException IE){
                 return false;
         }catch (ExecutionException ee) {
                 return false;
         }                
     }
     

     public static boolean CreateGoal(String[] data,int id){
    	 String methodname = "CreateGoal";
    	 SoapObject request = new SoapObject(NAMESPACE, methodname);
    	 request.addProperty("arg0",data[0]);
    	 request.addProperty("arg1",data[1]);
    	 request.addProperty("arg2",data[2]);
    	 request.addProperty("arg3",data[3]);
    	 request.addProperty("arg4",data[4]);
    	 request.addProperty("arg5",data[5]);
    	 request.addProperty("arg6",data[6]);
    	 request.addProperty("arg7",id);
    	 request.addProperty("arg8",data[7]);
    	 request.addProperty("arg9",data[8]);
    	 ExecuteMethod em = new ExecuteMethod();
         em.execute(request);
         try{
                 return Boolean.parseBoolean(em.get().toString());
         }catch(InterruptedException IE){
                 return false;
         }catch (ExecutionException ee) {
                 return false;
         }                
     }
     
     //-------Goal
     public static String GetGoal(String idUser){
    	 String methodname = "getGoals";
    	 SoapObject request = new SoapObject(NAMESPACE, methodname);
    	 request.addProperty("arg0",idUser);
    	 ExecuteMethod em = new ExecuteMethod();
         em.execute(request);
         try{
                 return em.get().toString();
         }catch(InterruptedException IE){
                 return null;
         }catch (ExecutionException ee) {
                 return null;
         }                
     }
     
     public static boolean ShareGoal(String id, String mail){
    	 String methodname = "addFriendGoal";
    	 SoapObject request = new SoapObject(NAMESPACE,methodname);
    	 request.addProperty("arg0",id);
         request.addProperty("arg1",mail);
         ExecuteMethod em = new ExecuteMethod();
         em.execute(request);
         try{
                 return Boolean.parseBoolean(em.get().toString());
         }catch(InterruptedException IE){
                 return false;
         }catch (ExecutionException ee) {
                 return false;
         }            
     }
 
     //Friends Goals
     public static String FriendsGoals(){
         String methodname = "FindGoalShared";  
         SoapObject request = new SoapObject(NAMESPACE,methodname);
         request.addProperty("arg0",ModeloFacade.getUser().getMail());
         ExecuteMethod em = new ExecuteMethod();
         em.execute(request);
         try{
                 return em.get().toString();
         }catch(InterruptedException IE){
                 return "Error";
         }catch (ExecutionException ee) {
                 return "Error";
         }                
 }
}