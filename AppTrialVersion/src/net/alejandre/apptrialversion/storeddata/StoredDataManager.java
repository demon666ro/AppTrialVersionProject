package net.alejandre.apptrialversion.storeddata;

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
public class StoredDataManager {
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
	 * Constructor StoredDataManager(Context ActivityInitApp):
	 * This method is called when the object is created.
	 * We instantiate the SharedPreferences and save the Context
	 * of the application which call this object. In this case InitActivity.java
	 * Finally this create a new Calendar with today.
	 */
	public StoredDataManager(Context ActivityInitApp) {
		// saving the Context of the activity we can call the getSharedPreferences method.
		initApp = ActivityInitApp;
		// set the SharedPreferences into our variable:
		sharedPreferences = initApp.getSharedPreferences("datesaved", Context.MODE_PRIVATE);
		// we save today Date:
		today = Calendar.getInstance();
	}
	
	/* --------------------------------------------------------------------
	 * ------- Methods to work with the dates -----------------------------
	 * -------------------------------------------------------------------- */
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
		// CHECK THIS OUT!
		// if the year is the same ...
		if(today.get(Calendar.YEAR) == dateEndOfTrial.get(Calendar.YEAR)) {
			return dateEndOfTrial.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR);
		} else if(today.get(Calendar.YEAR) > dateEndOfTrial.get(Calendar.YEAR)) {
			// if the year of today is biger than the year of trial end ...
			return 0;
		}
		// if the year of today is smaller than the year of trial end ...
		// what we have to do is take how many days of the year we have to left the year ...
		int daysToEndOfYear = 365 - today.get(Calendar.DAY_OF_YEAR);
		// now this days with days of year to the end of trial we have how many days we have to
		// the trial version ends...
		return dateEndOfTrial.get(Calendar.DAY_OF_YEAR) + daysToEndOfYear;
		
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
	
	/* --------------------------------------------------------------------
	 * ------- End of Methods to work with the dates ----------------------
	 * --------------------------------------------------------------------
	 * --------------------------------------------------------------------
	 * ------- Methods to work with the payment data  ---------------------
	 * -------------------------------------------------------------------- */
	
	/**
	 * Method initializePaid():
	 * This method extract the paid value from the SharedPreferences, and
	 * check if it is not set to initialize it to false.
	 * @return
	 */
	public void initializePaid() {
		// we extract the paid value from the sharedPreferences as String.
		String paidSaved = sharedPreferences.getString("paid", "not set");
		// if paid is not set ...
		if(paidSaved.equals("not set")) {
        	// we set it to false:
			setPaid(false);
        }
		
	}
	
	/**
	 * Method isPaid():
	 * This method extract the paid value from the SharedPreferences and
	 * return it.
	 * @return
	 */
	public boolean isPaid() {
		// we extract the paid value from the sharedPreferences as String.
		String paidSaved = sharedPreferences.getString("paid", "not set");
		// we check if it is saved true and return true or,
		// in other case we return false.
		return (paidSaved.equals("true")) ? true : false;
	}
	
	/**
	 * Method setPaid(boolean isPaid):
	 * this method set the paid value in the SharedPreferences.
	 * @param isPaid
	 */
	public void setPaid(boolean isPaid) {
		// we use an Editor (android.content.SharedPreferences.Editor) to storage
		// data in our SharedPreferences:
		Editor editor = sharedPreferences.edit();
		// we save the date in format dd/MM/yyyy.
 		editor.putString("paid", (isPaid) ? "true" : "false" );
 		// and make that change to persist in our SharedPreferences.
 		editor.commit();
	}
	
	
}
