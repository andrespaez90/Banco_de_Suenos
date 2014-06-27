package com.bds.Fragments;

import com.bd.Modelo.ModeloFacade;
import com.bds.BPO.BPOServer;
import com.devsmind.bancodesuenos.Accounts_Activity;
import com.devsmind.bancodesuenos.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ThirdSectionFragments extends Fragment implements OnClickListener {

	private Button Btn_Saving;
	private Button Btn_Expense;
	private Button Btn_Liabilities;
	private Button Btn_Loan;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View View = inflater.inflate(R.layout.fragment_accounts, container, false);
		return View;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ModeloFacade.FriendsGoalsInterpretate(BPOServer.FriendsGoals());
		init();
	}
	
	
	private void init() {
		Btn_Saving = (Button) getActivity().findViewById(R.id.fragment_accounts_saving);
		Btn_Expense = (Button) getActivity().findViewById(R.id.fragment_accounts_expense);
		Btn_Loan = (Button) getActivity().findViewById(R.id.fragment_accounts_loan);
		Btn_Liabilities = (Button) getActivity().findViewById(R.id.fragment_accounts_liabilities);
		
		//Listeners
		Btn_Saving.setOnClickListener(this);
		Btn_Liabilities.setOnClickListener(this);
		Btn_Expense.setOnClickListener(this);
		Btn_Loan.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		Intent i = new Intent(getActivity(),Accounts_Activity.class);
		int idView = view.getId();
		if(idView == Btn_Saving.getId()){
			i.putExtra("fragment", 0);
		}
		if(idView == Btn_Expense.getId()){
			i.putExtra("fragment", 1);
		}
		if(idView == Btn_Liabilities.getId()){
			i.putExtra("fragment", 2);
		}
		if(idView == Btn_Loan.getId()){
			i.putExtra("fragment", 3);
		}
		startActivity(i);
	}

	
}
