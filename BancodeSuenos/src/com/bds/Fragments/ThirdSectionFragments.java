package com.bds.Fragments;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import com.bd.Modelo.Dream;
import com.bd.Modelo.Lista_entrada;
import com.bd.Modelo.ModeloFacade;
import com.bd.Modelo.Saving;
import com.bd.persistencia.PersistManager;
import com.bds.BPO.BPOLocal;
import com.db.Activities.Activity_MoneytoDream;
import com.db.adapters.Lista_adaptador;
import com.db.adapters.NumberTextWatcher;
import com.devsmind.bancodesuenos.Accounts_Activity;
import com.devsmind.bancodesuenos.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ThirdSectionFragments extends Fragment implements OnItemClickListener, OnClickListener {

	private ListView List;
	private ArrayList<Saving> Savings;
	
	
	private int Type;
	private String s_Description;
	private String s_Value;
	
	private ImageButton btn_MoneytoDream;
	private RadioButton add;
	private EditText Descricption;
	private EditText Value;
	private TextView Total;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View View = inflater.inflate(R.layout.fragment_accounts, container, false);
		return View;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		CrearLista();
		btn_MoneytoDream.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CrearLista();
	}
	
	
	private void init() {
		add = (RadioButton) getActivity().findViewById(R.id.account_radio0);
		btn_MoneytoDream = (ImageButton) getActivity().findViewById(R.id.accout_moneytodream);
		Descricption = (EditText) getActivity().findViewById(R.id.accout_description);
		Total = (TextView) getActivity().findViewById(R.id.accout_total);
		Value = (EditText) getActivity().findViewById(R.id.accout_value);
		Value.addTextChangedListener(new NumberTextWatcher(Value));
		Value.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
		            if(confirm_Data()){
		            	PersistManager pm = new PersistManager(getActivity());
		            	String aux = s_Value.replace(".", "");
		            	if(!aux.equals("")){		
			            	int valueaux = Integer.parseInt(aux);
			            	pm.saveSving(Savings.size()+1,Type,s_Description,getDate(),valueaux);
			            	CrearLista();
		            	}
		            }
		            return true;
		        }
				return false;
			}			
		});
	}

	
	private String getDate() {
		final Calendar calendar = Calendar.getInstance();
		int myYear = calendar.get(Calendar.YEAR);
		int myMonth = calendar.get(Calendar.MONTH);
		int myDay = calendar.get(Calendar.DAY_OF_MONTH);
		return(myDay+"/"+(myMonth+1)+"/"+myYear);
	}
	
	
	private boolean confirm_Data() {
		if(!add.isChecked()){
			Type = 0;
		}else Type=1;
		s_Description = Descricption.getText().toString();
		s_Value = Value.getText().toString();
		if(s_Description.length() > 2 && s_Value.length() > 2)
			return true;
		return false;
	}
	
	protected void CrearLista(){
		PersistManager pm = new PersistManager(getActivity());
		List = (ListView) getActivity().findViewById(R.id.accout_list);
		Savings = pm.getSave_account();
		getValue(Savings);
		List.setAdapter(new Lista_adaptador(getActivity(), R.layout.list_money, Savings){

			@Override
			public void onEntrada(Object data, View view) {
				if(data != null){
					TextView description = (TextView) view.findViewById(R.id.listmoney_description); 
		            if (description != null) 
		            	description.setText(((Saving) data).getDescrition());
		            
		            TextView date = (TextView) view.findViewById(R.id.listmoney_date); 
		            if (date != null) 
		            	date.setText(((Saving) data).getDate());
		            
		            TextView value = (TextView) view.findViewById(R.id.listmoney_value); 
		            if (value != null) {
		            	if(((Saving) data).getType() == 0){
		            		value.setTextColor(Color.RED);
		            		value.setText("("+formatAmount(((Saving) data).getValue())+")");
		            	}else{
		            		value.setTextColor(Color.BLACK);
		            		value.setText(formatAmount(((Saving) data).getValue()));
		            	}
		            	
		            }
				}
				
			}
			
		});
		List.setOnItemClickListener(this);
	}

	public static String formatAmount(int num) {
	    DecimalFormat decimalFormat = new DecimalFormat();
	    DecimalFormatSymbols decimalFormateSymbol = new DecimalFormatSymbols();
	    decimalFormateSymbol.setCurrencySymbol("$");
	    decimalFormateSymbol.setGroupingSeparator('.');
	    decimalFormateSymbol.setMonetaryDecimalSeparator(',');
	    ((DecimalFormat) decimalFormat).setDecimalFormatSymbols(decimalFormateSymbol);
	    return decimalFormat.format(num);
	}
	
	private void getValue(ArrayList<Saving> savings) {
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(Value.getWindowToken(), 0);
		Descricption.setText("");
		Value.setText("");
		int value=0;
		for(Saving s: savings){
			if(s.getType() == 1){
				value += s.getValue();
			}else{
				value -= s.getValue();
			}
		}
		Total.setText(formatAmount(value));
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		final Saving elegido = (Saving) arg0.getItemAtPosition(position); 
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.dialog_account_saving);
		dialog.setTitle("Cuentas");
		
		final EditText descrition = (EditText) dialog.findViewById(R.id.asaving_description);
		final EditText value = (EditText) dialog.findViewById(R.id.asaving_money);
		final RadioButton add = (RadioButton) dialog.findViewById(R.id.asaving_add);
		final RadioButton remove = (RadioButton) dialog.findViewById(R.id.asaving_remove);
		
		descrition.setText(elegido.getDescrition());
		value.setText(formatAmount(elegido.getValue()));
		if(elegido.getType() == 1){
			add.setChecked(true);
		}else{
			remove.setChecked(true);
		}
		
		
		final Button cancel = (Button)dialog.findViewById(R.id.asaving_cancel);
		final Button edit = (Button) dialog.findViewById(R.id.asaving_edit);
		final Button delete = (Button) dialog.findViewById(R.id.asaving_delete);
				
		OnClickListener events = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int id = v.getId();
				if(id== cancel.getId()){
					dialog.dismiss();
					Descricption.setSelectAllOnFocus(false);
					return;
				}
				PersistManager pm = new PersistManager(getActivity());
				if(id == edit.getId() ){
					String S_description = descrition.getText().toString();
					String s_value = (value.getText().toString()).replace(".", "");
					int type=0;
					if(add.isChecked()){
						type=1;
					}
					pm.editGeneralSaving(elegido.getid(),type,S_description,Integer.parseInt(s_value));
					
					CrearLista();
					InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromInputMethod(Descricption.getWindowToken(), 0);
					dialog.dismiss();
					return;
				}
				if(id == delete.getId()){
					pm.deleteGeneralSaving(elegido.getid());
					dialog.dismiss();
					InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromInputMethod(descrition.getWindowToken(), 0);
					imm.hideSoftInputFromInputMethod(value.getWindowToken(), 0);
					CrearLista();
				}
			}
		};
		
		cancel.setOnClickListener(events);
		edit.setOnClickListener(events);
		delete.setOnClickListener(events);	
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == btn_MoneytoDream.getId()){
			s_Value = Total.getText().toString();
			int valueaux = (Integer.parseInt(s_Value.replace(".", "")));
			if(valueaux > 0){
				Intent i = new Intent(getActivity(), Activity_MoneytoDream.class);
				i.putExtra("maxValue", valueaux);
				getActivity().startActivity(i);
			}else{
				BPOLocal.PossitiveMessageDialog(getString(R.string.acpass_title_info), "No tienes ahorrar disponibles", getActivity());
			}
		}
		
	}
	
}
