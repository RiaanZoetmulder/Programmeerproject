package com.nl.mprog.ourstreet;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/* What else to implement
 * - shared preferences
 * - when a user exits it remembers where the user last was
 * - Optionally: animations for transitions between pages
 * - Refactor: Make a class for ParseUser, currentuser Data.
*/ 

public class MainActivity extends FragmentActivity   {
    private TabsPagerAdapter mTabsPagerAdapter;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    
    public static FragmentManager fragmentManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        Parse.initialize(this, "3AGy1SAlrM5EzI6udBQTVlNnnGSF0QcB4xuoIBM6", "nhdQQiAfhz51oISQMXWsKD1kaOfhTcksafCUxxC6");
        
    
        ParsePush.subscribeInBackground("", new SaveCallback() {

			@Override
			public void done(ParseException arg0) {
				// TODO Auto-generated method stub
				 if (arg0 == null) {
	        	      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
	        	    } else {
	        	      Log.e("com.parse.push", "failed to subscribe for push", arg0);
	        	    }
			}

        	});
        
        // initialize adapter and ViewPager
        mTabsPagerAdapter =
                new TabsPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mTabsPagerAdapter);
        
        // TODO: make it flexible so that the user can return to whatever page they left
        mViewPager.setCurrentItem(1);
        
        // initialize Sliding tabs
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        
        fragmentManager = getSupportFragmentManager();
    }
}
