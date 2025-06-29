import java.util.Scanner;

public class p4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("여행지>>");
        String arrival = scanner.nextLine();

        System.out.print("인원수>>");
        int people = scanner.nextInt();
        int rooms = people /2  + people%2;

        System.out.print("숙박일>>");
        int day=scanner.nextInt();

        System.out.print("1인당 항공료>>");
        int airplane = scanner.nextInt();

        System.out.print("1방 숙박비>>");
        int room = scanner.nextInt();

        int cost = airplane* people + room * day*rooms;
        System.out.println(people+"명의 "+arrival+" "+(day-1)+"박 "+day+"일 여행에는 방이 "+rooms+"개 필요하며 경비는 "+cost+"입니다.");
        scanner.close();
    }
}
