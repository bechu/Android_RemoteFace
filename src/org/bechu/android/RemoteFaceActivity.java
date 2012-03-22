	package org.bechu.android;
	
	import android.app.Activity;
	import android.os.Bundle;
	import android.view.View;
	import android.view.Window;
	import android.view.WindowManager;
	import android.widget.Button;
	import android.widget.TextView;
	
	public class RemoteFaceActivity extends Activity implements View.OnClickListener {
	    /** Called when the activity is first created. */
		 private Button mButton;
		 private TextView tv ;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	     	requestWindowFeature(Window.FEATURE_NO_TITLE);
	   	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    	   
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.main);
	        tv = (TextView)findViewById(R.id.test1);	
	        tv.setText("super");
	        mButton = (Button)findViewById(R.id.button1);
	        mButton.setOnClickListener(this);
	       // runTcpServer();
	    }
	    
	    public void onClick(View v) {
	    	tv.setText("trop cool");
	    }

	}
