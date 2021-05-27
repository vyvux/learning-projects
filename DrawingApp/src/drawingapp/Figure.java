package drawingapp;

import javax.swing.*;
import java.awt.*;

/**
 * The class to store information of a figure
 */
public class Figure extends JComponent {
    int x, oriX;
    int y, oriY;
    int bounding_height;
    int bounding_width;
    final Color color;

    /**
     * The constructor to create Figure object
     *
     * @param position Point object of top left position of the bounding rectangle
     * @param size Dimension object of width and height of the bounding rectangle
     * @param pickedColor The figure color
     */
    public Figure(Point position, Dimension size, Color pickedColor) {
        x = oriX = position.x;
        y = oriY = position.y;
        bounding_height = size.height;
        bounding_width = size.width;
        color = pickedColor;
    }

    /**
     * The constructor to create Figure object that doesn't need to specific bounding rectangle
     *
     * @param position Point object of starting position
     * @param pickedColor The figure color
     */
    public Figure(Point position, Color pickedColor){
        x = oriX = position.x;
        y = oriY = position.y;
        color = pickedColor;
    }

    /**
     * The method to draw figure on a graphic object
     *
     * @param page the graphic object
     */
    public void draw(Graphics page){ }

//    /**
//     * The method to get the figure color
//     *
//     * @return figure color
//     */
//    public Color getColor() { return color; }
//
//
//    /**
//     * The method to get the height of bounding rectangle
//     *
//     * @return bounding height
//     */
//    @Override
//    public int getHeight() { return bounding_height; }
//
//    /**
//     * The method to get the width of bounding rectangle
//     *
//     * @return bounding width
//     */
//    @Override
//    public int getWidth() { return bounding_width; }
//
//    /**
//     * The method to get the X top left position of the bounding rectangle
//     *
//     * @return the X
//     */
//    @Override
//    public int getX() { return x; }
//
//    /**
//     * The method to get the Y top left position of the bounding rectangle
//     *
//     * @return the Y
//     */
//    @Override
//    public int getY() { return y; }

    /**
     * The method to enlarge the figure bounding rectangle
     */
    public void enlarge(){
        bounding_width *= 1.5;
        bounding_height *= 1.5;
    }

    /**
     * The method to shrink the figure bounding rectangle
     */
    public void shrink(){
        bounding_width *= 0.9;
        bounding_height *= 0.9;
    }

    public void boundingHighlighter(Graphics page){
        page.setColor(Color.orange);
        page.drawRect(x, y, bounding_width, bounding_height);
    }

    public void moveShape(int varianceX, int varianceY){
        x = oriX + varianceX;
        y = oriY+ varianceY;
    }

    public void startMoving(){
        oriX = x;
        oriY = y;
    }

    public void terminateMove(){
        x = oriX;
        y = oriY;
    }
    public boolean containPoint(Point point){
        int pointX = point.x;
        int pointY = point.y;
        if (pointX >= x && pointX <= x+bounding_width && pointY >= y && pointY <= y+bounding_height){
            return true;
        } else return false;
    }


}
