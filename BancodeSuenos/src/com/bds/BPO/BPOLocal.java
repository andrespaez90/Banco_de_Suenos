package com.bds.BPO;

import android.app.Activity;
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

}
