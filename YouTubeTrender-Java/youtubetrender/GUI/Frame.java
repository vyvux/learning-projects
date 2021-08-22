package youtubetrender.GUI;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private final int WIDTH = 500;
    private final int HEIGHT = 600;
    public Frame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("YouTube Trender App");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        FilePanel filePanel = new FilePanel();
        getContentPane().add(filePanel, BorderLayout.NORTH);

        TrendingSortingPanel trendingSortingPanel = new TrendingSortingPanel();
        getContentPane().add(trendingSortingPanel, BorderLayout.CENTER);

        DetailsPanel detailsPanel = new DetailsPanel();
        getContentPane().add(detailsPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
        setResizable(false);

    }
}
