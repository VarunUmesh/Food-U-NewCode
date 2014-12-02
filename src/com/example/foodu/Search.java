package com.example.foodu;

import helper.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Eatery;

import com.example.adapters.CustomListAdapter;
import com.example.adapters.ExpandableListAdapter;
import com.example.adapters.ItemModel;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

public class Search extends ActionBarActivity implements OnItemClickListener, OnGroupExpandListener, OnGroupClickListener  {

	private List<ItemModel> movieList = new ArrayList<ItemModel>();
	DatabaseHandler db = new DatabaseHandler(this);
	private ListView listView;
	private CustomListAdapter adapter;
	
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<ItemModel> listDataHeader;
    HashMap<ItemModel, List<String>> listDataChild;
    int previousGroup = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		String message = "";
		final Intent queryIntent = getIntent();
		if(queryIntent.getStringExtra("SEARCH_MESSAGE")!="" || !queryIntent.getStringExtra("SEARCH_MESSAGE").isEmpty() || queryIntent.getStringExtra("SEARCH_MESSAGE") != null){
			System.out.println("search message== " + queryIntent.getStringExtra("SEARCH_MESSAGE"));
			message = queryIntent.getStringExtra("SEARCH_MESSAGE").toLowerCase();
		}
		setTitle(message);

		List<Eatery> eateries = db.getEateries();
		List<Eatery> result = new ArrayList<Eatery>();
		for (Eatery e : eateries) {
			if (e.getName().toLowerCase().contains(message)) {
				result.add(e);
			}
		}

		if (result.size() < 1) {
			setContentView(R.layout.activity_food_search);
			//Intent i = new Intent(this, FoodSearch.class);
			//i.putExtra("SEARCH_MESSAGE", message);
			//startActivity(i);
			List<Eatery> res = db.getEateryByItem(message); //db.getEateries();
			
			// get the listview
	        expListView = (ExpandableListView) findViewById(R.id.lvExp);
	 
	        // preparing list data
	        prepareListData();
	 
	        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
	 
	        // setting list adapter
	        expListView.setAdapter(listAdapter);
		} else {
			setContentView(R.layout.activity_search);
			for (Eatery e : result)
				movieList.add(new ItemModel(e.getLogo(), e.getName(), e
						.getAddress1() + "\n" + e.getAddress2(), "", "", "", e
						.getId()));
			listView = (ListView) findViewById(R.id.list);
			adapter = new CustomListAdapter(this, movieList);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
		}
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
		getMenuInflater().inflate(R.menu.search, menu);
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
		} else if (id == R.id.action_filter) {
			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.filter_search);
			Button cancel = (Button) dialog.findViewById(R.id.cancel);
			Button refine = (Button) dialog.findViewById(R.id.refine);
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();

				}
			});
			refine.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();

				}
			});
			// dialog.getWindow().setBackgroundDrawable(new
			// ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ItemModel item = (ItemModel) adapter.getItem(arg2);
		Intent intent = new Intent(this, SearchDetail.class);
		intent.putExtra("EATERY", item.getKey());
		startActivity(intent);
	}

	@Override
	public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onGroupExpand(int groupPosition) {
		if(groupPosition != previousGroup)
			expListView.collapseGroup(previousGroup);
		
        previousGroup = groupPosition;
		
	}
}
