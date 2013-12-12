package com.devsmind.bancodesuenos;

import com.bd.persistencia.PersistManager;
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
    private ImageView perfil;
    private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.samples.hellofacebook:PendingAction";
    //Facebook bd
    private GraphUser usuario;
    private String cumple;
    private String nombre;
    private String apellido;
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

        LoginFacebook.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                StartActivity.this.usuario = user;
                updateUI();
                handlePendingAction();
                Intent i=new Intent(getApplicationContext(),NewGoalActivity.class);
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
		LoginFacebook.setOnClickListener(this);
        
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
			ValidateUser();
			return;
		}
		else if(view.getId() == LoginFacebook.getId()){
			
			return;
		}if (view.getId() == IngresarCorreo.getId()){
			Intent i = new Intent(this,LoginActivity.class);
			startActivity(i);
			return;
		}
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
            profilePictureView.setProfileId(usuario.getId());
            nombre=usuario.getFirstName();
            ImageView fbImage = ( ( ImageView)profilePictureView.getChildAt( 0));
            Bitmap    bitmap  = ( ( BitmapDrawable) fbImage.getDrawable()).getBitmap();
            perfil.setImageBitmap(bitmap);
        } else {
            profilePictureView.setProfileId(null);
            greeting.setText(null);
        }
    }

    private void handlePendingAction() {
        pendingAction = PendingAction.NONE;

        
    }
    
}
