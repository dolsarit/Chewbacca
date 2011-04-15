package cmuHCI.WalkyScotty;

import java.io.IOException;
import java.util.ArrayList;

import cmuHCI.WalkyScotty.entities.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MoreItemsActivity extends Activity{
	private String[] places = new String[0];
	@Override
    public void onCreate(Bundle savedInstanceState) {
		ArrayList<? extends Location> locations;

		LocationType locType = LocationType.valueOf(getIntent().getStringExtra("which"));
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moreitems);
        
        locations = getLocationList(locType);
        
        ListView brlv = (ListView) findViewById(R.id.more_items_list);
		brlv.setTextFilterEnabled(true);
		brlv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, places));

		
		
		TextView bc = (TextView) findViewById(R.id.more_items_breadcrumb);
        bc.setClickable(true);
        
        TextView title = (TextView) findViewById(R.id.more_items_title);
        bc.setText(locType.toString());
        
        bc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MoreItemsActivity.this.finish();
			}
        });

    }
	
	private ArrayList<? extends Location> getLocationList(LocationType type){
		ArrayList<? extends Location> locs = new ArrayList<Location>();
		
		DBAdapter adp = new DBAdapter(this);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adp.openDataBase();
		
		switch(type){
			case BUILDINGS :
				locs = adp.getBuildings();
				break;
			case ROOMS :
				locs = adp.getRooms();
				break;
			case RESTAURANTS :
				locs = adp.getRestaurants();
				break;
			default:
				locs = adp.getAllLocations();
				break;
		}
		
		adp.close();
		
		places = new String[locs.size()];
		
		int i = 0;
		for(Location l:locs){
			places[i] = l.getName();
			i++;
		}
		return locs;
	}
}
