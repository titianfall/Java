package lang.Object.equals;

public class Identity {
    public static void main(String[] args) {
        User a = new User("id-100");
        User b = new User("id-100");

        System.out.println(a == b); // false 다른 객체
        System.out.println(a.getId().equals(b.getId())); // 동일한 id
    }
}
