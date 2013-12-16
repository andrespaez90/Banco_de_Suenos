package com.devsmind.bancodesuenos;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bd.Modelo.Goal;
import com.bd.Modelo.Lista_entrada;
import com.bd.Modelo.ModeloFacade;
import com.bds.BPO.BPOServer;
import com.db.Activities.AmigosActivity;
import com.db.Activities.ConfigActivity;
import com.db.Activities.LoginActivity;
import com.db.Activities.NewGoalActivity;
import com.db.adapters.Lista_adaptador;

public class MainActivity extends Activity implements OnItemClickListener,OnClickListener{
	
	private ListView Lista; 
	private int IdTitulos;
	private ImageButton btn_agregar_sueno_s;
	private ImageButton btn_amigo_s;
	private TextView Tittle_List;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		addListeners();
		CrearLista();
	}
	
	private void init() {
		String Mensaje = BPOServer.GetGoal(ModeloFacade.getUser().getId());
		ModeloFacade.GoalsInterpretate(Mensaje);
		btn_agregar_sueno_s=(ImageButton) findViewById(R.id.btn_agregar_sueno_s);
		btn_amigo_s=(ImageButton) findViewById(R.id.btn_amigo_s);
		Tittle_List=(TextView) findViewById(R.id.Tittle_List);
		
	}
	
	private void addListeners() {
        btn_agregar_sueno_s.setOnClickListener(this);
        btn_amigo_s.setOnClickListener(this);
        Tittle_List.setOnClickListener(this);
	}

	public void CrearLista(){
		TextView tl = (TextView) findViewById(R.id.Tittle_List);
		tl.setText(ModeloFacade.getUser().getName());
    	ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  
        ArrayList<Goal> goal = ModeloFacade.getGoals();
        for(int i=0; i< goal.size();i++){
        	if((i%2)==0){
        		datos.add(new Lista_entrada(android.R.drawable.ic_menu_myplaces,goal.get(i).getNombre(),0,goal.get(i).getId()));        		
        	}else{
    			datos.add(new Lista_entrada(android.R.drawable.ic_menu_mapmode,goal.get(i).getNombre(),2,goal.get(i).getId()));
    			Toast.makeText(this,"espacio"+ goal.get(i).getNombre()+"hola", Toast.LENGTH_LONG).show();
    		}
        }
                
         Lista = (ListView) findViewById(R.id.listGoal);
         Lista.setAdapter(new Lista_adaptador(this, R.layout.img_list, datos){
	 			
	     	@Override
				public void onEntrada(Object entrada, View view) {
			        if (entrada != null) {
			            
			        	TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());
			            
			            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
			            if (texto_inferior_entrada != null) 
			            	texto_inferior_entrada.setVisibility(View.GONE);
	 		              
	 		              
	 		            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
	 		            if (imagen_entrada != null)
	 		            	imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());

	 		            LinearLayout plazo=(LinearLayout) view.findViewById(R.id.plazo);
	 		            if (plazo!=null){
	 		            	plazo.setBackgroundResource(R.drawable.background0);
	 		            	switch ((((Lista_entrada)entrada).getPlazo())) {
							case 0:
								plazo.setBackgroundColor(Color.rgb(239, 109, 109));
								break;
							case 1:
								plazo.setBackgroundColor(Color.rgb(109, 239, 220));
								break;
							case 2:
								plazo.setBackgroundColor(Color.rgb(109, 178, 239));
								break;		
							default:
								plazo.setBackgroundColor(Color.rgb(109, 178, 239));
								break;
							}
	 		            }
	 		        }
	 			}
	
	 		});
	         /*
	         Lista.setOnItemClickListener(new OnItemClickListener() { 
	 			@Override
	 			public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
	 				 Lista_entrada elegido = (Lista_entrada) pariente.getItemAtPosition(posicion); 
	                 CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
	                 Toast toast = Toast.makeText(ImgSimpleList_Activity.this, texto, Toast.LENGTH_LONG);
	                 toast.show();
	                
	                 elegido.setIdImage(R.drawable.ic_check);
	                 Lista.requestLayout();
	 			}
	         });*/
         Lista.setOnItemClickListener(this);
	    }
	    
	    @Override
		public boolean onOptionsItemSelected(MenuItem item){
	    	switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
	
			default:
				break;
			}
	    return false;
	    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
			Lista_entrada elegido = (Lista_entrada) arg0.getItemAtPosition(position); 
			 Intent i=new Intent(getApplicationContext(),PerfilGoalActivity.class);
			 Bundle b = new Bundle();
             b.putString("position", elegido.getId());
             i.putExtras(b);
			 startActivity(i);
		
		
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == btn_agregar_sueno_s.getId()){
			Intent i = new Intent(this,NewGoalActivity.class);
			startActivity(i);
			return;
		}
		if (view.getId() == btn_amigo_s.getId()){
			Intent i = new Intent(this,AmigosActivity.class);
			startActivity(i);
			return;
		}
		if (view.getId() == Tittle_List.getId()){
			Intent i = new Intent(this,ConfigActivity.class);
			startActivity(i);
			return;
		}
		
	}

}
