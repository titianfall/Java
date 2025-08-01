import java.util.Vector;

class Point{
    private int x,y;
    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
}
public class E2 {
    public static void main(String[] args) {
        var v = new Vector<Point>();

        v.add(new Point(2,3));
        v.add(new Point(-5,20));
        v.add(new Point(30,-8));

        v.remove(1);

        for(int i=0;i<v.size();i++){
            Point p = v.get(i);
            System.out.println(p);
        }
    }    
}
