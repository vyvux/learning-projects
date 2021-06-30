package drawingapp;

import javax.swing.*;

/**
 * The class to assign listener to control panel components
 */
public class ListenerAssignment {

    /**
     * Constructor: Sets listener for components
     *
     * @param controlPanel the control panel
     * @param drawingPanel the drawing panel
     */
    public ListenerAssignment(ControlPanel controlPanel, DrawingPanel drawingPanel) {
        ItemHandler itemHandler = new ItemHandler(controlPanel, drawingPanel);

        // assign listener
        controlPanel.getFigureMenus()[0].addActionListener(itemHandler); // FigureType
        controlPanel.getFigureMenus()[1].addActionListener(itemHandler); // FigureAction
        for (JCheckBox color : controlPanel.getColorCheckboxes()) { // 4 color checkboxes
            color.addItemListener(itemHandler);
        }
    }

}
