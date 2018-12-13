package mukulrathi.algorithms.geometric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GrahamScan {
    //O(n lg n) - sort points in terms of angle and then go through sorted list making sure you always turn left -
    // if not keep popping the last points off until you turn left relative to last line segment.

    public static List<Point> computeConvexHull(List<Point> pts){ //O(nlgn)
        if(pts.size()<=2) return null; //no valid hull since need 3 points to form triangular hull

        //pick point with lowest y coordinate  - if tie then choose point with largest x co-ord.
        Point q0  = pts.get(0);
        for(Point p : pts){
            if(q0.y>p.y || (q0.y==p.y && p.x>q0.x)){
                q0 = p;
            }
        }
        final Point r0 = q0; // point with smallest value - made final so can be used in lambda function
        List<Point> convexHull = new ArrayList<Point>();
        convexHull.add(r0);

        //sort points based on counter-clockwise angle from horizontal
        Collections.sort(pts,
                new Comparator<Point>() {
                    @Override
                    public int compare(Point p1, Point p2) {
                        if(p1 == r0) return -1;
                        if(p2 ==r0) return 1;
                        int side = p2.subtract(r0).cross(p1.subtract(r0)); //if positive then p2 on right of r-->p1
                        if(side!=0) return  side;
                        //side ==0 so angle same sort in descending order of distance
                        return p2.length() - p1.length();

                    }
                }); //if p1 or p2 are = r0 then they come first
        convexHull.add(pts.get(1));

        for(int i=2; i<pts.size(); i++){
            Point p = pts.get(i);
            //let q-->r be the last line segment in hull. If p is on right of q--> r then remove r and repeat with new values of q and r.
            Point q = convexHull.get(convexHull.size()-2);
            Point r = convexHull.get(convexHull.size()-1);
            //Graham's scan picks points such that the next line segment always turns left.
            while(p.subtract(q).cross(r.subtract(q))>0){
                convexHull.remove(convexHull.size()-1);
                r = q;
                q = convexHull.get(convexHull.size()-2);
            }
            if(p.subtract(q).cross(r.subtract(q))<0){ //i.e. p strictly on left, since if p lies on line then we ignore as we take furthest point out
                convexHull.add(p);
            }
        }

        return  convexHull;
    }

}
