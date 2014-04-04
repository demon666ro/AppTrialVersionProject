package net.alejandre.apptrialversion;

import net.alejandre.apptrialversion.staticdata.StaticData;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class InitApp extends Activity {
	
	private StaticData staticData;
	private int daysToEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_app);
        // instantiate the TextView:
        TextView prompt = (TextView) findViewById(R.id.displayDaysToEnd);
        // 1º - we instantiate our StaticData object:
        staticData = new StaticData(this);
        // 2º we ask if the date of end is set:
        if(staticData.isSetDate()) {
        	// 3º - we ask if today is the final day of free trial.
        	if(staticData.isEndOfTrial()) {
        		daysToEnd = staticData.DaysToEnd();
        		Toast.makeText(this, "END OF TRIAL VERSION.", Toast.LENGTH_SHORT).show();
        		// TODO - Here you have to send to the user 
        		// to your playStore app premium version or whatever you want to do.
        	} else {
        		// TODO - Here you will send the user to the main activity of your
        		// application. With an Intent. In this example, we save the number of
        		// days to finish the trial.
        		daysToEnd = staticData.DaysToEnd();
        	}
        } else {
        	// 3º - if the date is not set ...
        	staticData.setEndOfTrialDate();
        	// we set the date. You don not have to do it. just make the intent
        	// deleting from here to the first }.
        	staticData.isSetDate();
        	// TODO - Here you will send the user to the main activity of your
    		// application. With an Intent. In this example, we save the number of
    		// days to finish the trial.
    		daysToEnd = staticData.DaysToEnd();
        }
        // 4º - we put the days in the prompt to see it in the Activity:
        prompt.setText(daysToEnd+" Trial days.");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.init_app, menu);
        return true;
    }
    
}
