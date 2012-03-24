	package org.bechu.android;
	
	import android.app.Activity;
	import android.os.Bundle;
	import android.view.View;
	import android.view.Window;
	import android.view.WindowManager;
	import android.widget.Button;
import android.widget.TextView;
	
	public class RemoteFaceActivity extends Activity {
		
		private FaceView faceView;
		private Thread sThread = null;
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	     	requestWindowFeature(Window.FEATURE_NO_TITLE);
	   	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    	   
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.main);
	        faceView = (FaceView)findViewById(R.id.FaceView);
	        if(sThread == null)
	        {
	        	sThread = new Thread(new PainterService(faceView));
	        	sThread.start();
	        }
	        
	    }

	}
