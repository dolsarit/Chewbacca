package cmuHCI.WalkyScotty.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

import cmuHCI.WalkyScotty.util.*;

public class Dijkstra
{
	/**
	 * Returns the shortest path on the given graph using Dijkstra's Algorithm
	 * In this case, shortest path is defined as the path for which the total weight is minimized
	 * 
	 * @param g The graph that we are searching for a path on
	 * @param start The vertex that begins the path
	 * @param goal The vertex we are trying to find a path to
	 * @throws IllegalArgumentException if either start or goal is not a vertex in g  
	 * @throws IllegalArgumentException
	 *             if g has negative edge weights.
	 * @return A list of Edges that represent a path between start and goal if one exists
	 * 		   Otherwise, returns null
	 */
	@SuppressWarnings("unchecked")
	public <V extends Comparable<V>,E extends WeightedEdge<V>> Path<V,E> shortestPath(Graph<V,E> g, V start, V goal)
	{
		if(!g.vertices().contains(start) || !g.vertices().contains(goal))
			throw new IllegalArgumentException();
		
		// Check for negative edge weights
		for (V vertex : g.vertices()) {
			for (E edge : g.outgoingEdges(vertex)) {
				if (edge.weight() < 0)
					throw new IllegalArgumentException();
			}
		}
		
		// If start is goal then return empty path
		if(start.equals(goal))
			return new Path<V, E>(start);
		
		// Queue of vertices prioritized by estimated distance from start
		PriorityQueue<DistanceVertex<V>> vertexQueue = new PriorityQueue<DistanceVertex<V>>();
		
		// To keep track of predecessors to reconstruct path
		// Maps destination node to its edge back 
		Map<V, WeightedEdge<V>> edgesBackMap = new HashMap<V, WeightedEdge<V>>();

		// Load all vertices into priority queue
		for(V vertex : g.vertices()) {
			if (vertex.equals(start))
				vertexQueue.offer(new DistanceVertex<V>(vertex, 0));
			else
			    vertexQueue.offer(new DistanceVertex<V>(vertex));
		}
		
		// Pick next node
		while(!vertexQueue.isEmpty()) {
			DistanceVertex<V> currentVertex = vertexQueue.poll();
			List<DistanceVertex<V>> updatedVertices = new LinkedList<DistanceVertex<V>>();
			
			// Check all remaining queued nodes for possible relaxing of edges
			while (!vertexQueue.isEmpty()) {
				DistanceVertex<V> queuedVertex = vertexQueue.poll();
				WeightedEdge<V> connectingEdge = g.connected(currentVertex.getVertex(), queuedVertex.getVertex());
				
				// If node from queue is adjacent to current node
				if (connectingEdge != null) {
					// Relax edge if necessary
					if (currentVertex.getDistance() + connectingEdge.weight() < queuedVertex.getDistance()) {
						queuedVertex.setDistance(currentVertex.getDistance() + connectingEdge.weight());
						edgesBackMap.put(queuedVertex.getVertex(), connectingEdge);
					}
				}
				
				// Saved possibly updated node for insertion back into queue
				updatedVertices.add(queuedVertex);
			}
			
			// Insert all (possibly) updated nodes back into queue
			vertexQueue.addAll(updatedVertices);
		}
		
		// Goal was never reached from traversing edges so no path exists
		if (edgesBackMap.get(goal) == null)
			return null;
		
		// Stack path edges by backtracking from goal 
		Stack<WeightedEdge<V>> pathEdges = new Stack<WeightedEdge<V>>();
		pathEdges.push(edgesBackMap.get(goal));
		while (edgesBackMap.containsKey(pathEdges.peek().src()))
			pathEdges.push(edgesBackMap.get(pathEdges.peek().src()));
		
		// Start was never reached from backtracking so path exists
		if (!pathEdges.peek().src().equals(start))
			return null;
		
		// Build path from stacked edges
		Path<V, E> shortestPath = new Path<V, E>(start);
		while(!pathEdges.isEmpty())
			shortestPath = shortestPath.cons((E)pathEdges.pop());
		
		return shortestPath;
	}
	
	/**
	 * Returns the shortest path on the given graph from start to all nodes using Dijkstra's Algorithm
	 * In this case, shortest path is defined as the path for which the total weight is minimized
	 * 
	 * NOTE: This method is NOT unit tested, but will be helpful to you in the last part!
	 * 
	 * @param g The graph that we are searching for paths
	 * @param start The vertex that begins the path
	 * @throws IllegalArgumentException if start is not a vertex in g  
	 * @throws IllegalArgumentException
	 *             if g has negative edge weights
	 * @return A mapping from all vertices v to lists of paths that begin at start and end at v
	 * 			  if a path from start to v exists
	 * 			  otherwise, that mapping should be null
	 */
	public <V extends Comparable<V>,E extends WeightedEdge<V>> Map<V,Path<V,E>> allShortestPaths(Graph<V,E> g, V start)
	{
		if (!g.vertices().contains(start))
			throw new IllegalArgumentException();
		
		Map<V, Path<V, E>> shortestPaths = new HashMap<V, Path<V,E>>();
		shortestPaths.put(start, null);

		for (V vertex : g.vertices()) {
			if (!shortestPaths.containsKey(vertex)) {
				Path<V, E> path = new Path<V, E>(start);
				
				// Add every sub-path of shortest path from start to current vertex 
				for (E edge : shortestPath(g, start, vertex).edges()) {
					path = path.cons(edge);
					shortestPaths.put(edge.dest(), path);
				}
			}
		}
		
		return shortestPaths;
	}
	
	// Container class for vertex and estimated distance to vertex
	private static class DistanceVertex<V extends Comparable<V>> implements Comparable<DistanceVertex<V>>
	{
		private V myVertex;
		private int myDistance;
		
		public DistanceVertex(V vertex, int distance)
		{
			myVertex = vertex;
			myDistance = distance;
		}
		
		public DistanceVertex(V vertex)
		{
			// Initialize estimated distance to positive infinity by default
			this(vertex, Integer.MAX_VALUE);
		}
		
		public V getVertex()
		{
			return myVertex;
		}
		
		public int getDistance()
		{
			return myDistance;
		}
		
		public void setDistance(int distance)
		{
			myDistance = distance;
		}
		
		// Only looks at vertices
		public boolean equals(Object obj)
		{
			if (!(obj instanceof DistanceVertex<?>))
				return false;

			DistanceVertex<?> v = (DistanceVertex<?>) obj;
			
			return v.getVertex().equals(myVertex);
		}

		// Only looks at distances
		public int compareTo(DistanceVertex<V> obj)
		{
			return myDistance - obj.getDistance();
		}
		
		public int hashCode()
		{
			return myVertex.hashCode();
		}
		
		public String toString()
		{
			return "[" + myVertex.toString() + ", " + myDistance + "]";
		}
	}
}

 