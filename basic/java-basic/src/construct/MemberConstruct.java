package construct;

public class MemberConstruct {

    String name;
    int age;
    int grade;

    MemberConstruct(String name, int age, int grade) {
        System.out.println("생성자 호출 MemberConstruct(String name, int age, int grade)");
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public MemberConstruct(String name, int age) {
//        this.name = name;
//        this.age = age;
//        this.grade = 50;

        // System.out.println(go); // 컴파일 오류 발생
        this(name , age, 50); // 생성자 코드의 첫 줄에만 작성이 가능하다.
    }

    @Override
    public String toString() {
        return "MemberConstruct{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", grade=" + grade +
                '}';
    }
}
