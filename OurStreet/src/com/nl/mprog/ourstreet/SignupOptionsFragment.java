package com.nl.mprog.ourstreet;

/* Author: Riaan Zoetmulder
 * Project: Ourstreet			Date: 27-01-2015
 * Description: This fragment creates the layout on the first activity
 * and allows users to sign up or log in. 
 * 
 * Facebook button wasn't functional and was therefore removed.
 * Problems with the API Key which couldn't be resolved in a timely
 * manner.
 * 
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SignupOptionsFragment extends Fragment {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	}
	
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_first_dialog, container, false);
	    
	    // create manual input button
	 	Button manualbutton =(Button)view.findViewById(R.id.manualinput);
	 	manualbutton.setOnClickListener(new OnClickListener(){

	 		@Override
	 		public void onClick(View v) {
	 				
	 			// create intent to manual data entry.
	 			Intent intent = new Intent(getActivity(), ManualDataEntry.class);
	 			startActivity(intent);
	 			
	 		}
	 	});
	 	
	 	Button loginButton = (Button) view.findViewById(R.id.loginbutton);
	 	loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				// create intent to the login activity
				Intent intent = new Intent(getActivity(), Login.class);
 				startActivity(intent);

			}
	 	});
	 	
	    return view;
	}
}
