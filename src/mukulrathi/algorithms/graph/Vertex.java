package mukulrathi.algorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vertex{
    // use package modifier for brevity of graph algorithms (should use private and getters/setters in production)
     double value;
    HashMap<Vertex, Double> neighbours; //Adjacency list representation
    //used for search algorithms:
    boolean seen;
    int distance;
    Vertex parent;


    public Vertex(double new_val){
        value = new_val;
        neighbours = new HashMap<Vertex,Double>();
    }
}
