class Book{
    private String writer;
    private String bookName;
    private String customer;

    public Book(String writer, String bookName, String customer){
        this.writer  = writer;
        this.bookName =bookName;
        this.customer = customer;
    }

    @Override
    public String toString(){
        return customer+"이 구입한 도서: "+writer+"의 "+bookName;
    }

    public Boolean equals(Book a){
        return this.writer.equals(a.writer)&&this.bookName.equals(a.bookName)&&this.customer.equals(customer);
    }
}
public class Ex2 {
    public static void main(String[] args) {
        Book a = new Book("황기태", "명품자바", "김하진");
		Book b = new Book("황기태", "명품자바", "하여린");
		System.out.println(a);
		System.out.println(b);
	
		if(a.equals(b))
			System.out.println("같은 책");
		else
			System.out.println("다른 책");
    }
}
