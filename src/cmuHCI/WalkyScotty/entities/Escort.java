package cmuHCI.WalkyScotty.entities;

public class Escort extends Service {

	public Escort(int ident, String nm, String desc, String image, String sh_stops, String hrs) {
		super(ident, nm, desc, image);
		// TODO Auto-generated constructor stub
		stops = sh_stops;
		hours = hrs;
	}

}
