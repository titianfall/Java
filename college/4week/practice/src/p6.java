class Rectangle{
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height){
        this.x = x;
        this.y=y;
        this.width =width;
        this.height =height;
    }

    public void show(){
        System.out.println("크기가 ("+x+","+y+")에서 크기가 "+width+"x"+height+"인 사각형");
    }
    public boolean isSquare(){
        if(width == height) return true;
        else return false;
    }
    public boolean contains(Rectangle r){
        if(r.x >=this.x && r.y >=this.y && (r.x+r.width)<=(x+width) &&(r.y+r.height)<=y+height){
            return true;
        }
        else return false;
    }

}

public class p6 {
    public static void main(String[] args) {
        Rectangle reca = new Rectangle(3,3,6,6);
        Rectangle recb = new Rectangle(4,4,2,3);

        reca.show();

        if(reca.isSquare()) System.out.println("a는 정사각형입니다.");
        else System.out.println("a는 직사각형입니다.");
        if(reca.contains(recb)) System.out.println("a는 b를 포함합니다.");
    }
}
