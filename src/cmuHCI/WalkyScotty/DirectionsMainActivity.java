package cmuHCI.WalkyScotty;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import cmuHCI.WalkyScotty.entities.Building;
import cmuHCI.WalkyScotty.entities.Restaurant;

public class DirectionsMainActivity extends Activity {
	
	AutoCompleteTextView from, to;
    
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.directions);
	     
	     from = (AutoCompleteTextView) findViewById(R.id.directions_from);
	     to = (AutoCompleteTextView) findViewById(R.id.directions_to);
	     
	     // Set up form interactions
	     
	     Button goButton = (Button) findViewById(R.id.directions_go);
	     goButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(DirectionsMainActivity.this, DirectionsListActivity.class);
				//i.putExtra("from", from.getText());
				//i.putExtra("to", to.getText());
				//DirectionsMainActivity.this.setResult(0, i);
				//DirectionsMainActivity.this.finish();
				DirectionsMainActivity.this.startActivity(i);
			}
	     });
	     
	     DBAdapter adp = new DBAdapter(this);
	     try {
	    	 adp.createDataBase();
	     } catch (IOException e) {
	 	// TODO Auto-generated catch block
	    	 e.printStackTrace();
	     }
	     
	     adp.openDataBase();
	     ArrayList<Building> buildingList = adp.getBuildings(); //Spits out an arraylist of buildings
	     ArrayList<Restaurant> restList = adp.getRestaurants();
	     //ArrayList<Room> roomList = adp.getRooms();
	     adp.close();
	     
	     ArrayList<String> stringList = new ArrayList<String>();
	     for (int i=0;i<buildingList.size();i++)
	    	 stringList.add(buildingList.get(i).getName());
	     for (int j=0;j<restList.size();j++)
	    	 stringList.add(restList.get(j).getName());
	     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.autocomplete_list_item, stringList);
	     from.setAdapter(adapter);
	     to.setAdapter(adapter);
	}
}
