package com.nl.mprog.ourstreet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FriendListFragment extends ListFragment {
	protected Activity mActivity;
	 
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.list, android.R.layout.simple_list_item_1);
        
        setListAdapter(adapter);
        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friendlistlayout, container, false);
        
        return view;
    }
	
}
