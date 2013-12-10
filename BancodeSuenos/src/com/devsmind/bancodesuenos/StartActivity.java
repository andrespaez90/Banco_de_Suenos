package com.devsmind.bancodesuenos;

import com.bd.persistencia.PersistManager;
import com.db.Activities.LoginActivity;
import com.db.Activities.NewGoalActivity;

import android.location.Address;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends Activity implements OnClickListener {
 
	
	private ImageView Logo;
	private Button IngresarCorreo;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Init();
        addListeners();
    }

    private void Init() {
		Logo = (ImageView) findViewById(R.id.start_logo);
		IngresarCorreo = (Button) findViewById(R.id.start_bcorreo);
	}


    private void addListeners() {
		Logo.setOnClickListener(this);
		IngresarCorreo.setOnClickListener(this);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

	@Override
	public void onClick(View view) {
		if (view.getId() == Logo.getId()){
			ValidateUser();
			return;
		}
		if (view.getId() == IngresarCorreo.getId()){
			Intent i = new Intent(this,LoginActivity.class);
			startActivity(i);
			return;
		}
	}

	private void ValidateUser() {
		PersistManager pm = new PersistManager(StartActivity.this);
    	if(pm.getAllRegisters("User").size()==0){
    		//Crear Usuario falso
    		Intent i = new Intent(this, NewGoalActivity.class);
			startActivity(i);
    	}else{
    		//Actualzar Modelo
	    	Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
    	}
		
	}
    
}
