/*	Mason Wischhover
 *  LocationMap.java
 *  8/4/22
 */ 

import java.io.*;
import java.util.*;
import javax.swing.*;

public class LocationMap extends JFrame {
   
	/**
	 * 
	 */
	
	static double x = Double.MAX_VALUE;
	static double minLat = x;
	static double minLon = x;
	static double maxLat = x * -1;
    static double maxLon = x * -1;
    static boolean SP = false;
    static boolean MWST = false;
    static ArrayList<Vertex> SPintersections;
    static ArrayList<Edge> MWSTroads;
    
   
  
    
    public LocationMap() {
        setSize(900, 800);
        add(new GUI());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 2) {
            System.exit(0);
        }

        boolean show, directions, meridianmap;
        
        String a, b;
        show = directions = meridianmap = false;  
        a = b = "";
        
        

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-show")) {
                show = true;
            }
            else if (args[i].equals("-directions")) {
                directions = true;
                i++;               
                a = args[i];
                i++;
                b = args[i];
            }
            else if (args[i].equals("-meridianmap")) {
                meridianmap = true;
            }
        }

        FileInputStream newFile;
        Scanner cL = null;
       
        try {
            newFile = new FileInputStream(args[0]);
            cL = new Scanner(newFile);
        }
        catch (FileNotFoundException e) {
            System.out.println(args[0] + " is not found in source folder");
            System.exit(0);
        }
        
        Graph mapPaths = new Graph();

        while (cL.hasNextLine()) {
            String[] line = cL.nextLine().split("\t");

            if (line[0].equals("i")) {
                Vertex intersection = new Vertex(Double.parseDouble(line[2]), Double.parseDouble(line[3]), line[1]);
                mapPaths.addIntersection(intersection);
          
                if (intersection.getLatitude() > maxLat)
                    maxLat = intersection.getLatitude();
                
                if (intersection.getLongitude() > maxLon)
                    maxLon = intersection.getLongitude();

                if (intersection.getLatitude() < minLat)
                    minLat = intersection.getLatitude();

                if (intersection.getLongitude() < minLon)
                    minLon = intersection.getLongitude();
            }
            // road -> edge
            else if (line[0].equals("r")) {
                // all intersections are declared before road data in data file
                Edge road = new Edge(line[1], mapPaths.getVertices().get(line[2]), mapPaths.getVertices().get(line[3]));
                mapPaths.addRoad(road, mapPaths.getVertices().get(line[2]), mapPaths.getVertices().get(line[3]));
            }
        }

        cL.close();
        
        
 
        if (directions == true) {
            Vertex start = mapPaths.getVertices().get(a.toString());
            Vertex end = mapPaths.getVertices().get(b.toString());

            SP = mapPaths.shortestPath(start, end);
            if (SP == true) {
            	int i;
              
                SPintersections = mapPaths.getPath(end, start);
                            
                System.out.println("Shortest Path from " + start.getID() + " to " + end.getID() + ":");
                
                for (i = SPintersections.size() - 1; i > 0; i--) {
                    System.out.print(SPintersections.get(i).getID() + " -> ");
                }
                System.out.println(SPintersections.get(i).getID());
                System.out.printf("Distance Travelled: %.2f miles\n", SPintersections.get(i).getDistance());
            }
            else {
                System.out.println("Shortest Path could not be found!");
            }
        }

        if (meridianmap == true) {
            MWSTroads = mapPaths.createMM();
            if (!MWSTroads.isEmpty()) {
                MWST = true;

                System.out.println("Minimum Weight Spanning Tree:");
              
                for (int i = 0; i < MWSTroads.size(); i++) {
                    System.out.print(MWSTroads.get(i).getID() + " -> ");
                    if (i == MWSTroads.size() - 1) {
                     System.out.println(MWSTroads.get(i).getID());
                    }
                }
              
            }
            else {
                System.out.println("Minimum Weight Spanning Tree is Unavailable");
            }
        }
        
        if (show == true) {
            new LocationMap().setVisible(true);
        }

    }
}