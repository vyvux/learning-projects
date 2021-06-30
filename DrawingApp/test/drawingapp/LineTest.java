package drawingapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {
    private Line line;

    @BeforeEach
    public void setUpClass(){
        Point startingPoint = new Point(100, 150);
        Point endingPoint = new Point(200, 30);
        Color color = Color.blue;
        line = new Line(startingPoint, color, endingPoint);
    }

    @DisplayName("Test top left position of line's bounding rectangle")
    @Test
    void testTopLeftLocation(){
        assertEquals(100, line.getLocation().x);
        assertEquals(30, line.getLocation().y);
    }

    @DisplayName("Test move line accordingly to mouse point variance")
    @Test
    void testMoveShape(){
        line.startMoving();
        line.moveShape(50, 70);
        assertEquals(150, line.getLocation().x);
        assertEquals(100, line.getLocation().y);
    }

    @DisplayName("Test terminate moving, line comes back its original location")
    @Test
    void testTerminateMove() {
        line.startMoving();
        line.moveShape(50, 70);
        line.terminateMove();
        assertEquals(100, line.getLocation().x, "return top-left position");
        assertEquals(30, line.getLocation().y, "return top-left position");
    }
}