class Song{
    private String title;
    private String singer;
    private int year;
    private String lang;

    public Song(){
        this("","",0,"");
    }
    public Song(String t,String s,int y,String l){
        title= t;
        singer =s;
        year = y;
        lang =l;
    }

    public void show(){
        System.out.println(year +"년 "+lang+"의 "+singer+"가 부른 "+title);
    }
}


public class p5 {
    public static void main(String[] args) {
        Song song =new Song("가로수 그늘 아래","이문세",1988,"한국");
        song.show();
    }
}
