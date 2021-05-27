package drawingapp;

import java.awt.*;

public class Circle extends Figure {

    public Circle(Point position, Dimension size, Color pickedColor) {
        super(position,size, pickedColor);
    }

    @Override
    public void draw(Graphics page){
        page.setColor(color);
        page.drawOval(x, y, bounding_width,bounding_height);
    }

}
