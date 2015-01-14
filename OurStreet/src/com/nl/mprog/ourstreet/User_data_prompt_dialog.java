package com.nl.mprog.ourstreet;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/* What else to do here?
 *  - improve layout of the dialog (lines, nicer buttons, seperator lines)
 *  - implement OnClicks properly
 *  
*/  
public class User_data_prompt_dialog extends DialogFragment {
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		// create custom layout
		View inflater = getActivity().getLayoutInflater().inflate(R.layout.activity_first_dialog, null);
		builder.setView(inflater);
		
		// create facebookbutton
		Button facebookbutton = (Button) inflater.findViewById(R.id.facebookbutton);
		facebookbutton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// implement soon!
				// temporary code to viewpager
				Intent intent = new Intent(getActivity(), MainActivity.class);
				startActivity(intent);
				
				// destroy activity
				getActivity().finish();
			}
		});
		
		// create manual input button
		Button manualbutton =(Button)inflater.findViewById(R.id.manualinput);
		manualbutton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// experiment, erase later.
				Intent intent = new Intent(getActivity(), TestActivity.class);
				startActivity(intent);
				
				getActivity().finish();
				// implement soon!
			}
		});
		return builder.create();
	}
}
