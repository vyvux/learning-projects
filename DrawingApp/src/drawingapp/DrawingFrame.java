package drawingapp;

import javax.swing.*;
import java.awt.*;

public class DrawingFrame extends JFrame implements Constants {
    private ControlPanel controlPanel;
    private DrawingPanel drawingPanel;

    /**
     * Constructor: Sets up the drawing frame
     */
    public DrawingFrame() {
        JFrame frame = new JFrame("Drawing Application");
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlPanel = new ControlPanel();
        drawingPanel = new DrawingPanel(controlPanel);
        ListenerAssignment assignment = new ListenerAssignment(controlPanel, drawingPanel);
        frame.getContentPane().add(controlPanel, BorderLayout.NORTH);
        frame.getContentPane().add(drawingPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true); // should be last line of the MyFrame constructor
    }

    /**
     * @return the drawing panel
     */
    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    /**
     * @return the control panel
     */
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

}
