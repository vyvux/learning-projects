package youtubetrender.GUI;

import javax.swing.*;
import java.awt.*;

public class TrendingSortingPanel extends JPanel {
    public TrendingSortingPanel(){
        JPanel topicPanel = new JPanel();
        JList topics = new JList();
        topicPanel.add(topics);
        topicPanel.setBorder(BorderFactory.createTitledBorder("Trending"));
        topicPanel.setPreferredSize(new Dimension(150, 250));
        add(topicPanel);

        // Sorting Panel
        JPanel sort = new JPanel();
        sort.setPreferredSize(new Dimension(320, 50));
        JRadioButton channel = new JRadioButton("Channel");
        JRadioButton date = new JRadioButton("Date");
        JRadioButton view = new JRadioButton("View");
        JRadioButton description = new JRadioButton("Description");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(channel);
        buttonGroup.add(date);
        buttonGroup.add(view);
        buttonGroup.add(description);

        sort.add(channel);
        sort.add(date);
        sort.add(view);
        sort.add(description);
        sort.setBorder(BorderFactory.createTitledBorder("Sorting"));

        // Videos Panel
        JPanel videoPanel = new JPanel();
        videoPanel.setPreferredSize(new Dimension(320, 200));
        JList videos = new JList();
        videoPanel.add(videos);
        videoPanel.setBorder(BorderFactory.createTitledBorder("Videos"));

        // Sort and Video combined Panel
        JPanel combined = new JPanel();
        combined.setLayout(new BorderLayout());
        combined.add(sort, BorderLayout.NORTH);
        combined.add(videoPanel, BorderLayout.SOUTH);
        add(combined);

    }
}
