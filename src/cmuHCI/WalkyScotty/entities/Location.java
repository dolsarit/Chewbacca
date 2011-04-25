package cmuHCI.WalkyScotty.entities;

import java.util.Comparator;

public class Location implements Comparable<Location>{
	private int id;
	private String name;
	private String description;
	private String img;
	private String abbreviation;
	
	private LocationType lType;
	
	public Location(int ident, String nm, String desc, String image, String abbr){
		id = ident;
		name = nm;
		description = desc;
		img = image;
		abbreviation = abbr;
		lType = lType.BUILDINGS;
	}
	
	public LocationType getlType() {
		return lType;
	}

	public void setlType(String lType) {
		if (lType == null)
			return;
		
		if(lType.equals("Food"))
			this.lType = LocationType.RESTAURANTS;
		if(lType.equals("Room"))
			this.lType = LocationType.ROOMS;
		if(lType.equals("Shuttle"))
			this.lType = LocationType.SHUTTLES;
		if(lType.equals("Escort"))
			this.lType = LocationType.ESCORTS;
		if(lType.equals("Service"))
			this.lType = LocationType.SERVICES;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		if(name == null)
			return "";
		return name;
	}

	public String getDescription() {
		if(description == null)
			return "";
		return description;
	}

	public String getImg() {
		return img;
	}

	@Override
	public int compareTo(Location another) {		
		return Integer.valueOf(this.id).compareTo(Integer.valueOf(another.id));
	}
	
	@Override
	public boolean equals(Object that){
		return (that instanceof Location) && compareTo((Location)that) == 0;
	}
	
	@Override
	public int hashCode(){
		return this.getId();
	}
	
}

