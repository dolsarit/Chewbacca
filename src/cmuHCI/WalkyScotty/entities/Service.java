package cmuHCI.WalkyScotty.entities;

public class Service extends Location{
	protected String stops;
	protected String hours;
	
	public Service(int ident, String nm, String desc, String image, String abbr) {
		super(ident, nm, desc, image, abbr);
		// TODO Auto-generated constructor stub
	}
	
	public String getStops() {
		return stops;
	}

	public String getHours() {
		return hours;
	}

}
