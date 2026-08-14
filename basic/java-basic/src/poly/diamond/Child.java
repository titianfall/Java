package poly.diamond;

// 다중 상속(Interface)
public class Child implements InterfaceA, InterfaceB {
    @Override
    public void methodA() {
        System.out.println("Child::methodA");
    }

    @Override
    public void methodCommon() {
        System.out.println("Child::methodCommon");
    }

    @Override
    public void methodB() {
        System.out.println("Child::methodB");
    }

}
