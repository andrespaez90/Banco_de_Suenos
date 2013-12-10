package com.db.Activities;

import com.devsmind.bancodesuenos.R;
import com.devsmind.bancodesuenos.R.layout;
import com.devsmind.bancodesuenos.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class CustomGoalActivity extends Activity {

	private String Name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_goal);
		Init();
	}

	private void Init() {
		Intent i = getIntent();
		Name = i.getStringExtra("Type") +" "+ i.getStringExtra("Goal");
		((TextView)findViewById(R.id.CustomG_Name)).setText(Name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.custom_goal, menu);
		return true;
	}

}
