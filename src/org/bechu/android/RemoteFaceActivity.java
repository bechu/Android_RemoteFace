package org.bechu.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class RemoteFaceActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
     	 requestWindowFeature(Window.FEATURE_NO_TITLE);
   	   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
