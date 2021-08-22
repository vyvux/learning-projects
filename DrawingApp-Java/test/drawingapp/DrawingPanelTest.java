package drawingapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DrawingPanelTest {
    private DrawingFrame frame;
    private DrawingPanel drawingPanel;
    private ControlPanel controlPanel;
    private Figure shape1, shape2, shape3, shape4, shape5;

    @BeforeEach
    public void setUpClass() {
        frame = new DrawingFrame();
        drawingPanel = frame.getDrawingPanel();
        controlPanel = frame.getControlPanel();
        shape1 = new Line(new Point(10, 40), Color.red, new Point(30, 20));
        shape2 = new Circle(new Point(10, 20), Constants.defaultDimension, Color.red);
        shape3 = new Rect(new Point(10, 20), Constants.defaultDimension, Color.red);
        shape4 = new Cross(new Point(10, 20), Constants.defaultDimension, Color.red);
        shape5 = new Line(new Point(10, 20), Color.red, new Point(30, 40));
        drawingPanel.add(shape1);
        drawingPanel.add(shape2);
        drawingPanel.add(shape3);
        drawingPanel.add(shape4);
        drawingPanel.add(shape5);
    }


    static Stream<Arguments> colorSelection() {
        return Stream.of(
                arguments(0, Color.red),
                arguments(1, Color.green),
                arguments(2, Color.blue),
                arguments(3, Color.black)
        );
    }

    @DisplayName("Test set drawing color")
    @ParameterizedTest
    @MethodSource("colorSelection")
    void testSetShapeColor(int index, Color color) {
        drawingPanel.setColor(controlPanel.getColors()[index]);
        assertEquals(color, drawingPanel.getColor());
    }

    @DisplayName("Test find figure function of Enlarge and Shrink")
    @Test
    public void testFindFigureIgnoreLine() {
        Point point1 = new Point(20, 20); // point inside all shapes
        assertEquals(shape2, drawingPanel.findFigureIgnoreLine(true, point1), "check affected item of Enlarge action");
        assertEquals(shape4, drawingPanel.findFigureIgnoreLine(false, point1), "check affected item of Shrink action");

        Point point2 = new Point(40, 40); // point inside shapes other than lines
        assertEquals(shape2, drawingPanel.findFigureIgnoreLine(true, point2), "check affected item of Enlarge action");
        assertEquals(shape4, drawingPanel.findFigureIgnoreLine(false, point2), "check affected item of Shrink action");

        Point point3 = new Point(10, 10); // point outside all shapes
        assertNull(drawingPanel.findFigureIgnoreLine(true, point3), "check affected item of Enlarge action");
        assertNull(drawingPanel.findFigureIgnoreLine(false, point3), "check affected item of Shrink action");

    }

    @DisplayName("Test find figure function of Delete and Move")
    @Test
    public void testFindLeastRecentFigureConsiderLine() {
        Point point1 = new Point(25, 25); // point inside all shapes
        assertEquals(shape1, drawingPanel.findLeastRecentFigureConsiderLine(point1), "check affected item of Delete and Move action");

        Point point2 = new Point(40, 40); // point inside shapes other than lines
        assertEquals(shape2, drawingPanel.findLeastRecentFigureConsiderLine(point2), "check affected item of Delete and Move action");

        Point point3 = new Point(10, 10); // point outside all shapes
        assertNull(drawingPanel.findLeastRecentFigureConsiderLine(point3), "check affected item of Delete and Move action");

    }


    @DisplayName("Test draw lines that are parallel with x axe")
    @Test
    public void testLineParallelXAxe() {
        Point start = new Point(20, 100);
        Point end = new Point(80, 100);
        Figure line1 = new Line(start, Color.red, end);
        drawingPanel.add(line1);
        assertEquals(line1, drawingPanel.findLeastRecentFigureConsiderLine(new Point(50, 100)), "tested point belongs to the line");
    }

    @DisplayName("Test draw lines that are parallel with y axe")
    @Test
    public void testLineParallelYAxe() {
        Point start = new Point(100, 20);
        Point end = new Point(100, 80);
        Figure line1 = new Line(start, Color.red, end);
        drawingPanel.add(line1);
        assertEquals(line1, drawingPanel.findLeastRecentFigureConsiderLine(new Point(100, 50)), "tested point belongs to the line");
    }


}