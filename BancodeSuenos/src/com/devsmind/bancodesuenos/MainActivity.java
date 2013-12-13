package com.devsmind.bancodesuenos;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bd.Modelo.Lista_entrada;
import com.db.adapters.Lista_adaptador;

public class MainActivity extends Activity implements OnItemClickListener{
	private ListView Lista; 
	private int IdTitulos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CrearLista();
	}
	
	public void CrearLista(){
		TextView tl = (TextView) findViewById(R.id.Tittle_List);
    	ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  
        String [] titulos = {"Aprender a bailar","Aprender a tocar guitarra",
        		"Estudiar Inglés","Estudiar una Maestría","Viajar a Cacún",
        		"Paseo por el caribe","Conocer la nieve","Comprar Celular",
        		"Matrimonio"};
        for(int i=0; i< titulos.length;i++){
//        	if((i%2)==0)
//        		datos.add(new Lista_entrada(android.R.drawable.ic_menu_myplaces,titulos[i]));
//        		else{
        			datos.add(new Lista_entrada(android.R.drawable.ic_menu_mapmode,titulos[i]+i));
//        		}
        }
                
         Lista = (ListView) findViewById(R.id.listGoal);
         Lista.setAdapter(new Lista_adaptador(this, R.layout.img_list, datos){
	 			
	     	@Override
				public void onEntrada(Object entrada, View view) {
			        if (entrada != null) {
			            TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima()); 
	 		              
	 		              
	 		            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
	 		            if (imagen_entrada != null)
	 		            	imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
