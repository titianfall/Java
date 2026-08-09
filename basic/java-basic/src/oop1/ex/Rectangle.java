package oop1.ex;

public class Rectangle {
    int width;
    int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // 넓이
    public int calculateArea(){
        return width * height;
    }

    // 둘레
    public int calculatePerimeter(){
        return 2 * (width + height);
    }

    // 정사각형 유무
    public boolean isSquare(){
        return width == height;
    }
}
