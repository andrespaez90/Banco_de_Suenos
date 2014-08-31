package com.devsmind.bancodesuenos;

import java.security.MessageDigest;

import com.bd.Modelo.ModeloFacade;
import com.bds.BPO.BPOLocal;
import com.devsmind.bancodesuenos.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Passnumber_Activity extends Activity implements OnClickListener{

	private Button btn_1, btn_2, btn_3, btn_4;
	private Button btn_5, btn_6, btn_7, btn_8;
	private Button btn_9, btn_0, btn_delete;
	
	private EditText pass_1, pass_2, pass_3, pass_4;
	private String Key;
	
	private int Type; // if type is for nuew passnumber the value is 1 
	private String FirstPass;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_password);
	        init();
	        Type = getIntent().getIntExtra("Type", 0);
	        
	 }

	private void init() {
		FirstPass ="-1";
		Key = "";
		btn_1 = (Button) findViewById(R.id.activity_pass_num1);
		btn_2 = (Button) findViewById(R.id.activity_pass_num2);
		btn_3 = (Button) findViewById(R.id.activity_pass_num3);
		btn_4 = (Button) findViewById(R.id.activity_pass_num4);
		btn_5 = (Button) findViewById(R.id.activity_pass_num5);
		btn_6 = (Button) findViewById(R.id.activity_pass_num6);
		btn_7 = (Button) findViewById(R.id.activity_pass_num7);
		btn_8 = (Button) findViewById(R.id.activity_pass_num8);
		btn_9 = (Button) findViewById(R.id.activity_pass_num9);
		btn_0 = (Button) findViewById(R.id.activity_pass_num0);
		btn_delete = (Button) findViewById(R.id.activity_pass_delete);
		pass_1 = (EditText) findViewById(R.id.activity_pass_1);
		pass_2 = (EditText) findViewById(R.id.activity_pass_2);
		pass_3 = (EditText) findViewById(R.id.activity_pass_3);
		pass_4 = (EditText) findViewById(R.id.activity_pass_4);
		btn_1.setOnClickListener(this);
		btn_2.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_4.setOnClickListener(this);
		btn_5.setOnClickListener(this);
		btn_6.setOnClickListener(this);
		btn_7.setOnClickListener(this);
		btn_8.setOnClickListener(this);
		btn_9.setOnClickListener(this);
		btn_0.setOnClickListener(this);
		btn_delete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == btn_delete.getId()){
			deleteNumber();
			return;
		}
		addPassNumber();
		addKey(v.getId());
		validateKey();
		
	}
	
	
	@Override
	public void onBackPressed() {
	    setResult(RESULT_CANCELED);
	    finish();
	}

	private void validateKey() {
		if(Key.length() == 4 && Type == 0){
			if(Integer.parseInt(Key) == ModeloFacade.getUserPassNumber()){
				setResult(RESULT_OK);
				finish();
			}else{
				LinearLayout Layout = (LinearLayout)findViewById(R.id.activity_pass_lay);
				Animation animator = AnimationUtils.loadAnimation(this, R.anim.wrong_paassnumber);
		        Layout.startAnimation(animator);
				deleteKey();
			}
		}else if(Key.length() == 4 && Type == 1){
			if(FirstPass.equals("-1")){
				BPOLocal.PossitiveMessageDialog(getString(R.string.acpass_title_info), getString(R.string.acpass_repeat_PassNumaber),this);
				FirstPass = Key;
				deleteKey();
			}else if(FirstPass.equals(Key)){
				PossitiveMessageDialog(getString(R.string.acpass_title_info), getString(R.string.acpass_confirmed_pass),this);
			}else{
				BPOLocal.PossitiveMessageDialog(getString(R.string.acpass_title_error), getString(R.string.acpass_incorrect_pass),this);
				FirstPass = "-1";
				deleteKey();				
			}
		}
	}

	private void addKey(int viewId) {
		if(viewId == btn_1.getId())
			Key=Key+"1";
		if(viewId == btn_2.getId())
			Key=Key+"2";
		if(viewId == btn_3.getId())
			Key=Key+"3";
		if(viewId == btn_4.getId())
			Key=Key+"4";
		if(viewId == btn_5.getId())
			Key=Key+"5";
		if(viewId == btn_6.getId())
			Key=Key+"6";
		if(viewId == btn_7.getId())
			Key=Key+"7";
		if(viewId == btn_8.getId())
			Key=Key+"8";
		if(viewId == btn_9.getId())
			Key=Key+"9";
		if(viewId == btn_0.getId())
			Key=Key+"0";
	}

	private void addPassNumber() {
		switch (Key.length()) {
			case 0:
				pass_1.setText("1");
				break;
			case 1:
				pass_2.setText("2");
				break;
			case 2:
				pass_3.setText("3");
				break;
			default:
				pass_4.setText("4");
				break;
		}
	}

	private void deleteKey(){
		pass_1.setText("");
		pass_2.setText("");
		pass_3.setText("");
		pass_4.setText("");
		Key="";
	}
	
	private void deleteNumber() {
		int count = Key.length();
		if(count > 0){
			switch (count) {
				case 1:
					pass_1.setText("");
					break;
				case 2:
					pass_2.setText("");
					break;
				default:
					pass_3.setText("");
					break;
			}
			Key = Key.substring(0, Key.length()-1); 
		}
	}
	
	public void PossitiveMessageDialog(String Title,String message, Context context){
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 builder.setTitle(Title);
		 builder.setMessage(message)
		 	.setPositiveButton("Aceptar",new  DialogInterface.OnClickListener() {
       public void onClick(DialogInterface dialog, int id) {
    	   Intent intent = new Intent();
			intent.putExtra("Passnumber",Key);
			setResult(RESULT_OK,intent);
			finish();
       }
   });
		 builder.create();
		 builder.show();
	}
	
}
