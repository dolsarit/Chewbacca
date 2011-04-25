package cmuHCI.WalkyScotty.entities;

public class Restaurant extends Location {
	private String hours;
	private String menu_link;
	
	public Restaurant(int ident, String nm, String desc, String image, String abbr, String hrs, String menu) {
		super(ident, nm, desc, image, abbr);
		// TODO Auto-generated constructor stub
		hours = hrs;
		menu_link = menu;
		this.setlType("Food");
	}

	public String getHours() {
		return hours;
	}

	public String getMenu_link() {
		return menu_link;
	}
	
	
	
}
