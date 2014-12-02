package com.example.foodu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class Recommendation extends ActionBarActivity implements OnClickListener {

	Button cuisine, profile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommendation);
		
		//TextView recommendation = (TextView)findViewById(R.id.recommendation);
		//recommendation.setText("Orange chicken" + System.getProperty("line.separator") + "abs Restaurant");

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		cuisine = (Button) findViewById(R.id.cuisine);
		profile = (Button) findViewById(R.id.profile);
		cuisine.setOnClickListener(this);
		profile.setOnClickListener(this);
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recommendation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_recommendation,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.cuisine:
			final CharSequence[] items = getResources().getStringArray(R.array.Cuisines_choices);// {"American","Cafe","Chinese","Italian","Juices","Mexican","Vietnamese"};
			final ArrayList<Integer> seletedItems=new ArrayList<Integer>();
			final boolean[] states = new boolean[items.length];
		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Which cuisine do you want?");
		    builder.setMultiChoiceItems(items, states, new DialogInterface.OnMultiChoiceClickListener(){
		        public void onClick(DialogInterface dialogInterface, int item, boolean state) {
		        	 if (state) {
	                     seletedItems.add(item);
	                 } else if (seletedItems.contains(item)) {
	                     seletedItems.remove(Integer.valueOf(item));
	                 }

		        }
		    });
		    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int id) {
		        	ArrayList<Integer> selctedList=new ArrayList<Integer>();
		            SparseBooleanArray CheCked = ((AlertDialog)dialog).getListView().getCheckedItemPositions();
		            String result = "";
		            for (int i = 0; i < items.length; i++) {
	                    if (states[i]) {
	                    	result = result + (String) items[i] + "\n";
	                    	selctedList.add(i);
	                    }
	                }
		            
		           if(seletedItems.size() > 0){
		            Intent userrecommendation = new Intent(Recommendation.this, CuisineRecommendation.class);
		            //userrecommendation.putExtra("randomChoiceVariable",randomChoice);
		            userrecommendation.putExtra("selection",seletedItems);
				    startActivity(userrecommendation);
		           }
		           else{
		        	   Toast.makeText(getApplicationContext(), "Make a Cuisine Recommendation", Toast.LENGTH_LONG);
		           }
		        }
		    });
		    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int id) {
		             dialog.cancel();
		        }
		    });
		    
		    builder.create().show();
			break;
		case R.id.profile:
			Intent userrecommendation = new Intent(Recommendation.this, CuisineRecommendation.class);
		    startActivity(userrecommendation);
			break;
		}
		
	}
	
	/*private int generateRandomChoice(ArrayList<Integer> myList){
		if(myList!=null){
			Log.d("ArrayListThings", "The contents of the arraylist are-->" +myList);
			Collections.shuffle(myList, new Random());
			return myList.get(0).intValue();
		}
		else{
			return 100;
		}
		
	}*/

}
