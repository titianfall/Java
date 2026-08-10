package construct;

public class MethodInitMain2 {

    public static void main(String[] args) {
        MemberInit member1 = new  MemberInit();
        initMember(member1, "user1", 15, 90);

        MemberInit member2 = new  MemberInit();
        initMember(member2, "user2", 15, 90);

        MemberInit[] members = {member1, member2};

        for (MemberInit member : members) {
            System.out.println("이름: " + member.name + " 나이: " + member.age + " 성적: " + member.grade);
        }
    }

    private static void initMember(MemberInit member1, String name, int age, int grade) {
        member1.name = name;
        member1.age = age;
        member1.grade = grade;
    }
}

