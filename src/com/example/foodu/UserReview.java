package com.example.foodu;

import helper.DatabaseHandler;

import java.util.Date;

import model.Eatery;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class UserReview extends Activity implements OnClickListener {

	DatabaseHandler db = new DatabaseHandler(this);
	TextView title, address;
	RatingBar food,ambience,economy, cleanliness,service;
	ImageView logo;
	EditText comments;
	Button submitReview;
	Eatery e;
	model.Review review;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_review);
		
		Intent intent = getIntent(); 
		int id = intent.getIntExtra("EATERY", 1);
		int eatery = intent.getIntExtra("EATERY", -1);
		String user = intent.getStringExtra("USER");
		
		if(eatery != -1 && user != null && !user.isEmpty())
			review = db.getUserEateryReview(user, eatery);
		e = db.getEatery(id);
		logo = (ImageView) findViewById(R.id.restauLogo);
		Bitmap bitmap = BitmapFactory.decodeByteArray(e.getLogo(), 0, e.getLogo().length);
        logo.setImageBitmap(bitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_review, menu);
		
		title = (TextView) findViewById(R.id.review_restaurant_name);
		address = (TextView) findViewById(R.id.review_rest_address);
		
		title.setText(e.getName());
		address.setText(e.getAddress1() + "\n" + e.getAddress2() + "\n" + e.getAddress3());

		food = (RatingBar)findViewById(R.id.food_stars);
		ambience = (RatingBar)findViewById(R.id.ambience_stars);
		economy = (RatingBar)findViewById(R.id.economical_stars);
		cleanliness = (RatingBar)findViewById(R.id.hygiene_stars);
		service = (RatingBar)findViewById(R.id.service_stars);
		comments = (EditText)findViewById(R.id.et_comments);
		submitReview = (Button)findViewById(R.id.btn_submit);
		
		
		submitReview.setOnClickListener(this);
		
		
		if(review != null){
		ambience.setRating(review.getAmbience());
		economy.setRating(review.getEconomy());
		cleanliness.setRating(review.getCleanliness());
		service.setRating(review.getService());
		food.setRating(review.getFood());
		comments.setText(review.getComment());
		submitReview.setVisibility(View.INVISIBLE);
		}
		
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
	public void onClick(View v) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch(v.getId())
		{
		case R.id.btn_submit:
			
			builder.setTitle("Thank You");
			builder.setMessage(
					"Your review is queued in the approval process. It will take 48 hours to process.");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							db.addReview(new model.Review("poual001@umn.edu", 1, food.getRating(),
									ambience.getRating(),
									economy.getRating(),
									cleanliness.getRating(),
									service.getRating(),
									comments.getEditableText().toString(), new Date()));
				            Intent intent = new Intent(UserReview.this, Review.class);

						    startActivity(intent);
						}
					});
			builder.create().show();
			break;
			//setIcon(android.R.drawable.ic_dialog_alert).show();
			
		/*	db.addReview(new model.Review("poual001@umn.edu", 1, food.getRating(),
					ambience.getRating(),
					economy.getRating(),
					cleanliness.getRating(),
					service.getRating(),
					comments.getEditableText().toString(), new Date()));
			Intent intent = new Intent(this, com.example.foodu.Review.class);
		    startActivity(intent);
			break;*/
		}
		
	}
}
