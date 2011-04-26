package cmuHCI.WalkyScotty.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class MyDirectedGraph<V extends Comparable<V>,E extends Edge<V>> implements Graph<V,E>
{
	// Map each vertex to a map from its neighbors to the edges between them
	private Map<V, Map<V, E>> myGraph;
	
	/**
     * Creates an empty graph (a graph with no vertices or edges).
     */
    public MyDirectedGraph()
    {
    	myGraph = new HashMap<V, Map<V, E>>();
    }
    
    /**
     * Creates a graph with pre-defined vertices.
     * 
     * @param vertices 
     *             The list of the vertices in the graph.
     * @throws NullPointerException 
     *             If vertices is null, or contains null items
     */
    public MyDirectedGraph (Collection<? extends V> vertices)
    {
    	myGraph = new HashMap<V, Map<V, E>>();
        addVertices(vertices);
    }
    
    /**
     * Creates a graph with pre-defined edges and vertices.
     * 
     * @param vertices
     *             A list of the vertices in the graph.
     * @param edges
     *             A list of edges for the graph.
     * @throws IllegalArgumentException 
     *             If edges contains invalid edges.
     * @throws NullPointerException
     *          If vertices or edges is null, or either contain null items
     */
    public MyDirectedGraph (Collection<? extends V> vertices, Collection<? extends E> edges)
    {
    	myGraph = new HashMap<V, Map<V, E>>();
        addVertices(vertices);
        addEdges(edges);
    }
    
    /**
     * Copy Constructor
     * 
     * @param g
     *            A graph to copy
     * @throws IllegalArgumentException if g violates Graph invariants by
     *            returning illegal edges in its outgoingEdge methods
     * @throws NullPointerException
     *             If g is null, or g's methods violates Graph invariants
     *             by returning null items in vertices or outgoingEdges
     */
    public MyDirectedGraph (Graph <V, E> g)
    {
    	if (g == null)
    		throw new NullPointerException();
    	
    	myGraph = new HashMap<V, Map<V, E>>();
        addVertices(g.vertices());
        
        for (V vertex : myGraph.keySet()) {
        	Set<E> outgoingEdges = g.outgoingEdges(vertex);
        	addEdges(outgoingEdges);
        }
    }

	public boolean addVertex(V vertex)
	{
		if (vertex == null)
			throw new NullPointerException();
		
		if (myGraph.containsKey(vertex))
			return false;
		
		myGraph.put(vertex, new HashMap<V, E>());
		return true;
	}
	
    public boolean addVertices(Collection<? extends V> vertices)
    {
        if (vertices == null)
            throw new NullPointerException();
        
        boolean vertexAdded = false;
        
        for (V vertex : vertices)
            vertexAdded = addVertex(vertex) || vertexAdded;
        
        return vertexAdded;
    }
	
	public boolean addEdge (E e)
	{
		if (e == null)
			throw new NullPointerException();
		
		if (!myGraph.containsKey(e.src()) || !myGraph.containsKey(e.dest()))
			throw new IllegalArgumentException();
		
		for (V neighbor : outgoingNeighbors(e.src())) {
			if (neighbor.equals(e.dest()))
				return false;
		}
		
		myGraph.get(e.src()).put(e.dest(), e);
		return true;
	}
	
	public boolean addEdges (Collection<? extends E> edges)
    {
        if (edges == null)
            throw new NullPointerException();
        
        boolean edgeAdded = false;
        
        for (E edge : edges)
            edgeAdded = addEdge(edge) || edgeAdded;
        
        return edgeAdded;
    }

	public boolean removeEdge (V src, V dest)
	{
		if (src == null || dest == null)
			throw new NullPointerException();
		
		if (!myGraph.containsKey(src) || !myGraph.containsKey(dest))
			throw new IllegalArgumentException();
		
		return myGraph.get(src).remove(dest) != null;
	}

	public E connected (V i, V j)
	{
		if (i == null || j == null)
			throw new NullPointerException();
		
		if (!myGraph.containsKey(i) || !myGraph.containsKey(j))
			throw new IllegalArgumentException();
		
		return myGraph.get(i).get(j);
	}
	
	public Set<V> outgoingNeighbors (V vertex)
	{
		if(vertex == null)
			throw new NullPointerException();
		
		if(!myGraph.containsKey(vertex))
			return null;
		
		return myGraph.get(vertex).keySet();
	}
	
	public Set<E> outgoingEdges (V vertex)
	{
		if (vertex == null)
			throw new NullPointerException();
		
		if (!myGraph.containsKey(vertex))
			return null;
		
		return new HashSet<E>(myGraph.get(vertex).values());
	}
	
	public void clearEdges ()
	{
		for (V vertex : myGraph.keySet())
			myGraph.put(vertex, new HashMap<V, E>());
	}

	public Set<V> vertices ()
	{
		return myGraph.keySet();
	}

	// For testing only
	public void printGraph() {
		for (V vertex : vertices()) {
			System.out.println(vertex.toString() + " -> " + myGraph.get(vertex).values().toString());
		}
	}
}

