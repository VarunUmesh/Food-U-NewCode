package com.example.foodu;

import model.Eatery;
import helper.DatabaseHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.util.LogPrinter;
import android.util.Log;

public class CuisineRecommendation extends Activity implements OnClickListener {

	DatabaseHandler db = new DatabaseHandler(this);
	Button like, dislike;
	TextView title, address;
	int count = 0,randomChoice;
	TextView titleForRandom,addressForRandom,foodDescForRandom;
	ImageView logoForRandom,foodImageForRandom;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cuisine_recommendation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Intent grabChoice = getIntent();
		String logData=getIntent().getDataString();
		randomChoice = grabChoice.getIntExtra("randomChoiceVariable", 0);
		
		getMenuInflater().inflate(R.menu.cuisine_recommendation, menu);
		like = (Button) findViewById(R.id.like);
		dislike = (Button) findViewById(R.id.dislike);
		like.setOnClickListener(this);
		dislike.setOnClickListener(this);
		
		title = (TextView) findViewById(R.id.title);
		address = (TextView) findViewById(R.id.button_text);
		Log.d("IntentData", "The value of randomChoice is ---->" +randomChoice);
		buidAndroidComponentsFromRandom(randomChoice);
		return true;
	}
	
	/* This is a method to change the android components based on the random choice 
	 * American=0,
	   Cafe=1,
	   Chinese=2,
	   Italian=3,
	   Juices=4,
	   Mexican=5,
	   Vietnamese=6
	 * 
	 * randomChoice
	 * 
	 * */
	
	private void buidAndroidComponentsFromRandom(int localVarRandomChoice){
		
		switch(localVarRandomChoice){
		
			case 0:
						/* American food */
						titleForRandom = (TextView) findViewById(R.id.title);
						logoForRandom = (ImageView) findViewById(R.id.imageView1);
						addressForRandom = (TextView) findViewById(R.id.button_text);
						foodImageForRandom = (ImageView) findViewById(R.id.numberDays);
						foodDescForRandom = (TextView) findViewById(R.id.foodDesc);
						titleForRandom.setText("How about Subway?");
						logoForRandom.setImageResource(R.drawable.subway);
						addressForRandom.setText("Coffman Union\n3rd Floor\nWest bank");
						foodImageForRandom.setImageResource(R.drawable.sandwish);
						foodDescForRandom.setText("Fish Sandich\n$5");
						break;
						
			case 1:
						/* Cafe */
						titleForRandom = (TextView) findViewById(R.id.title);
						logoForRandom = (ImageView) findViewById(R.id.imageView1);
						addressForRandom = (TextView) findViewById(R.id.button_text);
						foodImageForRandom = (ImageView) findViewById(R.id.numberDays);
						foodDescForRandom = (TextView) findViewById(R.id.foodDesc);
						titleForRandom.setText("How about Caribou Cafe?");
						logoForRandom.setImageResource(R.drawable.cariboulogo);
						addressForRandom.setText("Coffman Union\n3rd Floor\nWest bank");
						foodImageForRandom.setImageResource(R.drawable.cariboufood);
						foodDescForRandom.setText("Fish Sandich\n$5");
						break;
						
			case 2:
						/* Chinese */
						titleForRandom = (TextView) findViewById(R.id.title);
						logoForRandom = (ImageView) findViewById(R.id.imageView1);
						addressForRandom = (TextView) findViewById(R.id.button_text);
						foodImageForRandom = (ImageView) findViewById(R.id.numberDays);
						foodDescForRandom = (TextView) findViewById(R.id.foodDesc);
						titleForRandom.setText("How about Panda Express?");
						logoForRandom.setImageResource(R.drawable.pandaexpresslogo);
						addressForRandom.setText("Coffman Union\n3rd Floor\nWest bank");
						foodImageForRandom.setImageResource(R.drawable.pandaexpressfood);
						foodDescForRandom.setText("Fish Sandich\n$5");
						break;
						
			case 3:
						/* Italian */
						titleForRandom = (TextView) findViewById(R.id.title);
						logoForRandom = (ImageView) findViewById(R.id.imageView1);
						addressForRandom = (TextView) findViewById(R.id.button_text);
						foodImageForRandom = (ImageView) findViewById(R.id.numberDays);
						foodDescForRandom = (TextView) findViewById(R.id.foodDesc);
						titleForRandom.setText("How about Topios?");
						logoForRandom.setImageResource(R.drawable.topiologo);
						addressForRandom.setText("Coffman Union\n3rd Floor\nWest bank");
						foodImageForRandom.setImageResource(R.drawable.topiofood);
						foodDescForRandom.setText("Fish Sandich\n$5");
						break;
						
			case 4:
						/* Juice */
						titleForRandom = (TextView) findViewById(R.id.title);
						logoForRandom = (ImageView) findViewById(R.id.imageView1);
						addressForRandom = (TextView) findViewById(R.id.button_text);
						foodImageForRandom = (ImageView) findViewById(R.id.numberDays);
						foodDescForRandom = (TextView) findViewById(R.id.foodDesc);
						titleForRandom.setText("How about Jamba Juice?");
						logoForRandom.setImageResource(R.drawable.jambajuicelogo);
						addressForRandom.setText("Coffman Union\n3rd Floor\nWest bank");
						foodImageForRandom.setImageResource(R.drawable.jambajuicefood);
						foodDescForRandom.setText("Fish Sandich\n$5");
						break;
						
			case 5:
						/* Mexican */
						titleForRandom = (TextView) findViewById(R.id.title);
						logoForRandom = (ImageView) findViewById(R.id.imageView1);
						addressForRandom = (TextView) findViewById(R.id.button_text);
						foodImageForRandom = (ImageView) findViewById(R.id.numberDays);
						foodDescForRandom = (TextView) findViewById(R.id.foodDesc);
						titleForRandom.setText("How about Baja Sol?");
						logoForRandom.setImageResource(R.drawable.bajasollogo);
						addressForRandom.setText("Coffman Union\n3rd Floor\nWest bank");
						foodImageForRandom.setImageResource(R.drawable.bajasolfood);
						foodDescForRandom.setText("Fish Sandich\n$5");
						break;
						
			case 6:
						/* Vietnamese */
						titleForRandom = (TextView) findViewById(R.id.title);
						logoForRandom = (ImageView) findViewById(R.id.imageView1);
						addressForRandom = (TextView) findViewById(R.id.button_text);
						foodImageForRandom = (ImageView) findViewById(R.id.numberDays);
						foodDescForRandom = (TextView) findViewById(R.id.foodDesc);
						titleForRandom.setText("How about Bun Mi?");
						logoForRandom.setImageResource(R.drawable.bunmilogo);
						addressForRandom.setText("Coffman Union\n3rd Floor\nWest bank");
						foodImageForRandom.setImageResource(R.drawable.bunmifood);
						foodDescForRandom.setText("Fish Sandich\n$5");
						break;
						
			default:
						/* Display some shit, who cares!! */
						titleForRandom = (TextView) findViewById(R.id.title);
						logoForRandom = (ImageView) findViewById(R.id.imageView1);
						addressForRandom = (TextView) findViewById(R.id.button_text);
						foodImageForRandom = (ImageView) findViewById(R.id.numberDays);
						foodDescForRandom = (TextView) findViewById(R.id.foodDesc);
						titleForRandom.setText("How about Topios?");
						logoForRandom.setImageResource(R.drawable.topiologo);
						addressForRandom.setText("Coffman Union\n3rd Floor\nWest bank");
						foodImageForRandom.setImageResource(R.drawable.topiofood);
						foodDescForRandom.setText("Fish Sandich\n$5");
						break;
		}
		
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
				Eatery e = db.getEatery(count+1);
				title.setText("How about " + e.getName());
				address.setText(e.getAddress1() + "\n" + e.getAddress2() + "\n" + e.getAddress3());
			}
			else{
				new AlertDialog.Builder(this)
			    .setTitle("Sorry")
			    .setMessage("Sorry you did not like our recommendations.")
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
