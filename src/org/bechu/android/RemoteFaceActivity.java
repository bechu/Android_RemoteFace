	package org.bechu.android;
	
	import android.app.Activity;
	import android.os.Bundle;
	import android.view.View;
	import android.view.Window;
	import android.view.WindowManager;
	import android.widget.Button;
import android.widget.TextView;
	
	public class RemoteFaceActivity extends Activity implements View.OnClickListener {
		
		private FaceView faceView;
		
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	     	requestWindowFeature(Window.FEATURE_NO_TITLE);
	   	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    	   
	        super.onCreate(savedInstanceState);
	        
	        
	        
	        setContentView(R.layout.main);
	        faceView = (FaceView)findViewById(R.id.FaceView);
		    Thread sThread = new Thread(new PainterService(faceView));
	        sThread.start();
	        
	    }
	    
	    public void onClick(View v) {
	    }

	}
