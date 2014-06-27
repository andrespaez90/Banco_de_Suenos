package com.bds.Fragments;

import com.devsmind.bancodesuenos.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LiabilitySectionFragment extends GeneralFragment_Section {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View View = inflater.inflate(R.layout.fragment_general_account, container, false);
		return View;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		setTitle(getResources().getString(R.string.fragment_liabilities_title));
	}

	@Override
	public void addMethod() {
		// TODO Auto-generated method stub
		
	}
}
