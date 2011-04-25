package cmuHCI.WalkyScotty.entities;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

import cmuHCI.WalkyScotty.DBAdapter;

public class Building extends Location{

	private ArrayList<Room> rooms;
	
	public Building(int ident, String nm, String desc, String image, String abbr) {
		super(ident, nm, desc, image, abbr);
		// TODO Auto-generated constructor stub
		rooms = new ArrayList<Room>();
	}
	
	public void loadRooms(Context mContext){
		DBAdapter adp = new DBAdapter(mContext);
		
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		adp.openDataBase();

		adp.getRooms(this);
		
		adp.close();
		
	}

}
