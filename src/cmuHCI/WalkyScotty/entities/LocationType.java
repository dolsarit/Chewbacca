package cmuHCI.WalkyScotty.entities;

public enum LocationType {
	BUILDING(0), ROOM(1);
	
	private int type; 
	
	private LocationType(int c){
		type = c;
	}
	
	public static LocationType fromInt(int c){
		switch(c){
			case 0:
				return BUILDING;
			case 1:
				return ROOM;
			default:
				return BUILDING;
		}
	}
	
	public int getLocType(){
		return type;
	}
}
