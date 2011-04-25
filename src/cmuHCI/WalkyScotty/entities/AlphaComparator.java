package cmuHCI.WalkyScotty.entities;

import java.util.Comparator;

public class AlphaComparator implements Comparator<Location> {

	@Override
	public int compare(Location arg0, Location arg1) {
		// TODO Auto-generated method stub
		return arg0.getName().compareTo(arg1.getName());
	}
	
	
}