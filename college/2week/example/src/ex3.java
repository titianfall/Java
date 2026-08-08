public class ex3 {
    public static void main(String[] args) {
        byte b=127;
        int i = 100;
        System.out.println(b+i); //227
        System.out.println(10/4);//2
        System.out.println(10.0/4);//2.5
        System.out.println((char)0x12340041);
        //16진수인 0x12340041은 32비트의 int값인데
        //java의 char은 2바이트 16비트이므로 
        //하위 2바이트 16비트만 남으므로 
        //0x0041이 되고 유니코드 문자 A에 해당되어 0x41 'A'출력
        System.out.println((int)2.9+1.8);//3.8
        System.out.println((int)(2.9+1.8));//4
        System.out.println((int)2.9+(int)1.8);//3
        System.out.println((byte)(128+128)+(int)22.9);
        //byte의 경우 오버플로우일경우 -256이라 생각하고 더하기
    }
}
