package com.devsmind.bancodesuenos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bd.Modelo.ModeloFacade;
import com.bd.Modelo.User;
import com.bd.persistencia.PersistManager;
import com.bds.BPO.BPOServer;
import com.db.Activities.LoginActivity;
import com.db.Activities.NewGoalActivity;
import com.db.Activities.NewGoalActivity;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView; 

import android.location.Address;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends FragmentActivity implements OnClickListener {
 
	
	private ImageView Logo;
	private Button IngresarCorreo;
	//Facebook
	private LoginButton LoginFacebook;
    private ProfilePictureView profilePictureView;
    private PendingAction pendingAction = PendingAction.NONE;    
    private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.samples.hellofacebook:PendingAction";
    //Facebook bd
    private GraphUser usuario;
    private String Cumple;
    private String Nombre;
    private String Apellido;
    private String Correoface;
    private Bitmap  fotoPerfil;
    

    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() {
    	
    	@Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //facebook
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        
        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }
        setContentView(R.layout.activity_start);
        Init();
        addListeners();
                
        //facebook
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }
        LoginFacebook.setReadPermissions(Arrays.asList("basic_info","email"));
        LoginFacebook.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                StartActivity.this.usuario = user;
                updateUI();
                handlePendingAction();
            }
        });
            
        
    }


    private void Init() {
		Logo = (ImageView) findViewById(R.id.start_logo);
		IngresarCorreo = (Button) findViewById(R.id.start_correo);
		LoginFacebook = (LoginButton) findViewById(R.id.start_facebook);
		
	}


    private void addListeners() {
		Logo.setOnClickListener(this);
		IngresarCorreo.setOnClickListener(this);
        
	}
    
   

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

	@Override
	public void onClick(View view) {
		if (view.getId() == Logo.getId()){
			 ContinueLogout();
			return;
		}
		if (view.getId() == IngresarCorreo.getId()){
			Intent i = new Intent(this,LoginActivity.class);
			startActivity(i);
			return;
		}
	}


	private void ContinueLogout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle("O.O");
		 builder.setMessage(getResources().getString(R.string.WhyLog))
		 	.setPositiveButton("Aceptar",new  DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int id) {
		  	
		  }
     }).setNegativeButton("Continuar",new  DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int id) {
			  ValidateUser();
		  }
     });
		 builder.create();
		 builder.show();
	}

	private void ValidateUser() {
		PersistManager pm = new PersistManager(StartActivity.this);
    	if(pm.getAllRegisters("User").size()==0){
    		//Crear Usuario falso
    		Intent i = new Intent(this, NewGoalActivity.class);
			startActivity(i);
    	}else{
    		//Actualzar Modelo
	    	Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
    	}
		
	}
	
	//Comienzan funciones facebook
	
	@Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (pendingAction != PendingAction.NONE &&
                (exception instanceof FacebookOperationCanceledException ||
                exception instanceof FacebookAuthorizationException)) {
                new AlertDialog.Builder(StartActivity.this)
                    .setTitle(R.string.cancelled)
                    .setMessage(R.string.permission_not_granted)
                    .setPositiveButton(R.string.ok, null)
                    .show();
            pendingAction = PendingAction.NONE;
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
            handlePendingAction();
        }
        updateUI();
    }

    private void updateUI() {
        Session session = Session.getActiveSession();
        boolean enableButtons = (session != null && session.isOpened());
     
        if (enableButtons && usuario != null) {
//            profilePictureView.setProfileId(usuario.getId());
            Nombre=usuario.getFirstName()+" "+usuario.getLastName();
            Correoface = usuario.asMap().get("email").toString();
            Cumple = ""+usuario.getBirthday();
            String id = usuario.getId();
            String response = BPOServer.CreateUserFacebook(Correoface, Nombre,Cumple,id);
            if(!response.equals("already") ){
            	Intent i = new Intent(this,MainActivity.class);
            	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(i);
            	User u = new User(Correoface,Nombre,Cumple,id);
            	ModeloFacade.setUser(u);
            }else if(!response.equals("Created")){
            	Intent i = new Intent(this,NewGoalActivity.class);
            	User u = new User(Correoface,Nombre,Cumple,id);
            	ModeloFacade.setUser(u);
            	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(i);
            }else {
            	ModeloFacade.UserInterpretate(response);
            	Intent i = new Intent(this,MainActivity.class);
            	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(i);
            }
            
//            ImageView fbImage = ( ( ImageView)profilePictureView.getChildAt( 0));
//            fotoPerfil  = ( ( BitmapDrawable) fbImage.getDrawable()).getBitmap();
           // Intent i=new Intent(getApplicationContext(),NewGoalActivity.class);
            //startActivity(i);

           // profilePictureView.setProfileId(usuario.getId());
           
            //ImageView fbImage = ( ( ImageView)profilePictureView.getChildAt(0));
            //fotoPerfil  = ( ( BitmapDrawable) fbImage.getDrawable()).getBitmap();

        }
    }

    private void handlePendingAction() {
        pendingAction = PendingAction.NONE;
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
