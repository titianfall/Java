class Memo{
    private String name;
    private String clock;
    private String act;

    public Memo(String name, String clock, String act){
        this.name =name;
        this.clock = clock;
        this.act =act;
    }

    public void show(){
        System.out.println(name+", "+clock+" "+act);
    }

    public boolean isSameName(Memo m){
        if(name == m.name){
            return true;
        }
        else return false;
    }
    public String getName(){
        return name;
    }
    public int length(){
        return act.length();
    }
}

public class p7 {
    public static void main(String[] args) {
        Memo a =new Memo("유송연","10:10","자바 과제중");
        Memo b =new Memo("박채원","10:15","시카고로 어학연수중");

        a.show();

        if(a.isSameName(b)) System.out.println("동일한 사람임");
        else System.out.println("다른 사람입니다.");
        System.out.println(b.getName()+"이 작성한 메모의 길이는 "+b.length());
    }
}
