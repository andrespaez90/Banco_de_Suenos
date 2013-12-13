package com.db.Activities;

import com.devsmind.bancodesuenos.MainActivity;
import com.devsmind.bancodesuenos.R;
import com.devsmind.bancodesuenos.R.layout;
import com.devsmind.bancodesuenos.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoGoalActivity extends Activity implements OnClickListener{

	private String Name;
	private ImageView Imagen;
	private Button Next;
	private ImageButton GridPhotos;
	private Button Skip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_goal);
		Init();
		addListeners();
	}

	private void addListeners() {
		GridPhotos.setOnClickListener(this);
		Next.setOnClickListener(this);
		
	}

	private void Init() {
		Intent i = getIntent();
		Name = i.getStringExtra("Goal");
		((TextView)findViewById(R.id.info_namegoal)).setText(Name);
		Imagen = (ImageView) findViewById(R.id.info_img);
		SelectPhoto(Name);
		Next = (Button) findViewById(R.id.info_bnext);
		Skip = (Button) findViewById(R.id.info_bskip);
		GridPhotos = (ImageButton) findViewById(R.id.info_gridphoto);
	}

	public void SelectPhoto(String goal){
		String[] goals = getResources().getStringArray(R.array.type_goal);
		for(int i = 0; i <goals.length; i++){
			if(goals[i].indexOf(goal)!=-1){
				if(!SpecificPhoto(goal))
					PhotoLoader(i);
				return;
			}
		}
		Imagen.setImageResource(R.drawable.paisaje);
	}
	
	private boolean SpecificPhoto(String goal) {
		if(goal.contains("bailar")){
			Imagen.setImageResource(R.drawable.bailar);
			return true;
		}if(goal.contains("playa")){
			Imagen.setImageResource(R.drawable.playa2);
			return true;
		}
		return false;
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info_goal, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == GridPhotos.getId()){
			Intent i = new Intent(this,PhotosActivity.class);
			startActivityForResult(i, v.getId());
			return;
		}else if(v.getId() == Next.getId()){
			Intent i = new Intent(this,MainActivity.class);
			startActivity(i);
		}
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(GridPhotos.getId() == requestCode && resultCode == RESULT_OK ){
			 TypedArray imgs = getResources().obtainTypedArray(R.array.photo_ids);
			 Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
					 imgs.getResourceId(data.getIntExtra("img", 0), -1));
			 Imagen.setImageBitmap(bitmap);
		}
	}

}
