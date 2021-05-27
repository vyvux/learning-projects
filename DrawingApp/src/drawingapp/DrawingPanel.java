package drawingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DrawingPanel extends JPanel implements Constants {
    final DrawingFrame frame;
    ArrayList<Figure> shapes = new ArrayList<>(); // the storage for figure items
    Color color;
    boolean drawLine, moveItem, inAction = false; // control when to draw temporary lines, to draw moving item, to wait for successful action
    Point position, previousPoint; // point of the most current mouse click
    Figure itemInMove, hoveredItem; // temporary lines or moving items| the chosen item to be moved
    int movingX, movingY; // index of the chosen item to be moved in the list of shapes


    public DrawingPanel(DrawingFrame drawFrame){
        frame = drawFrame;
        color = Color.red;
        this.setBackground(Color.white);
        this.setLayout(null);
        this.addMouseListener(new ActionHandler()); // listen to mouseClicked method
        this.addMouseMotionListener(new ActionHandler()); // listen to mouseMoved method
    }

    public void paintComponent(Graphics page){
        super.paintComponent(page);

        // disable Figure Action JComboBox while no available shape to be affected by actions
        if (!shapes.isEmpty() && frame.figureAction.getItemCount()==1) {
            frame.enableFigureActions(true);
        }
        if (shapes.isEmpty()) frame.enableFigureActions(false);

        // the loop to draw all figure items in the list
        for (Figure item : shapes){
            if ( item!=null){
                item.draw(page);
            }
        }

        // draw temporary line accordingly to mouse motion
        if (drawLine){
            page.setColor(color);
            page.drawLine(position.x, position.y , movingX, movingY);
        }

        // draw temporary moving figure item
        if (moveItem ){
            itemInMove.moveShape(movingX - position.x, movingY - position.y);
        }

        // draw an orange bounding rectangle of hovered Figure item
        if (hoveredItem!=null){
            hoveredItem.boundingHighlighter(page);
        }

    }

    public void setShapeColor(Color pickedColor){
        color = pickedColor;
    }

    public void terminateDrawingLineAndMove(){
        if (drawLine) drawLine = false;
        if (moveItem){
            moveItem = false;
            itemInMove.terminateMove();
        }
    }

    private class ActionHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            position = new Point(e.getX(),e.getY());
            System.out.println("Mouse pressed at (" + e.getX()+","+e.getY()+")");

            String figureAction = frame.getSelectedFigureAction();


            // draw figure on click only in NONE action => draw figure
            if (figureAction.equals("None")){
                switch (frame.getSelectedFigureType()){
                    case "Circle" -> {
                        Figure circle = new Circle(position, defaultDimension, color);
                        shapes.add(circle);
                    }
                    case "Square" -> {
                        Figure square = new Rect(position,defaultDimension, color);
                        shapes.add(square);
                    }
                    case "Cross" -> {
                        Figure cross = new Cross(position,defaultDimension, color);
                        shapes.add(cross);
                    }
                    case "Line" -> {
                        if (!drawLine){ // first click action - haven't drawn line
                            drawLine = true; // start drawing temp line
                            previousPoint = position;
                        }
                        else { // second click action - is drawing line
                            drawLine = false; // stop drawing temp line
                            Line line = new Line(previousPoint, color, position);
                            shapes.add(line); // add the most recent tempLine to shape list
                        }
                    }
                }
            }

            // Figure actions other than NONE and MOVE
            if (!figureAction.equals("Move")){
                switch (figureAction){
                    case "Delete" -> deleteAction();
                    case "Enlarge" -> enlargeAction();
                    case "Shrink" -> shrinkAction();
                }
                hoveredItem = null;
                if (!inAction) frame.setFigureActionNone();
            }

            // MOVE action: wait till the second click to set figure action to NONE
            if (figureAction.equals("Move")){
                moveAction();
                hoveredItem = null;
                if (!moveItem && !inAction) frame.setFigureActionNone();
            }

            repaint();
        } // end mouseClick()

        @Override
        public void mouseMoved(MouseEvent e) {
            movingX = e.getX();
            movingY = e.getY();
            Point movingPoint = new Point(movingX, movingY);
            e.consume();

            // find if the mouse is belong to any bounding rectangle
            String figureAction = frame.getSelectedFigureAction();
            if (!figureAction.equals("None") && !moveItem){
                hoveredItem = null;
                switch (figureAction){
                    case "Delete", "Move" -> hoveredItem = checkItemList(true,false, movingPoint);
                    case "Enlarge" -> hoveredItem = checkItemList(true, true, movingPoint);
                    case "Shrink" ->  hoveredItem = checkItemList(false, true, movingPoint);
                }
                if (hoveredItem!=null) {
                    frame.setCurrentAction(hoveredItem); // set text to the figure type of hovered item
                } else {
                    frame.setCurrentAction(); // set text to the current selected item of Figure Type JComboBox
                }
            }

            repaint();

        } // end mouseMoved()


        public void deleteAction(){
            inAction = true; // perform the action until user successfully select an item
            // assess items from the beginning of the list and delete only 1 shape
            Figure item = checkItemList(true,false,position);
            if (item != null){
                inAction = false;
                shapes.remove(item);
            } else {
                System.out.println("The point doesn't belong to any bounding rectangle");
            }
        }

        public void enlargeAction(){
            inAction = true; // perform the action until user successfully select an item
            // assess items from the beginning of the list and enlarge only 1 shape
            Figure item = checkItemList(true,true,position);
            if (item!=null){
                inAction = false;
                item.enlarge();
            } else {
                System.out.println("The point doesn't belong to any bounding rectangle");
            }
        }


        public void shrinkAction(){
            inAction = true;
            // assess items from the end of the list and shrink only 1 shape
            Figure item = checkItemList(false,true,position);
            if (item!= null){
                inAction = false;
                item.shrink();
            } else {
                System.out.println("The point doesn't belong to any bounding rectangle");
            }
        }

        public void moveAction(){
            // 1. first click of Move mode
            if (!moveItem){
                inAction = true;
                // assess items from the beginning of the list and delete only 1 shape
                Figure item = checkItemList(true,false,position);
                if (item!=null){
                    inAction = false;
                    moveItem = true;
                    itemInMove = item;
                    item.startMoving();
                } else {
                    System.out.println("The point doesn't belong to any bounding rectangle");
                }
            }

            // 2. second click of Move mode
            else {
              moveItem = false; // stop moving item
            }
        } // end moveAction()

        public Figure checkItemList(boolean fromBeginning, boolean ignoreLine,Point thePoint){
            Figure affectedFigure = null;
            boolean pointInItem = false;
            // find the least recently added item: Enlarge +  Delete + Move
            if (fromBeginning){
                for (int i = 0; i<=shapes.size()-1 && !pointInItem; i++) {
                    Figure figure = shapes.get(i); // each item in the list of figures
                    if (figure.containPoint(thePoint)) {
                        if (!ignoreLine || !(figure instanceof Line)){
                            affectedFigure =  figure;
                            pointInItem = true;
                        }
                    }
                }
            }

            // find the most recently added item: Shrink
            if (!fromBeginning){
                for (int i = shapes.size()-1; i>=0 && !pointInItem; i--) {
                    Figure figure = shapes.get(i);
                    if (figure.containPoint(thePoint)) {
                        if (!ignoreLine || !(figure instanceof Line)) {
                            affectedFigure = figure;
                            pointInItem = true;
                        }
                    }
                }
            }
            return affectedFigure;
        }

    }// end ActionHandler

}
