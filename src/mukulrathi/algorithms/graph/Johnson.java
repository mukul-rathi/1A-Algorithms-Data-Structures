package mukulrathi.algorithms.graph;

import mukulrathi.customexceptions.NegativeWeightCycleException;

import java.util.ArrayList;
import java.util.HashMap;

public class Johnson { //calculate all shortest paths by first reweighting graph using Bellmann Ford, and then running Dijkstra V times

    public static HashMap<Vertex, HashMap<Vertex, Integer>> allShortestPaths(Graph g) throws NegativeWeightCycleException {

        //create helper graph with extra vertex s, which has edges to all other vertices with weight 0
        Graph helperGraph = new Graph();
        helperGraph.vertices = new ArrayList<Vertex>();
        helperGraph.edges = new ArrayList<Edge>();
        for(Edge e: g.edges){
            helperGraph.edges.add(e);
        }
        Vertex s = new Vertex(0); //arbitrary value associated with vertex which we don't care about since only interested in distance
        helperGraph.vertices.add(s);
        for(Vertex v : g.vertices){
            helperGraph.vertices.add(v);
            helperGraph.edges.add(new Edge(s,v,0));
            s.neighbours.put(v, 0);
        }

        BellmannFord.shortestPath(s,helperGraph); //run shortest path algorithm on helper graph

        for(Vertex v: g.vertices){
            v.helperDistance = v.distance;
        }

        //re-weight the graph so all edges now non-negative
        for(Edge e: g.edges){
            e.weight = e.weight + e.start.helperDistance - e.end.helperDistance;
        }

        HashMap<Vertex, HashMap<Vertex,Integer>> shortestPathWeights = new  HashMap<Vertex, HashMap<Vertex,Integer>>();
        //can run Dijkstra V times, to get all shortest paths
        for(Vertex v: g.vertices){
            Dijkstra.shortestPath(v,g);
            HashMap<Vertex, Integer> vSPweights = new HashMap<Vertex,Integer>();
            for(Vertex w : g.vertices){
                int reweightedDist = w.distance - v.helperDistance + w.helperDistance; //re-weight
                vSPweights.put(w,reweightedDist);
            }
            shortestPathWeights.put(v,vSPweights);
        }

        //now re-weight graph so edges have original weights
        for(Edge e: g.edges){
            e.weight = e.weight - e.start.helperDistance + e.end.helperDistance;
        }
        return shortestPathWeights;
    }

}
