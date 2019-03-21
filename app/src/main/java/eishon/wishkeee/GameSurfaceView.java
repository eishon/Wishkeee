package eishon.wishkeee;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Callback {
	
	Context context;
	SurfaceHolder surfaceHolder;
	DrawingThread drawingThread;
	
	float x1,x2,y1,y2;

	public GameSurfaceView(Context context) {
		super(context);
		this.context=context;
		
		surfaceHolder=getHolder();
		surfaceHolder.addCallback(this);
		
		drawingThread=new DrawingThread(context, this);
		
	}
	
	public GameSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
		
		surfaceHolder=getHolder();
		surfaceHolder.addCallback(this);
		
		drawingThread=new DrawingThread(context, this);
	}



	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		
		surfaceHolder=getHolder();
		surfaceHolder.addCallback(this);
		
		drawingThread=new DrawingThread(context, this);
	}



	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			drawingThread.start();
		} catch (Exception e) {
			e.printStackTrace();
			restartThread();
		}
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		drawingThread.stopThread();
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			y1 = event.getY();
			break;

		case MotionEvent.ACTION_UP:
			y2 = event.getY();

			if (y1 < y2) {
				drawingThread.slideFlag=true;
			}

			if (y1 > y2) {
				drawingThread.jumpFlag=true;
				drawingThread.slideFlag=false;
			}
			break;
		default:
			break;
			
		}
		return true;
	}

	
	
	private void restartThread() {
		drawingThread.stopThread();
		drawingThread=null;
		drawingThread=new DrawingThread(context, this);
		drawingThread.start();
		
	}

}
