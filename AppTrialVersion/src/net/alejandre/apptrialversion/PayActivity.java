package net.alejandre.apptrialversion;

import net.alejandre.apptrialversion.storeddata.StoredDataManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PayActivity extends Activity {
	
	private StoredDataManager storedDataMngr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_app);
        // instantiate the TextView:
        TextView prompt = (TextView) findViewById(R.id.displayDaysToEnd);
        // 1º - we instantiate our StoredDataManager object:
        storedDataMngr = new StoredDataManager(this);
        // 2º - now we set
        storedDataMngr.setPaid(true);
        prompt.setText("This App has been paid, Thank you!, press back");

    }

    

    @Override
	public void onBackPressed() {
    	// we do it to finish this Activity.
    	finish();
    	// we navigate to the initial activity:
    	Intent intent=new Intent(this,InitActivity.class);
    	startActivity(intent);
		super.onBackPressed();
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.init_app, menu);
        return true;
    }
    
}
