package com.astrolome;

import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.widget.DatePicker;
import com.astrolome.RegisterActivity;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	String callingActivity;
	int yy;
	int mm;
	int dd;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	Locale locale = Locale.getDefault();
	final Calendar c = Calendar.getInstance(locale);	
		   //Read the passed bundle from the activity
	Bundle args = this.getArguments();
	callingActivity = args.getString("callingActivity");
	Long currDate = args.getLong("setDate");
	yy= Integer.parseInt(args.getString("yearString"));
	mm = Integer.parseInt(args.getString("monthString"));
	dd = Integer.parseInt(args.getString("dayString"));
	if(currDate!= 0){
		if(yy==0){
			c.setTimeInMillis(currDate);
			yy=c.get(Calendar.YEAR);
			mm = c.get(Calendar.MONTH);
			dd=c.get(Calendar.DAY_OF_MONTH);
		}
	}
	else{
		if(yy==0){
			yy = c.get(Calendar.YEAR);
			mm = c.get(Calendar.MONTH);
			dd = c.get(Calendar.DAY_OF_MONTH);
		}
	}
		
	return new DatePickerDialog(getActivity(), this, yy, mm, dd);
	}

	public void onDateSet(DatePicker view, int yy, int mm, int dd) {
		if (view.isShown()) {
			if(callingActivity == "RegisterActivity"){
			 ((RegisterActivity) getActivity()).populateSetDate(yy, mm, dd);
			}
			else{
				((SettingsActivity)getActivity()).populateSetDate(yy, mm, dd);
			}
		}
	}

}


