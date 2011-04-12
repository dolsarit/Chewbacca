package cmuHCI.WalkyScotty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DirectionsListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directions_list);
		
		TextView tb = (TextView) findViewById(R.id.directions_list_text);
		
		String s = 
				"From: Current Location (Baker Hall) \n"+
				"To: Merson Couryard\n\n" +
				"1. Walk forward (200 feet) \n" +
				"2. Turn right and walk (100 feet) \n" +
				"3. Destination will be on your left";
		
		tb.setText(s);
	
		Button b = (Button) findViewById(R.id.directions_list_goto_map);
		
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MapSingleton.overlayOn = true;
				DirectionsListActivity.this.startActivity(new Intent(DirectionsListActivity.this, MapMainActivity.class));
			}
		});
		
		Button searchButton = (Button) findViewById(R.id.Dir_list_search_button);
        
        searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				DirectionsListActivity.this.startActivity(new Intent(DirectionsListActivity.this, SearchActivity.class));
			}
        	
        });
        
        Button directionsButton = (Button) findViewById(R.id.Dir_list_directions_button);
        
        directionsButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view) {
        		DirectionsListActivity.this.startActivity(new Intent(DirectionsListActivity.this, DirectionsMainActivity.class));
        	}
        });
        
        Button mapButton = (Button) findViewById(R.id.Dir_list_map_button);
        
        mapButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view) {
        		DirectionsListActivity.this.startActivity(new Intent(DirectionsListActivity.this, MapMainActivity.class));
        	}
        });
        
        TextView bc = (TextView) findViewById(R.id.directions_list_breadcrumb);
        bc.setClickable(true);
        
        bc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DirectionsListActivity.this.finish();
			}
        });
	}
	
}
