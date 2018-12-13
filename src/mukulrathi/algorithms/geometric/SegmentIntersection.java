package mukulrathi.algorithms.geometric;

public class SegmentIntersection {


    public static boolean doSegmentsIntersect(Point p1, Point p2, Point q1, Point q2) {
        //check which side of q1-->q2 p1 and p2 are
        //can check side using cross product of (p1-q1) with (q2-q1) and ditto with p2

        int p1Side = (p1.subtract(q1)).cross(q2.subtract(q1));
        int p2Side = (p2.subtract(q1)).cross(q2.subtract(q1));
        if((p1Side>0 && p2Side>0)|| (p1Side<0 && p2Side<0)){ //if lie on same side don't intersect
            return false;
        }

        //now do same for q1, q2 for p1-->p2
        int q1Side = (q1.subtract(p1)).cross(p2.subtract(p1));
        int q2Side = (q2.subtract(p1)).cross(p2.subtract(p1));
        if((q1Side>0 && q2Side>0)|| (q1Side<0 && q2Side<0)){ //if lie on same side don't intersect
            return false;
        }

        return true; //note if p1 lies on the segment q1-->q2 then clearly q1 will be on one side of p1-->p2 and q2 will be on other side
        //likewise if p2 lies on segment p1-->p2
    }
}
