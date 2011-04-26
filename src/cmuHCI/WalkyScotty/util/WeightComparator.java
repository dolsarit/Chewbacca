package cmuHCI.WalkyScotty.util;

import java.util.Comparator;

public class WeightComparator implements Comparator<WeightedEdge> {

	@Override
	public int compare(cmuHCI.WalkyScotty.util.WeightedEdge arg0,
			cmuHCI.WalkyScotty.util.WeightedEdge arg1) {
		// TODO Auto-generated method stub
		return Integer.valueOf(arg0.weight).compareTo(Integer.valueOf(arg1.weight));
	}

	
}