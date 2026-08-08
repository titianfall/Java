class TV{
    private String name;
    private int size;
    private int price;

    public TV(){
        this("LG",32,100);
    }
    public TV(String name, int size, int price){
        this.name =name;
        this.size= size;
        this.price =price;
    }
    public void show(){
        System.out.println(price+"만원짜리 "+name+"에서 만든"+size+"인치 TV");
    }
}
public class P1 {
    public static void main(String[] args) {
        TV television = new TV("Samsung",50,300);

        television.show();
    }
}
