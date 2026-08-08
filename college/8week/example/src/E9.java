import java.io.*;
public class E9 {
    public static void main(String[] args) {
        File src = new File("c:\\windows\\system.ini");
        File dest = new File("c:\\Temp\\system.ini");

        int c;
        try {
            FileReader fr = new FileReader(src);
            FileWriter fw = new FileWriter(dest);

            while((c=fr.read())!=-1){
                fw.write((char)c);
            }
            fr.close();
            fw.close();
            System.out.println(src.getPath()+" to "+dest.getPath()+" 복사 완료");
        } catch (IOException e) {
            System.out.println("경로 확인 필요");
        }
    }
}
