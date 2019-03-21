package eishon.wishkeee;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DisplaySize {
	
	GameSurfaceView gameSurfaceView;
	
	Context context;
	
	private int displayX,displayY;
	
	public DisplaySize(GameSurfaceView gameSurfaceView) {
		this.gameSurfaceView=gameSurfaceView;
		this.context=gameSurfaceView.context;
		
		getDisplay();
	}
	
	private void getDisplay() {
		WindowManager windowManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay=windowManager.getDefaultDisplay();
		
		Point displayDimension=new Point();
		
		defaultDisplay.getSize(displayDimension);
		
		displayX=displayDimension.x;
		displayY=displayDimension.y;
		
	}

	public int getDisplayX() {
		return displayX;
	}

	public int getDisplayY() {
		return displayY;
	}
	
	

}
