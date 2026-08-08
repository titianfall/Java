class Person { }
class Student extends Person { }
class Researcher extends Person { }
class Professor extends Researcher { }

public class ex5 {
    static void print(Person p){
        if(p instanceof Person){
            System.out.println("Person");
        }
        if(p instanceof Student){
            System.out.println("Student");
        }
        if(p instanceof Researcher){
            System.out.println("Researcher");
        }
        if(p instanceof Professor){
            System.out.println("Professor");
        }
    }    
    public static void main(String[] args) {
        System.out.println("new Student() "); print(new Student());
        System.out.println("new Researcher() "); print(new Researcher());
        System.out.println("new Professor() "); print(new Professor());
    }

}
