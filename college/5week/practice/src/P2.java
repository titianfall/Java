class TV{
    private int size;
    public TV(int size){this.size = size;}
    protected int getSize(){return size;}
}

class ColorTV extends TV{
    private int color;
    public ColorTV(int size, int color){
        super(size);
        this.color = color;
    }
    public int getColor(){return color;}
}
class SmartTV extends ColorTV{
    private String ip;
    public SmartTV(String ip, int size,int color){
        super(size,color);
        this.ip=ip;
    }
    public void printProperty(){
        System.out.println("SmartTV ip "+ip+" 주소를 가지며 "+getSize()+"인치 "+getColor()+"컬러");
    }
}
public class P2{
    public static void main(String[] args){
        SmartTV myTv = new SmartTV("192.168.0.5",65,65536);
        myTv.printProperty();    
    }
}