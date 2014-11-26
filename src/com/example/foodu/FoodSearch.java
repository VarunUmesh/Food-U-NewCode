package com.example.foodu;

import helper.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Eatery;

import com.example.adapters.ExpandableListAdapter;
import com.example.adapters.ItemModel;
import com.example.foodu.R.color;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class FoodSearch extends Activity implements OnGroupExpandListener, OnGroupClickListener {

	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<ItemModel> listDataHeader;
    HashMap<ItemModel, List<String>> listDataChild;
    int previousGroup = -1;
    
    DatabaseHandler db = new DatabaseHandler(this);
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_search);
		
		final Intent queryIntent = getIntent();
		String message= queryIntent.getStringExtra("SEARCH_MESSAGE").toLowerCase();
		
		List<Eatery> res = db.getEateryByItem(message); //db.getEateries();
		
		// get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
 
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
	}
	
	private void prepareListData() {
        listDataHeader = new ArrayList<ItemModel>();
        List<Eatery> eateries = db.getEateries();
        for(Eatery e :  eateries)
        	listDataHeader.add(new ItemModel(e.getLogo(), e.getName(), e.getAddress1() + "\n" + e.getAddress2(),"",  "", "", e.getId()));
        listDataChild = new HashMap<ItemModel, List<String>>();
 
        // Adding child data
        //listDataHeader.add("Top 250");
       // listDataHeader.add("Now Showing");
        //listDataHeader.add("Coming Soon..");
 
        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
 
        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
 
        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        for(int i = 0; i< listDataHeader.size(); i++)
        	listDataChild.put(listDataHeader.get(i), top250);
 
        //listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        //listDataChild.put(listDataHeader.get(1), nowShowing);
        //listDataChild.put(listDataHeader.get(2), comingSoon);
        expListView.setOnGroupExpandListener(this);
        expListView.setOnGroupClickListener(this);
        //expListView.seto
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_search, menu);
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
	
	@Override
	public void onGroupExpand(int groupPosition) {
		if(groupPosition != previousGroup)
			expListView.collapseGroup(previousGroup);
		
        previousGroup = groupPosition;
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		
	
		return false;
	}
}
