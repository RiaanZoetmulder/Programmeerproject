package com.nl.mprog.ourstreet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

/* What else to implement
 * - shared preferences
 * - when a user exits it remembers where the user last was
 * - Optionally: animations for transitions between pages
 * 
*/ 

public class MainActivity extends FragmentActivity   {
    private TabsPagerAdapter mTabsPagerAdapter;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    
    public static FragmentManager fragmentManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        
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
    }
}
