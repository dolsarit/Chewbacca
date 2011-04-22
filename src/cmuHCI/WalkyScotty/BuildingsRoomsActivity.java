package cmuHCI.WalkyScotty;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cmuHCI.WalkyScotty.entities.Building;
import cmuHCI.WalkyScotty.entities.Room;



public class BuildingsRoomsActivity extends WSActivity{

	private String[] ROOMS;
	private ArrayList<Room> rooms;
	private String building;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildingrooms);
        
        getRooms();
        
        ListView brlv = (ListView) findViewById(R.id.buildingrooms_list);
		brlv.setTextFilterEnabled(true);
		brlv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, ROOMS));
		
		TextView bc = (TextView) findViewById(R.id.buildingrooms_breadcrumb_building);
        bc.setText(building);

    }
	
	private void getRooms(){
		Building b;
		DBAdapter adp = new DBAdapter(this);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adp.openDataBase();
		b = adp.getBuilding(getIntent().getIntExtra("lID", 1));
		
		if(b != null){
			building = b.getName();
			
			rooms = adp.getRooms(b);
			adp.close();
			ROOMS = new String[rooms.size()];
			int i = 0;
			for(Room r: rooms){
				ROOMS[i] = r.getName();
				i++;
			}
		}
		else{
			ROOMS = new String[0];
			rooms = null;
		}
		
		
	}
}
