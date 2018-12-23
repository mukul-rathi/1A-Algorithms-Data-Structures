package mukulrathi.algorithms.graph;

import mukulrathi.customexceptions.PathNotFoundException;
import mukulrathi.customexceptions.UnderflowException;
import mukulrathi.datastructures.abstractdatatypes.Queue;
import mukulrathi.datastructures.implementations.queues.ArrayQueue;

import java.util.ArrayList;
import java.util.List;

public class EdmondsKarp{

    public void maxFlow(Graph g, Vertex s, Vertex t){
        for(Vertex v:  g.vertices){//initialise flow to zero
            for(Vertex w : v.neighbours.keySet()){
                v.flows.put(w,0);

                w.flows.put(v,0);
                v.edges.add(w); //edges is an undirected graph which we'll use when determining if edge in residual graph
                w.edges.add(v);
            }
        }
        while(true){
            List<Vertex> augmentingPath;
            try {
                augmentingPath = BFS_path(s, t, g);
            } catch (PathNotFoundException e) {
                break;
            }
            //find amount to augment flow by
            int augFlow = Integer.MAX_VALUE;
            for(int i=0; i< augmentingPath.size() -1; i++){
                Vertex u = augmentingPath.get(i);
                Vertex v = augmentingPath.get(i+1);
                if(u.neighbours.containsKey(v)){ // u->v is the edge in the residual graph
                    augFlow = Math.min(u.neighbours.get(v)- u.flows.get(v),augFlow); //c(u,v)-f(u,v)
                }
                else{ //v->u is the edge in the residual graph
                    augFlow = Math.min(v.neighbours.get(u),augFlow); //f(v, u)
                }
            }

            //augment the flow along augmenting path
            for(int i=0; i< augmentingPath.size() -1; i++){
                Vertex u = augmentingPath.get(i);
                Vertex v = augmentingPath.get(i+1);
                if(u.neighbours.containsKey(v)){ // u->v is the edge in the residual graph
                    u.flows.put(v, u.flows.get(v) + augFlow); //increase flow
                }
                else{ //v->u is the edge in the residual graph
                    v.flows.put(u, v.flows.get(u) - augFlow);  // decrease the flow (since we're pushing back flow)
                }
            }
        }


    }

    //modified breadth-first search on the augmenting path
    public static List<Vertex> BFS_path(Vertex source, Vertex target, Graph g) throws PathNotFoundException {
        g.initGraph();

        source.distance = 0;
        source.parent = null;
        Queue<Vertex> toExplore = new ArrayQueue<Vertex>();
        toExplore.put(source);
        while (!toExplore.isEmpty()) {
            try {
                Vertex v = toExplore.get();
                if(v==target) break; // we've found augmenting path so stop
                for (Vertex w : v.edges) {
                    boolean residualEdge;
                    if (v.neighbours.containsKey(w)) { //if original graph had edge v->w
                        residualEdge = (v.flows.get(w) < v.neighbours.get(w));
                    }
                    else { //the original graph has edge w-> v
                        residualEdge = (w.flows.get(v) > 0);
                    }

                    if (residualEdge && !w.seen) {
                        w.seen = true;
                        w.parent = v;
                        w.distance = v.distance + 1;
                        toExplore.put(w);
                    }
                }
            } catch (UnderflowException e) { //not going to happen because we check if non-empty in the while loop
                e.printStackTrace();
            }

        }
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        Vertex curr = target;
        while (curr != source) {
            if(curr==null){
                throw new PathNotFoundException();
            }
            path.add(0, curr);
            curr = curr.parent;
        }
        path.add(0, source);
        return path;

    }

}
