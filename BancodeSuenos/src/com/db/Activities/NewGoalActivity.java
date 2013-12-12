package com.db.Activities;

import java.util.Calendar;

import com.bd.Modelo.Goal;
import com.devsmind.bancodesuenos.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
		final Calendar calendar = Calendar.getInstance();
		myYear = calendar.get(Calendar.YEAR);
		myMonth = calendar.get(Calendar.MONTH);
		myDay = calendar.get(Calendar.DAY_OF_MONTH);
		Date = (TextView) findViewById(R.id.newgoal_date);
		Date.setText(myDay+"/"+(myMonth+1)+"/"+myYear);
		Button_Next = (Button) findViewById(R.id.newgoal_bsiguiente);
	}

	
	private void addListeners() {
		Button_Calendar.setOnClickListener(this);
		Button_Next.setOnClickListener(this);
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
			ValueCalculator();
		}
		if(v.getId() == Button_Next.getId()){
			if(ValidateDate()){
				Intent i = new Intent(this, InfoGoalActivity.class);
				i.putExtra("Goal", DGoal);
				startActivity(i);
			}
			return;
		}
		
	}

	private void ValueCalculator() {
		DataCatcher();
		if(Value.equals("")){
			
		}
		
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
			if(myMonth > calendar.get(Calendar.MONTH)+1)
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
		T_Saving = ((Spinner)findViewById(R.id.newgoal_saving)).getSelectedItem().toString();
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

