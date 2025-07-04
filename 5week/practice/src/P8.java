import java.util.Scanner;

abstract class Box{
	protected int size; // 현재 박스에 들어 있는 재료의 양
	public Box(int size) { // 생성자
		this.size = size;
	}
	public boolean isEmpty() {return size == 0; } // 박스가 빈 경우 true 리턴
	public abstract boolean consume(); // 박스에 들어있는 재료를 일정량 소비
	public abstract void print(); // 박스에 들어있는 양을 "*"문자로 출력
}

class IngrediendtBox extends Box{
    private String name;
    public IngrediendtBox(String name, int size){
        super(size);
        this.name = name;
    }
    public boolean consume(){
        if(isEmpty()) return false;
        size--;
        return true;
    }
    public void print(){
        System.out.print(name+" ");
        for(int i=0;i<size;i++){
            System.out.print("*");
        }
        System.out.println(size);
    }
}
public class P8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IngrediendtBox a = new IngrediendtBox("커피", 5);
        IngrediendtBox b = new IngrediendtBox("프림", 5);
        IngrediendtBox c = new IngrediendtBox("설탕", 5);

        System.out.println("******청춘 커피 자판기 입니다.******");
        int op;
        while (true) { 
            a.print();
            b.print();
            c.print();
            System.out.print("다방커피 : 1, 설탕 커피 : 2, 블랙 커피 : 3, 종료 4>>");
            op = scanner.nextInt();
            if(op == 1){
                if(a.consume()&&b.consume()&&c.consume()){ 
                }
                else{
                    System.out.println("원료가 부족합니다.");
                }
            }
            if(op == 2){
                if(a.consume()&&c.consume()){}
                else{
                    System.out.println("원료가 부족합니다.");
                }
            }
            if(op == 3){
                if(a.consume()){
                }
                else{
                    System.out.println("원료가 부족합니다.");
                }
            }
            if(op == 4){
                System.out.println("청춘 커피 자판기 프로그램을 종료합니다.");
                scanner.close();
                return;
            }
            //System.out.println("잘못된 입력 다시입력해주세요");
        }
        
    }
}
