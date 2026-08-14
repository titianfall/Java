package poly.basic;

public class PolyMain {
    public static void main(String[] args) {
        System.out.println("Parent -> Parent");
        Parent parent = new Parent();
        parent.parentMethod();

        System.out.println("Child -> Parent");
        Child child = new Child();
        child.childMethod();
        child.parentMethod();

        System.out.println("Parent -> Child");
        Parent poly = new Child(); // 다운캐스팅
        poly.parentMethod();
//        poly.childMethod(); // 'Parent'의 메서드 'childMethod'을(를) 해결할 수 없습니다

        // 호환되지 않는 타입입니다. 발견: 'poly.basic.Parent', 필요: 'poly.basic.Child'
//        Child child1 = new Parent(); // 자식은 부모를 담을 수 없음


    }
}
