package com.nl.mprog.ourstreet;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*Class: ManualDataEntru
 * Purpose: to collect data if a user doesn't have facebook
 * to implement:
 * - Sharedprefs
 * - write to database
 * - errorchecken so it doesn't crash when textfields are empty
 * - nicer layout
 * - additional textfields
 * 
 * 
*/
public class ManualDataEntry extends Activity {
	 
	 
	 
	
	// defining EditTexts and Button
	private EditText mFirstname;
	private EditText mLastname;
	private EditText mStreetname;
	private EditText mCity;
	private EditText mNumber;
	private EditText mPassword;
	private EditText mConfirmPassword;
	private Button mConfirmButton;
	private Button mBackButton;
	
	// defining Variables to store Data
	private String firstname;
	private String lastname;
	private String streetname;
	private Integer number;
	private String password;
	private String city;
	private String confirmPassword;
	
	private boolean succesfulSignup;
	
	
	protected void onCreate(Bundle SavedInstanceState){
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.manualdataentry);
		
		// initialize parse database
	    Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", "nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
	    
		// connect all the Edittexts and buttons to XML
		mFirstname = (EditText) findViewById(R.id.firstname);
		mLastname = (EditText) findViewById(R.id.lastname);
		mStreetname = (EditText) findViewById(R.id.streetname);
		mNumber = (EditText) findViewById(R.id.number);
		mCity = (EditText) findViewById(R.id.city);
		mPassword = (EditText) findViewById(R.id.password);
		mConfirmPassword = (EditText) findViewById(R.id.confirmpassword);
		mConfirmButton = (Button)findViewById(R.id.done);
		mBackButton = (Button) findViewById(R.id.backbutton);
		
		// not yet signed up
		succesfulSignup = false;
		
		// listen for clicking the button
		mConfirmButton.setOnClickListener(
				new View.OnClickListener()
		        {
		            public void onClick(View view)
		            {
		            	
		            	// call savedata method
		            	savedata();
		            	
		            	if (succesfulSignup == true){
		            		
		            		Intent i = new Intent(ManualDataEntry.this,Activity_First_Time.class);
			            	startActivity(i);
			            		
			            	// finish current activity to save 
			            	finish();
		            	}	
		            }
		            

					private void savedata() {
						
						// initialize variables
		            	firstname = mFirstname.getText().toString();
		            	lastname = mLastname.getText().toString();
		            	streetname = mStreetname.getText().toString();
		            	number =  Integer.parseInt(mNumber.getText().toString());
		            	password = mPassword.getText().toString();
		            	confirmPassword = mConfirmPassword.getText().toString();
		            	city = mCity.getText().toString();
		            	
		            	Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		            	String textToSearch = streetname + " " + number + ", " + city;
		            	List<Address> fromLocationName = null;
		            	
		            	try {
							fromLocationName = geocoder.getFromLocationName(textToSearch,   1);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		            	
		            	Double latitude = fromLocationName.get(0).getLatitude();
		            	Double longitude = fromLocationName.get(0).getLongitude();
		            
		            	
		            	// if not null go to viewpager
		            	if (firstname.length() > 0 && lastname.length() > 0
		            			&& streetname.length() > 0 && number != null 
		            			&& password.equals(confirmPassword) && password.length() > 0){
		            		
		            		// Load to parse.com
		            		ParseUser user = new ParseUser();
		            		user.setUsername(firstname + " " + lastname);
		            		user.setPassword(password);
		            		user.put("streetname", streetname);
		            		user.put("housenumber", number);
		            		user.put("city", city);
		            		user.put("latitude", latitude);
		            		user.put("longitude", longitude);
		            		
		            		user.signUpInBackground(new SignUpCallback(){
		            			
		            			@Override
								public void done(ParseException e) {
									if (e == null) {
										Toast.makeText(getApplicationContext(), "No error in signing up",
												Toast.LENGTH_LONG).show();
			            			      // Hooray! Let them use the app now.
			            			    } else {
			            			      // Sign up didn't succeed. Look at the ParseException
			            			      // to figure out what went wrong
			            			    }
		            			}
		            			
		            		});
		            		ParseUser.logOut();		            		

		            		// report a succesful signup
		            		succesfulSignup = true;
		            		
		            	}else{
		            		// uh-oh something went wrong!
		            		Toast.makeText(ManualDataEntry.this, "Signup Error, please check all the fields", Toast.LENGTH_LONG)
		                    .show();
		            	}
					}
		        });
		mBackButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// return to starting screen
				finish();
			}
			
		});
	}
}

 
