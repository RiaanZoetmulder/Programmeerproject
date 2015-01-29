package com.nl.mprog.ourstreet;

/*Author: Riaan Zoetmulder
* Project: Ourstreet			Date: 27-01-2015
* Description: This activity allows doing a search through the database to find 
* other users you would like to send a friend request to.
*/

import com.parse.Parse;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FirstActivity extends FragmentActivity {
	
	// Variable for dialogFragment
	private SignupOptionsFragment mainFragment;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", "nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
	    
	    if (ParseUser.getCurrentUser() != null) {
	    	
	    	  // Start an intent for the logged in activity
	    	  startActivity(new Intent(this, MainActivity.class));
	    	  
	    	  // finish activity
	    	  finish();
	    	  
	    } else {
	    	
	    	    // Start and intent for the logged out activity
	    		// Add the fragment on initial activity setup
		        mainFragment = new SignupOptionsFragment();
		        getSupportFragmentManager()
		        .beginTransaction()
		        .add(android.R.id.content, mainFragment)
		        .commit();
		        
	    }
	}

	    @Override
	    public void onResume(){
	    super.onResume();
	    	Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", "nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
	    
	    	if (ParseUser.getCurrentUser() != null) {
	    		
	    		// if current ParseUser, finish this activity.
	    		finish();
	    		
	    	} else {
	   
	    	    // Start and intent for the logged out activity
	    		// Add the fragment on initial activity setup
		        mainFragment = new SignupOptionsFragment();
		        getSupportFragmentManager()
		        .beginTransaction()
		        .add(android.R.id.content, mainFragment)
		        .commit();
		        
	    	}
	    }
}
