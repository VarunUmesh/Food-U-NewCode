package com.example.foodu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import helper.DatabaseHandler;
import model.Eatery;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CuisineRecommendation extends Activity implements OnClickListener {

	DatabaseHandler db = new DatabaseHandler(this);
	Button like, dislike;
	ImageView logo, food;
	TextView title, address, foodDesc;
	int count = 0, randomChoice=-1;
	TextView titleForRandom, addressForRandom, foodDescForRandom;
	ImageView logoForRandom, foodImageForRandom;

	int eateryCount = 0;
	List<Integer> recomendations = new ArrayList<Integer>();
	List<String> phrases = new ArrayList<String>();
	ArrayList<CharSequence> selectionStrings = new ArrayList<CharSequence>();
	Eatery e;
	model.MenuItem m = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cuisine_recommendation);

		Intent grabChoice = getIntent();
		// randomChoice = grabChoice.getIntExtra("randomChoiceVariable", 0);
		ArrayList<Integer> selection = grabChoice
				.getIntegerArrayListExtra("selection");
		CharSequence[] items = getResources().getStringArray(
				R.array.Cuisines_choices);
		selectionStrings = new ArrayList<CharSequence>();
		for (int i : selection)
			selectionStrings.add(items[i]);

		randomChoice = generateRandomChoice(selection);
		// Toast.makeText(this, "it is " + randomChoice,
		// Toast.LENGTH_LONG).show();

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
		food = (ImageView) findViewById(R.id.numberDays);
		foodDesc = (TextView) findViewById(R.id.foodDesc);

		eateryCount = db.getEateries().size();
		
		
		if(randomChoice > -1){
			int choice = buidAndroidComponentsFromRandom(randomChoice);
			e = db.getEatery(choice);
			m = db.getItem(choice);
		}
		else{
			int recom = getRecommendation();
			e = db.getEatery(recom);
			m = db.getItem(recom);
		}
		title.setText(phrases.get(count) + e.getName() + "?");
		address.setText(e.getAddress1() + "\n" + e.getAddress2() + "\n"
				+ e.getAddress3());
		Bitmap bitmap;
		bitmap = BitmapFactory.decodeByteArray(e.getLogo(), 0,
				e.getLogo().length);
		logo.setImageBitmap(bitmap);
		if(m != null){
			bitmap = BitmapFactory.decodeByteArray(m.getImage(), 0,
					m.getImage().length);
			food.setImageBitmap(bitmap);
			foodDesc.setText(m.getPrice() + "\n" + m.getName());
		}

		return true;
	}

	public int getRecommendation() {
		int r = randInt(1, eateryCount);
		while (this.recomendations.indexOf(r) > -1) {
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
		switch (u) {
		case R.id.like:
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			break;
		case R.id.dislike:
			if (count < 2) {
				count++;
				if(randomChoice > -1){
					int choice = buidAndroidComponentsFromRandom(randomChoice);
					e = db.getEatery(choice);
					m = db.getItem(choice);
				}
				else{
					int recom = getRecommendation();
					e = db.getEatery(recom);
					m = db.getItem(recom);
				}
				title.setText(phrases.get(count) + e.getName());
				address.setText(e.getAddress1() + "\n" + e.getAddress2() + "\n"
						+ e.getAddress3());
				Bitmap bitmap = BitmapFactory.decodeByteArray(e.getLogo(), 0,
						e.getLogo().length);
				logo.setImageBitmap(bitmap);
				if(m != null){
					bitmap = BitmapFactory.decodeByteArray(m.getImage(), 0,
							m.getImage().length);
					food.setImageBitmap(bitmap);
					foodDesc.setText(m.getPrice() + "\n" + m.getName());
				}
			} else {
				new AlertDialog.Builder(this)
						.setTitle("Sorry")
						.setMessage(
								"Sorry you did not like our recommendations. Please try again next time.")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										Intent i = new Intent(getApplication(),
												MainActivity.class);
										startActivity(i);
									}
								}).setIcon(android.R.drawable.ic_dialog_alert)
						.show();
			}

			break;
		}
	}

	private int generateRandomChoice(ArrayList<Integer> myList) {
		if (myList != null) {
			Log.d("ArrayListThings", "The contents of the arraylist are-->"
					+ myList);
			Collections.shuffle(myList, new Random());
			return myList.get(0).intValue();
		} else {
			return 100;
		}

	}

	private int buidAndroidComponentsFromRandom(int localVarRandomChoice) {

		switch (localVarRandomChoice) {

		case 0:
			/* American food */
			return 13;

		case 1:
			/* Cafe */
			return 3;

		case 2:
			/* Chinese */
			return 5;

		case 3:
			/* Italian */
			return 1;

		case 4:
			/* Juice */
			return 7;

		case 5:
			return 2;

		case 6:
			/* Vietnamese */
			return 15;

		default:
			return getRecommendation();
		}

	}
}
