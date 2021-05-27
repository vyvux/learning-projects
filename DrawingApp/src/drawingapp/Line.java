package drawingapp;

import java.awt.*;

/**
 * The class to store information of a Line
 */
public class Line extends Figure {
    int startX, oriStartX;
    int startY, oriStartY;
    int endX, oriEndX;
    int endY, oriEndY;

    /**
     * The constructor to create Line object
     *
     * @param position Point object of the line starting point
     * @param pickedColor The line color
     * @param endPosition Point object of the line ending point
     */
    public Line(Point position, Color pickedColor, Point endPosition) {
        super(position, pickedColor);
        startX = oriStartX = position.x;
        startY = oriStartY = position.y;
        endX = oriEndX = endPosition.x;
        endY = oriEndY = endPosition.y;
    }

    /**
     * The method to draw line on a graphic object
     *
     * @param page the graphic object to draw line on
     */
    @Override
    public void draw(Graphics page){
        page.setColor(color);
        page.drawLine(startX,startY,endX,endY);
    }

    @Override
    public void boundingHighlighter(Graphics page){
        page.setColor(Color.orange);
        page.drawRect(Math.min(startX,endX), Math.min(startY,endY), Math.abs(endX - startX), Math.abs(endY - startY));
    }

    @Override
    public void moveShape(int varianceX, int varianceY){
        startX = oriStartX+ varianceX;
        startY = oriStartY+ varianceY;
        endX = oriEndX+ varianceX;
        endY = oriEndY + varianceY;
    }

    @Override
    public void startMoving(){
        oriStartX = startX;
        oriStartY = startY;
        oriEndX = endX;
        oriEndY = endY;
    }

    @Override
    public void terminateMove(){
        startX = oriStartX;
        startY = oriStartY;
        endX = oriEndX;
        endY = oriEndY;
    }

    @Override
    public boolean containPoint(Point point){
        int minX = Math.min(startX, endX);
        int minY = Math.min(startY, endY);
        int maxX = Math.max(startX, endX);
        int maxY = Math.max(startY, endY);
         if (point.x >= minX && point.x <= maxX && point.y >= minY && point.y <= maxY){
            return true;
        } else return false;
    }
}
