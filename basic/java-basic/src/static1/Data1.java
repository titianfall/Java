package static1;

/**
 * 인스턴스 변수로 카운트를 세는 경우
 * count가 인스턴스마다 새로 만들어지므로 항상 1이 출력된다.
 */
public class Data1 {

    public String name;
    public int count;

    public Data1(String name) {
        this.name = name;
        count++;
    }
}
