package cmuHCI.WalkyScotty.entities;

public class Shuttle extends Service{

	public Shuttle(int ident, String nm, String desc, String image, String abbr, String sh_stops, String hrs) {
		super(ident, nm, desc, image, abbr);
		// TODO Auto-generated constructor stub
		stops = sh_stops;
		hours = hrs;
	}

	
	
}
