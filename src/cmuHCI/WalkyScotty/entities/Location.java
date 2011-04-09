package cmuHCI.WalkyScotty.entities;

public class Location {
	private int id;
	private String name;
	private String description;
	private String img;
	
	public Location(int ident, String nm, String desc, String image){
		id = ident;
		name = nm;
		description = desc;
		image = img;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getImg() {
		return img;
	}

	
}
