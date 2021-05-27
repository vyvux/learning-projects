package drawingapp;

import java.awt.*;

public class Rect extends Figure {

    public Rect(Point position, Dimension size, Color pickedColor) {
        super(position, size, pickedColor);
    }

    @Override
    public void draw(Graphics page){
        page.setColor(color);
        page.drawRect(x,y, bounding_width, bounding_height);
    }

    @Override
    public void boundingHighlighter(Graphics page){
        page.setColor(Color.orange);
        page.drawRect(x-2, y-2, bounding_width+4, bounding_height+4);
    }

}
