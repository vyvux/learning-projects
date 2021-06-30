package drawingapp;

import java.awt.*;

/**
 * The class representing a Rectangle
 */
public class Rect extends Figure {

    /**
     * Constructor: Creates the Square
     *
     * @param position Point object of top left position of the bounding rectangle
     * @param size Dimension object of width and height of the bounding rectangle
     * @param pickedColor The figure color
     */
    public Rect(Point position, Dimension size, Color pickedColor) {
        super(position, size, pickedColor);
    }

    /**
     * Draws square with its upper-left corner at (0,0)
     *
     * @param page the graphic object to draw square on
     */
    @Override
    public void paintComponent(Graphics page){
        super.paintComponent(page);
        page.drawRect(0, 0, getWidth() - 1, getHeight() - 1);  // values getWidth() and getHeight() overhang the bounding
        // make right and bottom sides invisible
    }

}
