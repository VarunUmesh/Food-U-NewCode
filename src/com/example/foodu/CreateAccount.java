package com.example.foodu;

import helper.DatabaseHandler;
import model.User;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class CreateAccount extends ActionBarActivity implements OnClickListener {

	DatabaseHandler db = new DatabaseHandler(this);
	EditText fname, lname, email, password, cpassword, budget;
	ToggleButton vegan, vegeterian, gluten;
	Button createBtn;
	User u;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		
		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Button save = (Button)findViewById(R.id.save);
		//save.setOnClickListener(this);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account, menu);
		
		fname = (EditText) findViewById(R.id.fname);
		lname = (EditText) findViewById(R.id.lname);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.pwd);
		cpassword = (EditText) findViewById(R.id.cpwd);
		budget = (EditText)findViewById(R.id.budget);
		createBtn = (Button) findViewById(R.id.create);
		
		vegeterian = (ToggleButton) findViewById(R.id.vegeterian);
		vegan = (ToggleButton) findViewById(R.id.vegan);
		gluten = (ToggleButton) findViewById(R.id.gluten);
		vegeterian = (ToggleButton) findViewById(R.id.vegeterian);
		
		fname.setText("Joel");
		lname.setText("Poualeu");
		email.setText("poual001@umn.edu");
		password.setText("Joel001");
		cpassword.setText("Joel001");
		budget.setText("8");
		vegeterian.setChecked(true);
		createBtn.setOnClickListener(this);
		
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
		        R.array.Cuisines_array, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);
		spinner1.setSelection(adapter1.getPosition("Indian"));
		
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
		        R.array.Cuisines_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);
		spinner2.setSelection(adapter2.getPosition("Chinese"));
		
		Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
		        R.array.Cuisines_array, android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(adapter3);
		spinner3.setSelection(adapter3.getPosition("American"));
		
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
			View rootView = inflater.inflate(R.layout.fragment_create_account,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			//case R.id.profile:
		case R.id.create:
			db.addUser(new User(email.getText().toString().toLowerCase(), 
								password.getText().toString(), 
								fname.getText().toString(), 
								lname.getText().toString(), 
								0, 0, 0, 8));
			Intent intent = new Intent(this, MainActivity.class);
		    startActivity(intent); 
			break;
		}
	}

}
