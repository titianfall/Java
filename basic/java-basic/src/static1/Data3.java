package static1;

/**
 * static 변수(정적 변수, 클래스 변수)로 카운트를 세는 경우
 * count는 인스턴스가 아니라 Data3 클래스에 하나만 존재한다.
 */
public class Data3 {

    public String name;
    public static int count; // static

    public Data3(String name) {
        this.name = name;
        count++;
    }
}
