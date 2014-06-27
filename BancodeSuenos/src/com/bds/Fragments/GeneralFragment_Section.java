package com.bds.Fragments;

import com.devsmind.bancodesuenos.R;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public abstract class GeneralFragment_Section extends Fragment implements OnClickListener {
	
	protected Button Btn_add;
	protected TextView Title;
	
	protected void init() {
		Btn_add = (Button) getActivity().findViewById(R.id.fragment_general_add);
		Btn_add.setOnClickListener(this);
		Title = (TextView) getActivity().findViewById(R.id.fragment_general_title);
	}
	
	protected void setTitle (String title){
		Title.setText(title);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == Btn_add.getId()){
			addMethod();
		}
	}
	
	public abstract void addMethod();

}
