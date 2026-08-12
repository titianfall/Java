package extends1.super1;

public class Child extends Parent {
    public String value = "child"; // 부모와 이름이 같은 필드

    @Override
    public void hello() {
        System.out.println("Child.hello()");
    }

    public void call() {
        System.out.println("this value = " + this.value); // this 생략 가능
        System.out.println("super value = " + super.value);

        this.hello(); // Child.hello();
        super.hello(); // Parent.hello();
    }
}
