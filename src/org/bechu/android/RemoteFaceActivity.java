package org.bechu.android;
	
import org.bechu.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
	
public class RemoteFaceActivity extends Activity {
		private FaceView faceView;
		private Thread sThread = null;
		private PainterService painter = null;
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	     	requestWindowFeature(Window.FEATURE_NO_TITLE);
	   	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	   	    super.onCreate(savedInstanceState);

	        setContentView(R.layout.main);
	        faceView = (FaceView)findViewById(R.id.FaceView);
			if(sThread == null)
			{
				painter = new PainterService(faceView);
				sThread = new Thread(painter);
				sThread.start();
			}
		}
		
		@Override
		protected void onDestroy() {

            Log.d("RemoteFace", "Kill app");
			painter.kill();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    super.onDestroy();
		}
}
