package com.db.Activities;

import java.util.Calendar;

import com.bd.Modelo.Goal;
import com.bd.Modelo.ModeloFacade;
import com.bds.BPO.BPOLocal;
import com.bds.BPO.BPOServer;
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
import android.graphics.YuvImage;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewGoalActivity extends Activity implements OnClickListener{

	private int myYear, myMonth, myDay;
	static final int ID_DATEPICKER = 0;
	
	private Button Button_Next;
	private Button Calculate;
	private ImageButton Button_Calendar;
	private TextView Date;
	
	private String DGoal;			//Descripción del sueño
	private String Value;
	private String Saving;
	private String T_Saving;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_goal);
		Init();
		addListeners();
	}

	private void Init() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.type_goal));
		((AutoCompleteTextView)findViewById(R.id.newgoal_dgoal)).setAdapter(adapter);
		Button_Calendar = (ImageButton) findViewById(R.id.newgoal_calendar);
		Calculate = (Button) findViewById(R.id.newgoal_calculate);
		Button_Next = (Button) findViewById(R.id.newgoal_bsiguiente);
		Date = (TextView) findViewById(R.id.newgoal_date);
		Date.setText(TodayDate());
	}

	private String TodayDate(){
		final Calendar calendar = Calendar.getInstance();
		myYear = calendar.get(Calendar.YEAR);
		myMonth = calendar.get(Calendar.MONTH);
		myDay = calendar.get(Calendar.DAY_OF_MONTH);
		return(myDay+"/"+(myMonth+1)+"/"+myYear);
		
	}
	
	private void addListeners() {
		Button_Calendar.setOnClickListener(this);
		Button_Next.setOnClickListener(this);
		Calculate.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_goal, menu);
		return true;
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
		if(v.getId() == Button_Calendar.getId()){
		  showDialog(ID_DATEPICKER);
		  return;
		}
		if(v.getId() == Calculate.getId()){
			try {
				ValueCalculator();
				
			} catch (Exception e) {
				CuadroDialogo("Datos incorrectos:'(", "Ingresa Datos válidos");
			}
		}
		if(v.getId() == Button_Next.getId()){
			if(BPOLocal.isNetwork(this)){
				if(ValidateDate() && ValidateInformation()){
					Intent i = new Intent(this, InfoGoalActivity.class);
					i.putExtra("Goal", DGoal);
					startActivityForResult(i,Button_Next.getId());
				}else{
					CuadroDialogo("Datos incorrectos:'(", "Los datos que ingresaste no corresponden, si deseas que te ayudemos selecciona calcular");
				}
			return;
			}else{
				 AlertDialog.Builder builder = new AlertDialog.Builder(this);
				 builder.setTitle("Ups!");
				 builder.setMessage("Internet no disponible, deseas activarlo")
				    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					})
				 	.setPositiveButton("Aceptar",new  DialogInterface.OnClickListener() {
				 		public void onClick(DialogInterface dialog, int id) {
					    	Intent settings = new Intent(Settings.ACTION_WIFI_SETTINGS);
							startActivityForResult(settings, 001);
				 		}
				 	});
				 builder.create();
				 builder.show();
			}
		}
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String[] info = new String[10];
		if(requestCode == Button_Next.getId() && resultCode == Activity.RESULT_OK){
			info[0] = DGoal;
			info[1] = TodayDate();
			info[2] = Date.getText().toString();
			info[3] = Value;
			info[4] = Saving;
			info[5] = T_Saving;
			info[6] = data.getStringExtra("Name");
			info[7] = data.getStringExtra("Why");
			info[8] = ModeloFacade.getUser().getMail();
			int id = data.getIntExtra("ImgId", 0);
			if(BPOServer.CreateGoal(info,id)){
				finish();
				Intent i =  new Intent(this,MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(i);
			}
		}
	}
	
	
	private void ValueCalculator() throws Exception{
		DataCatcher();
		if(Value.equals("") && Saving.equals("") && istoday()){
			CuadroDialogo("Ayudanos!", "Agrega al menos dos datos");
			return;
		}else if(!Value.equals("") && !Saving.equals("") && !istoday()){
			if(!ValidateInformation()){
				TodayDate();
				ValueCalculator();
				return;
			}
		}else if(!Value.equals("") && !Saving.equals("") && istoday() ){
			int Number = Integer.parseInt(Value) / Integer.parseInt(Saving);
			if(T_Saving.equals(getResources().getStringArray(R.array.type_save)[0])){
				 Date.setText(NewDate(0, Number));
				 CuadroDialogo("Cambio!", "Hemos cambiado la fecha de tu sueño");
			}else if(T_Saving.equals(getResources().getStringArray(R.array.type_save)[2])){
				Calendar c1 = Calendar.getInstance(); 
				c1.add(Calendar.DATE,Number);
				ChangeDate(c1);
				Date.setText(myDay+"/"+myMonth+"/"+myYear);
				CuadroDialogo("Cambio!", "Hemos cambiado la fecha de tu sueño");
			}else if(T_Saving.equals(getResources().getStringArray(R.array.type_save)[1])){
				Calendar c1 = Calendar.getInstance(); 
				c1.add(Calendar.WEEK_OF_YEAR,Number);
				ChangeDate(c1);Date.setText(myDay+"/"+myMonth+"/"+myYear);
				CuadroDialogo("Cambio!", "Hemos cambiado la fecha de tu sueño");
			}
		}
		
	}

	private void ChangeDate(Calendar c1) {
		myYear = c1.get(Calendar.YEAR);
		myMonth =c1.get(Calendar.MONTH)+1;
		myDay = c1.get(Calendar.DAY_OF_MONTH);
	}

	private boolean ValidateInformation() {
		//Ahorro Mensual
		int Number = Integer.parseInt(Value) / Integer.parseInt(Saving);
		if(T_Saving.equals(getResources().getStringArray(R.array.type_save)[0])){
			String date = NewDate(0, Number);
			if(date.equals(Date.getText().toString()) || isLowerDate(date)){
				return true;
			}	
		}else if(T_Saving.equals(getResources().getStringArray(R.array.type_save)[2])){
			Calendar c1 = Calendar.getInstance(); 
			c1.add(Calendar.DATE,Number);
			String auxdate = (c1.get(Calendar.DAY_OF_MONTH)+"/"+(c1.get(Calendar.MONTH)+1)+"/"+c1.get(Calendar.YEAR));
			if(auxdate.equals(Date.getText().toString())){
				return true;
			}
		}else if(T_Saving.equals(getResources().getStringArray(R.array.type_save)[1])){
			Calendar c1 = Calendar.getInstance(); 
			c1.add(Calendar.WEEK_OF_YEAR,Number);
			String auxdate = (c1.get(Calendar.DAY_OF_MONTH)+"/"+(c1.get(Calendar.MONTH)+1)+"/"+c1.get(Calendar.YEAR));
			if(auxdate.equals(Date.getText().toString())){
				return true;
			}
		}
		return false;
	}

	private boolean isLowerDate(String date2) {
		// TODO Auto-generated method stub
		return false;
	}

	public String NewDate(int type, int value){
		TodayDate();
		switch (type) {
		case 0:
			myMonth = myMonth + value;
			while(myMonth > 12){
					myMonth-=12;
					myYear++;
			}
			return (myDay+"/"+myMonth+"/"+myYear); 

		default:
			break;
		}
		return null;
	}
	
	private boolean istoday() {
		final Calendar calendar = Calendar.getInstance();
		if(myYear == calendar.get(Calendar.YEAR)
				&& myMonth == calendar.get(Calendar.MONTH)
				 && myDay == calendar.get(Calendar.DAY_OF_MONTH))
			return true;
		return false;
	}

	private boolean ValidateDate() {
		DataCatcher();
		if(!DateValidator()){
			CuadroDialogo("Error!", "Por favor revisa la fecha ingresada");
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

	private void DataCatcher() {
		DGoal = ((TextView)findViewById(R.id.newgoal_dgoal)).getText().toString();
		Value = ((TextView)findViewById(R.id.newgoal_value)).getText().toString();
		Saving = ((TextView)findViewById(R.id.newgoal_saving)).getText().toString();
		T_Saving = ((Spinner)findViewById(R.id.newgoal_t_saving)).getSelectedItem().toString();
	}

	private void CuadroDialogo(String Tittle,String mensaje){
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle(Tittle);
		 builder.setMessage(mensaje)
		 	.setPositiveButton("Aceptar",new  DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
           	
           }
       });
		 builder.create();
		 builder.show();
	}
	
	

}

