import java.util.Scanner;

public class Ex5 {
    public static void main(String[] args) {
        //Scanner 클래스 객체 생성
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("여러 과목의 학점을 빈 칸으로 분리 입력");
            //scanner.nextLine()을 쓰게되면 자동으로 버퍼를 비워줌
            String line = scanner.nextLine();
            System.out.println("입력값: [" + line + "]");
            //무한루프 정지 조건
            if(line.equals("그만")){ break; }
            //입력받은 학점들을 띄어쓰기 기준으로 분리 저장
            String[] gradeArray = line.split(" ");
            //잘못된 입력인지 확인을 위해
            boolean isError = false;
            String errorInput = new String();
            double sum=0;//평균을 위한 합산 변수

            for(String s : gradeArray){
                switch(s){
                    //학점대로 점수 더해주기
                    case "A" : case "a": sum+=100; break;
                    case "B" : case "b": sum+=90; break;
                    case "C" : case "c": sum+=80; break;
                    case "D" : case "d": sum+=70; break;
                    case "F" : case "f": sum+=0; break;
                    //잘못된 입력
                    default:
                        isError = true;
                        errorInput = s;
                        break;
                }
            }
            if(isError == true){
                System.out.println("입력 오류: "+errorInput);
            }
            else{
                System.out.println("평균은 "+sum/gradeArray.length);
            }
        }
        scanner.close();
    }
}
