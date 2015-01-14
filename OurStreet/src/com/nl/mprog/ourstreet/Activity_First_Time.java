package com.nl.mprog.ourstreet;

import java.util.concurrent.TimeUnit;

import android.support.v7.app.ActionBarActivity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
/* What else to implement here:
 *  - shared preferences
 *  - a logo in the middle of the screen
 *  - delayed popping up of dialog
 *  - if dialog is terminated without info, restart it.
 * 
*/
public class Activity_First_Time extends ActionBarActivity {
	
	//TODO: create sharedprefs!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__first__time);
        
        // create dialogbox
        // TODO: check whether sharedpreferences have already been implemented
        DialogFragment thedialog = new User_data_prompt_dialog();
        thedialog.show(getFragmentManager(), "whatever");
    }

}

