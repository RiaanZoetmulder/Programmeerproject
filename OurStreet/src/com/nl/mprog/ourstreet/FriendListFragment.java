package com.nl.mprog.ourstreet;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class FriendListFragment extends ListFragment {
	protected Activity mActivity;
	
	
	private ListView friendsList;
	private ParseObject friend;
	 
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
        
        // create Friendlist and populate it
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_list_item_1);
        friendsList = (ListView)getActivity().findViewById(android.R.id.list);
        friendsList.setAdapter(adapter);
        
        // get all accepted friendships
    	// TODO: get online/Offline statuses.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
		query.whereEqualTo("to", ParseUser.getCurrentUser().getUsername().toString());
		query.whereEqualTo("accepted", "accepted");
        
		// execute the query
     	query.findInBackground(new FindCallback<ParseObject>() {

     		@Override
     		public void done(List<ParseObject> arg0, ParseException arg1) {
     				
     				// iterate over users
     				for (int i = 0; i < arg0.size(); i ++){
     					
     					// get object from list and display the name
     					ParseObject friend = arg0.get(i);
     					adapter.add(friend.get("from").toString());
     				}
     			}
     		});
        
    }
 

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friendlistlayout, container, false);

        // initialize buttons
        Button chat = (Button) view.findViewById(R.id.chat);
        Button add = (Button) view.findViewById(R.id.add);
        Button requests = (Button) view.findViewById(R.id.requests);
        
        // Make Buttons responsive
        chat.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// not implemented as chat.
        		
				
			}
		});
        
        add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), AddFriendActivity.class);
				startActivity(i);
			}
		});
        
        requests.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// implement soon!
				// temporary code to RequestList
				
				Intent intent = new Intent(getActivity(), RequestList.class);
				startActivity(intent);
			}
		});
        
        
        return view;
    }
	
}
