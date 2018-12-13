package mukulrathi.algorithms.graph;

public class Edge{
    public Vertex start;
    public Vertex end;
    public int weight;

    public Edge(Vertex u, Vertex v, int c){
        start = u;
        end = v;
        weight = c;
    }


}