package drawingapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class RectTest {
    private Figure square1, square2;
    private Point point1, point2;

    @BeforeEach
    public void setUpClass() {
        point1 = new Point(100, 150);
        Color color1 = Color.red;
        square1 = new Rect(point1, Constants.defaultDimension, color1);

        point2 = new Point(200, 30);
        Dimension dimension2 = new Dimension(80, 80);
        Color color2 = Color.green;
        square2 = new Rect(point2, dimension2, color2);
    }

    @DisplayName("Test enlarge the width/height of bounding rectangle by 1.5 times")
    @Test
    void testEnlarge() {
        square1.enlarge();
        assertEquals(75, square1.getWidth(), "test enlarge cross 1");
        assertEquals(75, square1.getHeight(), "test enlarge cross 1");

        square2.enlarge();
        assertEquals(120, square2.getWidth(), "test enlarge cross 2");
        assertEquals(120, square2.getHeight(), "test enlarge cross 2");
    }

    @DisplayName("Test shrink the width/height of bounding rectangle by 10%")
    @Test
    void testShrink() {
        square1.shrink();
        assertEquals(45, square1.getWidth(), "test shrink cross 1");
        assertEquals(45, square1.getHeight(), "test shrink cross 1");

        square2.shrink();
        assertEquals(72, square2.getWidth(), "test shrink cross 2");
        assertEquals(72, square2.getHeight(), "test shrink cross 2");
    }

    @DisplayName("Test move item accordingly to mouse point variance")
    @Test
    void testMoveShape(){
        square1.startMoving();
        square1.moveShape(220, 10);
        assertEquals(320, square1.getLocation().x);
        assertEquals(160, square1.getLocation().y);

        square2.startMoving();
        square2.moveShape(99, 100);
        assertEquals(299, square2.getLocation().x);
        assertEquals(130, square2.getLocation().y);
    }

    @DisplayName("Test terminate moving, square comes back its original location")
    @Test
    void testTerminateMove(){
        square1.startMoving();
        square1.moveShape(220, 10);
        square1.terminateMove();
        assertEquals(point1, square1.getLocation());

        square2.startMoving();
        square2.moveShape(99, 100);
        square2.terminateMove();
        assertEquals(point2, square2.getLocation());
    }
}