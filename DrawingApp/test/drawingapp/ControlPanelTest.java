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

class ControlPanelTest {
    private DrawingFrame frame = new DrawingFrame();
    private ControlPanel controlPanel;
    private DrawingPanel panel;

    @BeforeEach
    public void setUpClass() {
        controlPanel = frame.getControlPanel();
        panel = frame.getDrawingPanel();
    }

    static Stream<Arguments> figureTypesSelection() {
        return Stream.of(
                arguments(0, " Circle"),
                arguments(1, " Square"),
                arguments(2, " Cross"),
                arguments(3, " Line")
        );
    }

    static Stream<Arguments> figureActionHoveredItem() {
        Figure shape1 = new Circle(new Point(10,20), new Dimension(50,50), Color.red);
        Figure shape2 = new Rect(new Point(10, 20), new Dimension(50, 50), Color.red);
        Figure shape3 = new Cross(new Point(10, 20), new Dimension(50, 50), Color.red);
        Figure shape4 = new Line(new Point(10, 20), Color.red, new Point(30, 40));
        return Stream.of(
                arguments(shape1, " Circle"),
                arguments(shape2, " Square"),
                arguments(shape3, " Cross"),
                arguments(shape4, " Line")
        );
    }

    static Stream<Arguments> colorSelection() {
        return Stream.of(
                arguments(0, Color.red),
                arguments(1, Color.green),
                arguments(2, Color.blue),
                arguments(3, Color.black)
        );
    }


    @DisplayName("Test display of Figure Type ComboBox selection on Current Action label")
    @ParameterizedTest
    @MethodSource("figureTypesSelection")
    void testSelectedFigureTypeDisplay(int index, String type) {
        controlPanel.getFigureMenus()[0].setSelectedIndex(index);
        controlPanel.setCurrentAction();
        assertEquals(type, controlPanel.getCurrentAction().getText(), "Test current action display");
    }

    @DisplayName("Test display of hovered item's Figure Type on Current Action label ")
    @ParameterizedTest
    @MethodSource("figureActionHoveredItem")
    void testHoveredItemTypeDisplay(Figure shape, String type) {
        controlPanel.setCurrentAction(shape);
        assertEquals(type, controlPanel.getCurrentAction().getText(), "Test current action display");
    }

    @DisplayName("Test set figure action to None")
    @Test
    void testSetFigureActionNone() {
        // make sure selected item is different from NONE -> set figure action to Delete
        controlPanel.getFigureMenus()[1].setSelectedIndex(1);
        assertEquals(1, controlPanel.getFigureMenus()[1].getSelectedIndex(), "set to Delete - index 1");
        // set to None
        controlPanel.setFigureActionNone();
        assertEquals(0, controlPanel.getFigureMenus()[1].getSelectedIndex(), "test set to None - index 0");
    }

    @DisplayName("Test set color by selecting color buttons")
    @ParameterizedTest
    @MethodSource("colorSelection")
    void testChangeColorBySelectingColorButton(int index, Color color) {
        controlPanel.getColorCheckboxes()[index].setSelected(true);
        assertEquals(color, panel.getColor());
    }

}