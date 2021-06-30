package youtubetrender.GUI;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    public DetailsPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 270));
        setBorder(BorderFactory.createTitledBorder("Video Details"));

        JPanel channelPanel = new JPanel();
        JLabel channel = new JLabel("Channel");
        channel.setPreferredSize(new Dimension(60, 20));
        JLabel channelInfo = new JLabel();
        channelInfo.setPreferredSize(new Dimension(380, 20));
        channelInfo.setBorder(BorderFactory.createEtchedBorder());
        channelPanel.add(channel);
        channelPanel.add(channelInfo);

        JPanel datePanel = new JPanel();
        JLabel date = new JLabel("Date Posted");
        date.setPreferredSize(new Dimension(60, 20));
        JLabel dateInfo = new JLabel();
        dateInfo.setPreferredSize(new Dimension(380, 20));
        dateInfo.setBorder(BorderFactory.createEtchedBorder());
        datePanel.add(date);
        datePanel.add(dateInfo);

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Title");
        title.setPreferredSize(new Dimension(60, 20));
        JLabel titleInfo = new JLabel();
        titleInfo.setPreferredSize(new Dimension(380, 20));
        titleInfo.setBorder(BorderFactory.createEtchedBorder());
        titlePanel.add(title);
        titlePanel.add(titleInfo);

        JPanel viewPanel = new JPanel();
        JLabel view = new JLabel("View Count");
        view.setPreferredSize(new Dimension(60, 20));
        JLabel viewInfo = new JLabel();
        viewInfo.setPreferredSize(new Dimension(380, 20));
        viewInfo.setBorder(BorderFactory.createEtchedBorder());
        viewPanel.add(view);
        viewPanel.add(viewInfo);

        JPanel descPanel = new JPanel();
        JLabel description = new JLabel("Description");
        description.setPreferredSize(new Dimension(60, 20));
        JLabel descInfo = new JLabel();
        descInfo.setPreferredSize(new Dimension(450, 150));
        descInfo.setBorder(BorderFactory.createEtchedBorder());
        descPanel.add(description);
        descPanel.add(descInfo);

        add(channelPanel);
        add(datePanel);
        add(titlePanel);
        add(viewPanel);
        add(descPanel);

    }
}
