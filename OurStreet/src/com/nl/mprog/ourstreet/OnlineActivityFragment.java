package com.nl.mprog.ourstreet;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class OnlineActivityFragment extends Fragment {
	protected Activity mActivity;
	
	// for future queries
	private ParseQuery<ParseObject> query;
	
	// for the logout and status button
	private Button logoutButton;
	private Button statusButton;
	
	// check if user status is online
	private boolean online;
 
    @Override
    public void onAttach(Activity act)
    {
        super.onAttach(act);
        mActivity = act;
    }
	   
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
    }
    
    @Override
    public void onActivityCreated(Bundle saveInstanceState)
    {
        super.onActivityCreated(saveInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	Parse.initialize(getActivity(), "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6",
    			"nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
        View view = inflater.inflate(R.layout.onlineactivitylayout,
        		container, false);
        
        // set online to default "false"
        online = false;
        
        // implement change status button
        statusButton = ((Button) view.findViewById(R.id.changestatus));
        statusButton.setOnClickListener(new OnClickListener (){

			@Override
			public void onClick(View v) {
				// iterate through "Friends" object.
				query = ParseQuery.getQuery("Friends");
				query.whereContains("from", ParseUser.getCurrentUser().getUsername());
				
				query.findInBackground( new FindCallback<ParseObject>(){

					@Override
					public void done(List<ParseObject> arg0, ParseException arg1) {
						
						if(online == false){
							try{
								// iterate through list and change status to online
								for(int l = 0; l < arg0.size(); l++){
									ParseObject link = arg0.get(l);
									link.put("status", "online");
									link.saveInBackground();
									online = true;
							
								}
							}catch (Exception e){
								
							}
						}else{
							try{ 
								
								// iterate through list and change status to offline
								for(int l = 0; l < arg0.size(); l++){
									ParseObject link = arg0.get(l);
									link.put("status", "offline");
									link.saveInBackground();
									online = false;
								}
							}catch(Exception e){
								
							}
					}
					}
				});
			}
        });
        
        // set logoutButton
        logoutButton = ((Button) view.findViewById(R.id.logoutbutton));	
        logoutButton.setOnClickListener(new OnClickListener() {
        	
        	  public void onClick(View v) {
        		  
        		// set status to offline
        		try{
        			
        			setToOffline();
        	    
        		}catch(Exception e){
        			
        		}
        		  
        		// logout and return to login screen
        	    Intent intent = new Intent(getActivity(), FirstActivity.class);
        	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        	    startActivity(intent);
        	    ParseUser.getCurrentUser();
				ParseUser.logOut();
        	    getActivity().finish();
        	  }

			private void setToOffline() {
				
				// start a query
				query = ParseQuery.getQuery("Friends");
				query.whereContains("from", ParseUser.getCurrentUser().getUsername());
				
				query.findInBackground(new FindCallback<ParseObject>(){

					@Override
					public void done(List<ParseObject> arg0, ParseException arg1) {
						
						try{
							
							// iterate through list and change status to online
							for(int l = 0; l < arg0.size(); l++){
								ParseObject link = arg0.get(l);
								link.put("status", "offline");
								link.saveInBackground();
							
							}
							
						}catch(Exception e){
							
						}

					}
					
				});
        		}
        	});
        
        return view;
    }
}
