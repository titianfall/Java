import java.util.Scanner;
public class ex4 {
    public static void main(String[] args) {
        System.out.println("string string int double boolean");

        Scanner scanner = new Scanner(System.in);

        String name = scanner.next();
        System.out.println("이름 "+name);

        String city = scanner.next();
        System.out.println("도시 "+city);

        int age=scanner.nextInt();
        System.out.println("나이 "+age);

        double weight = scanner.nextDouble();
        System.out.println("몸무게 "+weight);

        boolean isSingle = scanner.nextBoolean();
        System.out.println("싱글이야? "+isSingle);

        scanner.close();
    }
}
