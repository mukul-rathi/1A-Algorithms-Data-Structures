package mukulrathi.algorithms.graph;

import mukulrathi.customexceptions.NegativeWeightCycleException;

public class BellmannFord { // O(VE)

    public static boolean relaxEdge(Edge e){
        Vertex u = e.start;
        Vertex v = e.end;
        if((e.weight + u.distance) < v.distance){
            v.distance = e.weight + u.distance;
            v.parent = u;
            return true; //edge was relaxed
        }
        return false; //edge not relaxed
    }
    public static void shortestPath(Vertex source, Graph g) throws NegativeWeightCycleException {
        g.initGraph();
        source.distance = 0;
        source.parent = null;
        for(int i=0; i<g.vertices.size()-1; i++){
            for(Edge e : g.edges){
                relaxEdge(e);
            }
        }
        //check for negative weight cycles
        for(Edge e : g.edges){
            if(relaxEdge(e)) throw new NegativeWeightCycleException();
        }
    }


}
