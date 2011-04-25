package cmuHCI.WalkyScotty.entities;

public enum LocationType {
	BUILDINGS(0), RESTAURANTS(1), ROOMS(2), SERVICES(3), OTHER(4), SHUTTLES(5), ESCORTS(6), ;
	
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
			case 3:
				return SERVICES;
			case 4:
				return OTHER;
			case 5:
				return SHUTTLES;
			case 6:
				return ESCORTS;
			default:
				return BUILDINGS;
		}
	}
	
	public int getLocType(){
		return type;
	}
}
