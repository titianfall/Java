package access.b;

import access.a.AccessData;

public class AccessOuterMain {
    public static void main(String[] args) {
        AccessData data = new  AccessData();

        data.publicField = 1;
        data.publicMethod();
        System.out.println("data = " + data);

        data.innerAccess();
        System.out.println("data = " + data);
    }
}
