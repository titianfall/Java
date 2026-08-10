package construct.ex;

public class Book {

    String title;
    String author;
    int page;

    // todo
    public Book(String title, String author, int page) {
        this.title = title;
        this.author = author;
        this.page = page;
    }

    public Book(String title, String author) {
        this(title, author, 0);
    }

    public Book() {
        this("", "", 0);
    }

    void displayInfo() {
        System.out.println("title : " + title + " author : " + author + " page : " + page );
    }
}
