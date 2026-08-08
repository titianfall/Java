import java.io.*;
import java.util.*;
public class P8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File file = new File("c:");
        File [] subFiles = file.listFiles();

        int max=0,index=0;
        for(int i=0;i< subFiles.length;i++){
            if(max<(int)subFiles[i].length()){
                max=(int)subFiles[i].length();
                index=i;
            }
        }
        System.out.println("가장 큰 파일은 "+subFiles[index].getPath()+(int)subFiles[index].length()+"바이트");
        scanner.close();
    }
}
