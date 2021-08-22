package drawingapp;

import javax.swing.*;

public class DrawingApp {

    public static void main(String[] args) {
        DrawingFrame frame = new DrawingFrame();
        System.out.println("Starting drawing application...");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

