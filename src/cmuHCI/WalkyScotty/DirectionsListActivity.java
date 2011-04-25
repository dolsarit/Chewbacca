package cmuHCI.WalkyScotty;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.os.Bundle;
import android.widget.TextView;

public class DirectionsListActivity extends WSMapActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directions_list);
		
		TextView tb = (TextView) findViewById(R.id.directions_list_text);
		
		String s = 
				"1. Walk forward (200 feet) \n" +
				"2. Turn right and walk (100 feet) \n" +
				"3. Destination will be on your left";
		
		tb.setText(s);
		
		TextView bc = (TextView) findViewById(R.id.directionslist_breadcrumb_locations);
		bc.setText("Baker Hall to Merson Ctyd.");
        
        MapView mapView = (MapView) findViewById(R.id.directions_mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        
        MapController mc = mapView.getController();
        mc.setCenter(new GeoPoint(40443110, -79943450)); // CMU Campus
        mc.setZoom(17); // Zoom just enough to see the entirety of main campus
        
        MapSingleton.overlayOn = true;
        
        redrawMap();
	}
	
    private void redrawMap() {
    	
    	MapView mapView = (MapView) findViewById(R.id.directions_mapview);
    	
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
	
}
