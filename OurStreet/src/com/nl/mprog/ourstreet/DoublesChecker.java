package com.nl.mprog.ourstreet;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class DoublesChecker {
	private ParseUser mUser;
	private String mFullname;
	private String[] mFriends;
	private ParseQuery<ParseObject> mQuery;
	private String mCheckName;
	private boolean mCheck;
	
	// constructor
	public DoublesChecker(ParseUser user){
		
		mUser = user;
		mFullname = ParseUser.getCurrentUser().getUsername();
		mQuery = ParseQuery.getQuery("Friends");
	}
	
	public void setCheckName(String name){
		mCheckName = name;
	}
	
	public boolean toCheck(){
		mCheck = false;
		
		// query for friends
		mQuery.whereEqualTo("to", mFullname);
		mQuery.whereEqualTo("accepted", "accepted");
		mQuery.findInBackground(new FindCallback<ParseObject>() {

     		@Override
     		public void done(List<ParseObject> arg0, ParseException arg1) {
     				
     				// iterate over users
     				for (int i = 0; i < arg0.size(); i++){
     					
     					// get object from list and display the name
     					ParseObject friend = arg0.get(i);
     					mFriends[i] = friend.get("from").toString();
     				}
     				
     				// check for Duplicates
     				for (int j = 0; j < mFriends.length; j ++){
     					if( mFriends[j].equals(mCheckName)){
     						mCheck = true;
     					}
     				}
     			}
     		});
		return mCheck;
		
	}
	

}
