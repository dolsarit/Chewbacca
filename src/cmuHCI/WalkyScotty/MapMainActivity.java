package cmuHCI.WalkyScotty;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class MapMainActivity extends MapActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        Button searchButton = (Button) findViewById(R.id.Map_search_button);
        
        searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				MapMainActivity.this.startActivity(new Intent(MapMainActivity.this, SearchActivity.class));
			}
        	
        });
        
        Button directionsButton = (Button) findViewById(R.id.Map_directions_button);
        
        directionsButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view) {
        		MapMainActivity.this.startActivity(new Intent(MapMainActivity.this, DirectionsMainActivity.class));
        	}
        });
        
        Button helpButton = (Button) findViewById(R.id.Map_help_button);
        
        helpButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view) {
        		MapMainActivity.this.startActivity(new Intent(MapMainActivity.this, HelpActivity.class));
        	}
        });
        
        // Do Nothing for Map button (obviously)
        
		DBAdapter adp = new DBAdapter(this);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adp.openDataBase();
		
		adp.getBuildings(); //Spits out an arraylist of buildings
		
		adp.close();
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}