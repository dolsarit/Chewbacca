package cmuHCI.WalkyScotty;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

class RouteOverlay extends com.google.android.maps.Overlay {
	private List<GeoPoint> locations;
	
	public RouteOverlay(List<GeoPoint> locations) {
		this.locations = new ArrayList<GeoPoint>(locations);
	}
	
	@Override
    public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
    {
        super.draw(canvas, mapView, shadow);                   

        float[] pts = new float[4 * locations.size() - 4];
        
        int i = 0;
        
        for (GeoPoint p : locations) {
        	Point out = new Point();
        	mapView.getProjection().toPixels(p, out);
        	if (i != 0 && i < 4 * locations.size() - 6) {
        		pts[i++] = out.x;
        		pts[i++] = out.y;
        	}
        	pts[i++] = out.x;
    		pts[i++] = out.y;
        }
        
        Paint p = new Paint();
        p.setColor(Color.GREEN);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        
        canvas.drawLines(pts, p);
        return true;
    }
}
