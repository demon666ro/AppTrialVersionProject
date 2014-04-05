package net.alejandre.apptrialversion;

import net.alejandre.apptrialversion.storeddata.StoredDataManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class InitApp extends Activity {
	
	private StoredDataManager storedDataMngr;
	private int daysToEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_app);
        // instantiate the TextView:
        TextView prompt = (TextView) findViewById(R.id.displayDaysToEnd);
        // 1º - we instantiate our StoredDataManager object:
        storedDataMngr = new StoredDataManager(this);
        // 2º - now we try to initialize if the app is paid to false.
        storedDataMngr.initializePaid();
        // 3º - we check if the app is not paid to continue checking things...
        if(!storedDataMngr.isPaid()) {
        	// 4º we ask if the date of end is set:
        	if(storedDataMngr.isSetDate()) {
        		// 5º - we ask if today is the final day of free trial.
        		if(storedDataMngr.isEndOfTrial()) {
        			daysToEnd = storedDataMngr.DaysToEnd();
        			Toast.makeText(this, "END OF TRIAL VERSION.", Toast.LENGTH_SHORT).show();
        			// TODO - Here you have to send to the user 
        			// to your playStore app premium version or whatever you want to do.
        		} else {
        			// TODO - Here you will send the user to the main activity of your
        			// application. With an Intent. In this example, we save the number of
        			// days to finish the trial.
        			daysToEnd = storedDataMngr.DaysToEnd();
        		}
        	} else {
        		// 5º - if the date is not set ...
        		storedDataMngr.setEndOfTrialDate();
        		// we set the date. You don not have to do it. just make the intent
        		// deleting from here to the first }.
        		storedDataMngr.isSetDate();
        		// TODO - Here you will send the user to the main activity of your
        		// application. With an Intent. In this example, we save the number of
        		// days to finish the trial.
        		daysToEnd = storedDataMngr.DaysToEnd();
        	}
        	// 6º - we put the days in the prompt to see it in the Activity:
        	prompt.setText(daysToEnd+" Trial days.");
        } else {
        	// if we are here, the application is paid ...
        	prompt.setText("This App is paid, Thank you!");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.init_app, menu);
        return true;
    }
    
}
