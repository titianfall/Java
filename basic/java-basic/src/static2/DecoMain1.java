package static2;

public class DecoMain1 {

    public static void main(String[] args) {
        String s = "hello java";

        DecoUtil1 utils = new DecoUtil1(); // 상태가 없는데도 인스턴스를 만들어야 한다
        String deco = utils.deco(s);

        System.out.println("before: " + s);
        System.out.println("after: " + deco);
    }
}
