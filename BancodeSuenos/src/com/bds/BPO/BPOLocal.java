package com.bds.BPO;

import com.bd.Modelo.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BPOLocal {
	
	
	public static boolean isNetwork(Activity activity){
		ConnectivityManager conexion = (ConnectivityManager) activity.getSystemService(activity.getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo[] nets = conexion.getAllNetworkInfo();
        for(int i=0; i < nets.length; i++){
                if(nets[i].getState() == NetworkInfo.State.CONNECTED){
                	  return true;
                	  }
            }
          return false;
	}
	
	public static void PossitiveMessageDialog(String Title,String message, Context context){
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 builder.setTitle(Title);
		 builder.setMessage(message)
		 	.setPositiveButton("Aceptar",new  DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
        	
        }
    });
		 builder.create();
		 builder.show();
	}
	
	public static void NegativeMessageDialog(String Title,String message, Context context){
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 builder.setTitle(Title);
		 builder.setMessage(message)
		 	.setPositiveButton("Cancelar",new  DialogInterface.OnClickListener() {
       public void onClick(DialogInterface dialog, int id) {
       	
       }
   });
		 builder.create();
		 builder.show();
	}
	
	
}
