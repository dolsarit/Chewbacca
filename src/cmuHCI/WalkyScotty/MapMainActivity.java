package cmuHCI.WalkyScotty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapMainActivity extends MapActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        
        MapController mc = mapView.getController();
        mc.setCenter(new GeoPoint(40443110, -79943450)); // CMU Campus
        mc.setZoom(17); // Zoom just enough to see the entirety of main campus
        	
    }
    
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
    
    @Override
    public void onRestart() {
    	super.onRestart();
    	
    	redrawMap();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	redrawMap();
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == DIRECTIONS_RESULT && resultCode == 0) {
	    	redrawMap();	
    	}
    }

    private void redrawMap() {
    	
    	MapView mapView = (MapView) findViewById(R.id.mapview);
    	
    	if (MapSingleton.overlayOn) {
			
			GeoPoint p1 = new GeoPoint(40441320, -79944290);
			GeoPoint p2 = new GeoPoint(40442250, -79943770);
			GeoPoint p3 = new GeoPoint(40442920, -79942350);
			
			ArrayList<GeoPoint> points = new ArrayList<GeoPoint>(3);
			
			points.add(p1);
			points.add(p2);
			points.add(p3);
			
			RouteOverlay overlay = new RouteOverlay(points);
			
			List<Overlay> overlays = mapView.getOverlays();
			overlays.clear();
			overlays.add(overlay);
			
			mapView.invalidate();
    	} else {
    		List<Overlay> overlays = mapView.getOverlays();
			overlays.clear();
			
			mapView.invalidate();
    	}
    }
    
protected final int DIRECTIONS_RESULT = 1;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater mi = getMenuInflater();
    	mi.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menu_directions:
    		startActivityForResult(new Intent(this, DirectionsMainActivity.class), DIRECTIONS_RESULT);
    		return true;
    	case R.id.menu_help:
    		startActivity(new Intent(this, HelpActivity.class));
    		return true;
    	case R.id.menu_map:
    		startActivity(new Intent(this, MapMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    		return true;
    	case R.id.menu_search:
    		startActivity(new Intent(this, SearchActivity.class));
    		return true;
    	default:
        	return super.onOptionsItemSelected(item);
    	}
    }
    
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}