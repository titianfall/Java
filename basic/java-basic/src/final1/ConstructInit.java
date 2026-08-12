package final1;

public class ConstructInit {
    final int value; // 해당 필드는 생성자를 통해서 한번만 초기화가 가능하다.

    public ConstructInit(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConstructInit{" +
                "value=" + value +
                '}';
    }
}
