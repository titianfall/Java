class GStack<T>{
    int tos;
    Object [] stck;
    public GStack(){
        tos = -1;
        stck = new Object[10];
    }
    public void push(T item){
        if(tos == 10-1){
            return;
        }
        stck[++tos] = item;
    }
    public T pop(){
        if(tos == -1){
            return null;
        }
        return (T)stck[tos--];
    }
}
public class E10 {
    public static void main(String[] args) {
        GStack<String> stringStack = new GStack<String>();

        stringStack.push("incheon");
        stringStack.push("busan");
        stringStack.push("seoul");

        for(int i=0;i<3;i++){
            System.out.println(stringStack.pop());
        }

        GStack<Integer> intStack= new GStack<Integer>();

        intStack.push(1);
        intStack.push(2);
        intStack.push(3);

        while(true){
            Integer val = intStack.pop();
            if(val == null) break;
            System.out.println(val);
        }
    }
}
