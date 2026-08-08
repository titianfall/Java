class Point{
    private int x,y;
    public Point(int x,int y){
        this.x=x;
        this.y=y;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    protected void move(int x, int y){
        this.x=x;
        this.y=y;
    }
}
class ColorPoint2 extends Point{
    private String color;
    public ColorPoint2(){
        super(0,0);
        this.color="WHITE";
    }
    public ColorPoint2(int x, int y){
        super(x,y);
        this.color = "BLACK";
    }
    public ColorPoint2(int x, int y, String color){
        super(x,y);
        this.color= color;
    }
    public void set(int x, int y){ move(x,y); }
    public void set(String color){
        this.color=color;
    }
    public String toString(){
        return (color + "색의 "+"("+getX()+","+getY()+")의 점");
    }
    public double getDistance(ColorPoint2 cp2) {		
		double x = Math.abs(this.getX() - cp2.getX());
		double y = Math.abs(this.getY() - cp2.getY());
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return distance;
	}
}
public class P4 {
    public static void main(String[] args) {
        ColorPoint2 zeroPoint = new ColorPoint2();
        System.out.println(zeroPoint.toString()+"입니다.");

        ColorPoint2 cp = new ColorPoint2(10, 10, "RED");
        cp.set(10,20);
        cp.set("blue");
        String str = cp.toString();
        System.out.println(str+"입니다.");

        ColorPoint2 thresholdPoint = new ColorPoint2(100,100);
        System.out.println("cp에서 임계점까지의 거리는 " + cp.getDistance(thresholdPoint));
    }    
}
