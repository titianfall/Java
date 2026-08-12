package extends1.super2;

public class ClassB extends ClassA{
    public ClassB(int a) {
        super(a, 0); // ClassA() call
        System.out.println("ClassB(int a)");
    }

    public ClassB(int a, int b) {
        super();
        System.out.println("ClassB(int a, int b)");
    }
}
