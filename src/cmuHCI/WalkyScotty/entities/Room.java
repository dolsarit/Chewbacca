package cmuHCI.WalkyScotty.entities;

import java.io.IOException;

import android.content.Context;

import cmuHCI.WalkyScotty.DBAdapter;

public class Room extends Location{
	private Building building;
	
	public Room(int ident, String nm, String desc, String image) {
		super(ident, nm, desc, image);
		// TODO Auto-generated constructor stub
	}
	
	public void loadBuilding(Context mContext) throws Exception{
		DBAdapter adp = new DBAdapter(mContext);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		adp.openDataBase();

		adp.getBuildings();
		
		adp.close();
		
		throw new Exception("Not implemented yet...");
	
	}
	
}