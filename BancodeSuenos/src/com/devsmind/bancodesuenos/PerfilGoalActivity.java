package com.devsmind.bancodesuenos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bd.Modelo.Goal;
import com.bd.Modelo.ModeloFacade;
import com.bds.BPO.BPOServer;

public class PerfilGoalActivity extends Activity implements OnClickListener{
	
	private Goal goal;
	private TextView tvNameGoal;
	private TextView tvCostoTotal;
	private ImageView ivGlobo;
	private Button btn_Fecha_Fin;
	private Button btnCompartir;
	private Button btnAvances;
	private ImageView ivPortada;
	private	String idGoal;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil_goal);
		idGoal = this.getIntent().getStringExtra("position");
		for(Goal g:ModeloFacade.getGoals()){
			if(g.getId().equals(idGoal)){
				goal=g;
			}
		}
		init();
		addListeners();
		asigners();
		
		
		
	}

	private void asigners() {
		tvCostoTotal.setText(goal.getValue());
		tvNameGoal.setText(goal.getNombre());
		btn_Fecha_Fin.setText(goal.getDate_Done());
		TypedArray imgs = getResources().obtainTypedArray(R.array.photo_ids);
		 Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				 imgs.getResourceId(goal.getImg(), 0));
		 ivPortada.setImageBitmap(bitmap);
		
	}

	private void addListeners() {
		btnCompartir.setOnClickListener(this);
		
	}

	private void init() {
		tvNameGoal=(TextView) findViewById(R.id.tvNameGoal);
		tvCostoTotal=(TextView) findViewById(R.id.tvCostoTotal);
		ivGlobo=(ImageView) findViewById(R.id.ivGlobo);
		btn_Fecha_Fin=(Button) findViewById(R.id.btn_Fecha_Fin);
		btnAvances=(Button) findViewById(R.id.btnAvances);
		btnCompartir=(Button) findViewById(R.id.btnCompartir);
		ivPortada=(ImageView) findViewById(R.id.ivPortada);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perfil_goal, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(":D Comparitendo...");
		alert.setMessage("Ingresa el correo de la persona con quien quieres conpartirlo");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			String value = input.getText().toString();	
			if(value.contains("@") && value.contains(".") && value.length()>7){
				BPOServer.ShareGoal(idGoal, value);
			}
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}


}
