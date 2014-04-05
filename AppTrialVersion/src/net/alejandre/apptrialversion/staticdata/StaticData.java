package net.alejandre.apptrialversion.staticdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

// this SuppressLint is for the SimpleDateFormat.
@SuppressLint("SimpleDateFormat") 
public class StaticData {
	// we need to save the Context (android.content.Context) of our activity:
	private Context initApp;
	// we will save on the SharedPreferences as Key=Value (android.content.SharedPreferences) 
	// the date when the app finalize the free trial.
	private SharedPreferences sharedPreferences;
	// we save here the date saved in the SharedPreferences as java.util.Date
	private Calendar dateEndOfTrial;
	// we save the Date of today:
	private Calendar today;
	// days to end the trial version 
	// TODO - you should change this value.
	private static final int TRIAL_DAYS_AVAILED = 7;
	// format of our dates in string.
	private static final SimpleDateFormat Stringformat = 
			new SimpleDateFormat("dd/MM/yyyy");
	
	/**
	 * Constructor StaticData:
	 * This method is called when the object is created.
	 * We instantiate the SharedPreferences and save the Context
	 * of the application which call this object. In this case InitApp.java
	 * Finally this create a new Calendar with today.
	 */
	public StaticData(Context ActivityInitApp) {
		// saving the Context of the activity we can call the getSharedPreferences method.
		initApp = ActivityInitApp;
		// set the SharedPreferences into our variable:
		sharedPreferences = initApp.getSharedPreferences("datesaved", Context.MODE_PRIVATE);
		// we save today Date:
		today = Calendar.getInstance();
	}
	
	/**
	 * Method isSetDate():
	 * This method extract the date from the SharedPreferences, save it,
	 * and returns true if the date is set or false if it is not.
	 * @return
	 */
	public boolean isSetDate() {
		// we extract the date from the sharedPreferences as String.
		String dateSaved = sharedPreferences.getString("date", "no date set");
		// if the date is set ...
		if(!dateSaved.equals("no date set")) {
			// we transform the string Date to a Calendar.
        	TransformStrToDate(dateSaved);
        	return true;
        }
		// if the date is not set in the SharedPreferences we return false.
        return false;
	}
	
	/**
	 * Method TransformStrToDate(String dateSaved):
	 * This method transforms a String to a Calendar.
	 * @param dateSaved - String saved into the SharedPreferences.
	 */
	private void TransformStrToDate(String dateSaved) {
		// format of the date saved into the SharedPreferences.
		// day in two numbers
		// month in two numbers
		// year in four numbers
		try {
			dateEndOfTrial = Calendar.getInstance();
			dateEndOfTrial.setTime(Stringformat.parse(dateSaved));
		} catch (ParseException e) {
			// we can have a Parse Exception trying to parse a bad formated date
			// compared with the saved string into the SharedPreferences.
			e.printStackTrace();
		}
	}

	/**
	 * Method isEndOfTrial(String today):
	 * we compare the date saved into our SharedPreferences
	 * and today date.
	 * @return true - if is it the same day.
	 * @return false - if is not the same day.
	 */
	public boolean isEndOfTrial() {
		// we have not the same day and the day of the end has not passed ...
		if(DaysToEnd() >= 0)
			return false;
		// it is the same day.
		return true;
	}
	
	/**
	 * Method DaysToEnd():
	 * this method returns the days between today and the day when this application
	 * will expire.
	 */
	public int DaysToEnd() {
		// CHECK THIS OUT! - we catch the day of year from end of trial,
		// and we subtract today day of year. (bad english sorry).
		return dateEndOfTrial.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * Method setEndOfTrialDate(String date):
	 * This method set a date generated to the SharedPreferences.
	 */
	public void setEndOfTrialDate() {
		// we get today in a Calendar Object:
		Calendar end = today;
		Log.e("TODAY:",Stringformat.format(end.getTime()));
		// we add the days set from today:
		end.add(Calendar.DATE, TRIAL_DAYS_AVAILED);
		Log.e("END TRIAL DAY:",Stringformat.format(end.getTime()));
		// we use an Editor (android.content.SharedPreferences.Editor) to storage
		// data in our SharedPreferences:
		Editor editor = sharedPreferences.edit();
		// we save the date in format dd/MM/yyyy.
 		editor.putString("date",Stringformat.format(end.getTime()));
 		// and make that change to persist in our SharedPreferences.
 		editor.commit();
	}
	
	
}
