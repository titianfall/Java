class Student{
        private String name;
        private int id;

        public Student(String name, int id){
            this.name=name;
            this.id=id;
        }
        //@Override
        public boolean equals(Student a){
            return this.name.equals(a.name)&&this.id == a.id;
        }
        @Override
        public String toString(){
            return "학번이 "+id+"인 "+name;
        }
    }
public class EX1{
    
    public static void main(String[] args){
        
        Student a = new Student("박지호", 2271344);
        Student b = new Student("박지호", 2271344);
        System.out.println(a);
        if(a.equals(b)){
            System.out.println("동일학생");
        }
        else{
            System.out.println("다른 학생");
        }
    }
}