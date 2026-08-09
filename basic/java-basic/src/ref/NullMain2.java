package ref;

public class NullMain2 {
    public static void main(String[] args) {
        Data data = null;
        data.value = 10; // null.vlaue = 10 >> java.lang.NullPointerException
        System.out.println(data.value);
    }
}
