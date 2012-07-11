package org.bechu.android;

import java.io.*;
import java.net.*;

import android.util.Log;


public class PainterService implements Runnable
{
    private ServerSocket server;
    private FaceView render;
    private boolean run4ever = true;
    
    public PainterService(FaceView faceView) {
    	render = faceView;
	}   
 
    public void run() {
    	Log.d("RemoteFace", "PainterService Running ...");
              try {
                  server = new ServerSocket(7777);

              } catch (IOException e) {
            	  Log.d("RemoteFace", "S: Error ServerSocket instanciation");
            	  return ;
              }

            	try {
					server.setSoTimeout(100);
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
             Log.d("RemoteFace", "Server Created...");
              while (run4ever == true) {

                  Socket client = null;
				try {

					client = server.accept();
				} catch (IOException e3) {
					continue;
				}
                  Log.d("RemoteFace", "S: Receiving...");
 
     
                      BufferedReader in;
					try {
						in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	                      String str = in.readLine();
	                      Log.d("RemoteFace", "S: Received: '" + str + "'");
	                      
	            			treatCommand(str);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if(client.isClosed() == false)
							continue;
					}
                  }
              try {
				server.close();
                Log.d("RemoteFace", "S: Closed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    public void kill() {
    	run4ever = false;
        Log.d("RemoteFace", "Run4Ever ending");
    }
	public boolean treatRemoveCommand(String cmd) {
		Integer index = Integer.decode(cmd);
		this.render.thread.removePrimitive(index);
		return true;
	}
	
	public void treatParameter(Primitive p, String param) {
		int i = param.indexOf('(');
		int end = param.indexOf(')');
		if (i == -1 || end == -1)
			return;
		String name = param.substring(0, i);
		String args = param.substring(i+1, end);
		if(name.equals("type")) {
			if(args.equals("circle")) {
				p.type = Primitive.Type.Circle;
			}
			if(args.equals("rectangle")) {
				p.type = Primitive.Type.Rectangle;
			}
			if(args.equals("line")) {
				p.type = Primitive.Type.Line;
			}
			if(args.equals("arc")) {
				p.type = Primitive.Type.Arc;
			}
			if(args.equals("oval")) {
				p.type = Primitive.Type.Oval;
			}
		}
		if(name.equals("color")) {
			String[] splits = args.split(",");
			p.alpha = Integer.decode(splits[0]);
			p.red = Integer.decode(splits[1]);
			p.green = Integer.decode(splits[2]);
			p.blue = Integer.decode(splits[3]);			
		}
		if(name.equals("position")) {
			String[] splits = args.split(",");
			p.x = Integer.decode(splits[0]);
			p.y = Integer.decode(splits[1]);
		}
		if(name.equals("from")) {
			String[] splits = args.split(",");
			p.x = Integer.decode(splits[0]);
			p.y = Integer.decode(splits[1]);
		}
		if(name.equals("to")) {
			String[] splits = args.split(",");
			p.w = Integer.decode(splits[0]);
			p.h = Integer.decode(splits[1]);
		}
		if(name.equals("size")) {
			String[] splits = args.split(",");
			p.w = Integer.decode(splits[0]);
			p.h = Integer.decode(splits[1]);
		}
		if(name.equals("radius")) {
			p.radius = Integer.decode(args);
		}
		if(name.equals("rotate")) {
			p.rotate = Integer.decode(args);
		}
		if(name.equals("thickness")) {
			p.thickness = Integer.decode(args);
		}
		if(name.equals("startAngle")) {
			p.startAngle = Integer.decode(args);
		}
		if(name.equals("sweepAngle")) {
			p.sweepAngle = Integer.decode(args);
		}
		if(name.equals("useCenter")) {
			p.useCenter = (Integer.decode(args) == 1) ? true : false;
		}
		if(name.equals("fill")) {
			p.fill = (Integer.decode(args) == 1) ? true : false;
		}
		
		if(name.equals("oval")) {
			String[] splits = args.split(",");
			p.oval.set(((float)Integer.decode(splits[0])),
					((float)Integer.decode(splits[1])),
					((float)Integer.decode(splits[2])),
					((float)Integer.decode(splits[3])));			
		}
	}
	
	public boolean treatAddCommand(String cmd) {
		String[] splits = cmd.split(" ");
		if(splits.length < 2)
			return false;
		Integer index = Integer.decode(splits[0]);
		Primitive p = this.render.thread.getPrimitive(index);
		for(int i =1;i<splits.length;i++){
			treatParameter( p, splits[i] );
		}
		p.updated();
		this.render.thread.releasePrimitive();
		return false;
	}
	
	public  boolean treatCommand(String cmd) {
		int i = cmd.indexOf(' ');
		String action = cmd.substring(0, i);
		cmd = cmd.substring(i+1, cmd.length());
		if(action.equals("remove"))
			return treatRemoveCommand(cmd);
		if(action.equals("update"))
			return treatAddCommand(cmd);
		return false;
	}
}
