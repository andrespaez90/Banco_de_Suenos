package com.db.Activities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bd.Modelo.Dream;
import com.bd.Modelo.ModeloFacade;
import com.bd.persistencia.PersistManager;
import com.db.adapters.NumberTextWatcher;
import com.devsmind.bancodesuenos.R;
import com.devsmind.bancodesuenos.R.array;
import com.devsmind.bancodesuenos.R.id;
import com.devsmind.bancodesuenos.R.layout;
import com.devsmind.bancodesuenos.R.menu;

public class Activity_DreamProfile extends Activity implements OnClickListener{
	
	private final int CODE_EDITDREM = 001;
	
	private Dream Dream;
	private	int id_dream;
	
	private ImageView ImageDream;
	private TextView NameGoal;
	private TextView Date_Dream;
	private TextView Dream_saving;
	
	private ImageButton btn_Share;
	private Button btn_History;
	private Button btn_Settings;
	private Button btn_Delete;
	private Button btn_Add;
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dream_profile);
		id_dream = getIntent().getIntExtra("position",0);
		for(Dream g:ModeloFacade.getDreams()){
			if(g.getId() == id_dream){
				Dream=g;
				init();
				addListeners();
				asigners();
				break;
			}
		}		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private void init() {
		NameGoal=(TextView) findViewById(R.id.tvNameGoal);
		ImageDream=(ImageView) findViewById(R.id.ivPortada);
		Date_Dream = (TextView) findViewById(R.id.dreamprofile_date);
		Dream_saving = (TextView) findViewById(R.id.dreamprofile_saving);
		
		btn_Add = (Button) findViewById(R.id.dreamprofile_add);
		btn_History = (Button) findViewById(R.id.dreamprofile_history);
		btn_Settings = (Button) findViewById(R.id.dreamprofile_settings);
		btn_Delete = (Button) findViewById(R.id.dreamprofile_delete);
		btn_Share=(ImageButton) findViewById(R.id.dreamprofile_share);
		
	}
	
	private void asigners() {
		NameGoal.setText(Dream.getDream());
		Date_Dream.setText(Dream.getDate());
		updateSaving(Dream.getSaving());
		TypedArray imgs = getResources().obtainTypedArray(R.array.photo_ids);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				 imgs.getResourceId(Dream.getIdImg(), 0));
		ImageDream.setImageBitmap(bitmap);
		
	}

	private void updateSaving(int value){
		if(value <= 0){
			Dream_saving.setText(getString(R.string.signo_pesos)+"0.00");
			return;
		}
		Dream_saving.setText(getString(R.string.signo_pesos)+formatAmount(value));
		if(value > 9999 ){
			Dream_saving.setTextSize(58);
		}if(value > 999999 ){
			Dream_saving.setTextSize(50);
		}
	}
	
	private void addListeners() {
		btn_Share.setOnClickListener(this);
		btn_Delete.setOnClickListener(this);
		btn_Settings.setOnClickListener(this);
		btn_History.setOnClickListener(this);
		btn_Add.setOnClickListener(this);
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
			shareDream();
			return;
		}
		if(view.getId() == btn_Delete.getId()){
			DeleteDream();
			return;
		}
		if(view.getId() == btn_History.getId()){
			//Created Method
			return;
		}if(view.getId() == btn_Settings.getId()){
			editDream();
			return;
		}if(view.getId() == btn_Add.getId()){
			//add money
			newSave();
			return; 
		}
				
	}

	private void editDream() {
		Intent i = new Intent(this,Activity_NewDream.class);
		i.putExtra("Type", 1);
		i.putExtra("id", Dream.getId());
		i.putExtra("Name", Dream.getDream());
		i.putExtra("Image", Dream.getIdImg());
		i.putExtra("Date", Dream.getDate());
		startActivityForResult(i, CODE_EDITDREM);
	}

	private void newSave() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_saving);
		dialog.setTitle(getString(R.string.string_balance));
        
		
		Button dialogButtonA = (Button) dialog.findViewById(R.id.saving_acept);
		Button dialogButtonC = (Button) dialog.findViewById(R.id.saving_cancel);
		final EditText money = (EditText) dialog.findViewById(R.id.saving_money);
		money.addTextChangedListener(new NumberTextWatcher(money));
		dialogButtonA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String texttoNumber = money.getText().toString();
				texttoNumber = texttoNumber.replace(".", "");
				if(!texttoNumber.equals("")){
					int value = Integer.parseInt(texttoNumber);
					int newvalue = value;
					String date =TodayDate();
					int type=0;
					PersistManager pm = new PersistManager(getApplicationContext());
					if(((RadioButton)dialog.findViewById(R.id.saving_add)).isChecked()){
						type=1;
						newvalue += Dream.getSaving();
					}else{
						newvalue = Dream.getSaving()-value;
					}
					Dream.setSaving(value);
					pm.createSave_Dream(Dream.getId(),type,value,date,newvalue);
					updateSaving(newvalue);
					dialog.dismiss();
				}
			}
		});
		dialogButtonC.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	 }
		
	public static String formatAmount(int num) {
		
	    DecimalFormat decimalFormat = new DecimalFormat();
	    DecimalFormatSymbols decimalFormateSymbol = new DecimalFormatSymbols();
	    decimalFormateSymbol.setCurrencySymbol("$");
	    decimalFormateSymbol.setGroupingSeparator('.');
	    decimalFormateSymbol.setMonetaryDecimalSeparator(',');
	    ((DecimalFormat) decimalFormat).setDecimalFormatSymbols(decimalFormateSymbol);
	    return decimalFormat.format(num);
	}
	
	private String TodayDate(){
		final Calendar calendar = Calendar.getInstance();
		int myYear = calendar.get(Calendar.YEAR);
		int myMonth = calendar.get(Calendar.MONTH);
		int myDay = calendar.get(Calendar.DAY_OF_MONTH);
		return(myDay+"/"+(myMonth+1)+"/"+myYear);
		
	}
	
	private void shareDream() {
		String shareBody = getString(R.string.share_my_dream)+" "+Dream.getDream();
		shareBody = shareBody+" "+getString(R.string.share_date_dream)+" "+Dream.getDate();
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
	    sharingIntent.setType("text/plain");
	    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
	    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
	    startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

	private void DeleteDream() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.acpass_title_info));
		builder.setMessage("Esta seguro que desea Borrar Este sueño")
			.setNegativeButton("Cancelar",new  DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int id) {
		        	
		        }
		    })
		 	.setPositiveButton("Aceptar",new  DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	        	PersistManager pm = new PersistManager(getApplicationContext());
	        	pm.deleteDream(Dream.getId());
	        	ModeloFacade.deleteDream(Dream.getId());
	        	finish();
	        }
	    });
			 builder.create();
			 builder.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CODE_EDITDREM){
			PersistManager pm = new PersistManager(this);
			ModeloFacade.setDreams(pm.getDreams());
			for(Dream g:ModeloFacade.getDreams()){
				if(g.getId() == id_dream){
					Dream=g;
					asigners();
					break;
				}
			}	
		}
	}
	
}
