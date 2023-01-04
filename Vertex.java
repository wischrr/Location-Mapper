/*	Mason Wischhover
 *  Vertex.java
 *  8/4/22
 */ 

import java.util.*;

public class Vertex implements Comparable<Vertex> {
   
	
    private double latitude;
    private double longitude;
    private double length = Double.MAX_VALUE;
    private boolean visited = false;
    private String name;
    private Vertex prev = null;

    private ArrayList<Edge> adjList = new ArrayList<Edge>();

    
    public Vertex(double latitude, double longitude, String id) { 
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = id;
    }

   
    @Override
    public int compareTo(Vertex node) {
        if (length < node.length) {
            return -1;
        }
        else if (length > node.length) {
            return 1;
        }

        return 0;
    }

  

    void setParent(Vertex node) {
        prev = node;
    }

    void setDistance(double d) {
        length = d;
    }

    void setVisited(boolean val) {
        visited = val;
    }

    void setAdjList(ArrayList<Edge> list) {
        adjList = list;
    }

 

   

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Vertex getPrev() {
        return prev;
    }

    public double getDistance() {
        return length;
    }

    public boolean getVisited() {
        return visited;
    }
    
    public String getID() {
        return name;
    }
    
    public ArrayList<Edge> getAdjList() {
        return adjList;
    }

   
  
}
