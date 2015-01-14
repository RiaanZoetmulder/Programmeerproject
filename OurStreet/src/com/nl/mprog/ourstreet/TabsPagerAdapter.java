package com.nl.mprog.ourstreet;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// Adapter for Viewpager
public class TabsPagerAdapter extends FragmentPagerAdapter {
	private String[] tabnames = {"Map", "Status","Friends"};
	
	public CharSequence getPageTitle(int position){
		return tabnames[position];
	}
	
	// constructor method
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
    public android.support.v4.app.Fragment getItem(int index)
    {
		// depending on the index return different fragments
        switch (index)
            {
               case 0:
                  return new MapFragment();
               case 1:
                  return new OnlineActivityFragment();
               case 2:
                  return new FriendListFragment();
            }
 
        return null;
    }
 
    @Override
    public int getCount()
    {
    	// return size of total number of Possible Fragments
        return 3;
    }

}
