package com.nl.mprog.ourstreet;


/* Author: Riaan Zoetmulder
 * Project: Ourstreet			Date: 27-01-2015
 * Description: Creates a Viewpager that allows 
 * the Mapfragment, onlineactivityFragment and
 * FriendlistFragment to be displayed.
 * 
 * Also creates a slidingTablayout.
 * 
 * Sources: Google developer site for TabsPagerAdapter
 * 
*/

import com.parse.Parse;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity   {
	
	// create TabspagerAdapter, ViewPager and Slidingtablayout
    private TabsPagerAdapter mTabsPagerAdapter;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    
    // create the fragmentManager
    public static FragmentManager fragmentManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", "nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
        
        // initialize adapter and ViewPager
        mTabsPagerAdapter =
                new TabsPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mTabsPagerAdapter);
        
        // set current viewpager page to middle one
        mViewPager.setCurrentItem(1);
        
        // initialize Sliding tabs
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        
        // create a fragmentmanager mainly for google maps
        fragmentManager = getSupportFragmentManager();
        
    }
    public void onDestroy(){
    	super.onDestroy();
    }
}
