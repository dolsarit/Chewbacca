package cmuHCI.WalkyScotty.entities;

public class Location implements Comparable<Location>{
	private int id;
	private String name;
	private String description;
	private String img;
	
	public Location(int ident, String nm, String desc, String image){
		id = ident;
		name = nm;
		description = desc;
		img = image;
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
