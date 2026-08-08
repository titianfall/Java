import java.util.Scanner;

interface IStack{
    int capacity();
    int length();
    boolean push(String val);

    String pop();
}

class StringStack implements IStack{
    private int top;
    private String array[];
    private int max;
    public StringStack(int size){
        array = new String[size];
        top = -1;
        max = size;
    }
    public int capacity(){ return max; }
    public int length(){ return top + 1; }
    public boolean push(String val){
        if(top == capacity() -1){
            return false;
        }
        array[++top]=val;
        return true;
    }
    public String pop(){
        if(top!=-1){
            return array[top--];
        }
        return null;
    }
}
public class P11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("스택 용량>>");
        int n = scanner.nextInt();
        StringStack s = new StringStack(n);
        scanner.nextLine();
        while(true){
            System.out.print("문자열 입력>>(그만을 입력하면 입력 종료)");
            String major = scanner.nextLine();
            if(major.equals("그만")) break;
            if(!s.push(major)){
                System.out.println("스택이 꽉차서 디지털 저장 불가");
                //break;
            }
            
        }
        System.out.print("스택에 저장된 문자열 팝 : ");
        String str;
        while ((str = s.pop()) != null) {
            System.out.print(str + " ");
        }

        scanner.close();
    }
}
