package drawingapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * The class listens to changes of figure choices in control panel
 */
public class ItemHandler implements ActionListener, ItemListener {
    private final ControlPanel controlPanel;
    private final DrawingPanel drawingPanel;

    /**
     * Constructor: Registers control panel and drawing panel
     *
     * @param controlPanel the control panel
     * @param drawPanel    the drawing panel
     */
    public ItemHandler(ControlPanel controlPanel, DrawingPanel drawPanel) {
        this.controlPanel = controlPanel;
        drawingPanel = drawPanel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Change Current Action label accordingly to selected Figure Type
        if (source == controlPanel.getFigureMenus()[0]) {
            System.out.println("Choice changed to " + controlPanel.getFigureMenus()[0].getSelectedItem());
            controlPanel.setCurrentAction();
            // terminate drawing temporary line or Move action
            drawingPanel.terminateDrawingLineAndMoveAction();

        }

        // Notify Figure Action changes
        if (source == controlPanel.getFigureMenus()[1]) {
            System.out.println("Action changed to " + controlPanel.getFigureMenus()[1].getSelectedItem());
            drawingPanel.terminateDrawingLineAndMoveAction(); // terminate the ongoing Move or Draw line
        }

    }

    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     *
     * @param e the event to be processed
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        // Change the drawing panel color based on the selected color checkboxes
        for (int i = 0; i < controlPanel.getColorCheckboxes().length; i++) {
            if (controlPanel.getColorCheckboxes()[i].isSelected()) {
                drawingPanel.setColor(controlPanel.getColors()[i]);
            }
        }

    }

}
