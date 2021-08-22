package drawingapp;

import java.awt.*;

/**
 * The class representing a Circle
 */
public class Circle extends Figure {

    /**
     * Constructor: Creates the Circle
     *
     * @param position Point object of top left position of the bounding rectangle
     * @param size Dimension object of width and height of the bounding rectangle
     * @param pickedColor The figure color
     */
    public Circle(Point position, Dimension size, Color pickedColor) {
        super(position, size, pickedColor);
    }

    /**
     * Draws circle with its upper-left corner at (0,0)
     *
     * @param page the graphic object to draw circle on
     */
    @Override
    public void paintComponent(Graphics page){
        super.paintComponent(page);
        page.drawOval(0, 0, getWidth()-1, getHeight()-1);
    }

}
