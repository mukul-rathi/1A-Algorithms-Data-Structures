package mukulrathi.algorithms.graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T>{
    // use package modifier for brevity of graph algorithms (should use private and getters/setters in production)
     T value;
     boolean seen;
     int distance;
     List<Vertex> neighbours; //Adjacency list representation

    public Vertex(T new_val){
        value = new_val;
        neighbours = new ArrayList<Vertex>();
    }
}
