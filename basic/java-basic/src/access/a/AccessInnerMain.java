package access.a;

public class AccessInnerMain {
    public static void main(String[] args) {
        AccessData accessData = new AccessData();

        // public 호출
        accessData.publicField = 1;
        accessData.publicMethod();
        System.out.println("accessData = " + accessData);

        // 같은 패키지 default 호출 가능
        accessData.defaultField = 2;
        accessData.defaultMethod();
        System.out.println("accessData = " + accessData);

        // private 호출 불가
        // accessData.privateField = 3;
        // accessData.privateMethod();

        accessData.innerAccess();
        System.out.println("accessData = " + accessData);
    }
}
