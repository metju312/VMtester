package view;

import net.miginfocom.swing.MigLayout;
import view.util.TitledBorderPanel;

import javax.swing.*;
import java.awt.*;

public class ExperimentPanel extends TitledBorderPanel {

    private TitledBorderPanel settingsPanel = new TitledBorderPanel("Ustawienia");
    private TitledBorderPanel resultsPanel = new TitledBorderPanel("Wyniki");

    public ExperimentPanel(String name) {
        super(name);
        setLayout(new BorderLayout());
        refreshPanel();
    }

    private void refreshPanel() {
        setSettingsPanel();
        setResultsPanel();
    }

    private void setSettingsPanel() {
        settingsPanel.setLayout(new MigLayout());
        settingsPanel.add(new JLabel("Ustawienia"));
        add(settingsPanel, BorderLayout.NORTH);
    }

    private void setResultsPanel() {
        resultsPanel.setLayout(new MigLayout());
        resultsPanel.add(new JLabel("Srodowisko przed badaniem"), "span");
        resultsPanel.add(new JLabel("Pamięć:"));
        resultsPanel.add(new JLabel("145 MB"), "wrap");
        resultsPanel.add(new JLabel("Czas odpowiedzi:"));
        resultsPanel.add(new JLabel("0,2 s"), "wrap");
        resultsPanel.add(new JLabel("Srodowisko w trakcie badania"), "span");
        resultsPanel.add(new JLabel("Pamięć:"));
        resultsPanel.add(new JLabel("151 MB"), "wrap");
        resultsPanel.add(new JLabel("Czas odpowiedzi:"));
        resultsPanel.add(new JLabel("0,3 s"), "wrap");
        add(resultsPanel, BorderLayout.SOUTH);
    }
}
