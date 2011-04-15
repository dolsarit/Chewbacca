package cmuHCI.WalkyScotty.entities;

public enum LocationType {
	BUILDINGS(0), RESTAURANTS(1), ROOMS(2);
	
	private int type; 
	
	private LocationType(int c){
		type = c;
	}
	
	public static LocationType fromInt(int c){
		switch(c){
			case 0:
				return BUILDINGS;
			case 1:
				return RESTAURANTS;
			case 2:
				return ROOMS;
			default:
				return BUILDINGS;
		}
	}
	
	public int getLocType(){
		return type;
	}
}
