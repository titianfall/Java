package lang.Object.tostring;

public class ToStringMain1 {
    public static void main(String[] args) {
        Object object = new Object();
        String string = object.toString();

        // object.getClass().getName() + "@" + Integer.toHexString(hashCode());
        System.out.println(string);

        System.out.println(object);
    }
}
