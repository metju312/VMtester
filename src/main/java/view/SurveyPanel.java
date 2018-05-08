package view;

import controller.Experiment;
import controller.util.FileUtils;
import net.miginfocom.swing.MigLayout;
import view.util.TitledBorderPanel;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SurveyPanel extends JPanel {

    public List<ExperimentPanel> experimentPanelList = new ArrayList<ExperimentPanel>();
    private MainWindow mainWindow;

    public SurveyPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
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
        add(panel);
    }

    private void setExperimentPanelList() {
        //TODO zrobić default 1 experyment z możliwością dodawania kolejnych
        for (int i = 0; i < 3; i++) {
            experimentPanelList.add(new ExperimentPanel("Eksperyment " + (i+1), mainWindow));
        }

        for (ExperimentPanel experimentPanel : experimentPanelList) {
            add(experimentPanel);
        }
    }

    public void importExperiment(File file){
        Experiment experiment = FileUtils.importExperimentFromFile(file);
        ExperimentPanel experimentPanel = addExperiment();
        experimentPanel.importExperiment(experiment);
    }

    public ExperimentPanel addExperiment(){
        ExperimentPanel experimentPanel = new ExperimentPanel("Eksperyment " + (experimentPanelList.size()+1), mainWindow);
        experimentPanelList.add(experimentPanel);
        add(experimentPanel);
        return experimentPanel;
    }
}
