package cmuHCI.WalkyScotty;

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
        
        Button searchButton = (Button) findViewById(R.id.search_button);
        
        searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MapMainActivity.this.startActivity(new Intent(MapMainActivity.this, SearchActivity.class));
			}
        	
        });
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}