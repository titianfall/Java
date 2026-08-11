package access.a;

public class AccessData {

    public int publicField;
    int defaultField;
    private int privateField;

    public void publicMethod() {
        System.out.println(this.publicField);
    }

    void defaultMethod() {
        System.out.println(this.defaultField);
    }

    private void privateMethod() {
        System.out.println(this.privateField);
    }

    public void innerAccess() {
        System.out.println("innerAccess");
        publicField = 100;
        defaultField = 200;
        privateField = 300;

        publicMethod();
        defaultMethod();
        privateMethod();
    }

    @Override
    public String toString() {
        return "AccessData{" +
                "publicField=" + publicField +
                ", defaultField=" + defaultField +
                ", privateField=" + privateField +
                '}';
    }
}
