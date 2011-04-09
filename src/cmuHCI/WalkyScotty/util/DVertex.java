package cmuHCI.WalkyScotty.util;

public class DVertex<V extends Comparable<V>> implements Comparable {
	private V vertex;
	private int distance = (int)Double.POSITIVE_INFINITY;
	
	public DVertex(V ver){
		vertex = ver;
	}
	
	public DVertex(V ver, int dist){
		this(ver);
		distance = dist;	
	}
	
	public V getVertex(){
		return vertex;
	}
	
	public int getDistance(){
		return distance;
	}
	
	public void setDistance(int i){
		distance = i;
	}

	public int compareTo(Object o) {
		if(this.distance < ((DVertex)o).distance)
			return -1;

		if(this.distance > ((DVertex)o).distance)
			return 1;
		
		return 0;
	}
	
	public boolean equals(Object o){
		return vertex.equals(((DVertex)o).getVertex());
	}

}
