package org.bechu.android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.JetPlayer;
import android.media.JetPlayer.OnJetEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class FaceView extends SurfaceView implements SurfaceHolder.Callback {

	class FaceViewThread extends Thread implements OnJetEventListener {

		HashMap<Integer, Primitive> primitives = new HashMap<Integer, Primitive>();

		public boolean mRun = false;

		private SurfaceHolder mSurfaceHolder;

		ReentrantLock mutex;
		
		public FaceViewThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {
			mSurfaceHolder = surfaceHolder;
			mRun = false;
			mutex = new ReentrantLock();
		}

		public void setRunning(boolean b) {
			mRun = b;
		}

		@Override
		public void onJetEvent(JetPlayer arg0, short arg1, byte arg2,
				byte arg3, byte arg4, byte arg5) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onJetNumQueuedSegmentUpdate(JetPlayer arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onJetPauseUpdate(JetPlayer arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onJetUserIdUpdate(JetPlayer arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		public void run() {
			while (mRun) {
				Canvas c = null;
				try {
					c = mSurfaceHolder.lockCanvas(null);
					doDraw(c);
				} finally {
					if (c != null) {
						mSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}

		}

		public Primitive getPrimitive(Integer key) {
			mutex.lock();
			Set<Integer> keys = primitives.keySet();
			Iterator<Integer> it = keys.iterator();
			while (it.hasNext()) {
				Integer tkey = it.next();
				Primitive p = primitives.get(tkey);
				
				if(tkey.intValue() == key.intValue())
					return p;
			}
			Primitive p = new Primitive();
			primitives.put(key, p);
			return primitives.get(key);
		}
		
		public void removePrimitive(Integer key) {
			mutex.lock();
			Set<Integer> keys = primitives.keySet();
			Iterator<Integer> it = keys.iterator();
			while (it.hasNext()) {
				Integer tkey = it.next();
	
				if(tkey.intValue() == key.intValue())
				{
					primitives.remove(tkey);
					mutex.unlock();
					return;
				}
			}
			mutex.unlock();
		}
		
		public void releasePrimitive() {
			mutex.unlock();
		}
		
		private void doDraw(Canvas canvas) {
			canvas.drawColor(Color.rgb(255, 194, 194));
			mutex.lock();
			Set<Integer> keys = primitives.keySet();
			Iterator<Integer> it = keys.iterator();
			while (it.hasNext()) {
				Integer key = it.next();
				Primitive p = primitives.get(key);
				p.draw(canvas);
			}
			mutex.unlock();
		}

	}

	public FaceViewThread thread;

	public FaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		thread = new FaceViewThread(holder, context, new Handler() {
		});

	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.d("RemoteFace", "surfaceCreated");
		thread.setRunning(true);
		thread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;
		thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;

            } catch (InterruptedException e) {
            }
        }

	}

}
