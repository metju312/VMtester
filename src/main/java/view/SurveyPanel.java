package view;

import net.miginfocom.swing.MigLayout;
import view.util.TitledBorderPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyPanel extends JPanel {

    public List<ExperimentPanel> experimentPanelList = new ArrayList<ExperimentPanel>();

    public SurveyPanel() {
        setLayout(new MigLayout());
        refreshPanel();
    }

    private void refreshPanel() {
        setExperimentPanelList();
        setAddNewExperimentPanel();
    }

    private void setAddNewExperimentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        JButton newExperimentButton = new JButton("Dodaj eksperyment");
        JButton importExperimentButton = new JButton("Importuj eksperyment");
        panel.add(newExperimentButton, "wrap");
        panel.add(importExperimentButton);
        add(panel);
    }

    private void setExperimentPanelList() {
        //TODO zrobić default 1 experyment z możliwością dodawania kolejnych
        for (int i = 0; i < 10; i++) {
            experimentPanelList.add(new ExperimentPanel("Eksperyment " + (i+1)));
        }

        for (ExperimentPanel experimentPanel : experimentPanelList) {
            add(experimentPanel);
        }
    }
}
