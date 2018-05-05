package view;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {
    public FooterPanel() {
        add(new Label("I6B3S4 Mateusz Sadło - Praca magisterska"), BorderLayout.WEST);
        add(new Label("v. 1.0.2"), BorderLayout.EAST);
    }
}
