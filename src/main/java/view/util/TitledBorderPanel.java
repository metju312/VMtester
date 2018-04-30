package view.util;

import javax.swing.*;

public class TitledBorderPanel extends JPanel {
    public TitledBorderPanel(String name) {
        setBorder(BorderFactory.createTitledBorder(name));
    }
}
