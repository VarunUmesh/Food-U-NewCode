package com.example.foodu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Eatery;
import helper.DatabaseHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CuisineRecommendation extends Activity implements OnClickListener {

	DatabaseHandler db = new DatabaseHandler(this);
	Button like, dislike;
	ImageView logo;
	TextView title, address;
	int count = 0;
	int eateryCount = 0;
	List<Integer> recomendations = new ArrayList<Integer>();
	List<String> phrases = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cuisine_recommendation);
		
		phrases.add("How about ");
		phrases.add("Would you prefer ");
		phrases.add("You may like ");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cuisine_recommendation, menu);
		
		like = (Button) findViewById(R.id.like);
		dislike = (Button) findViewById(R.id.dislike);
		like.setOnClickListener(this);
		dislike.setOnClickListener(this);
		
		title = (TextView) findViewById(R.id.title);
		address = (TextView) findViewById(R.id.button_text);
		logo = (ImageView) findViewById(R.id.logo);
		
		eateryCount = db.getEateries().size();
		int recom = getRecommendation();
		Eatery e = db.getEatery(recom);
		title.setText(phrases.get(count) + e.getName());
		address.setText(e.getAddress1() + "\n" + e.getAddress2() + "\n" + e.getAddress3());
		Bitmap bitmap = BitmapFactory.decodeByteArray(e.getLogo(), 0, e.getLogo().length);
        logo.setImageBitmap(bitmap);
		
		return true;
	}
	public int getRecommendation(){
		int r=randInt(1, eateryCount);
		while(this.recomendations.indexOf(r) > -1){
			r = randInt(1, eateryCount);
		}
		this.recomendations.add(r);
		return r;
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
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
	public void onClick(View arg0) {
		int u = arg0.getId();
		switch(u){
		case R.id.like:
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			break;
		case R.id.dislike:
			if(count < 2){
				count++;
				Eatery e = db.getEatery(getRecommendation());
				title.setText(phrases.get(count)  + e.getName());
				address.setText(e.getAddress1() + "\n" + e.getAddress2() + "\n" + e.getAddress3());
				Bitmap bitmap = BitmapFactory.decodeByteArray(e.getLogo(), 0, e.getLogo().length);
		        logo.setImageBitmap(bitmap);
			}
			else{
				new AlertDialog.Builder(this)
			    .setTitle("Sorry")
			    .setMessage("Sorry you did not like our recommendations. Please build your profile and we will do better next time")
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	Intent i = new Intent(getApplication(), MainActivity.class);
						startActivity(i);
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
			}
				
			break;
		}
	}
}
