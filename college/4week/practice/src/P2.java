class Cube{
    private int width;
    private int length;
    private int height;

    public Cube(){
        this(0,0,0);
    }
    public Cube(int width,int length,int height){
        this.width = width;
        this.length= length;
        this.height = height;
    }
    public int getVolume(){
        return (width*length*height);
    }
    public void increase(int width,int length, int height){
        this.width+=width;
        this.length+=length;
        this.height+=height;
    }
    public boolean isZero(){
        if((width*length*height)==0){
            return true;
        }
        else return false;
    }
}
public class P2{
    public static void main(String[] args){
        Cube cube = new Cube(1,2,3);

        System.out.println("큐브의 부피는 "+cube.getVolume());
        cube.increase(1,2,3);
        System.out.println("큐브의 부피는 "+cube.getVolume());
        if(cube.isZero()){
            System.out.println("큐브의 부피는 0");
        }
        else{
            System.out.println("큐브의 부피는 0이 아님");
        }
    }
}