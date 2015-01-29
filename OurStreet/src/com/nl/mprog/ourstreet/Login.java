package com.nl.mprog.ourstreet;

/* Author: Riaan Zoetmulder
 * Project: Ourstreet			Date: 27-01-2015
 * Description: Activity that allows the user to Log in to the 
 * App using his first and lastname and a chosen password
 * 
*/
import com.parse.LogInCallback;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
	// create textfield and button variables
	private EditText mFirstname;
	private EditText mLastname;
	private EditText mPassword;
	private Button mLogin;
	
	// create string variables to store data in
	private String firstname;
	private String lastname;
	private String password;
	private String fullname;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginlayout);
		Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", "nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
		
		// set textfields and button
		mFirstname = (EditText) findViewById(R.id.firstnamelogin);
		mLastname = (EditText) findViewById(R.id.lastnamelogin);
		mPassword = (EditText) findViewById(R.id.passwordlogin);
		mLogin = (Button) findViewById(R.id.done);
		
		// change textfields to Strings
		firstname = mFirstname.getText().toString();
		lastname = mLastname.getText().toString();
		password = mPassword.getText().toString();
		fullname = firstname + " " + lastname;
		
		
		
		mLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				// change textfields to Strings
				firstname = mFirstname.getText().toString();
				lastname = mLastname.getText().toString();
				password = mPassword.getText().toString();
				fullname = firstname + " " + lastname;
				
				login();
				
			}

			private void login() {
				ParseUser.logInInBackground(fullname, password, new LogInCallback() {

					@Override
					public void done(ParseUser arg0, ParseException arg1) {
						
						if (arg1 != null) {
							
						    // Show the error message
						    Toast.makeText(Login.this, arg1.toString(), 
						      Toast.LENGTH_LONG).show();
						    
						  } else if (ParseUser.getCurrentUser() != null) {
							
							  
						    // Start an intent for the dispatch activity
						    Intent intent = new Intent(Login.this, MainActivity.class);
						    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | 
						      Intent.FLAG_ACTIVITY_NEW_TASK);
						    
						    // Greet User 
						    Toast.makeText(Login.this, "Welcome " + arg0.getUsername(), 
								      Toast.LENGTH_LONG).show();
						    startActivity(intent);
						    
						  } else{
							  
							  // Most likely an internet error
							  // Inform the user about this
							  Toast.makeText(Login.this, "Internet error: Please Connect", 
								      Toast.LENGTH_LONG).show();
							  
						  }
						  
					}
				});
			}
		});
	}
}
