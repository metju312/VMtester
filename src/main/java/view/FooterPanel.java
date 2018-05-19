package view;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {
    public FooterPanel() {
        JPanel panel = new JPanel();
        panel.add(new Label("2018 I6B3S4 Mateusz Sadło - Praca magisterska"), BorderLayout.EAST);
        panel.add(new Label("v. 1.0.2"), BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
    }
}
