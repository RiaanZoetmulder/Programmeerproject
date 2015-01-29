package com.nl.mprog.ourstreet;

/* Author: Riaan Zoetmulder
 * Project: Ourstreet			Date: 27-01-2015
 * Description: This activity allows users to look check which neighbours
 * have made a friendrequest
 * 
 * 
 * Sources: http://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
*/

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class RequestList extends Activity {
	
	// create listview, object to store listitems
	// and an integer to remember position of selected
	// listitem
	private ListView listview;
	List<ParseObject> tempList;
	private int selectedListItem;
	
	// declare the buttons
	private Button accept;
	private Button deny;
	private Button goBack;
	
	// create object for joined table
	private ParseObject linkBack;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.requestlistlayout);
		Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", 
				"nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
		
		// set Adapter
		final ArrayAdapter<CharSequence> listAdapter = 
				new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1);
		listview = (ListView)findViewById(android.R.id.list);
		listview.setAdapter(listAdapter);
		
		// ensure no item in list is selected
		selectedListItem = -1;
		
		// set up the query on the Friends table
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
		query.whereEqualTo("to", ParseUser.getCurrentUser().getUsername().toString());
		query.whereEqualTo("accepted", "pending");
		 
		// execute the query
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				
				// save the list of requests in a seperate list
				tempList = arg0;
				try{
					// iterate over users
					for (int i = 0; i < arg0.size(); i ++){
					
						// get object from list and display the name
						ParseObject friend = arg0.get(i);
						listAdapter.add(friend.get("from").toString());
					}
				}catch(Exception e){
					
				}
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// when clicked keep selected and store current position
				for (int k = 0; k < parent.getChildCount(); k++){
				parent.getChildAt(k).setBackgroundColor(Color.TRANSPARENT);
				}
				
				view.setBackgroundColor(Color.BLUE);
				selectedListItem = position;
			}
		});
		accept = (Button) findViewById(R.id.accept);
		accept.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// when accept is clicked and list item is selected
				// accept
				if(selectedListItem != - 1){
					ParseObject clickedOn = tempList.get(selectedListItem);
					
					// change on variable
					clickedOn.put("accepted", "accepted");
					clickedOn.saveInBackground();
					
					linkBack = new ParseObject("Friends");
					
					// create the link to the other user. 
					linkBack.put("from", ParseUser.getCurrentUser().getUsername());
					linkBack.put("to", clickedOn.get("from"));
					linkBack.put("accepted", "accepted");
					linkBack.put("streetname", ParseUser.getCurrentUser().get("streetname"));
					linkBack.put("housenumber", ParseUser.getCurrentUser().get("housenumber"));
					linkBack.put("city", ParseUser.getCurrentUser().get("city"));
					linkBack.put("status", "offline");
					linkBack.put("latitude", ParseUser.getCurrentUser().get("latitude"));
					linkBack.put("longitude", ParseUser.getCurrentUser().get("longitude"));
					
					linkBack.saveInBackground();
					
					// delete from list
					ParseObject friend = tempList.get(selectedListItem);
					listAdapter.remove(friend.get("from").toString());
					
					selectedListItem= -1;
					
				}
			}
		});
		deny = (Button) findViewById(R.id.deny);
		deny.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// if deny is clicked, reject individual.
				
				if(selectedListItem != - 1){
					ParseObject clickedOn = tempList.get(selectedListItem);
					
					// change variable
					clickedOn.put("accepted", "rejected");
					clickedOn.saveInBackground();
					
					ParseObject friend = tempList.get(selectedListItem);
					listAdapter.remove(friend.get("from").toString());
					
					// ensure item is deselected
					selectedListItem= -1;
					
				}
			}
		});
		
		// return button
		goBack = (Button) findViewById(R.id.back);
		goBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// finish current activity
				finish();
			}
		});
	}
}
