package drawingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ItemHandler implements ActionListener, ItemListener {
    private final DrawingFrame frame;
    private final DrawingPanel panel;

    public ItemHandler(DrawingFrame drawFrame, DrawingPanel drawPanel){
        frame = drawFrame;
        panel = drawPanel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Red color checkbox clicked -> change color used in DrawingPanel to Red
        if (frame.redCheckbox.isSelected()){
            System.out.println("Color changed to Red");
            panel.setShapeColor(Color.red);
        }

        // Green color checkbox clicked -> change color used in DrawingPanel to Green
        if (frame.greenCheckbox.isSelected()){
            System.out.println("Color changed to Green");
            panel.setShapeColor(Color.green);
        }

        // Blue color checkbox clicked -> change color used in DrawingPanel to Blue
        if (frame.blueCheckbox.isSelected()){
            System.out.println("Color changed to Blue");
            panel.setShapeColor(Color.blue);
        }

        // Black color checkbox clicked -> change color used in DrawingPanel to Black
        if (frame.blackCheckbox.isSelected()){
            System.out.println("Color changed to Black");
            panel.setShapeColor(Color.black);
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
        Object source = e.getSource();

        // Change Current Action label accordingly to selected figure type in JComboBox
        if (source == frame.figureType) {
            if(e.getStateChange() == ItemEvent.SELECTED){
                System.out.println("Choice changed to "+ frame.getSelectedFigureType());
                frame.setCurrentAction();

                // terminate drawing temporary lines if another figure type selected
                if (!frame.getSelectedFigureType().equals("Line") && panel.drawLine){
                    panel.terminateDrawingLineAndMove();
                }
            }

        }

        // Change current action label accordingly to selected figure type in JComboBox
        if (source == frame.figureAction){
            if(e.getStateChange() == ItemEvent.SELECTED){
                System.out.println("Action changed to "+ frame.getSelectedFigureAction());

                if (!frame.getSelectedFigureType().equals("Move") && (panel.moveItem || panel.drawLine)){
                    panel.terminateDrawingLineAndMove();
                }
            }
        }

    }

}
