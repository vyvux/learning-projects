package drawingapp;

import java.awt.*;

/**
 * The class representing a Line
 */
public class Line extends Figure {
    private Point start, end;

    /**
     * Constructor: Sets up the Line object, resets location, size, starting point and ending point
     *
     * @param position The starting point of the Line based on the drawing panel location
     * @param pickedColor The figure color
     * @param endPoint The end point of the Line based on the drawing panel location
     */
    public Line(Point position, Color pickedColor, Point endPoint) {
        super(position, pickedColor);
        Point topLeft = new Point(Math.min(position.x, endPoint.x), Math.min(position.y, endPoint.y));
        setLocation(topLeft);

        // calculate the size of bounding rectangle
        int width = Math.max(Math.abs(position.x - endPoint.x), 1); // if the line is parallel with y axis, bounding width is 1 pixel
        int height = Math.max(Math.abs(position.y - endPoint.y), 1); // if the line is parallel with x axis, bounding height is 1 pixel
        setSize(width, height);

        // determine starting and ending points based on top left point of bounding rect at (0,0)
        start = new Point(position.x - topLeft.x, position.y - topLeft.y);
        end = new Point(endPoint.x - topLeft.x, endPoint.y - topLeft.y);
    }

    /**
     * Draws line with its upper-left corner at (0,0)
     *
     * @param page the graphic object to draw line on
     */
    @Override
    public void paintComponent(Graphics page){
        super.paintComponent(page);
        page.drawLine(start.x, start.y, end.x, end.y);
    }

}
