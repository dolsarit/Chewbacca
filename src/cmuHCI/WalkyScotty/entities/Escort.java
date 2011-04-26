package cmuHCI.WalkyScotty.entities;

public class Escort extends Service {

	public Escort(int ident, String nm, String desc, String image, String abbr, String sh_stops, String hrs) {
		super(ident, nm, desc, image, abbr, -1);
		// TODO Auto-generated constructor stub
		stops = sh_stops;
		hours = hrs;
		
		this.setlType("Escort");
	}

}
