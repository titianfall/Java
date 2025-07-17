import java.util.*;

class Student{
    private int id;
    private String tel;
    public Student(int id, String tel){this.id=id; this.tel=tel;}
    public int getId(){return id;}
    public String getTel(){return tel;}
}

public class E7 {
    public static void main(String[] args) {
        var map = new HashMap<String, Student>();
        //객체를 저장한다!
        map.put("황기태", new Student(1,"010-0000-0000"));
        map.put("이재문", new Student(2,"010-1111-1111"));

        var scanner = new Scanner(System.in);

        while(true){
            System.out.print("검색할 이름?");
            String findName = scanner.nextLine();
            if(findName.equals("exit")) break;
            Student student = map.get(findName);
            if(student == null){
                System.out.println(findName+"은 없는 사람입니다.");
            }
            else{
                System.out.println(findName+", "+student.getId()+", "+student.getTel());
            }
        }
        scanner.close();
    }
}
