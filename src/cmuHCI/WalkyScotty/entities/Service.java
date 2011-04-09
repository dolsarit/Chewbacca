package cmuHCI.WalkyScotty.entities;

public class Service extends Location{
	protected String stops;
	protected String hours;
	
	public Service(int ident, String nm, String desc, String image) {
		super(ident, nm, desc, image);
		// TODO Auto-generated constructor stub
	}
	
	public String getStops() {
		return stops;
	}

	public String getHours() {
		return hours;
	}

}
