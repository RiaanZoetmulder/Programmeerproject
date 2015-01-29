package com.nl.mprog.ourstreet;

/* Author: Riaan Zoetmulder
 * Project: Ourstreet			Date: 27-01-2015
 * Description: This activity allows users to look up their neighbours on a map.
 * 
 * 
 * Sources: http://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
*/

import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {
	
	// create view and google map
	private static View view;
	private static GoogleMap mMap;
	
	// create variables for user and friends location
	private Double[] latitude, longitude;
	private static Double homelatitude, homelongitude;
	
	// store length of friendlist
	private int lengthList;
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Parse.initialize(this.getActivity(), "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", "nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
       
  
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.mapfragmentlayout, container, false);
        if (ParseUser.getCurrentUser() != null){
        	
        		// get location user's home
                homelatitude = (Double) ParseUser.getCurrentUser().get("latitude");
                homelongitude = (Double) ParseUser.getCurrentUser().get("longitude");
        }
                
                // collect data from friends
                gatherData();
                
                // For setting up the MapFragment
                setUpMapIfNeeded(); 

        return view;
    }


	private void gatherData() {
		// get locations from Users
		 	final ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
			query.whereEqualTo("to", ParseUser.getCurrentUser().getUsername().toString());
			query.whereEqualTo("accepted", "accepted");
	        
			// execute the query
	     	query.findInBackground(new FindCallback<ParseObject>() {

	     		@Override
	     		public void done(List<ParseObject> arg0, ParseException arg1) {
	     				try{
	     					lengthList = arg0.size();
	     					latitude = new Double[arg0.size()];
	     					longitude = new Double[arg0.size()];
	     				
	     					// iterate over users
	     					for (int i = 0; i < arg0.size(); i++){

	     						// get object from list and display the name
	     						ParseObject friend = arg0.get(i);
	     						latitude[i] = (Double)friend.get("latitude");
	     						longitude[i] = (Double)friend.get("longitude");
	     					}
	     				}catch(Exception e){
	     					
	     				}
	     				
	     			}
	     		});
	}

	private void setUpMapIfNeeded() {
		
		// Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	    	
	        // Try to obtain the map from the SupportMapFragment.
	        mMap = ((SupportMapFragment) MainActivity.fragmentManager
	                .findFragmentById(R.id.location_map)).getMap();
	        
	        // Check if we were successful in obtaining the map.
	        if (mMap != null)
	            setUpMap();
	    }
		
	}


	private void setUpMap() {
		
		// For showing a move to my loction button
	    mMap.setMyLocationEnabled(true);
	    
	    // For dropping a marker at a point on the Map for users location
	    mMap.addMarker(new MarkerOptions().position(new LatLng(homelatitude, homelongitude)).title("You").snippet("Adress"));
	    
	    // drops several markers 
	    // if you have any friends
	    if(latitude != null && longitude != null){
	    	for (int j = 0; j < lengthList; j++ ){
	    		mMap.addMarker(new MarkerOptions().position(new LatLng(latitude[j], longitude[j])).title("Neighbours").snippet("Adress"));
	    	}
	    }

	    // For zooming automatically to the Dropped PIN Location
	    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(homelatitude,
	            homelongitude), 12.0f));
		
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	   
		
	    if (mMap != null)
	        setUpMap();

	    if (mMap == null) {
	    	
	        // Try to obtain the map from the SupportMapFragment.
	        mMap = ((SupportMapFragment) MainActivity.fragmentManager
	                .findFragmentById(R.id.location_map)).getMap(); 
	        
	        // Check if we were successful in obtaining the map.
	        if (mMap != null)
	            setUpMap();
	    }
	}
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    
	    // remove map if destroyed
	    try{
	    	if (mMap != null) {
	    		MainActivity.fragmentManager.beginTransaction()
	            	.remove(MainActivity.fragmentManager.findFragmentById(R.id.location_map)).commit();
	    		mMap = null;
	    }
	    }catch(Exception e){
	    	System.out.println(e.toString());
	    }
	}
}

 
