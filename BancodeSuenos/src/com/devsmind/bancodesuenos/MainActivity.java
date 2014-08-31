package com.devsmind.bancodesuenos;

import java.util.ArrayList;
import java.util.Vector;

import com.bd.Modelo.Dream;
import com.bd.Modelo.Lista_entrada;
import com.bd.Modelo.ModeloFacade;
import com.bd.Modelo.User;
import com.bd.persistencia.PersistManager;
import com.bds.BPO.BPOLocal;
import com.bds.Fragments.SecondSectionFragment;
import com.bds.Fragments.ThirdSectionFragments;
import com.db.Activities.Activity_DreamProfile;
import com.db.Activities.ConfigActivity;
import com.db.Activities.Activity_NewDream;
import com.db.adapters.Lista_adaptador;

import android.R.array;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener{

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    
    PersistManager PM = new PersistManager(MainActivity.this);
    
    private final int CODE_NEW_USER = 001;
    private final int CODE_PASSNUMBER = 002;

	public void onCreate(Bundle saveIntanceStated){
		super.onCreate(saveIntanceStated);
		setContentView(R.layout.main_activity);
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(),getApplicationContext());
		getUser();   
		final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	initPager(actionBar);
        createTabs(actionBar);
	}
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig); 
		}  
	
	@Override
	protected void onStart() {
	    super.onStart();
	    	
	}
	 
	@Override
	protected void onResume() {
		super.onResume();
	}
	 
	private void getUser() {
		Vector<User> data = PM.getUser();
		if(data.size() == 0){
			Intent i  = new Intent(this,NewUser_Activity.class);
			startActivityForResult(i,this.CODE_NEW_USER);
		}
		else{
			User u = data.get(0);
			ModeloFacade.setUser(u);
			if(u.isPassnumber() == 1){
				Intent i = new Intent(this, Passnumber_Activity.class);
				startActivityForResult(i,CODE_PASSNUMBER);
			}else{
				ArrayList<Dream> dreams = PM.getDreams();
				ModeloFacade.setDreams(dreams);
			}
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CODE_PASSNUMBER){
			if(resultCode == RESULT_CANCELED){
				finish();
			}
			else if(resultCode == RESULT_OK){
				if(ModeloFacade.getDreams().size()==0){
					ArrayList<Dream> dreams = PM.getDreams();
					ModeloFacade.setDreams(dreams);
				}
			}
		}
		if(requestCode == CODE_NEW_USER && resultCode == RESULT_CANCELED){
			finish();
		}
		if(resultCode == RESULT_OK){
			ArrayList<Dream> dreams = PM.getDreams();
			ModeloFacade.setDreams(dreams);
		}
	}
	
	private void initPager(final ActionBar actionBar) {
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
               actionBar.setSelectedNavigationItem(position);
            }
        });
	}
	
	private void createTabs(ActionBar actionBar) {
		  for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
	            actionBar.addTab(
	                    actionBar.newTab()
	                            .setIcon(mAppSectionsPagerAdapter.getPageIcon(i))
	                            .setTabListener(this));
	        }
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
		 mViewPager.setCurrentItem(tab.getPosition());		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub
		
	}

	
	//class AppSection
	  public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		  private Context context; 
		  
	        public AppSectionsPagerAdapter(FragmentManager fm, Context context) {
	            super(fm);
	            this.context = context;
	        }

	        @Override
	        public Fragment getItem(int i) {
	            switch (i) {
	                case 0:
	                	return new LaunchpadSectionFragment();
	                case 1:
	                	return new ThirdSectionFragments();
	                default:
	                	return new SecondSectionFragment();
	            }
	        }

	        @Override
	        public int getCount() {
	        	//Number of section that I want
	            return 3;
	        }
	        
	        
	        @Override
	        public CharSequence getPageTitle(int position) {
	            //return the title of the section tab
	        	switch (position) {
					case 0:
						return context.getText(R.string.title_activity_main);
					case 1:
						return context.getText(R.string.title_activity_new_goal);
					default:
						return context.getText(R.string.title_activity_amigos);
					}
	        }
	        
	        public int getPageIcon(int position) {
	        	switch (position) {
				case 0:
					return (R.drawable.pie_suenos_trans);
				case 1:
					return (R.drawable.pie_wallet);
				default:
					return (R.drawable.pie_amigos_trans);
				}
			}

	    }
	
	
	/*Class that cotroller the main view (fragment_main) 
	  the main view content the list of the user's dreams
	 */
	public static class LaunchpadSectionFragment extends Fragment implements OnItemClickListener,OnClickListener{

	
		private ListView Lista; 
		private TextView Tittle_List;
		private ImageButton Btn_newDream;
		private ImageView Image_Profile;
		
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        	View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        	return rootView;
        }
        
      
        @Override
    	public void onActivityCreated(Bundle savedInstanceState) {
    		super.onActivityCreated(savedInstanceState);
    		init();
    		addListeners();
    	  	CrearLista();
        }

        @Override
        public void onStart() {
            super.onStart();
            CrearLista();
        }
       
                
        private void init() {
    		Tittle_List=(TextView) getActivity().findViewById(R.id.Tittle_List);
    		Btn_newDream = (ImageButton) getActivity().findViewById(R.id.fragment_main_new_dream);
    		Image_Profile = (ImageView) getActivity().findViewById(R.id.fragment_main_profile);
        }
    	
    	private void addListeners() {
            Tittle_List.setOnClickListener(this);
            Btn_newDream.setOnClickListener(this);
    	}

    	protected void CrearLista(){
    		TextView tl = (TextView) getActivity().findViewById(R.id.Tittle_List);
    		tl.setText(ModeloFacade.getUser().getName());
        	ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  
            ArrayList<Dream> dream = ModeloFacade.getDreams();
            for(int i=0; i< dream.size();i++){
            	if((i%2)==0){
            		datos.add(new Lista_entrada(i+1,android.R.drawable.ic_menu_myplaces,dream.get(i).getDream(),0,dream.get(i).getDate()));        		
            	}else{
        			datos.add(new Lista_entrada(i+1,android.R.drawable.ic_menu_mapmode,dream.get(i).getDream() ,2,dream.get(i).getDate()));
        		}
            }
                    
             Lista = (ListView) getActivity().findViewById(R.id.listGoal);
             Lista.setAdapter(new Lista_adaptador(getActivity(), R.layout.list_imagen, datos){
    	 			
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
             Lista.setOnItemClickListener(this);
    	    }
    	    
    	    @Override
    		public boolean onOptionsItemSelected(MenuItem item){
    	    	switch (item.getItemId()) {
    			case android.R.id.home:
    				getActivity().finish();
    				return true;
    	
    			default:
    				break;
    			}
    	    return false;
    	    }
    	
   
    	@Override
    	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
    			Lista_entrada elegido = (Lista_entrada) arg0.getItemAtPosition(position); 
    			 Intent i=new Intent(getActivity().getApplicationContext(),Activity_DreamProfile.class);
    			 Bundle b = new Bundle();
                 b.putInt("position", elegido.getId());
                 i.putExtras(b);
    			 startActivity(i);    		
    	}

    
    	
    	@Override
    	public void onClick(View view) {
    		if (view.getId() == Tittle_List.getId()){
    			Intent i = new Intent(getActivity(),ConfigActivity.class);
    			startActivity(i);
    			return;
    		}
    		if(view.getId() == Btn_newDream.getId()){
    			Intent i = new Intent(getActivity(),Activity_NewDream.class);
    			startActivity(i);
    			
    			return;
    		}
    		
    	}

    }
	
	
	
	
}
