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
import cmuHCI.WalkyScotty.entities.Location;
import cmuHCI.WalkyScotty.entities.Room;

public class RoomInfo extends WSActivity{
	
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roominfo);
        
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
				RoomInfo.this.startActivity(new Intent(RoomInfo.this, DirectionsMainActivity.class));
			}
        	
        });
                
        Button nearby = (Button) findViewById(R.id.BakerHall_Nearby_Button);
        
        nearby.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(RoomInfo.this, BakerNearby.class);
				i.putExtra("lID", getIntent().getIntExtra("lID", 1));
				RoomInfo.this.startActivity(i);
			}
        	
        });
    }
    
    private void loadDetails(int locID){
    	Room loc;
    	DBAdapter adp = new DBAdapter(this);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adp.openDataBase();
		
		loc = adp.getRoom(locID); 
		
		adp.close();
		
		TextView title = (TextView) findViewById(R.id.BakerHallText);
		title.setText(loc.getName());
		
		TextView nick = (TextView) findViewById(R.id.BuildingNick);
        nick.setText(loc.getAbbreviation());
		
		TextView desc = (TextView) findViewById(R.id.BakerHallDes);
        desc.setText(loc.getDescription());
        
        TextView crumb = (TextView) findViewById(R.id.bakerinfo_breadcrumb_building);
        crumb.setText(loc.getName());
    }

}