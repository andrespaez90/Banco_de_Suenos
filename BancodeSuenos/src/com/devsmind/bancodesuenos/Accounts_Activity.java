package com.devsmind.bancodesuenos;


import java.util.ArrayList;

import com.bd.Modelo.Dream;
import com.bd.Modelo.Lista_entrada;
import com.bd.Modelo.ModeloFacade;
import com.bds.Fragments.ExpenseSectionFragment;
import com.bds.Fragments.LiabilitySectionFragment;
import com.bds.Fragments.LoanSectionFragment;
import com.bds.Fragments.SavingSectionFragment;
import com.bds.Fragments.SecondSectionFragment;
import com.bds.Fragments.ThirdSectionFragments;
import com.db.Activities.Activity_DreamProfile;
import com.db.Activities.ConfigActivity;
import com.db.Activities.Activity_NewDream;
import com.db.adapters.Lista_adaptador;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Accounts_Activity extends FragmentActivity implements ActionBar.TabListener{

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
	
	public void onCreate(Bundle saveIntanceStated){
		super.onCreate(saveIntanceStated);
		setContentView(R.layout.main_activity);
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(),getApplicationContext());
	    int TabSelected = getIntent().getIntExtra("fragment", 0);
		final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        initPager(actionBar);
        createTabs(actionBar);
        actionBar.setSelectedNavigationItem(TabSelected);
      
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
	                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
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
	                	return new SavingSectionFragment();
	                case 1:
	                	return new LiabilitySectionFragment();
	                case 2:
	                	return new ExpenseSectionFragment();
	                default:
	                	return new LoanSectionFragment();
	            }
	        }

	        @Override
	        public int getCount() {
	        	//Number of section that I want
	            return 4;
	        }

	        @Override
	        public CharSequence getPageTitle(int position) {
	            //return the title of the tab section
	        	switch (position) {
					case 0:
						return context.getText(R.string.fragment_accounts_btn_saving);
					case 2:
						return context.getText(R.string.fragment_accounts_btn_liabilities);
					case 1:
						return context.getText(R.string.fragment_accounts_btn_expense);
					default:
						return context.getText(R.string.fragment_accounts_btn_loan);
					}
	        }
	        
	        public int getPageIcon(int position) {
	        	switch (position) {
				case 0:
					return (R.drawable.pie_suenos_trans);
				case 1:
					return (R.drawable.pie_amigos_trans);
				default:
					return (R.drawable.pie_agregar_trans);
				}
			}

	    }	
	
}
