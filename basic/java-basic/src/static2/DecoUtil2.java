package static2;

/**
 * 정적 메서드로 만든 유틸리티
 * 인스턴스 상태(필드)에 의존하지 않는 기능은 static이 적합하다.
 */
public class DecoUtil2 {

    public static String deco(String str) {
        String result = "*" + str + "*";
        return result;
    }
}
