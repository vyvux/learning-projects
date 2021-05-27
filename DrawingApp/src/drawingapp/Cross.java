package drawingapp;

import java.awt.*;

public class Cross extends Figure {

    public Cross(Point position, Dimension size, Color pickedColor) {
        super(position, size, pickedColor);
    }

    @Override
    public void draw(Graphics page){
        page.setColor(color);
        page.drawLine(x,y,x+bounding_width,y+ bounding_height);
        page.drawLine(x,y+ bounding_height,x+bounding_width,y);
    }

}
