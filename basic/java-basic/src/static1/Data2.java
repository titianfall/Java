package static1;

/**
 * 카운터 인스턴스를 외부에서 받아서 공유하는 경우
 * 값 공유에는 성공하지만, 생성할 때마다 Counter를 넘겨야 하는 번거로움이 생긴다.
 */
public class Data2 {

    public String name;

    public Data2(String name, Counter counter) {
        this.name = name;
        counter.count++;
    }
}
