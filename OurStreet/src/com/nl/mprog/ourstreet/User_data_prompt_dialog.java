package com.nl.mprog.ourstreet;


import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
/* What else to do here?
 *  - improve layout of the dialog (lines, nicer buttons, seperator lines)
 *  - Make sure to change to "release key" of facebook before you release it!
 *  
 *  
 *  source: facebook Developer site.
*/  
public class User_data_prompt_dialog extends Fragment {
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	private TextView userInfoTextView;
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
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
	 				// manual signup button
	 				Intent intent = new Intent(getActivity(), ManualDataEntry.class);
	 				startActivity(intent);
	 				getActivity().finish();
	 			
	 			}
	 		});
	 	
	 	Button loginButton = (Button) view.findViewById(R.id.loginbutton);
	 	loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), Login.class);
 				startActivity(intent);

			}
	 		
	 	});
	 	
	 	// initialize authentication button and ask for permissions
	 	// to access data from facebook.
	 	LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	 	authButton.setFragment(this);
	 	authButton.setReadPermissions(Arrays.asList("user_location", "user_birthday", "user_likes"));
	 	
	 	// Textview that displays data
	 	userInfoTextView = (TextView) view.findViewById(R.id.userInfoTextView);

	    return view;
	}
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		
		if (state.isOpened()) {
	        userInfoTextView.setVisibility(View.VISIBLE);
	    } else if (state.isClosed()) {
	        userInfoTextView.setVisibility(View.INVISIBLE);
	    }
		
		
	}
	
	private String buildUserInfoDisplay(GraphUser user) {
		StringBuilder userInfo = new StringBuilder("");
		
		// Example: typed access (name)
	    // - no special permissions required
	    userInfo.append(String.format("Name: %s\n\n", 
	        user.getName()));

	    
	    // - requires user_birthday permission
	    userInfo.append(String.format("Birthday: %s\n\n", 
	        user.getBirthday()));

	    // Example: partially typed access, to location field,
	    // name key (location)
	    // - requires user_location permission
	    userInfo.append(String.format("Location: %s\n\n", 
	        user.getLocation().getProperty("name")));

	    // Example: access via property name (locale)
	    // - no special permissions required
	    userInfo.append(String.format("Locale: %s\n\n", 
	        user.getProperty("locale")));
		 
		return userInfo.toString();
	}
	    
	private interface MyGraphLanguage extends GraphObject {
	    // Getter for the ID field
	    String getId();
	    // Getter for the Name field
	    String getName();
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
}
