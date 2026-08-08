import java.io.File;

public class E8 {
    public static void listDirectory(File dir){
        System.out.println("-----"+dir.getPath()+"의 서브리스트 입니다.");

        File[] subFiles = dir.listFiles();

        for(int i=0;i<subFiles.length;i++){
            File f = subFiles[i];
            long t = f.lastModified();//마지막으로 수정된 시간

            System.out.print(f.getName()+"\t 파일 크기: "+f.length());
            System.out.printf("\t수정한 시간: %td %td %ta %tT\n", t, t, t, t);//포맷 출력
        }
    }
    public static void main(String[] args) {
        File f1 = new File("c:\\windows\\system.ini");
        System.out.println(f1.getPath()+", "+f1.getParent()+", "+f1.getName());//경로 , 부모 디렉터리 , 파일(디렉토리) 이름
        //            c:\\windows\\system.ini  c:\\windows        system.ini

        String res = ""; //파일검사를 위해 플래그용 String 객체생성
        if(f1.isFile()) res="파일"; //플래그 재설정
        else if(f1.isDirectory()) res = "디렉토리";
        System.out.println(f1.getPath()+"는 "+res+"입니다."); //파일인지 디렉토리인지 출력

        File f2 = new File("c:\\Temp\\java_Sample"); //새로 만들고자 하는 디렉토리
        if(!f2.exists()){//파일이 존재하는지 검사
            f2.mkdir();
        }

        listDirectory(new File("c:\\Temp"));
        //dir , ls -al 같은 역할
        f2.renameTo(new File("c:\\Temp\\javasample"));

        listDirectory(new File("c:\\Temp"));
    }   
}
