import java.io.File;
import java.util.Scanner;

public class P9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count=0;

        File file = new File("c:\\temp");
        File [] subFiles = file.listFiles();

        System.out.println("c:\\temp\\디렉터리의 .txt 파일을 모두 삭제합니다 ... ");
        for(int i=0;i< subFiles.length;i++){
            String string = subFiles[i].getPath();
            int index = string.lastIndexOf(".txt");
            if(index!=-1){
                File f = new File(string);
                System.out.println(string+" 삭제");
                count++;
                f.delete();
            }
        }
        System.out.println("총 "+count+"개의 .txt 파일을 삭제하였습니다.");
        scanner.close();
    }
}
