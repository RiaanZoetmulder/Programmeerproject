package com.nl.mprog.ourstreet;

/* Author: Riaan Zoetmulder
 * Project: Ourstreet			Date: 27-01-2015
 * Description: Creates a user in the database, allows the user to 
 * type in their adress name etc. and a password.
 * 
 * 
 * 
*/

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
	private Double latitude;
	private Double longitude;
	
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
		            	try{
		            		savedata();
		            	}catch(Exception e){
		            		
		            	}
		            	
		            	
		            	if (succesfulSignup == true){
		            		
		            		Intent i = new Intent(ManualDataEntry.this,FirstActivity.class);
			            	startActivity(i);
			            		
			            	// succesful signup is true thus log them out
			            	// gives the database time to refresh
			            	ParseUser.getCurrentUser();
							ParseUser.logOut();	
							
			            	// finish current activity to save 
			            	finish();

		            	}	
		            }
		            
					private void savedata() {
						
						// initialize variables
		            	firstname = mFirstname.getText().toString();
		            	lastname = mLastname.getText().toString();
		            	streetname = mStreetname.getText().toString();
		            	
		            	// see if int is valid before using it
		            	try{
		            		
		            		number =  Integer.parseInt(mNumber.getText().toString());
		            	
		            	}catch(Exception e){
		            		number = -1;
		            		
		            	}
		            	
		            	password = mPassword.getText().toString();
		            	confirmPassword = mConfirmPassword.getText().toString();
		            	city = mCity.getText().toString();
		            	
		            	final Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		            	final String textToSearch = streetname + " " + number + ", " + city;
		            	List<Address> fromLocationName = null;
		            	
		            	try {
							fromLocationName = geocoder.getFromLocationName(textToSearch,   1);
						} catch (IOException e1) {
							e1.printStackTrace();
							
						}
		            	if (fromLocationName != null){
		            		latitude = fromLocationName.get(0).getLatitude();
		            		longitude = fromLocationName.get(0).getLongitude();
		            	}else{
		            		Toast.makeText(ManualDataEntry.this,"cannot geocode, check all the fields and your internet", Toast.LENGTH_LONG)
		                    .show();
		            		
		            	}
		            
		            	// if not null go back to login
		            	if (firstname.length() > 0 && lastname.length() > 0
		            			&& streetname.length() > 0 && number != null 
		            			&& password.equals(confirmPassword) && password.length() > 0
		            			&& city.length() > 0 && number >= 0 && latitude != null
		            			&& longitude != null){
		            		try{
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
										Toast.makeText(getApplicationContext(), "Please, login now!",
												Toast.LENGTH_LONG).show();
			            			    
			            			    } else {
			            			    	
			            			  
			            			    }
		            			}
		            			
		            		});
		            		            		
		            		// report a succesful signup
		            		succesfulSignup = true;
		            		}catch(Exception e){
		            			
		            		}
		            		
		            	}else{
		            		// uh-oh something went wrong!
		            		Toast.makeText(ManualDataEntry.this, "Signup Error, please check all the fields", Toast.LENGTH_LONG)
		                    .show();
		            	}
					}
		        });
		
		// return to the first screen
		mBackButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// return to starting screen
				finish();
			}
			
		});
	}
}

 
