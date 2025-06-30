class Human{
    String name;
    int age;//기본 접근 제어자
    double height,weight;

    public Human(){
        this.name = "";
        age = 0;
        height = 0;
        weight = 0;
    }

    public Human(String name){
        this.name = name;
        age = 0;
        height = 0;
        weight = 0;
    }
}
//check-time p208
public class p208{
    public static void main(String[] args) {
        Human aHuman = new Human("홍길동");
        aHuman.age = 21;
        aHuman.height =180.5;
        aHuman.weight = 73.2;

        Human[] h = new Human[5];
        for(int i=0;i<h.length;i++){
            h[i] = new Human();
        }
    }
}