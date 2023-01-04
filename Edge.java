/*	Mason Wischhover
 *  Edge.java
 *  8/4/22
 */ 

import java.lang.Math;

public class Edge implements Comparable<Edge> {
    private String id;
    private String startVertexID;
    private String endVertexID;
    private double weight;
   

    public Edge(String id, Vertex v1, Vertex v2) {
        this.id = id;
        this.startVertexID = v1.getID();
        this.endVertexID = v2.getID();
        this.weight = findDist(v1, v2);
    }

    // distance between two intersections via haversine Formula 
    // source code fount at: https://stackoverflow.com/questions/38623122/finding-the-distance-between-several-points-lat-long
    
    public double findDist(Vertex v1, Vertex v2) {
        
        double R = 3959;

        double dLat = Math.toRadians(v1.getLatitude() - v2.getLatitude());
        double dLon = Math.toRadians(v1.getLongitude() - v2.getLongitude());

        double a = (Math.sin(dLat/2) * Math.sin(dLat/2)) +
                    Math.cos(Math.toRadians(v1.getLatitude())) * Math.cos(Math.toRadians(v2.getLatitude())) * 
                    (Math.sin(dLon/2) * Math.sin(dLon/2));

        double c = 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return c;
    }

   
    @Override
    public int compareTo(Edge e) {
        return (int)(this.getWeight() - e.getWeight());
    }

 
    

    public String getStartVertexID() {
        return startVertexID;
    }

    public String getEndVertexID() {
        return endVertexID;
    }
    
    public String getID() {
        return id;
    }

    public double getWeight() {
        return weight;
    }
   
}
