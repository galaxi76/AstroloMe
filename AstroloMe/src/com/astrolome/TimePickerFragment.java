package com.astrolome;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements OnTimeSetListener{

	int hourOfDay;
	int minute;
	String callingActivity;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
//		Locale locale = Locale.getDefault();
		final Calendar c = Calendar.getInstance();
		Bundle timeArgs = this.getArguments();
		callingActivity = timeArgs.getString("callingActivity");
		Long previousTime = timeArgs.getLong("setTime");
		hourOfDay= Integer.parseInt(timeArgs.getString("hourString"));
		minute = Integer.parseInt(timeArgs.getString("minString"));
		
		if (previousTime != 0) {
			if(hourOfDay==0){
				c.setTimeInMillis(previousTime);
				hourOfDay = c.get(Calendar.HOUR_OF_DAY);
				minute = c.get(Calendar.MINUTE);
			}
		}
		else{
			if(hourOfDay==0){
				hourOfDay = 12;
				minute = 00;
			}
		}
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hourOfDay, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		if (view.isShown()) {

			String curTime = String.format("%02d:%02d", hourOfDay, minute);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			
	        Date date = new Date();
	        try {
	           sdf.setLenient(true);
	           
	           date = sdf.parse(curTime);
	        } catch (ParseException e) {
	        	e.printStackTrace();
	        }
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
			
	        if(callingActivity == "RegisterActivity"){
		
				((RegisterActivity) getActivity()).populateSetTime(date, hourOfDay, minute);
			}
			else{
				((SettingsActivity)getActivity()).populateSetTime(date, hourOfDay, minute);
			}
		}
	}

}
