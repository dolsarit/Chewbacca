package cmuHCI.WalkyScotty;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

class RouteOverlay extends com.google.android.maps.Overlay {
	private List<GeoPoint> locations;
	private Context context;
	
	public RouteOverlay(List<GeoPoint> locations, Context context) {
		this.locations = new ArrayList<GeoPoint>(locations);
		this.context = context;
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
        p.setColor(Color.parseColor("#7a7acf"));
        p.setStrokeWidth(4);
        p.setStrokeMiter(100);
        p.setAlpha(127);
        p.setStyle(Paint.Style.STROKE);
        
        canvas.drawLines(pts, p);
        
        Point out = new Point();
        
        Drawable s = context.getResources().getDrawable(R.drawable.map_marker_green);
        mapView.getProjection().toPixels(locations.get(0), out);
        Rect sbounds = new Rect();
        sbounds.set(out.x-9, out.y-34, out.x+9, out.y);
        s.setBounds(sbounds);
        s.draw(canvas);
        
        Drawable f = context.getResources().getDrawable(R.drawable.map_marker_red);
        mapView.getProjection().toPixels(locations.get(locations.size()-1), out);
        Rect fbounds = new Rect();
        fbounds.set(out.x-9, out.y-34, out.x+27, out.y);
        f.setBounds(fbounds);
        f.draw(canvas);
        
        return true;
    }
}
