package mukulrathi.algorithms.geometric;

public class Point {
    public int x;
    public int y;

    public Point(int new_x, int new_y){
        x = new_x;
        y = new_y;
    }

    public int dot(Point p){
        return x*p.x + y*p.y;
    }

    public int cross(Point p){
        return x*p.y - y*p.x;
    }

    public Point subtract(Point p){
        return new Point(x-p.x, y-p.y);
    }

    public Point transpose(){
        return new Point(y,x);
    }

    public int length(){
        return (int) Math.sqrt(x*x+y*y);
    }
}
