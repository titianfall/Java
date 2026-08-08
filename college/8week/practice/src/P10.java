import java.io.*;
import java.util.*;

public class P10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String,String> map = new HashMap<>();

        try {
            BufferedReader bf = new BufferedReader(new FileReader("c:\\temp\\phone.txt"));
            String str;
            String [] s = new String[2];

            while((str=bf.readLine())!=null){
                StringTokenizer stringTokenizer = new StringTokenizer(str," ");
                int index=0;
                while(stringTokenizer.hasMoreTokens()){
                    s[index]=stringTokenizer.nextToken();
                    index++;
                }
                map.put(s[0],s[1]);
            }


            System.out.println("총 "+map.size()+"개의 전화번호를 읽었습니다.");
            while(true){
                System.out.print("이름>> ");
                String name = scanner.nextLine();
                if(name.equals("그만"))
                    break;
                if(map.containsKey(name)){
                    System.out.println(map.get(name));
                }
                else
                    System.out.println("찾는 이름이 없습니다.");
            }
        }
        catch(IOException e){
            System.out.println("입출력 오류");
        }
        scanner.close();
    }
}
