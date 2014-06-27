package com.bds.Fragments;

import java.util.ArrayList;

import com.bd.Modelo.Dream;
import com.bd.Modelo.GoalsFirends;
import com.bd.Modelo.Lista_entrada;
import com.bd.Modelo.ModeloFacade;
import com.bds.BPO.BPOServer;
import com.db.Activities.ConfigActivity;
import com.db.Activities.Activity_NewDream;
import com.db.adapters.Lista_adaptador;
import com.devsmind.bancodesuenos.R;
import com.devsmind.bancodesuenos.R.layout;
import com.devsmind.bancodesuenos.R.menu;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/*This class content a fragment that controller the list of user's friends */

public class SecondSectionFragment extends Fragment implements OnClickListener,OnItemClickListener{
	
	private ListView Lista; 
	private TextView Tittle_List_a;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View View = inflater.inflate(R.layout.fragment_friends_dreams, container, false);
		return View;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ModeloFacade.FriendsGoalsInterpretate(BPOServer.FriendsGoals());
		init();
		addListeners();
		CrearLista();
	}
	
	private void init() {
		Tittle_List_a=(TextView) getActivity().findViewById(R.id.Tittle_List_a);
	}
	
	private void addListeners() {
        Tittle_List_a.setOnClickListener(this);
	}
	
	public void CrearLista(){
		TextView tl = (TextView) getActivity().findViewById(R.id.Tittle_List_a);
		tl.setText(ModeloFacade.getUser().getName());
    	ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>(); 
    	ArrayList<GoalsFirends> gf = ModeloFacade.getFriendsGoals();
        for(int i=0; i< gf.size();i++){
        	if((i%2)==0){
        		datos.add(new Lista_entrada(true,gf.get(i).getNameGoal(),gf.get(i).getMailFriend(),0));        		
        	}else{
    			datos.add(new Lista_entrada(false,gf.get(i).getNameGoal(),gf.get(i).getMailDreamer(),2));
    		}
        }
                
         Lista = (ListView)getActivity(). findViewById(R.id.listAmigosGoal);
         Lista.setAdapter(new Lista_adaptador(getActivity(), R.layout.amigos_list, datos){
	 			
	     	@Override
				public void onEntrada(Object entrada, View view) {
			        if (entrada != null) {
			            
			        	TextView texto_superior_entrada = (TextView) view.findViewById(R.id.tv_sueno_a); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());
			            
			            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.tv_amigo_a); 
			            if (texto_inferior_entrada != null) 
			            	texto_inferior_entrada.setText(((Lista_entrada)entrada).get_textoDebajo());
	 		              
	 		              
	 		            final ToggleButton mov =  (ToggleButton) view.findViewById(R.id.tb_motiv_amigo); 
	 		            if (mov != null){
	 		            	mov.setChecked(((Lista_entrada) entrada).isMov());
	 		            	mov.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									if(mov.isChecked()){
										Toast.makeText(getActivity().getApplicationContext(), "+1", Toast.LENGTH_LONG).show();
									}else{
										Toast.makeText(getActivity().getApplicationContext(), "-1", Toast.LENGTH_LONG).show();
									}
									
								}
							});
	 		            }

	 		            LinearLayout plazo=(LinearLayout) view.findViewById(R.id.plazo_a);
	 		            if (plazo!=null){
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

/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.amigos, menu);
		return true;
	}
*/
	@Override
	public void onClick(View view) {
		if (view.getId() == Tittle_List_a.getId()){
			Intent i = new Intent(getActivity(),ConfigActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
