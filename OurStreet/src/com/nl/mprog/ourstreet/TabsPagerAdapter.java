package com.nl.mprog.ourstreet;
/* Author: Riaan Zoetmulder
 * Project: Ourstreet			Date: 27-01-2015
 * Description: Adapter for the Tabspager.
 * 
 * sources:
 * http://www.androidhive.info/2013/10/android-tab-layout-with-swipeable-views-1/
*/
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// Adapter for Viewpager
public class TabsPagerAdapter extends FragmentPagerAdapter {
	private String[] tabnames = {"Map", "Status","Friends"};
	private int size = 3;
	
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
        return size;
    }

}
