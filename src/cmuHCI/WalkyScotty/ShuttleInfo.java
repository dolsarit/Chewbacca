package cmuHCI.WalkyScotty;

import java.io.IOException;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cmuHCI.WalkyScotty.entities.Escort;
import cmuHCI.WalkyScotty.entities.Location;
import cmuHCI.WalkyScotty.entities.Restaurant;
import cmuHCI.WalkyScotty.entities.Shuttle;

public class ShuttleInfo extends WSActivity{
	
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuttleinfo);
        
        // Set title of the page
        TextView title = (TextView) findViewById(R.id.BakerHallText);
        title.setText(R.string.BakerHall);
        
        // Add photo to screen
        Drawable myImage = (this.getResources()).getDrawable(DBAdapter.getImage(getIntent().getIntExtra("lID", 1)));
        ImageView photo = (ImageView) findViewById(R.id.BakerHallPhoto);
        photo.setImageDrawable(myImage);
        
        // Set title of the description
        TextView desc = (TextView) findViewById(R.id.BakerHallDes);
        desc.setText(R.string.BakerDes);
        
        loadDetails(getIntent().getIntExtra("lID", 1));
        
        // Set up Button Actions
        
        Button directions = (Button) findViewById(R.id.BakerHall_Direction_Button);
        
        directions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ShuttleInfo.this.startActivity(new Intent(ShuttleInfo.this, DirectionsMainActivity.class));
			}
        	
        });
        
        
        Button nearby = (Button) findViewById(R.id.BakerHall_Nearby_Button);
        
        nearby.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(ShuttleInfo.this, BakerNearby.class);
				i.putExtra("lID", getIntent().getIntExtra("lID", 1));
				ShuttleInfo.this.startActivity(i);
			}
        	
        });
    }
    
    private void loadDetails(int locID){
    	Shuttle loc;
    	DBAdapter adp = new DBAdapter(this);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adp.openDataBase();
		
		loc = adp.getShuttle(locID); 
		
		adp.close();
		
		TextView title = (TextView) findViewById(R.id.BakerHallText);
		title.setText(loc.getName());
		
		TextView desc = (TextView) findViewById(R.id.BakerHallDes);
        desc.setText(loc.getDescription());
        
        TextView nick = (TextView) findViewById(R.id.BuildingNick);
        nick.setText(loc.getAbbreviation());
        
        TextView hours = (TextView) findViewById(R.id.FoodHours);
        hours.setText(loc.getHours());
        
        TextView stops = (TextView) findViewById(R.id.EscortStops);
        stops.setText(loc.getStops());
        
        TextView crumb = (TextView) findViewById(R.id.bakerinfo_breadcrumb_building);
        crumb.setText(loc.getName());
    }

}