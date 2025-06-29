import java.util.Scanner;

public class p13 {
    public static void main(String[] args) {
		String course [] = {"C", "C++", "Python", "Java", "HTML5" };
		String grade [] = {"A", "B+", "B", "A+", "D"};

		Scanner scanner = new Scanner(System.in);
		String courseName;
		boolean isCourse;
		
		while(true) {
			isCourse = false;
			
			System.out.print("과목>>");
			courseName = scanner.next();
			
			if(courseName.equals("그만"))
				break;
			
			for(int i=0; i<course.length; i++) {
				if(course[i].equals(courseName)) {
					System.out.println(courseName + " 학점은 " + grade[i]);
                    break;
				}
                else if(i == course.length - 1){
                    System.out.println(courseName+"은 없는 과목입니다.");
                }
			}

		}
		scanner.close();
	}
}
