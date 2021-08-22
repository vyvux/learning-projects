package drawingapp;

import javax.swing.*;
import java.awt.*;

/**
 * The class to store information of a figure
 */
public class Figure extends JComponent {
    // variables to store x, y coordinates to relocate the original position
    private int originalX;
    private int originalY;

    /**
     * Constructor: Creates Figure object requiring dimension specification
     *
     * @param position Point object of top left position of the bounding rectangle
     * @param size Dimension object of width and height of the bounding rectangle
     * @param pickedColor The figure color
     */
    public Figure(Point position, Dimension size, Color pickedColor) {
        super();
        setLocation(position);
        setSize(size);
        setForeground(pickedColor);
        originalX = originalY = 0;
    }

    /**
     * Constructor: Creates Figure object that doesn't require dimension specification
     *
     * @param position Point object of top left position of the bounding rectangle
     * @param pickedColor The figure color
     */
    public Figure(Point position, Color pickedColor) {
        super();
        setLocation(position);
        setForeground(pickedColor);
    }

    /**
     * Enlarges the figure bounding rectangle by 50%
     */
    public void enlarge(){
        setSize(new Dimension((int) (getWidth() * 1.5), (int) (getHeight() * 1.5)));
    }

    /**
     * Shrinks the figure bounding rectangle by 10%
     */
    public void shrink(){
        setSize(new Dimension((int) (getWidth() * 0.9), (int) (getHeight() * 0.9)));
    }


    /**
     * Moves the figure accordingly to the mouse move
     * @param varianceX the difference of X coordinate
     * @param varianceY the difference of Y coordinate
     */
    public void moveShape(int varianceX, int varianceY){
        setLocation(originalX +varianceX, originalY +varianceY);
    }

    /**
     * Records the original X,Y of the figure.
     * (This is exclusively used for later termination of move action,
     * the figure comes back its original location)
     */
    public void startMoving(){
        originalX = getX();
        originalY = getY();
    }

    /**
     * Sets the figure back to its original location when terminate move action
     */
    public void terminateMove(){
        setLocation(originalX, originalY);
    }


}
