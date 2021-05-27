package drawingapp;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class DrawingFrame extends JFrame implements Constants {

    final DrawingPanel drawingPanel = new DrawingPanel(this);
    JPanel controlPanel, infoColorPanel, masterPanel;
    JComboBox figureType, figureAction;
    JLabel currentAction;
    GridLayout defaultGridSetting;
    JCheckBox redCheckbox, greenCheckbox, blueCheckbox, blackCheckbox;
    ItemHandler itemHandler = new ItemHandler(this, drawingPanel);

    public DrawingFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getLookAndFeelDefaults().put("TitledBorder.font", new Font("Default", Font.BOLD, 12));
            UIManager.getLookAndFeelDefaults().put("ComboBox.font", new Font("Default", Font.BOLD, 12));
        } catch (Exception e){
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Drawing Application");
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMasterPanel();
        frame.getContentPane().add(masterPanel, BorderLayout.NORTH);
        frame.getContentPane().add(drawingPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true); // should be last line of the MyFrame constructor
    }

    void setMasterPanel(){
        masterPanel = new JPanel();
        setDefaultGridSetting();
        setControlPanel();
        setInfoColorPanel();
        masterPanel.setLayout(new GridLayout(2,1));
        masterPanel.add(controlPanel);
        masterPanel.add(infoColorPanel);
    }

    void setControlPanel(){
        // figure selection
        String[] types = {"Circle", "Square", "Cross", "Line"};
        figureType = new JComboBox(types);
        figureType.setBorder(BorderFactory.createTitledBorder("Figure Types"));
        figureType.setForeground(Color.white);
        figureType.setOpaque(false);
        figureType.getEditor().getEditorComponent().setBackground(Color.YELLOW);
        figureType.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void setBackground(Color color){
                super.setBackground(Color.blue.darker());
            }

            @Override
            public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    c.setForeground(Color.yellow);
                }
                return c;
            }
        });

        figureType.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                return new BasicArrowButton(
                        BasicArrowButton.SOUTH,
                        Color.blue.darker(), null,
                        Color.black, Color.blue.darker());
            }
        });
        figureType.addItemListener(itemHandler);

        // figure action
//        String[] actions = {"None", "Delete", "Enlarge", "Shrink", "Move"};
        // Disable actions other than None because no shape to be selected
        // Other actions are added when there is shape in the page
        figureAction = new JComboBox(new String[] {"None"});
        figureAction.setOpaque(false);
        figureAction.setBorder(BorderFactory.createTitledBorder("Figure Actions"));
        figureAction.addItemListener(itemHandler);

        controlPanel = new JPanel();
        controlPanel.setLayout(defaultGridSetting);
        controlPanel.add(figureType);
        controlPanel.add(figureAction);
    }

    void setInfoColorPanel(){
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

        // red color
        redCheckbox = new JCheckBox("", true);
        redCheckbox.setBackground(Color.red);
        redCheckbox.setForeground(Color.white);
        redCheckbox.addActionListener(itemHandler);
        // green color
        greenCheckbox = new JCheckBox();
        greenCheckbox.setBackground(Color.green);
        greenCheckbox.setForeground(Color.white);
        greenCheckbox.addActionListener(itemHandler);
        // blue color
        blueCheckbox = new JCheckBox();
        blueCheckbox.setBackground(Color.blue);
        blueCheckbox.setForeground(Color.white);
        blueCheckbox.addActionListener(itemHandler);
        // black color
        blackCheckbox = new JCheckBox();
        blackCheckbox.setBackground(Color.black);
        blackCheckbox.setForeground(Color.white);
        blackCheckbox.addActionListener(itemHandler);

        colorPanel.add(redCheckbox);
        colorPanel.add(greenCheckbox);
        colorPanel.add(blueCheckbox);
        colorPanel.add(blackCheckbox);

        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(redCheckbox);
        colorGroup.add(greenCheckbox);
        colorGroup.add(blueCheckbox);
        colorGroup.add(blackCheckbox);

        infoColorPanel = new JPanel();
        infoColorPanel.setLayout(defaultGridSetting);
        infoColorPanel.add(currentAction);
        infoColorPanel.add(colorPanel);
    }

    void setDefaultGridSetting(){
        defaultGridSetting = new GridLayout(1,1);
        defaultGridSetting.setVgap(VERTICAL_GAP);
        defaultGridSetting.setHgap(HORIZONTAL_GAP);
    }

    public String getSelectedFigureType(){
        return (String) figureType.getSelectedItem();
    }

    public String getSelectedFigureAction(){ return (String) figureAction.getSelectedItem(); }

    public void setCurrentAction(){
        currentAction.setText(" "+ figureType.getSelectedItem());
    }
    public void setCurrentAction(Figure item){
        if (item instanceof Circle) currentAction.setText(" "+ figureType.getItemAt(0));
        if (item instanceof Rect) currentAction.setText(" "+ figureType.getItemAt(1));
        if (item instanceof Cross) currentAction.setText(" "+ figureType.getItemAt(2));
        if (item instanceof Line) currentAction.setText(" "+ figureType.getItemAt(3));
    }

    public void setFigureActionNone(){ figureAction.setSelectedIndex(0); }

    public void enableFigureActions(boolean enable){
        String[] actions = {"Delete", "Enlarge", "Shrink", "Move"};

        if (enable) { // Add other actions to FigureAction ComboBox
            for (String action : actions){
                figureAction.addItem(action);
            }
        }
        else { // Remove actions (when no shape on the drawing panel)
            int size = figureAction.getItemCount();
            while (size>1){
                figureAction.removeItemAt(size-1);
                size--;
            }
        }
    }
}
