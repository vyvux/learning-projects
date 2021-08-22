package drawingapp;

import java.awt.*;

/**
 * The class representing a Cross
 */
public class Cross extends Figure {

    /**
     * Constructor: Creates the Cross
     *
     * @param position Point object of top left position of the bounding rectangle
     * @param size Dimension object of width and height of the bounding rectangle
     * @param pickedColor The figure color
     */
    public Cross(Point position, Dimension size, Color pickedColor) {
        super(position, size, pickedColor);
    }

    /**
     * Draws cross with its upper-left corner at (0,0)
     *
     * @param page the graphic object to draw cross on
     */
    @Override
    public void paintComponent(Graphics page){
        super.paintComponent(page);
        page.drawLine(0,0, getWidth()-1, getHeight()-1);
        page.drawLine(0,getHeight()-1,getWidth()-1,0);
    }

}
