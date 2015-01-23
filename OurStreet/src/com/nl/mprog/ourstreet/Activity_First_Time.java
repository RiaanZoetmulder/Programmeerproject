package com.nl.mprog.ourstreet;

import com.parse.Parse;
import com.parse.ParseUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/* What else to implement here:
 *  - shared preferences
 *  - a logo in the middle of the screen, make layout nicer in general
 *  - delayed popping up of dialog
 *  - if dialog is terminated without info, restart it.
 * 
*/
public class Activity_First_Time extends FragmentActivity {
	
	// dialogFragment
	private User_data_prompt_dialog mainFragment;
	
	// defining Variables to store Data
	public String firstname;
	public String lastname;
	public String streetname;
	public Integer number;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", "nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
	    
	    
	    if (ParseUser.getCurrentUser() != null) {
	    	
	    	  // Start an intent for the logged in activity
	    	  startActivity(new Intent(this, MainActivity.class));
	    	  
	    } else {
	    	
	    	    // Start and intent for the logged out activity
	    		// Add the fragment on initial activity setup
		        mainFragment = new User_data_prompt_dialog();
		        getSupportFragmentManager()
		        .beginTransaction()
		        .add(android.R.id.content, mainFragment)
		        .commit();
		        
	    }
	}

	    
	    @Override
	    public void onResume(){
	    super.onResume();
	    /*mainFragment = (User_data_prompt_dialog) getSupportFragmentManager()
		        .findFragmentById(android.R.id.content);*/
	    
	    	Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", "nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
	    
	    	if (ParseUser.getCurrentUser() != null) {
	    		
	    		// if current ParseUser, finish this activity.
	    		finish();
	    		
	    	} else {
	    		
	    	    // Start and intent for the logged out activity
	    		// Add the fragment on initial activity setup
		        mainFragment = new User_data_prompt_dialog();
		        getSupportFragmentManager()
		        .beginTransaction()
		        .add(android.R.id.content, mainFragment)
		        .commit();
		        
	    	}
	    }
}
