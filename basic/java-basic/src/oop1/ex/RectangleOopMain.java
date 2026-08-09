package oop1.ex;

import java.awt.*;

public class RectangleOopMain {

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(5, 8);

        System.out.println("Area of rectangle: " + rectangle.calculateArea());
        System.out.println("Perimeter of rectangle: " + rectangle.calculatePerimeter());
        System.out.println("isSquare of rectangle: " + rectangle.isSquare());
    }
}
