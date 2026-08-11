package static2;

/**
 * 인스턴스 메서드로 만든 유틸리티
 * 사용하려면 의미 없는 인스턴스를 매번 생성해야 한다.
 */
public class DecoUtil1 {

    public String deco(String str) {
        String result = "*" + str + "*";
        return result;
    }
}
