package youtubetrender.GUI;

import javax.swing.*;
import java.awt.*;

public class FilePanel extends JPanel{
    public FilePanel(){
        JComboBox files = new JComboBox();
        files.addItem("youtubedata.json");
        files.addItem("youtubedata_15_50.json");
        files.addItem("youtubedata_malformed.json");
        files.addItem("youtudedata_indextest.json");
        files.addItem("youtubedata_loremipsum.json");
        files.setPreferredSize(new Dimension(350,20));
        add(files);

        JButton load = new JButton("Load");
        JButton index = new JButton("Index");
        add(load);
        add(index);
    }
}
