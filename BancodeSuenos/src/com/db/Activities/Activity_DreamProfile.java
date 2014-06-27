package com.db.Activities;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bd.Modelo.Dream;
import com.bd.Modelo.ModeloFacade;
import com.bds.BPO.BPOServer;
import com.devsmind.bancodesuenos.R;
import com.devsmind.bancodesuenos.R.array;
import com.devsmind.bancodesuenos.R.id;
import com.devsmind.bancodesuenos.R.layout;
import com.devsmind.bancodesuenos.R.menu;

public class Activity_DreamProfile extends Activity implements OnClickListener{
	
	private Dream Dream;
	private	String id_dream;
	
	private ImageView ImageDream;
	private TextView NameGoal;
	
	
	private ImageButton btn_Share;
	private ImageButton btn_Edit;
	private ImageButton btn_Settings;
	private ImageButton btn_Delete;
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dream_profile);
		id_dream = this.getIntent().getStringExtra("position");
		for(Dream g:ModeloFacade.getGoals()){
			if(g.getId().equals(id_dream)){
				Dream=g;
				break;
			}
		}
		init();
		addListeners();
		asigners();
		
	}

	private void init() {
		NameGoal=(TextView) findViewById(R.id.tvNameGoal);
		ImageDream=(ImageView) findViewById(R.id.ivPortada);
		
		btn_Edit = (ImageButton) findViewById(R.id.dreamprofile_edit);
		btn_Settings = (ImageButton) findViewById(R.id.dreamprofile_settings);
		btn_Delete = (ImageButton) findViewById(R.id.dreamprofile_delete);
		btn_Share=(ImageButton) findViewById(R.id.dreamprofile_share);
		
	}
	
	private void asigners() {
		NameGoal.setText(Dream.getNombre());
		//btn_Fecha_Fin.setText(Dream.getDate_Done());
		TypedArray imgs = getResources().obtainTypedArray(R.array.photo_ids);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				 imgs.getResourceId(Dream.getImg(), 0));
		ImageDream.setImageBitmap(bitmap);
		
	}

	private void addListeners() {
		btn_Share.setOnClickListener(this);
		btn_Delete.setOnClickListener(this);
		btn_Settings.setOnClickListener(this);
		btn_Edit.setOnClickListener(this);
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perfil_goal, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == btn_Share.getId()){
			//BPOServer.shareGoal();
			return;
		}
		if(view.getId() == btn_Delete.getId()){
			//Created Mehod
			return;
		}
		if(view.getId() == btn_Edit.getId()){
			//Created Method
			return;
		}if(view.getId() == btn_Settings.getId()){
			//Created Method
			return;
		}
				
	}

}
