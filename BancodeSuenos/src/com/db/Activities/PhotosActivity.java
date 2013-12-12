package com.db.Activities;

import java.util.ArrayList;

import com.db.Dialogs.PhotoDialog;
import com.db.adapters.GridViewAdapter;
import com.db.adapters.ImageItem;
import com.devsmind.bancodesuenos.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class PhotosActivity extends FragmentActivity implements PhotoDialog.DialogListener {

	private GridView gridView;
    private GridViewAdapter customGridAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos); 
        gridView = (GridView) findViewById(R.id.gridView);
        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
        gridView.setAdapter(customGridAdapter);
        addListeners();
    }
 
    private void addListeners() {
    	 gridView.setOnItemClickListener(new OnItemClickListener() {
    		 public void onItemClick(AdapterView<?> parent, View v,
                                 int position, long id) {
    			 FragmentManager fragmentManager = getSupportFragmentManager();
    			 TypedArray imgs = getResources().obtainTypedArray(R.array.photo_ids);
    			 Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
    	                    imgs.getResourceId(position, -1));
    			 PhotoDialog dialogo = new PhotoDialog(bitmap);
                 dialogo.show(fragmentManager, "tagPersonalizado");
                 }

         });	
	}

    
    @Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
    
    
	private ArrayList getData() {
        final ArrayList imageItems = new ArrayList();
        // retrieve String drawable array
        TypedArray imgs = getResources().obtainTypedArray(R.array.photo_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
 
        return imageItems;
 
    }
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photos, menu);
		return true;
	}

}
