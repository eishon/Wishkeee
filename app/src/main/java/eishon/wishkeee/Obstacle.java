package eishon.wishkeee;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;

public class Obstacle {
		
	int centerX,centerY;
	int velocityX;
	int displayX,displayY;
	int height,width;
	
	Bitmap obstacleBitmap;
	
	Paint obstaclePaint;

	public Obstacle(Bitmap obstacleBitmap) {
		this.obstacleBitmap=obstacleBitmap;
		
		centerX=0;
		centerY=0;
		velocityX=0;
		
		width=obstacleBitmap.getWidth();
		height=obstacleBitmap.getHeight();
		
		obstaclePaint=new Paint();
		
	}

	public Obstacle(Bitmap obstacleBitmap,int cX,int cY) {
		this(obstacleBitmap);
		centerX=cX;
		centerY=cY;
		
	}
	
	public Obstacle(Bitmap obstacleBitmap,int cX,int cY,int vX) {
		this(obstacleBitmap,cX, cY);
		velocityX=vX;
	}
	
	public void setCenter(Point centerPoint) {
		centerX=centerPoint.x;
		centerY=centerPoint.y;
		
	}
	
	public void setVelocity(int vX) {
		velocityX=vX;
		
	}

}
