package mukulrathi.algorithms.geometric;

import java.util.ArrayList;
import java.util.List;

public class JarvisMarch { //O(nH) where H = number of points in convex hull, n = total number of points
    //when picking each point in hull, go through all of the points to find smallest angle.

    public List<Point> computeConvexHull(List<Point> pts){
        if(pts.size()<=2) return null; //no valid hull since need 3 points to form triangular hull

        //pick point with lowest y coordinate  - if tie then choose point with largest x co-ord.
        Point q0  = pts.get(0);
        for(Point p : pts){
            if(q0.y>p.y || (q0.y==p.y && p.x>q0.x)){
                q0 = p;
            }
        }
        List<Point> convexHull = new ArrayList<Point>();
        convexHull.add(q0);

        //find point with smallest counter-clockwise angle from the last line segment of hull (for initial hull with one point this is horizontal line through that point)


        Point p = null; //the last point on the hull
        while(p!=q0) { //i.e. keep adding points to hull until we reach original
            Point r = convexHull.get(convexHull.size()-1);
            if (pts.get(0) !=r ) { //pick a random candidate for next point on hull, so long as not same as the first point
                p = pts.get(0);
            } else {
                p = pts.get(1);
            }
            for (Point p2 : pts) { //angle(p2) < angle(p) if p2 on right of r-->p
                if(p2==r) continue; //same point so ignore
                int side = p2.subtract(r).cross(p.subtract(r)); //if positive then p2 on right of r-->p
                if(side>0 || side==0 && (p2.subtract(r).length() > (p.subtract(r)).length())){ //if on right or if further away from r
                    p = p2; //p2 now the candidate for next point
                }
            }
            convexHull.add(p);
        }
        return convexHull;
    }
}
