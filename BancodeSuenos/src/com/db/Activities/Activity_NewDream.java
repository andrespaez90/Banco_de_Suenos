package com.db.Activities;

import java.util.Calendar;

import com.bd.Modelo.Dream;
import com.bd.Modelo.ModeloFacade;
import com.bd.persistencia.PersistManager;
import com.bds.BPO.BPOLocal;
import com.devsmind.bancodesuenos.MainActivity;
import com.devsmind.bancodesuenos.R;
import com.devsmind.bancodesuenos.StartActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.YuvImage;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class Activity_NewDream extends Activity implements OnClickListener{

	private int myYear, myMonth, myDay;
	static final int ID_DATEPICKER = 0;
	
	private int Type;
	private int idDream;
	
	
	private ImageButton Btn_camera;
	private ImageButton Btn_search_img;
	private Button Btn_Next;
	private Button Btn_cancel;
	private ImageButton Btn_Calendar;
	
	private TextView Date;
	private AutoCompleteTextView DreamName;
	private ImageView Imagen;
	
	private String Name_dream;			
	private int id_Image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_dream);
		Init();
		setType();
		addListeners();
	}

	/*In this method i'll know with activity invoke  
	 * if were newDream or EdiDream, if was edit dream , 
	 * we need to read new data from Intent 
	 * */
	private void setType() {
		Intent i = getIntent();
		Type = i.getIntExtra("Type", 0);
		if(Type != 0){
			idDream = i.getIntExtra("id", 0);
			DreamName.setText(i.getStringExtra("Name"));
			Date.setText(i.getStringExtra("Date"));
			DateofDream(Date.getText().toString());
			int image = i.getIntExtra("Image", 0); 
			TypedArray imgs = getResources().obtainTypedArray(R.array.photo_ids);
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
					 imgs.getResourceId(image, -1));
			Imagen.setImageBitmap(bitmap);
		}
		
	}

	/*this method init all variables and put the id of the image at 0
	taht is a image for defult*/
	
	private void Init() {
		DreamName = (AutoCompleteTextView)findViewById(R.id.newdream_name);
		Btn_camera = (ImageButton) findViewById(R.id.newdream_camera);
		Btn_search_img = (ImageButton) findViewById(R.id.newdream_search);
		Btn_Calendar = (ImageButton) findViewById(R.id.newdream_calendar);
		Btn_cancel = (Button) findViewById(R.id.newdream_bcancel);
		Btn_Next = (Button) findViewById(R.id.newdream_bsiguiente);
		Imagen = (ImageView) findViewById(R.id.newdream_img);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.type_goal));
		DreamName.setAdapter(adapter);
		Date = (TextView) findViewById(R.id.newdream_date);
		Date.setText(TodayDate());
		id_Image = 0;
		
		DreamName.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(DreamName.getWindowToken(), 0);
		            return true;
		        }
				return false;
			}			
		});
		
	}

	private String TodayDate(){
		final Calendar calendar = Calendar.getInstance();
		myYear = calendar.get(Calendar.YEAR);
		myMonth = calendar.get(Calendar.MONTH);
		myDay = calendar.get(Calendar.DAY_OF_MONTH);
		return(myDay+"/"+(myMonth+1)+"/"+myYear);
		
	}
	
	private void DateofDream(String date){
		String [] alldate = date.split("/");
		myDay = Integer.parseInt(alldate[0]);
		myMonth = Integer.parseInt(alldate[1]);
		myYear = Integer.parseInt(alldate[2]);
	}
	
	private void addListeners() {
		Btn_Calendar.setOnClickListener(this);
		Btn_Next.setOnClickListener(this);
		Btn_cancel.setOnClickListener(this);
		Btn_camera.setOnClickListener(this);
		Btn_search_img.setOnClickListener(this);
	}
	
	
	 @Override
	 protected Dialog onCreateDialog(int id) {
		 switch(id){
		 	case ID_DATEPICKER:
		 		return new DatePickerDialog(this,myDateSetListener,myYear, myMonth, myDay);
		 	default:
		 		return null;
		 }
	 }
	    
	 private DatePickerDialog.OnDateSetListener myDateSetListener
	  = new DatePickerDialog.OnDateSetListener(){
		 
		 @Override
		 public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			 myDay = dayOfMonth;
			 myMonth = monthOfYear+1;
			 myYear = year;
			 Date.setText(myDay+"/"+myMonth+"/"+myYear); 
		 }
	 };

	@Override
	public void onClick(View v) {
		if(v.getId() == Btn_Calendar.getId()){
		  showDialog(ID_DATEPICKER);
		  return;
		}
		if(v.getId() == Btn_cancel.getId()){
			finish();
			return;
		}
		if(v.getId() == Btn_Next.getId()){
			SaveData();
			return;
		}
		if(v.getId() == Btn_search_img.getId()){
			Intent i = new Intent(this,PhotosActivity.class);
			startActivityForResult(i, v.getId());
			return;
		}
	}

	private void SaveData() {
		Name_dream = DreamName.getText().toString();
		if(ValidateDate() && Name_dream.length()>5){
			PersistManager pm = new PersistManager(this);
			String date = myDay+"/"+myMonth+"/"+myYear;
			if(Type == 0){
				Dream d = new Dream(ModeloFacade.getDreams().size()+1, Name_dream, 0, "",id_Image, date,0);
				ModeloFacade.getDreams().add(d);
				pm.SaveDream(d);
				MessageDialog(getString(R.string.acpass_title_info), "Sueño creado, Buena Suerte");
			}else{
				pm.EditDream(idDream, Name_dream, 0, "", id_Image, date);
				setResult(RESULT_OK);
				MessageDialog(getString(R.string.acpass_title_info), "Sueño editado, Buena Suerte");
			}
		}
	}

	private boolean ValidateDate() {
		if(!DateValidator()){
			BPOLocal.PossitiveMessageDialog("Error!", "Por favor revisa la fecha ingresada",this);
			return false;
		}
		return true;
	}

	private boolean DateValidator() {
		final Calendar calendar = Calendar.getInstance();
		if(myYear > calendar.get(Calendar.YEAR))
			return true;
		if(myYear == calendar.get(Calendar.YEAR)){
			if(myMonth < calendar.get(Calendar.MONTH))
				return false;
			if(myMonth > calendar.get(Calendar.MONTH))
				return true;
			if(myDay > calendar.get(Calendar.DAY_OF_MONTH))
				return true;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(Btn_search_img.getId() == requestCode && resultCode == RESULT_OK ){
			id_Image = data.getIntExtra("img", 0); 
			TypedArray imgs = getResources().obtainTypedArray(R.array.photo_ids);
			 Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
					 imgs.getResourceId(id_Image, -1));
			 Imagen.setImageBitmap(bitmap);
		}
	}

	public void PhotoLoader(int type_goal){
		if(type_goal<9){
			Imagen.setImageResource(R.drawable.estudiar);
			return;
		}else if(type_goal < 20){
			Imagen.setImageResource(R.drawable.viajar);
			return;
		}
		
	}
	
	private void MessageDialog(String Title,String menssage){
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle(Title);
		 builder.setMessage(menssage)
		 	.setPositiveButton("Aceptar",new  DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   finish();
           }
       });
		 builder.create();
		 builder.show();
	}
	
	

}

