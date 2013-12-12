package com.db.Dialogs;

import java.util.zip.Inflater;

import com.devsmind.bancodesuenos.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class PhotoDialog extends DialogFragment {
	
	private Bitmap Imagen; 
	private DialogListener listener;

	 public interface DialogListener {
	        public void onDialogPositiveClick(DialogFragment dialog);
	 
	        public void onDialogNegativeClick(DialogFragment dialog);
	    }
	
	 
	 public PhotoDialog(Bitmap imagen) {
		super();
		Imagen = imagen;
	}

	@Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        // Verify that the host activity implements the callback interface
	        try {
	            // Instantiate the NoticeDialogListener so we can send events to the
	            // host
	            listener = (DialogListener) activity;
	        } catch (ClassCastException e) {
	            // The activity doesn't implement the interface, throw exception
	            throw new ClassCastException(activity.toString()
	                    + " must implement DialogListener");
	        }
	    }
	 
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = (inflater.inflate(R.layout.photo_dialog, null));
	    ImageView img =(ImageView) view.findViewById(R.id.dialog_img);
	    img.setImageBitmap(Imagen);
	    builder.setView(view)
	        .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                listener.onDialogPositiveClick(PhotoDialog.this);
                            }
                        })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(PhotoDialog.this);
                    }
                });
	    
	    return builder.create();
	    }
	
	
}
