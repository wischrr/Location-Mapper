/*	Mason Wischhover
 *  GUI.java
 *  8/4/22
 */  

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GUI extends JComponent {

    @Override
    public void paintComponent(Graphics g) {
       
        for (String key : Graph.vertices.keySet()) {
            ArrayList<Edge> neighbours = Graph.vertices.get(key).getAdjList();

            for (Edge e : neighbours) {
                
                Vertex start = Graph.vertices.get(key);
                Vertex end = Graph.vertices.get(e.getEndVertexID());
                
                printLine(g, start, end);
            }
        }

        // displays shortest path from two selected intersections in GUI
        if (LocationMap.SP == true) {
           
            g.setColor(Color.BLACK);          
           
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.GREEN);         

            for (int i = 0; i < LocationMap.SPintersections.size() - 1; i++) {
                Vertex v = LocationMap.SPintersections.get(i); 
                Vertex next = LocationMap.SPintersections.get(i+1);
                printLine(g2, v, next);
            }
        }

        // displays meridian map in GUI
        if (LocationMap.MWST) {
            
            g.setColor(Color.BLACK);                     
          
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.GREEN);

            for (Edge e : LocationMap.MWSTroads) {
                Vertex v = Graph.vertices.get(e.getStartVertexID());
                Vertex next = Graph.vertices.get(e.getEndVertexID());
                printLine(g2, v, next);
            }
        }
    }

       
    public int[] fitSize(double w, double h) {
    	int[] results = new int[2];
    	double width = LocationMap.maxLon - LocationMap.minLon;
    	double height = LocationMap.maxLat - LocationMap.minLat;
    	int mW = 9 * getWidth() / 10;
    	int sW = 1 * getWidth() / 20;
    	int mH = 7 * getHeight() / 10;
    	int sH = 3 * getHeight() / 20;
    	results[0] = (int)((w - LocationMap.minLon)/(width) * mW + sW); 
    	results[1] = (int)((h - LocationMap.minLat)/(height) * mH + sH);
    	
    	
    	return results;
    }
    
    
    public void printLine(Graphics g, Vertex start, Vertex end) {
    	int[] fittedS = fitSize(start.getLongitude(), start.getLatitude());
    	int[] fittedE = fitSize(end.getLongitude(), end.getLatitude());
        g.drawLine(Math.abs(fittedS[0]), Math.abs(getHeight() - fittedS[1]), 
                    Math.abs(fittedE[0]), Math.abs(getHeight() - fittedE[1]));
    }
}

