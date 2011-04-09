package cmuHCI.WalkyScotty.entities;

import java.util.ArrayList;
import java.util.Date;

public class DirectionList{
	private ArrayList<String> steps;
	private Location from;
	private Location to;
	private Date time;
	private int distance;
	
	public DirectionList(Location dFrom, Location dTo, Date dTime, int dDistance, ArrayList<String> dSteps){
		from = dFrom;
		to = dTo;
		time = dTime;
		distance = dDistance;
		steps = dSteps;
	}

	public ArrayList<String> getSteps() {
		return steps;
	}

	public Location getFrom() {
		return from;
	}

	public Location getTo() {
		return to;
	}

	public Date getTime() {
		return time;
	}

	public int getDistance() {
		return distance;
	}
	
	
	
}
