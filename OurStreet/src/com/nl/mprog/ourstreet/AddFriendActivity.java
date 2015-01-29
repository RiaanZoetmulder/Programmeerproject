package com.nl.mprog.ourstreet;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/* Author: Riaan Zoetmulder
 * Project: Ourstreet			Date: 27-01-2015
 * Description: This activity allows doing a search through the database to find 
 * other users you would like to send a friend request to.
 * 
 * Sources used:
 * 
 * https://www.parse.com/questions/android-parseuser-listview
 * http://stackoverflow.com/questions/17004035/parse-com-how-to-add-a-parseuser-to-current-user-for-a-friendlist
*/
public class AddFriendActivity extends Activity{
	
	// create ParseQuery
	private ParseQuery<ParseUser> query;
	
	// Create Searchbar, listview  and Buttons
	private EditText searchBar;
	private Button search;
	private Button goBack;
	private ListView listview;
	
	// create ParseUsers and list of parseusers
	private List<ParseUser> toRemember;
	private ParseUser currentUser;
	
	// variable to store username and friend ParseObject
	private String username;
	private ParseObject friend;
	
	public void onCreate(Bundle onSavedInstanceState){
		super.onCreate(onSavedInstanceState);
		setContentView(R.layout.addfriendlayout);
		Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", 
				"nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
		
		// initialize searchbar, and 2 buttons
		searchBar = (EditText) findViewById(R.id.searchbar);
		search = (Button) findViewById(R.id.search);
		goBack = (Button) findViewById(R.id.thebackbutton);
		
		// initialize listview
		final ArrayAdapter<CharSequence> listAdapter = 
				new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1);
		listview = (ListView)findViewById(android.R.id.list);
		
		listview.setAdapter(listAdapter);

		search.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				
				// convert the searchBar to string and remove old listItems
				username = searchBar.getText().toString();
				if(toRemember != null){
					emptylist();
				}
				
				// Start the query
				query = ParseQuery.getQuery(ParseUser.class);
				query.whereContains("username", username);
				query.findInBackground( new FindCallback<ParseUser>(){

					@Override
					public void done(List<ParseUser> arg0, ParseException arg1) {
						
						// Attempt to fill the list
						try{
							
							toRemember = arg0;
							
							// iterate over users
							for (int i = 0; i < arg0.size(); i ++){
								
								ParseUser user = arg0.get(i);
								
								Toast.makeText(getApplicationContext(), user.getUsername(),
								Toast.LENGTH_LONG).show();
								
								listAdapter.add(user.getUsername());
							}
							
							ParseUser user = arg0.get(0);
							Toast.makeText(getApplicationContext(), user.getUsername(),
							Toast.LENGTH_LONG).show();
						}catch(Exception e){
							Toast.makeText(getApplicationContext(), "Try again",
									Toast.LENGTH_LONG).show();
						}
					}
				});
			}

			private void emptylist() {
				
				// iterate through arraylist and remove those from previous search
				for(int j = 0; j < toRemember.size(); j++){
					
					ParseUser deletee = toRemember.get(j);
					listAdapter.remove(deletee.getUsername());
					
				}
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// find the user that is being clicked on.
				final ParseUser clickedUser = toRemember.get(position);
				final String Friends = clickedUser.getUsername().toString();
				currentUser = ParseUser.getCurrentUser();
				
					// if the current user is not null, add to join table
					if(currentUser != null){
						
						// declare new object called friend and fill it with data
						friend = new ParseObject("Friends");
						friend.put("from", currentUser.getUsername());
						friend.put("to", Friends);
						friend.put("accepted", "pending");
						friend.put("streetname", currentUser.get("streetname").toString());
						friend.put("housenumber", currentUser.get("housenumber"));
						friend.put("city", currentUser.get("city").toString());
						friend.put("status", "offline");
						friend.put("latitude", currentUser.get("latitude"));
						friend.put("longitude", currentUser.get("longitude"));
						
						// save in database
						friend.saveInBackground();
						
					}
			}
		});
		goBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				// return to viewpager
				finish();
				
			}
		});
	}
}
