package drawingapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class CrossTest {
    private Figure cross1, cross2;
    private Point point1, point2;

    @BeforeEach
    public void setUpClass() {
        point1 = new Point(100, 150);
        Color color1 = Color.red;
        cross1 = new Cross(point1, Constants.defaultDimension, color1);

        point2 = new Point(200, 30);
        Dimension dimension2 = new Dimension(80, 80);
        Color color2 = Color.green;
        cross2 = new Cross(point2, dimension2, color2);
    }

    @DisplayName("Test enlarge the width/height of bounding rectangle by 1.5 times")
    @Test
    void testEnlarge() {
        cross1.enlarge();
        assertEquals(75, cross1.getWidth(), "test enlarge cross 1");
        assertEquals(75, cross1.getHeight(), "test enlarge cross 1");

        cross2.enlarge();
        assertEquals(120, cross2.getWidth(), "test enlarge cross 2");
        assertEquals(120, cross2.getHeight(), "test enlarge cross 2");
    }

    @DisplayName("Test shrink the width/height of bounding rectangle by 10%")
    @Test
    void testShrink() {
        cross1.shrink();
        assertEquals(45, cross1.getWidth(), "test shrink cross 1");
        assertEquals(45, cross1.getHeight(), "test shrink cross 1");

        cross2.shrink();
        assertEquals(72, cross2.getWidth(), "test shrink cross 2");
        assertEquals(72, cross2.getHeight(), "test shrink cross 2");
    }

    @DisplayName("Test move cross accordingly to mouse point variance")
    @Test
    void testMoveShape(){
        cross1.startMoving();
        cross1.moveShape(220, 10);
        assertEquals(320, cross1.getLocation().x);
        assertEquals(160, cross1.getLocation().y);

        cross2.startMoving();
        cross2.moveShape(99, 100);
        assertEquals(299, cross2.getLocation().x);
        assertEquals(130, cross2.getLocation().y);
    }

    @DisplayName("Test terminate moving, cross comes back its original location")
    @Test
    void testTerminateMove(){
        cross1.startMoving();
        cross1.moveShape(220, 10);
        cross1.terminateMove();
        assertEquals(point1, cross1.getLocation());

        cross2.startMoving();
        cross2.moveShape(99, 100);
        cross2.terminateMove();
        assertEquals(point2, cross2.getLocation());
    }
}