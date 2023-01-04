 /*	Mason Wischhover
 *  Graph.java
 *  8/4/22
 */ 

import java.util.*;

public class Graph {
	
	public static HashMap<String, Vertex> vertices;

	
	public Graph() {
		vertices = new HashMap<String, Vertex>();
	}


	
	public void addIntersection(Vertex node) {
		vertices.put(node.getID(), node);
	}

	
	public void addRoad(Edge connect, Vertex node1, Vertex node2) {
		vertices.get(node1.getID()).getAdjList().add(connect);
		vertices.get(node2.getID()).getAdjList().add(connect);
		
	}

	
	// Allows program to obtain the vertices in the graph
	 
	public HashMap<String, Vertex> getVertices() {
		return vertices;
	}

	
	// Shortest Path Method
	
	public boolean shortestPath(Vertex start, Vertex end) {
		
		PriorityQueue<Vertex> unvisited = new PriorityQueue<Vertex>();

		
		start.setDistance(0);
		unvisited.add(start);

		
		while (!unvisited.isEmpty()) {
			
			Vertex current = unvisited.poll();
			current.setVisited(true);

			for (Edge e : current.getAdjList()) {
				Vertex adjacent = vertices.get(e.getEndVertexID());
				
				if (adjacent == current) {
					adjacent = vertices.get(e.getStartVertexID());
				}

				if (!adjacent.getVisited()) {
					double adjDistance = current.getDistance() + e.getWeight();
					if (adjDistance < adjacent.getDistance()) {
						
						adjacent.setDistance(adjDistance);
						adjacent.setParent(current);

						
						unvisited.add(adjacent);
						adjacent.setVisited(true);
					}
				}
			}

			
			if (end.getVisited()) {
				return true;
			}

		}

		return false;
	}

	
	public ArrayList<Vertex> getPath(Vertex last, Vertex first) {
		ArrayList<Vertex> route = new ArrayList<Vertex>();

		Vertex marker = last;
		route.add(marker);

	
		while (marker.getPrev() != first) {
			marker = marker.getPrev();
			route.add(marker);
		}

		route.add(first);

		return route;
	}

	// MWST method
	
	public ArrayList<Edge> createMM() {
		PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
		HashSet<HashSet<Vertex>> vSet = new HashSet<HashSet<Vertex>>();
		ArrayList<Edge> resultList = new ArrayList<Edge>();

		for (String key : vertices.keySet()) {
			
			HashSet<Vertex> v = new HashSet<Vertex>();
			v.add(vertices.get(key));
			vSet.add(v);

			ArrayList<Edge> temp = vertices.get(key).getAdjList();
			for (Edge e : temp) {
				if (!edges.contains(e))
					edges.add(e);
			}
		}

		while (vSet.size() > 1 && edges.size() > 0) {
			Edge next = edges.poll();

			
			HashSet<Vertex> vSet1 = get(vSet, vertices.get(next.getStartVertexID()));
			HashSet<Vertex> vSet2 = get(vSet, vertices.get(next.getEndVertexID()));

			

			if (!vSet1.equals(vSet2)) {
				
				
				vSet1.addAll(vSet2);
				vSet.remove(vSet2);
				resultList.add(next);
			}
		}

		return resultList;
	}

	
	public HashSet<Vertex> get(HashSet<HashSet<Vertex>> vertexSet, Vertex point) {
		for (HashSet<Vertex> set : vertexSet) {
			if (set.contains(point))
				return set;
		}

		return null;
	}
}
