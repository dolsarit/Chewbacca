package cmuHCI.WalkyScotty.entities;

public class Service extends Location{
	protected String stops;
	protected String hours;
	private int telephone;
	
	public Service(int ident, String nm, String desc, String image, String abbr, int tel) {
		super(ident, nm, desc, image, abbr);
		// TODO Auto-generated constructor stub
		this.setlType("Service");
		telephone = tel;
	}
	
	public int getTelephone() {
		return telephone;
	}

	public String getStops() {
		return stops;
	}

	public String getHours() {
		return hours;
	}

}
