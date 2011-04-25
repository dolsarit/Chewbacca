package cmuHCI.WalkyScotty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapMainActivity extends WSMapActivity {
	
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
        
		DBAdapter adp = new DBAdapter(this);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adp.openDataBase();
		
		adp.getRestaurants(); //Spits out an arraylist of buildings
		
		adp.close();
		
		
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
	    redrawMap();
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
			
			RouteOverlay overlay = new RouteOverlay(points, this);
			
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
    
}