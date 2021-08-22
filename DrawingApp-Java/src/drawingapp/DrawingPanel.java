package drawingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The drawing panel to draw figures on
 */
public class DrawingPanel extends JPanel implements Constants {
    private final ControlPanel controlPanel;
    private Color color;
    private boolean drawLine; // control when to draw temporary lines
    private boolean moveItem; // control when to move item
    private boolean inAction; // control when the action is successfully performed
    private Point position; // the most current mouse click position
    private Point previousPoint; // the first point to draw line
    private Point movingPoint; // the moving mouse position
    private Figure itemInMove; // the selected figure to be moved
    private Figure hoveredItem; // the chosen item to be moved


    /**
     * Constructor: Sets up this drawing panel
     *
     * @param controlPanel the control panel
     */
    public DrawingPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        color = Color.red;
        drawLine = moveItem = inAction = false;
        position = previousPoint = movingPoint = null;
        itemInMove = hoveredItem = null;
        setBackground(Color.white);
        setLayout(null);
        addMouseListener(new ActionHandler()); // listen to mouseClicked method
        addMouseMotionListener(new ActionHandler()); // listen to mouseMoved method
    }

    /**
     * Draws all figure items of line, circle, square, and cross
     * AND temporary line during while user haven't clicked the ending point
     * AND highlighting bounding rectangle of the item that would be selected based on the current mouse position
     *
     * @param page the Graphics object
     */
    @Override
    public void paintComponent(Graphics page){
        super.paintComponent(page);

        // draw temporary line accordingly to mouse motion
        if (drawLine){
            page.setColor(color);
            page.drawLine(position.x, position.y , movingPoint.x, movingPoint.y);
        }

        // Draw an orange bounding rectangle of hovered Figure item
        // Larger bounding rectangle for Square item
        if (hoveredItem!=null){
            page.setColor(Color.orange);
            if (hoveredItem instanceof Rect){
                page.drawRect(hoveredItem.getX()-2, hoveredItem.getY()-2, hoveredItem.getWidth()+3, hoveredItem.getHeight()+3);
            } else {
                page.drawRect(hoveredItem.getX(), hoveredItem.getY(), hoveredItem.getWidth(), hoveredItem.getHeight());
            }
        }
    }

    /**
     * Sets the color for drawing
     *
     * @param pickedColor the selected color
     */
    public void setColor(Color pickedColor) {
        color = pickedColor;
    }


    /**
     * @return the current selected color (use for testing)
     */
    public Color getColor() {
        return color;
    }

    /**
     * Stops drawing line and moving figure
     */
    public void terminateDrawingLineAndMoveAction(){
        // during drawing line
        if (drawLine) drawLine = false;

        // during moving item
        if (moveItem) {
            moveItem = false;
            itemInMove.terminateMove(); // set the figure back to its original location before moving
        }
    }

    /**
     * Determines whether a component excepting Line contains a point
     *
     * @param component the component to be assessed
     * @param point     the point to be assessed
     * @return true if the component is not a Line and contains the point, otherwise false
     */
    public boolean isFoundFigureIgnoreLine(Component component, Point point) {
        int theX = point.x - component.getX(); // the real X according to the component location
        int theY = point.y - component.getY(); // the real Y according to the component location
        return component.contains(theX, theY) && !(component instanceof Line);
    }


    /**
     * Checks which figure excepting Line the current mouse position would perform the action on
     *
     * @param leastRecently true: find the LEAST current figure, false: find the MOST current figure
     * @param point         the current mouse position
     * @return the Figure object if found, otherwise return null
     */
    public Figure findFigureIgnoreLine(boolean leastRecently, Point point) {
        Component[] components = this.getComponents();
        if (leastRecently) { // assess the components in drawing panel from the beginning
            for (int i = 0; i <= components.length - 1; i++) {
                if (isFoundFigureIgnoreLine(components[i], point))
                    return (Figure) components[i];
            }
        } else { // assess the components in drawing panel from the end
            for (int i = components.length - 1; i >= 0; i--) {
                if (isFoundFigureIgnoreLine(components[i], point))
                    return (Figure) components[i];
            }
        }

        return null; // return null if the point doesn't belong to any figure
    }


    /**
     * Checks which figure (considering all types) the current mouse position would perform the action on
     *
     * @param point the current mouse position
     * @return the Figure object if found, otherwise return null
     */
    public Figure findLeastRecentFigureConsiderLine(Point point) {
        Component c = getComponentAt(point);
        return (c == null || c == this) ? null : (Figure) c; // could return null or the container (this) if not found
    }

    /**
     * Adds figure to drawing panel accordingly to selected type
     */
    public void addFigure() {
        switch (controlPanel.getFigureMenus()[0].getSelectedIndex()) {
            case 0 -> { // Circle
                Figure circle = new Circle(position, defaultDimension, color);
                add(circle);
            }
            case 1 -> { // Square
                Figure square = new Rect(position, defaultDimension, color);
                add(square);
            }
            case 2 -> { // Cross
                Figure cross = new Cross(position, defaultDimension, color);
                add(cross);
            }
            case 3 -> { // Line
                if (!drawLine) { // first click action - haven't drawn line
                    drawLine = true; // start drawing temp line
                    previousPoint = position; // record the 1st point
                } else { // second click action - is drawing line
                    drawLine = false; // stop drawing temp line
                    Figure line = new Line(previousPoint, color, position);
                    add(line);
                }
            }
        }
    }

    /**
     * Performs action Delete/Enlarge/Shrink on the affected figure if found,
     * other wise prints out not found message
     */
    public void performDeleteEnlargeShrink() {
        int figureAction = controlPanel.getFigureMenus()[1].getSelectedIndex();
        inAction = true; // perform the action until user successfully select an item

        // find figure accordingly
        Figure item = null;
        switch (figureAction) {
            case 1 -> item = findLeastRecentFigureConsiderLine(position); // Delete
            case 2 -> item = findFigureIgnoreLine(true, position); // Enlarge
            case 3 -> item = findFigureIgnoreLine(false, position); // Shrink
        }

        // once a figure is found
        if (item != null) {
            inAction = false; // stop the action
            // perform action accordingly
            switch (figureAction) {
                case 1 -> remove(item); // Delete
                case 2 -> item.enlarge(); // Enlarge
                case 3 -> item.shrink(); // Shrink
            }
            hoveredItem = null; // refresh the affected figure
            controlPanel.setFigureActionNone(); // reset action to None
        } else { // notify if no figure is found
            System.out.println("The point doesn't belong to any bounding rectangle");
        }
    }


    /**
     * Performs Move action by determining which stage the move mode is happening in
     * 1st stage: Find figure and start moving if found, other wise prints out not found message
     * 2nd stage: Selected item is moving around, then stops moving action
     */
    public void moveAction() {
        // 1st Stage
        if (!moveItem) {
            inAction = true; // perform the action until user successfully select an item
            // find figure
            Figure item = findLeastRecentFigureConsiderLine(position);
            // once a figure is found
            if (item != null) {
                inAction = false; // stop finding
                moveItem = true; // start moving (used for mouse listener)
                itemInMove = item; // assign found figure to the object would be moved in mouse listener
                item.startMoving(); // record the original location in case of reversion
            } else {
                System.out.println("The point doesn't belong to any bounding rectangle");
            }
        }

        // 2nd stage
        else {
            moveItem = false; // stop moving item
        }
    }

    /**
     * Represents the listeners for all mouse events
     */
    private class ActionHandler extends MouseAdapter {

        /**
         * Responds to all the mouse presses on the drawing panel according to selected items of 2 figure menus
         *
         * @param e the MouseEvent to respond to
         */
        @Override
        public void mousePressed(MouseEvent e) {
            position = new Point(e.getX(), e.getY()); // record the current position for every mouse press
            System.out.println("Mouse pressed at (" + e.getX() + "," + e.getY() + ")");

            // Perform action according to the selected item in the Figure Action ComboBox
            int figureAction = controlPanel.getFigureMenus()[1].getSelectedIndex();
            switch (figureAction) {
                case 0 -> addFigure(); // None
                case 1, 2, 3 -> performDeleteEnlargeShrink(); // Delete, Enlarge, Shrink
                case 4 -> { // Move (separate with other actions because it requires 2 valid clicks to successfully perform the move action)
                    moveAction();
                    hoveredItem = null; // refresh the affected figure
                    if (!moveItem && !inAction) controlPanel.setFigureActionNone(); // reset action to None
                }
            }

            repaint();
        } // end mousePressed()

        /**
         * Responds to mouse move events on the drawing panel
         * to determine the affected item when the cursor hovered on
         * and relocate the figure during move action
         *
         * @param e the MouseEvent to respond to
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            movingPoint = new Point(e.getX(), e.getY()); // record the moving mouse position
            e.consume();

            // 1. Determine which figure would be affected by the current the mouse position
            // When user had selected action among Delete, Enlarge, Shrink, and Move, and hovers on the drawing panel
            int figureAction = controlPanel.getFigureMenus()[1].getSelectedIndex();
            if (figureAction != 0 && !moveItem) { // perform in the actions other than None, and not perform during a selected figure is moving
                hoveredItem = null;
                switch (figureAction) { // find the item according to types of actions
                    case 1, 4 -> hoveredItem = findLeastRecentFigureConsiderLine(movingPoint);// Delete and Move
                    case 2 -> hoveredItem = findFigureIgnoreLine(true, movingPoint); // Enlarge
                    case 3 -> hoveredItem = findFigureIgnoreLine(false, movingPoint);// Shrink
                }

                // when a figure is found, set text of Current Action label to the figure type of hovered item
                if (hoveredItem != null) {
                    controlPanel.setCurrentAction(hoveredItem);
                } else {
                    controlPanel.setCurrentAction();// set text to the current selected figure of Figure Type JComboBox
                }
            }

            // 2. Move selected figure according to the moving mouse
            if (moveItem){
                itemInMove.moveShape(movingPoint.x - position.x, movingPoint.y - position.y);
            }

            repaint();

        } // end mouseMoved()


    }// end ActionHandler
}
