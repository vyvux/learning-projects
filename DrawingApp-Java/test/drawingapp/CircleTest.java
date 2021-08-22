package drawingapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class CircleTest {
    private Figure circle1, circle2;
    private Point point1, point2;

    @BeforeEach
    public void setUpClass() {
        point1 = new Point(100, 150);
        Color color1 = Color.red;
        circle1 = new Circle(point1, Constants.defaultDimension, color1);

        point2 = new Point(200, 30);
        Dimension dimension2 = new Dimension(80, 80);
        Color color2 = Color.green;
        circle2 = new Circle(point2, dimension2, color2);
    }

    @DisplayName("Test enlarge the width/height of bounding rectangle by 1.5 times")
    @Test
    void testEnlarge() {
        circle1.enlarge();
        assertEquals(75, circle1.getWidth(), "test enlarge cross 1");
        assertEquals(75, circle1.getHeight(), "test enlarge cross 1");

        circle2.enlarge();
        assertEquals(120, circle2.getWidth(), "test enlarge cross 2");
        assertEquals(120, circle2.getHeight(), "test enlarge cross 2");
    }

    @DisplayName("Test shrink the width/height of bounding rectangle by 10%")
    @Test
    void testShrink() {
        circle1.shrink();
        assertEquals(45, circle1.getWidth(), "test shrink cross 1");
        assertEquals(45, circle1.getHeight(), "test shrink cross 1");

        circle2.shrink();
        assertEquals(72, circle2.getWidth(), "test shrink cross 2");
        assertEquals(72, circle2.getHeight(), "test shrink cross 2");
    }

    @DisplayName("Test move item accordingly to mouse point variance")
    @Test
    void testMoveShape(){
        circle1.startMoving();
        circle1.moveShape(220, 10);
        assertEquals(320, circle1.getLocation().x);
        assertEquals(160, circle1.getLocation().y);

        circle2.startMoving();
        circle2.moveShape(99, 100);
        assertEquals(299, circle2.getLocation().x);
        assertEquals(130, circle2.getLocation().y);
    }

    @DisplayName("Test terminate moving, circle comes back its original location")
    @Test
    void testTerminateMove(){
        circle1.startMoving();
        circle1.moveShape(220, 10);
        circle1.terminateMove();
        assertEquals(point1, circle1.getLocation());

        circle2.startMoving();
        circle2.moveShape(99, 100);
        circle2.terminateMove();
        assertEquals(point2, circle2.getLocation());
    }
}