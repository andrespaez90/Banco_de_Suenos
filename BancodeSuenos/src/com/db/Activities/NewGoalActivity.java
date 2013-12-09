package com.db.Activities;

import java.util.Calendar;

import com.devsmind.bancodesuenos.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewGoalActivity extends Activity implements OnClickListener{

	private int myYear, myMonth, myDay;
	static final int ID_DATEPICKER = 0;
	
	private ImageButton Button_Calendar;
	private TextView Date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_goal);
		Init();
		addListeners();
	}

	private void Init() {
		Button_Calendar = (ImageButton) findViewById(R.id.newgoal_calendar);
		final Calendar calendar = Calendar.getInstance();
		myYear = calendar.get(Calendar.YEAR);
		myMonth = calendar.get(Calendar.MONTH);
		myDay = calendar.get(Calendar.DAY_OF_MONTH);
		Date = (TextView) findViewById(R.id.newgoal_date);
		Date.setText(myDay+"/"+(myMonth+1)+"/"+myYear);
	}

	
	private void addListeners() {
		Button_Calendar.setOnClickListener(this);
		
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
		  showDialog(ID_DATEPICKER);
		
	}

	

}

