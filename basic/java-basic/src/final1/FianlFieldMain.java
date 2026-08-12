package final1;

public class FianlFieldMain {

    public static void main(String[] args) {
        System.out.println("생성자 초기화");
        ConstructInit constructInit = new ConstructInit(10); // 생성자 final 초기화
        System.out.println("constructInit = " + constructInit);

        ConstructInit constructInit2 = new ConstructInit(20);
        System.out.println("constructInit2 = " + constructInit2);


        System.out.println("필드 초기화");
        FieldInit fieldInit = new FieldInit();
        System.out.println("fieldInit = " + fieldInit);

        FieldInit fieldInit2 = new FieldInit();
        System.out.println("fieldInit2 = " + fieldInit2);



        FieldInit fieldInit3 = new FieldInit();
        System.out.println("fieldInit3 = " + fieldInit3);

        System.out.println("상수");
        System.out.println(FieldInit.CONST_VALUE);
    }
}
