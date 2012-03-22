package org.bechu.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class Primitive {
	public enum Type {
		Circle, Rectangle, Arc, Line, Oval;
	}
	public Type type;
	public int x;
	public int y;
	public int red;
	public int green;
	public int blue;
	public int alpha;
	
	public int radius; // case type
	
	public int w;
	public int h;
	
	public int rotate;
	
	public int thickness;
	
	public int startAngle;
	public int sweepAngle;
	public boolean useCenter;
	public RectF oval;
	
	private final Paint mPaint = new    Paint(Paint.ANTI_ALIAS_FLAG);
	
	public Primitive() {
		type = Type.Circle;
		x = 0;
		y = 0;
		red = 255;
		green = 0;
		blue = 0;
		alpha = 255;
		
		radius = 10;
		w = h = 0;
		rotate = 0;
		thickness = 1;
		startAngle = -90;
		sweepAngle = 360;
		useCenter = true;
		oval = new RectF();
		this.updated();
	}
	
	public void updated() {
		mPaint.setARGB(alpha, red, green, blue);
	}
	
	public void draw(Canvas canvas) {
		if(type == Type.Circle)
		{
			canvas.drawCircle(x, y, radius, mPaint);
		}
		if(type == Type.Rectangle)
		{
			canvas.rotate(rotate);
			canvas.drawRect(new Rect(x,y, w, h), mPaint);
			canvas.restore();
		}
		if(type == Type.Line)
		{
			mPaint.setStrokeWidth(thickness);
			canvas.drawLine(x,y, w, h, mPaint);
		}
		if(type == Type.Oval)
		{
			canvas.rotate(rotate);
			canvas.drawOval(new RectF(x,y, w, h), mPaint);
			canvas.restore();
		}
		if(type == Type.Arc)
		{
			//canvas.rotate(rotate);
			mPaint.setStrokeWidth(thickness);
			canvas.drawArc(oval, startAngle, sweepAngle, useCenter, mPaint);
			//RectF rectF = new RectF(50, 20, 100, 80);
			//canvas.drawOval(rectF, mPaint);
			//mPaint.setColor(Color.BLACK);
			//canvas.drawArc (rectF, 90, 15, true, mPaint);
			//Log.d("TCP", "Type.Arc");
			//canvas.restore();
		}
	}
}
