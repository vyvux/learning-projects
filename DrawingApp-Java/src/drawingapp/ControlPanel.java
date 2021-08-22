package drawingapp;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

/**
 * Control panel accepts user settings for the drawing app
 */
public class ControlPanel extends JPanel implements Constants {
    private JPanel figurePanel; // the panel holding 2 Figure menus
    private JPanel infoColorPanel; // the panel holding Current Action label and Color checkboxes
    private JComboBox[] figureMenus = new JComboBox[2]; // FigureType and FigureAction
    private JLabel currentAction;
    private GridLayout defaultGridSetting;
    private JCheckBox[] colorCheckboxes = new JCheckBox[4];
    private Color[] colors = new Color[4];

    /**
     * Constructor: Sets up control panel
     */
    public ControlPanel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getLookAndFeelDefaults().put("TitledBorder.font", new Font("Default", Font.BOLD, 12));
            UIManager.getLookAndFeelDefaults().put("ComboBox.font", new Font("Default", Font.BOLD, 12));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultGridSetting();
        setUpFigurePanel();
        setUpInfoColorPanel();
        setLayout(new GridLayout(2, 1));
        add(figurePanel);
        add(infoColorPanel);
    }

    /**
     * Sets up grid setting
     */
    void setDefaultGridSetting() {
        defaultGridSetting = new GridLayout(1, 1);
        defaultGridSetting.setVgap(VERTICAL_GAP);
        defaultGridSetting.setHgap(HORIZONTAL_GAP);
    }

    /**
     * Sets up 2 JComboBoxes: Figure Type and Figure Action
     */
    void setUpFigurePanel() {
        // Figure Type Menu
        String[] types = {"Circle", "Square", "Cross", "Line"};
        figureMenus[0] = new JComboBox(types);
        figureMenus[0].setBorder(BorderFactory.createTitledBorder("Figure Types"));
        figureMenus[0].setForeground(Color.white);
        figureMenus[0].getEditor().getEditorComponent().setBackground(Color.yellow);
        figureMenus[0].setRenderer(new DefaultListCellRenderer() {
            @Override
            public void setBackground(Color color) {
                super.setBackground(Color.blue.darker());
            }

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    c.setForeground(Color.yellow);
                }
                return c;
            }
        });

        figureMenus[0].setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                return new BasicArrowButton(
                        BasicArrowButton.SOUTH,
                        Color.blue.darker(), null,
                        Color.black, Color.blue.darker());
            }
        });

        // Figure Action Menu
        String[] actions = {"None", "Delete", "Enlarge", "Shrink", "Move"};
        figureMenus[1] = new JComboBox(actions);
        figureMenus[1].setOpaque(false);
        figureMenus[1].setBorder(BorderFactory.createTitledBorder("Figure Actions"));

        figurePanel = new JPanel();
        figurePanel.setLayout(defaultGridSetting);
        figurePanel.add(figureMenus[0]);
        figurePanel.add(figureMenus[1]);
    }

    /**
     * Sets up label and color checkboxes
     */
    void setUpInfoColorPanel() {
        // current action label
        currentAction = new JLabel("Circle");
        currentAction.setBorder(BorderFactory.createTitledBorder("Current Action"));
        String font = currentAction.getFont().getName();
        currentAction.setFont(new Font(font, Font.BOLD, 18));

        // color selection Panel
        JPanel colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createTitledBorder("Colors"));
        GridLayout colorLayout = new GridLayout();
        colorLayout.setHgap(HORIZONTAL_GAP);
        colorLayout.setVgap(VERTICAL_GAP);
        colorPanel.setLayout(colorLayout);

        colors = new Color[]{Color.red, Color.green, Color.blue, Color.black};
        ButtonGroup colorGroup = new ButtonGroup();

        // set up color checkboxes
        for (int i = 0; i < colorCheckboxes.length; i++) {
            if (i == 0) { // default selected color is Red
                colorCheckboxes[i] = new JCheckBox("", true);
            } else {
                colorCheckboxes[i] = new JCheckBox();
            }
            colorCheckboxes[i].setBackground(colors[i]);
            colorCheckboxes[i].setForeground(Color.white);
            colorPanel.add(colorCheckboxes[i]); // add checkboxes to the panel
            colorGroup.add(colorCheckboxes[i]); // add checkboxes to button group for only 1 selection at a time
        }

        // create the 2nd row of the control panel
        infoColorPanel = new JPanel();
        infoColorPanel.setLayout(defaultGridSetting);
        infoColorPanel.add(currentAction); // add current action label
        infoColorPanel.add(colorPanel); // add color selection panel
    }

    /**
     * @return the array of color checkboxes
     */
    public JCheckBox[] getColorCheckboxes() {
        return colorCheckboxes;
    }

    /**
     * @return the array of figure menus
     */
    public JComboBox[] getFigureMenus() {
        return figureMenus;
    }

    /**
     * @return the array of registered colors
     */
    public Color[] getColors() {
        return colors;
    }

    /**
     * @return the text on current action label (for testing)
     */
    public JLabel getCurrentAction() {
        return currentAction;
    }

    /**
     * Sets text of current action label accordingly to selected figure type
     */
    public void setCurrentAction() {
        currentAction.setText(" " + figureMenus[0].getSelectedItem());
    }

    /**
     * Sets text of current action label accordingly to the figure type of affected item
     *
     * @param item the Figure object
     */
    public void setCurrentAction(Figure item) {
        if (item instanceof Circle) currentAction.setText(" " + figureMenus[0].getItemAt(0));
        if (item instanceof Rect) currentAction.setText(" " + figureMenus[0].getItemAt(1));
        if (item instanceof Cross) currentAction.setText(" " + figureMenus[0].getItemAt(2));
        if (item instanceof Line) currentAction.setText(" " + figureMenus[0].getItemAt(3));
    }

    /**
     * Sets figure action selection to NONE
     */
    public void setFigureActionNone() {
        figureMenus[1].setSelectedIndex(0);
    }
}
